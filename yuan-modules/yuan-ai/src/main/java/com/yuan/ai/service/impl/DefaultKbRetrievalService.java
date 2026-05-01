package com.yuan.ai.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.yuan.ai.domain.KbBase;
import com.yuan.ai.domain.KbChunk;
import com.yuan.ai.domain.KbRetrievalHit;
import com.yuan.ai.domain.KbRetrievalLog;
import com.yuan.ai.domain.LlmEndpoint;
import com.yuan.ai.domain.LlmModel;
import com.yuan.ai.domain.dto.KbRetrievalHitDto;
import com.yuan.ai.domain.dto.KbRetrievalRequest;
import com.yuan.ai.domain.dto.KbRetrievalResponse;
import com.yuan.ai.kb.embedding.KbEmbeddingClient;
import com.yuan.ai.kb.vector.KbVectorSearchHit;
import com.yuan.ai.kb.vector.KbVectorStore;
import com.yuan.ai.mapper.KbBaseMapper;
import com.yuan.ai.mapper.KbChunkMapper;
import com.yuan.ai.mapper.KbRetrievalHitMapper;
import com.yuan.ai.mapper.KbRetrievalLogMapper;
import com.yuan.ai.mapper.LlmEndpointMapper;
import com.yuan.ai.service.KbRetrievalService;
import com.yuan.ai.service.LlmModelService;
import com.yuan.common.core.exception.ServiceException;
import com.yuan.common.core.utils.StringUtils;
import com.yuan.common.satoken.utils.LoginHelper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@Slf4j
@Service
@RequiredArgsConstructor
public class DefaultKbRetrievalService implements KbRetrievalService {

    private static final String DEL_NOT_DELETED = "0";
    private static final int DEFAULT_TOP_K = 5;
    private static final BigDecimal DEFAULT_MIN_SCORE = new BigDecimal("0.2");

    private final KbBaseMapper kbBaseMapper;
    private final KbChunkMapper chunkMapper;
    private final KbRetrievalLogMapper logMapper;
    private final KbRetrievalHitMapper hitMapper;
    private final LlmModelService modelService;
    private final LlmEndpointMapper endpointMapper;
    private final KbEmbeddingClient embeddingClient;
    private final KbVectorStore vectorStore;

    @Override
    public KbRetrievalResponse retrieve(KbRetrievalRequest request) {
        long start = System.currentTimeMillis();
        String tenantId = StringUtils.defaultIfBlank(request.getTenantId(), LoginHelper.getTenantId());
        List<Long> kbIds = normalizeKbIds(request);
        int topK = request.getTopK() == null || request.getTopK() <= 0 ? DEFAULT_TOP_K : request.getTopK();
        BigDecimal minScore = request.getMinScore() == null ? DEFAULT_MIN_SCORE : request.getMinScore();

        KbRetrievalLog retrievalLog = newLog(request, tenantId, kbIds, topK, minScore);
        logMapper.insert(retrievalLog);
        try {
            Long embeddingModelId = resolveEmbeddingModelId(request.getEmbeddingModelId(), kbIds);
            List<KbRetrievalHitDto> hits = vectorSearch(tenantId, kbIds, request.getQuery(), embeddingModelId, topK, minScore);
            if (hits.isEmpty()) {
                hits = keywordFallback(tenantId, kbIds, request.getQuery(), topK, minScore);
            }
            persistHits(tenantId, retrievalLog.getLogId(), hits);

            retrievalLog.setEmbeddingModelId(embeddingModelId);
            retrievalLog.setHitCount(hits.size());
            retrievalLog.setUsedCount(hits.size());
            retrievalLog.setLatencyMs((int) (System.currentTimeMillis() - start));
            retrievalLog.setStatus("SUCCESS");
            retrievalLog.setUpdateTime(LocalDateTime.now());
            logMapper.updateById(retrievalLog);
            return KbRetrievalResponse.builder()
                    .logId(retrievalLog.getLogId())
                    .hitCount(hits.size())
                    .usedCount(hits.size())
                    .hits(hits)
                    .build();
        } catch (Exception e) {
            log.error("[DefaultKbRetrievalService][retrieve] retrieval failed", e);
            retrievalLog.setLatencyMs((int) (System.currentTimeMillis() - start));
            retrievalLog.setStatus("FAILED");
            retrievalLog.setErrorMessage(truncate(e.getMessage(), 2000));
            retrievalLog.setUpdateTime(LocalDateTime.now());
            logMapper.updateById(retrievalLog);
            throw e;
        }
    }

    @Override
    public String buildPromptContext(KbRetrievalResponse response) {
        if (response == null || response.getHits() == null || response.getHits().isEmpty()) {
            return "";
        }
        StringBuilder builder = new StringBuilder();
        builder.append("Use the following knowledge base context when it is relevant. ")
                .append("If the context is insufficient, answer based on the conversation and state the limitation.\n\n");
        for (KbRetrievalHitDto hit : response.getHits()) {
            builder.append("[KB:")
                    .append(hit.getKbId())
                    .append(" DOC:")
                    .append(hit.getDocId())
                    .append(" CHUNK:")
                    .append(hit.getChunkId())
                    .append(" SCORE:")
                    .append(hit.getScore())
                    .append("]\n")
                    .append(hit.getContent())
                    .append("\n\n");
        }
        return builder.toString().trim();
    }

    private List<KbRetrievalHitDto> vectorSearch(String tenantId, List<Long> kbIds, String query, Long embeddingModelId,
                                                 int topK, BigDecimal minScore) {
        LlmModel model = modelService.getById(embeddingModelId);
        if (model == null) {
            throw new ServiceException("Embedding model not found: " + embeddingModelId);
        }
        LlmEndpoint endpoint = endpointMapper.selectById(model.getEndpointId());
        if (endpoint == null) {
            throw new ServiceException("Embedding endpoint not found: " + model.getEndpointId());
        }
        float[] queryVector = embeddingClient.embed(endpoint, model, query);
        List<KbVectorSearchHit> hits = vectorStore.search(tenantId, kbIds, queryVector, topK, minScore.doubleValue());
        List<KbRetrievalHitDto> result = new ArrayList<>();
        int rank = 1;
        for (KbVectorSearchHit hit : hits) {
            result.add(KbRetrievalHitDto.builder()
                    .kbId(hit.getKbId())
                    .docId(hit.getDocId())
                    .chunkId(hit.getChunkId())
                    .rankNo(rank++)
                    .score(toScore(hit.getScore()))
                    .content(hit.getContent())
                    .contentPreview(truncate(hit.getContent(), 300))
                    .build());
        }
        return result;
    }

    private List<KbRetrievalHitDto> keywordFallback(String tenantId, List<Long> kbIds, String query, int topK, BigDecimal minScore) {
        List<KbChunk> chunks = chunkMapper.selectList(Wrappers.<KbChunk>lambdaQuery()
                .eq(KbChunk::getTenantId, tenantId)
                .in(KbChunk::getKbId, kbIds)
                .eq(KbChunk::getStatus, "ENABLED")
                .last("limit 200"));
        Set<String> terms = new LinkedHashSet<>();
        for (String part : StringUtils.defaultString(query).toLowerCase().split("[\\s,.;:!?()\\[\\]{}\"'`|/\\\\-]+")) {
            if (StringUtils.isNotBlank(part)) {
                terms.add(part);
            }
        }
        List<KbRetrievalHitDto> scored = chunks.stream()
                .map(chunk -> toKeywordHit(chunk, terms))
                .filter(hit -> hit.getScore().compareTo(minScore) >= 0)
                .sorted((a, b) -> b.getScore().compareTo(a.getScore()))
                .limit(topK)
                .toList();
        List<KbRetrievalHitDto> ranked = new ArrayList<>();
        int rank = 1;
        for (KbRetrievalHitDto hit : scored) {
            hit.setRankNo(rank++);
            ranked.add(hit);
        }
        return ranked;
    }

    private KbRetrievalHitDto toKeywordHit(KbChunk chunk, Set<String> terms) {
        String content = StringUtils.defaultString(chunk.getContent()).toLowerCase();
        int matched = 0;
        for (String term : terms) {
            if (content.contains(term)) {
                matched++;
            }
        }
        double score = terms.isEmpty() ? 0D : (double) matched / terms.size();
        return KbRetrievalHitDto.builder()
                .kbId(chunk.getKbId())
                .docId(chunk.getDocId())
                .chunkId(chunk.getChunkId())
                .score(toScore(score))
                .content(chunk.getContent())
                .contentPreview(truncate(chunk.getContent(), 300))
                .build();
    }

    private void persistHits(String tenantId, Long logId, List<KbRetrievalHitDto> hits) {
        for (KbRetrievalHitDto dto : hits) {
            KbRetrievalHit hit = new KbRetrievalHit();
            hit.setTenantId(tenantId);
            hit.setLogId(logId);
            hit.setKbId(dto.getKbId());
            hit.setDocId(dto.getDocId());
            hit.setChunkId(dto.getChunkId());
            hit.setRankNo(dto.getRankNo());
            hit.setScore(dto.getScore());
            hit.setUsedInPrompt(1);
            hit.setContentPreview(dto.getContentPreview());
            hit.setStatus("SUCCESS");
            hit.setCreateBy(String.valueOf(LoginHelper.getUserId()));
            hit.setCreateTime(LocalDateTime.now());
            hit.setUpdateTime(LocalDateTime.now());
            hit.setDelFlag(DEL_NOT_DELETED);
            hitMapper.insert(hit);
        }
    }

    private KbRetrievalLog newLog(KbRetrievalRequest request, String tenantId, List<Long> kbIds, int topK, BigDecimal minScore) {
        LocalDateTime now = LocalDateTime.now();
        KbRetrievalLog log = new KbRetrievalLog();
        log.setTenantId(tenantId);
        log.setKbId(kbIds.size() == 1 ? kbIds.get(0) : null);
        log.setKbIds(StringUtils.join(kbIds, ","));
        log.setSessionId(request.getSessionId());
        log.setConversationId(request.getConversationId());
        log.setMessageId(request.getMessageId());
        log.setInvocationId(request.getInvocationId());
        log.setQueryText(request.getQuery());
        log.setRewriteQuery(request.getQuery());
        log.setTopK(topK);
        log.setMinScore(minScore);
        log.setHitCount(0);
        log.setUsedCount(0);
        log.setStatus("PENDING");
        log.setCreateBy(String.valueOf(LoginHelper.getUserId()));
        log.setCreateTime(now);
        log.setUpdateTime(now);
        log.setDelFlag(DEL_NOT_DELETED);
        return log;
    }

    private List<Long> normalizeKbIds(KbRetrievalRequest request) {
        List<Long> ids = new ArrayList<>();
        if (request.getKbIds() != null) {
            ids.addAll(request.getKbIds().stream().filter(id -> id != null && id > 0).toList());
        }
        if (request.getKbId() != null && request.getKbId() > 0) {
            ids.add(request.getKbId());
        }
        List<Long> distinct = ids.stream().distinct().toList();
        if (distinct.isEmpty()) {
            throw new ServiceException("Knowledge base id is required");
        }
        return distinct;
    }

    private Long resolveEmbeddingModelId(Long requestModelId, List<Long> kbIds) {
        if (requestModelId != null) {
            return requestModelId;
        }
        KbBase kb = kbBaseMapper.selectById(kbIds.get(0));
        if (kb == null || kb.getEmbeddingModelId() == null) {
            throw new ServiceException("Knowledge base embeddingModelId is not configured");
        }
        return kb.getEmbeddingModelId();
    }

    private BigDecimal toScore(double score) {
        return BigDecimal.valueOf(score).setScale(6, RoundingMode.HALF_UP);
    }

    private String truncate(String value, int max) {
        String text = StringUtils.defaultString(value);
        if (text.length() <= max) {
            return text;
        }
        return text.substring(0, max);
    }
}
