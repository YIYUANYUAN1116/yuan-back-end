package com.yuan.ai.service.impl;

import com.yuan.ai.domain.dto.KbRetrievalRequest;
import com.yuan.ai.domain.dto.KbRetrievalResponse;
import com.yuan.ai.domain.dto.ChatPrepareContext;
import com.yuan.ai.domain.dto.ChatRequest;
import com.yuan.ai.provider.UnifiedChatProvider;
import com.yuan.ai.service.ChatPrepareService;
import com.yuan.ai.service.ChatService;
import com.yuan.ai.service.KbRetrievalService;
import com.yuan.common.core.utils.StringUtils;
import com.yuan.ai.support.SseHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@Service
@RequiredArgsConstructor
public class ChatServiceImpl implements ChatService {
    private final UnifiedChatProvider chatProvider;
    private final SseHelper sse;
    private final ChatPrepareService chatPrepareService;
    private final KbRetrievalService kbRetrievalService;

    @Override
    public SseEmitter chat(ChatRequest req) {
        SseEmitter emitter = new SseEmitter(5 * 60 * 1000L);
        sse.init(emitter);
        try {
            ChatPrepareContext ctx = chatPrepareService.prepare(req);
            enrichWithKnowledgeBase(req, ctx);
            return chatProvider.stream(req, ctx, emitter);
        } catch (Exception e) {
            sse.error(emitter, e);
            return emitter;
        }
    }

    private void enrichWithKnowledgeBase(ChatRequest req, ChatPrepareContext ctx) {
        if (req.getKbIds() == null || req.getKbIds().isEmpty() || StringUtils.isBlank(req.getPrompt())) {
            return;
        }
        KbRetrievalRequest retrievalRequest = new KbRetrievalRequest();
        retrievalRequest.setTenantId(ctx.getTenantId());
        retrievalRequest.setKbIds(req.getKbIds());
        retrievalRequest.setQuery(req.getPrompt());
        retrievalRequest.setTopK(req.getRetrievalTopK());
        retrievalRequest.setMinScore(req.getRetrievalMinScore());
        retrievalRequest.setEmbeddingModelId(req.getRetrievalEmbeddingModelId());
        retrievalRequest.setConversationId(ctx.getConversationId());
        retrievalRequest.setMessageId(ctx.getUserMsgId());
        KbRetrievalResponse response = kbRetrievalService.retrieve(retrievalRequest);
        ctx.setKbRetrievalLogId(response.getLogId());
        String context = kbRetrievalService.buildPromptContext(response);
        if (StringUtils.isBlank(context)) {
            return;
        }
        String systemPrompt = StringUtils.defaultString(req.getSystemPrompt());
        req.setSystemPrompt(StringUtils.isBlank(systemPrompt) ? context : systemPrompt + "\n\n" + context);
    }
}
