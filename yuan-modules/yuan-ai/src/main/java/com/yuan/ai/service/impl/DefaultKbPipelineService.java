package com.yuan.ai.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.yuan.ai.domain.*;
import com.yuan.ai.domain.vo.KbDocumentVo;
import com.yuan.ai.core.kb.chunk.KbTextChunk;
import com.yuan.ai.core.kb.chunk.KbTextChunker;
import com.yuan.ai.core.kb.embedding.EmbeddingInvokerRegistry;
import com.yuan.ai.core.kb.embedding.KbEmbeddingClient;
import com.yuan.ai.core.kb.parser.KbDocumentParser;
import com.yuan.ai.core.kb.parser.KbDocumentParserRegistry;
import com.yuan.ai.core.kb.parser.KbParsedDocument;
import com.yuan.ai.core.kb.vector.KbVectorItem;
import com.yuan.ai.core.kb.vector.KbVectorStore;
import com.yuan.ai.mapper.*;
import com.yuan.ai.mq.KbIndexTaskProducer;
import com.yuan.ai.mq.task.DocumentIndexTask;
import com.yuan.ai.mq.task.EmbeddingTask;
import com.yuan.ai.service.KbPipelineService;
import com.yuan.ai.service.LlmModelService;
import com.yuan.common.core.bizno.BizNoPrefixEnum;
import com.yuan.common.core.exception.ServiceException;
import com.yuan.common.core.utils.StringUtils;
import com.yuan.common.oss.core.client.OssClient;
import com.yuan.common.oss.core.domin.FileObjectKey;
import com.yuan.common.oss.core.domin.OssScope;
import com.yuan.common.oss.enums.NameSpace;
import com.yuan.common.satoken.utils.LoginHelper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.time.LocalDateTime;
import java.util.HexFormat;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class DefaultKbPipelineService implements KbPipelineService {

    private static final String STATUS_ENABLED = "0";
    private static final String DEL_NOT_DELETED = "0";

    private final OssClient ossClient;
    private final KbBaseMapper kbBaseMapper;
    private final KbDocumentMapper documentMapper;
    private final KbDocumentTextMapper documentTextMapper;
    private final KbChunkMapper chunkMapper;
    private final KbEmbeddingMapper embeddingMapper;
    private final LlmModelService modelService;
    private final LlmEndpointMapper endpointMapper;
    private final LlmProviderMapper providerMapper;
    private final KbDocumentParserRegistry parserRegistry;
    private final KbTextChunker chunker;
    private final KbVectorStore vectorStore;
    private final EmbeddingInvokerRegistry invokerRegistry;
    private final KbIndexTaskProducer taskProducer;

    @Override
    @Transactional
    public KbDocumentVo uploadAndIndex(Long kbId, MultipartFile file) {
        if (kbId == null) {
            throw new ServiceException("Knowledge base id is required");
        }
        if (file == null || file.isEmpty()) {
            throw new ServiceException("Please choose a document to upload");
        }
        FileObjectKey key;
        try {
            OssScope scope = OssScope.builder()
                    .tenantId(LoginHelper.getTenantId())
                    .namespace(NameSpace.FORMAL)
                    .prefix(BizNoPrefixEnum.KB)
                    .build();
            key = ossClient.putObject(scope, file);
        } catch (IOException e) {
            throw new ServiceException("Document upload failed").setDetailMessage(e.getMessage());
        }

        KbDocument doc = newDocument(kbId, file.getOriginalFilename(), file.getContentType(), file.getSize(), key);
        documentMapper.insert(doc);
        taskProducer.sendDocumentIndexTask(toDocumentIndexTask(doc, file.getOriginalFilename(), file.getContentType()));
        return documentMapper.selectVoById(doc.getDocId());
    }

    @Override
    @Transactional
    public KbDocumentVo parseAndIndex(Long docId, String fileName, String contentType, byte[] bytes) {
        KbDocument doc = documentMapper.selectById(docId);
        if (doc == null) {
            throw new ServiceException("Document not found: " + docId);
        }
        KbBase kb = kbBaseMapper.selectById(doc.getKbId());
        if (kb == null) {
            throw new ServiceException("Knowledge base not found: " + doc.getKbId());
        }
        parseDocumentAndEnqueueEmbedding(doc, kb, fileName, contentType, bytes);
        return documentMapper.selectVoById(docId);
    }

    private void parseDocumentAndEnqueueEmbedding(KbDocument doc, KbBase kb, String fileName, String contentType, byte[] bytes) {
        markDocument(doc, "PARSING", "PENDING", null);
        try {
            KbDocumentParser parser = parserRegistry.resolve(fileName, contentType);
            KbParsedDocument parsed = parser.parse(fileName, bytes);
            String cleanText = StringUtils.defaultString(parsed.getCleanText());
            String hash = sha256(cleanText);
            clearPreviousIndex(doc);

            KbDocumentText text = buildText(doc, parsed, cleanText);
            documentTextMapper.insert(text);

            List<KbTextChunk> chunks = chunker.split(cleanText, parsed.getTitle(), kb.getChunkSize(), kb.getChunkOverlap());
            for (KbTextChunk textChunk : chunks) {
                KbChunk chunk = buildChunk(doc, textChunk);
                chunkMapper.insert(chunk);
            }

            doc.setTitle(StringUtils.defaultIfBlank(doc.getTitle(), parsed.getTitle()));
            doc.setParseStatus("SUCCESS");
            doc.setEmbedStatus("PENDING");
            doc.setChunkCount(chunks.size());
            doc.setTokenCount(estimateTokens(cleanText));
            doc.setCharCount(cleanText.length());
            doc.setContentHash(hash);
            doc.setErrorMessage(null);
            doc.setUpdateTime(LocalDateTime.now());
            documentMapper.updateById(doc);
            taskProducer.sendEmbeddingTask(toEmbeddingTask(doc));
        } catch (Exception e) {
            log.error("[DefaultKbPipelineService][parseAndIndex] index document error docId={}", doc.getDocId(), e);
            markDocument(doc, "FAILED", "FAILED", e.getMessage());
            throw e;
        }
    }

    @Override
    @Transactional
    public KbDocumentVo rebuildDocumentIndex(Long docId) {
        KbDocument doc = documentMapper.selectById(docId);
        if (doc == null) {
            throw new ServiceException("Document not found: " + docId);
        }
        KbBase kb = kbBaseMapper.selectById(doc.getKbId());
        if (kb == null) {
            throw new ServiceException("Knowledge base not found: " + doc.getKbId());
        }
        markDocument(doc, "PENDING", "PENDING", null);
        taskProducer.sendDocumentIndexTask(toDocumentIndexTask(doc, doc.getFileName(), doc.getFileType()));
        return documentMapper.selectVoById(docId);
    }

    @Override
    @Transactional
    public int rebuildKnowledgeBaseIndex(Long kbId) {
        if (kbId == null) {
            throw new ServiceException("Knowledge base id is required");
        }
        KbBase kb = kbBaseMapper.selectById(kbId);
        if (kb == null) {
            throw new ServiceException("Knowledge base not found: " + kbId);
        }
        List<KbDocument> documents = documentMapper.selectList(Wrappers.<KbDocument>lambdaQuery()
                .eq(KbDocument::getKbId, kbId)
                .eq(KbDocument::getStatus, STATUS_ENABLED));
        for (KbDocument doc : documents) {
            markDocument(doc, "PENDING", "PENDING", null);
            taskProducer.sendDocumentIndexTask(toDocumentIndexTask(doc, doc.getFileName(), doc.getFileType()));
        }
        return documents.size();
    }

    @Override
    @Transactional
    public void deleteDocumentIndex(Long docId) {
        KbDocument doc = documentMapper.selectById(docId);
        if (doc == null) {
            return;
        }
        clearVectorIndex(doc);
    }

    @Override
    @Transactional
    public void processDocumentIndexTask(DocumentIndexTask task) {
        if (task == null || task.getDocId() == null) {
            log.warn("[DefaultKbPipelineService][processDocumentIndexTask] empty task");
            return;
        }
        KbDocument doc = documentMapper.selectById(task.getDocId());
        if (doc == null) {
            log.warn("[DefaultKbPipelineService][processDocumentIndexTask] document not found docId={}", task.getDocId());
            return;
        }
        KbBase kb = kbBaseMapper.selectById(doc.getKbId());
        if (kb == null) {
            markDocument(doc, "FAILED", "FAILED", "Knowledge base not found: " + doc.getKbId());
            return;
        }
        if (StringUtils.isBlank(doc.getObjectKey())) {
            markDocument(doc, "FAILED", "FAILED", "Document objectKey is empty");
            return;
        }
        try {
            byte[] bytes = ossClient.getObject(doc.getObjectKey());
            parseDocumentAndEnqueueEmbedding(doc, kb,
                    StringUtils.defaultIfBlank(task.getFileName(), doc.getFileName()),
                    StringUtils.defaultIfBlank(task.getContentType(), doc.getFileType()),
                    bytes);
        } catch (Exception e) {
            log.error("[DefaultKbPipelineService][processDocumentIndexTask] process document index error docId={}", doc.getDocId(), e);
            markDocument(doc, "FAILED", "FAILED", e.getMessage());
        }
    }

    @Override
    @Transactional
    public void processEmbeddingTask(EmbeddingTask task) {
        if (task == null || task.getDocId() == null) {
            log.warn("[DefaultKbPipelineService][processEmbeddingTask] empty task");
            return;
        }
        KbDocument doc = documentMapper.selectById(task.getDocId());
        if (doc == null) {
            log.warn("[DefaultKbPipelineService][processEmbeddingTask] document not found docId={}", task.getDocId());
            return;
        }
        KbBase kb = kbBaseMapper.selectById(doc.getKbId());
        if (kb == null) {
            markDocument(doc, doc.getParseStatus(), "FAILED", "Knowledge base not found: " + doc.getKbId());
            return;
        }
        try {
            embedDocumentChunks(doc, kb);
            doc.setEmbedStatus("SUCCESS");
            doc.setErrorMessage(null);
            doc.setUpdateTime(LocalDateTime.now());
            documentMapper.updateById(doc);
        } catch (Exception e) {
            log.error("[DefaultKbPipelineService][processEmbeddingTask] embed document error docId={}", doc.getDocId(), e);
            markDocument(doc, doc.getParseStatus(), "FAILED", e.getMessage());
        }
    }

    private void embedDocumentChunks(KbDocument doc, KbBase kb) {
        markDocument(doc, doc.getParseStatus(), "EMBEDDING", null);
        clearEmbeddingIndex(doc);
        List<KbChunk> chunks = chunkMapper.selectList(Wrappers.<KbChunk>lambdaQuery()
                .eq(KbChunk::getDocId, doc.getDocId())
                .eq(KbChunk::getStatus, STATUS_ENABLED)
                .orderByAsc(KbChunk::getChunkNo));
        for (KbChunk chunk : chunks) {
            chunk.setEmbeddingStatus("PENDING");
            chunk.setEmbeddingId(null);
            chunk.setUpdateTime(LocalDateTime.now());
            chunkMapper.updateById(chunk);
            embedChunk(kb, doc, chunk);
        }
    }

    private KbDocument newDocument(Long kbId, String fileName, String contentType, long size, FileObjectKey key) {
        String tenantId = LoginHelper.getTenantId();
        LocalDateTime now = LocalDateTime.now();
        KbDocument doc = new KbDocument();
        doc.setTenantId(tenantId);
        doc.setKbId(kbId);
        doc.setFileName(StringUtils.defaultIfBlank(fileName, key.getFilename()));
        doc.setFileType(resolveFileType(fileName, contentType));
        doc.setFileSize(size);
        doc.setFileUrl(ossClient.presignGet(key));
        doc.setObjectKey(key.getObjectKey());
        doc.setTitle(stripExtension(fileName));
        doc.setSourceType("UPLOAD");
        doc.setParseStatus("PENDING");
        doc.setEmbedStatus("PENDING");
        doc.setChunkCount(0);
        doc.setTokenCount(0);
        doc.setCharCount(0);
        doc.setStatus(STATUS_ENABLED);
        doc.setCreateBy(String.valueOf(LoginHelper.getUserId()));
        doc.setCreateTime(now);
        doc.setUpdateTime(now);
        doc.setDelFlag(DEL_NOT_DELETED);
        return doc;
    }

    private KbDocumentText buildText(KbDocument doc, KbParsedDocument parsed, String cleanText) {
        LocalDateTime now = LocalDateTime.now();
        KbDocumentText text = new KbDocumentText();
        text.setTenantId(doc.getTenantId());
        text.setKbId(doc.getKbId());
        text.setDocId(doc.getDocId());
        text.setRawText(parsed.getRawText());
        text.setCleanText(cleanText);
        text.setParserType(parsed.getParserType());
        text.setParserVersion(parsed.getParserVersion());
        text.setCharCount(cleanText.length());
        text.setTokenCount(estimateTokens(cleanText));
        text.setStatus(STATUS_ENABLED);
        text.setCreateBy(resolveOperator(doc));
        text.setCreateTime(now);
        text.setUpdateTime(now);
        text.setDelFlag(DEL_NOT_DELETED);
        return text;
    }

    private KbChunk buildChunk(KbDocument doc, KbTextChunk textChunk) {
        LocalDateTime now = LocalDateTime.now();
        KbChunk chunk = new KbChunk();
        chunk.setTenantId(doc.getTenantId());
        chunk.setKbId(doc.getKbId());
        chunk.setDocId(doc.getDocId());
        chunk.setChunkNo(textChunk.getChunkNo());
        chunk.setChunkTitle(textChunk.getTitle());
        chunk.setContent(textChunk.getContent());
        chunk.setContentHash(sha256(textChunk.getContent()));
        chunk.setTokenCount(estimateTokens(textChunk.getContent()));
        chunk.setCharCount(textChunk.getContent().length());
        chunk.setSectionTitle(textChunk.getSectionTitle());
        chunk.setStartOffset(textChunk.getStartOffset());
        chunk.setEndOffset(textChunk.getEndOffset());
        chunk.setEmbeddingStatus("PENDING");
        chunk.setStatus(STATUS_ENABLED);
        chunk.setCreateBy(resolveOperator(doc));
        chunk.setCreateTime(now);
        chunk.setUpdateTime(now);
        return chunk;
    }

    private void embedChunk(KbBase kb, KbDocument doc, KbChunk chunk) {
        Long modelId = kb.getEmbeddingModelId();
        if (modelId == null) {
            throw new ServiceException("Knowledge base embeddingModelId is not configured");
        }
        LlmModel model = modelService.getById(modelId);
        if (model == null) {
            throw new ServiceException("Embedding model not found: " + modelId);
        }
        LlmEndpoint endpoint = endpointMapper.selectById(model.getEndpointId());
        if (endpoint == null) {
            throw new ServiceException("Embedding endpoint not found: " + model.getEndpointId());
        }
        LlmProvider llmProvider = providerMapper.selectById(model.getProviderId());
        if (llmProvider == null) {
            throw new ServiceException("Embedding provider not found: " + model.getProviderId());
        }

        try {
            chunk.setEmbeddingStatus("EMBEDDING");
            chunk.setUpdateTime(LocalDateTime.now());
            chunkMapper.updateById(chunk);
            KbEmbeddingClient embeddingClient = invokerRegistry.resolve(llmProvider.getProviderCode());
            float[] vector = embeddingClient.embed(endpoint, model, chunk.getContent());
            String vectorId = "kb_" + doc.getKbId() + "_doc_" + doc.getDocId() + "_chunk_" + chunk.getChunkId();
            vectorStore.upsert(KbVectorItem.builder()
                    .vectorId(vectorId)
                    .tenantId(doc.getTenantId())
                    .kbId(doc.getKbId())
                    .docId(doc.getDocId())
                    .chunkId(chunk.getChunkId())
                    .content(chunk.getContent())
                    .vector(vector)
                    .build());

            KbEmbedding embedding = new KbEmbedding();
            embedding.setTenantId(doc.getTenantId());
            embedding.setKbId(doc.getKbId());
            embedding.setDocId(doc.getDocId());
            embedding.setChunkId(chunk.getChunkId());
            embedding.setModelId(model.getId());
            embedding.setModelCode(model.getModelName());
            embedding.setVectorStore(vectorStore.storeType());
            embedding.setCollectionName("kb_" + doc.getKbId());
            embedding.setVectorId(vectorId);
            embedding.setVectorDim(vector == null ? 0 : vector.length);
            embedding.setContentHash(chunk.getContentHash());
            embedding.setEmbedVersion("1.0");
            embedding.setStatus("0");
            embedding.setCreateBy(resolveOperator(doc));
            embedding.setCreateTime(LocalDateTime.now());
            embedding.setUpdateTime(LocalDateTime.now());

            embeddingMapper.insert(embedding);

            chunk.setEmbeddingId(embedding.getEmbeddingId());
            chunk.setEmbeddingStatus("SUCCESS");
            chunk.setUpdateTime(LocalDateTime.now());
            chunkMapper.updateById(chunk);
        } catch (Exception e) {
            chunk.setEmbeddingStatus("FAILED");
            chunk.setUpdateTime(LocalDateTime.now());
            chunkMapper.updateById(chunk);
            throw e;
        }
    }

    private void clearPreviousIndex(KbDocument doc) {
        clearVectorIndex(doc);
        documentTextMapper.delete(Wrappers.<KbDocumentText>lambdaQuery().eq(KbDocumentText::getDocId, doc.getDocId()));
    }

    private void clearVectorIndex(KbDocument doc) {
        vectorStore.deleteByDocument(doc.getTenantId(), doc.getDocId());
        embeddingMapper.delete(Wrappers.<KbEmbedding>lambdaQuery().eq(KbEmbedding::getDocId, doc.getDocId()));
        chunkMapper.delete(Wrappers.<KbChunk>lambdaQuery().eq(KbChunk::getDocId, doc.getDocId()));
    }

    private void clearEmbeddingIndex(KbDocument doc) {
        vectorStore.deleteByDocument(doc.getTenantId(), doc.getDocId());
        embeddingMapper.delete(Wrappers.<KbEmbedding>lambdaQuery().eq(KbEmbedding::getDocId, doc.getDocId()));
    }

    private DocumentIndexTask toDocumentIndexTask(KbDocument doc, String fileName, String contentType) {
        DocumentIndexTask task = new DocumentIndexTask();
        task.setDocId(doc.getDocId());
        task.setKbId(doc.getKbId());
        task.setTenantId(doc.getTenantId());
        task.setFileName(StringUtils.defaultIfBlank(fileName, doc.getFileName()));
        task.setContentType(contentType);
        task.setObjectKey(doc.getObjectKey());
        return task;
    }

    private EmbeddingTask toEmbeddingTask(KbDocument doc) {
        EmbeddingTask task = new EmbeddingTask();
        task.setDocId(doc.getDocId());
        task.setKbId(doc.getKbId());
        task.setTenantId(doc.getTenantId());
        return task;
    }

    private String resolveOperator(KbDocument doc) {
        return StringUtils.defaultIfBlank(doc.getUpdateBy(), StringUtils.defaultIfBlank(doc.getCreateBy(), "system"));
    }

    private void markDocument(KbDocument doc, String parseStatus, String embedStatus, String errorMessage) {
        doc.setParseStatus(parseStatus);
        doc.setEmbedStatus(embedStatus);
        doc.setErrorMessage(errorMessage);
        doc.setUpdateBy(resolveOperator(doc));
        doc.setUpdateTime(LocalDateTime.now());
        documentMapper.updateById(doc);
    }

    private String resolveFileType(String fileName, String contentType) {
        String lower = StringUtils.defaultString(fileName).toLowerCase();
        if (lower.endsWith(".md") || lower.endsWith(".markdown")) {
            return "md";
        }
        if (lower.endsWith(".txt")) {
            return "txt";
        }
        if (StringUtils.defaultString(contentType).toLowerCase().contains("markdown")) {
            return "md";
        }
        return "txt";
    }

    private String stripExtension(String fileName) {
        String name = StringUtils.defaultIfBlank(fileName, "Untitled");
        int idx = name.lastIndexOf('.');
        return idx > 0 ? name.substring(0, idx) : name;
    }

    private int estimateTokens(String text) {
        String value = StringUtils.defaultString(text).trim();
        if (value.isEmpty()) {
            return 0;
        }
        return Math.max(1, value.length() / 4);
    }

    private String sha256(String value) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(StringUtils.defaultString(value).getBytes(StandardCharsets.UTF_8));
            return HexFormat.of().formatHex(hash);
        } catch (Exception e) {
            throw new IllegalStateException("SHA-256 unavailable", e);
        }
    }
}
