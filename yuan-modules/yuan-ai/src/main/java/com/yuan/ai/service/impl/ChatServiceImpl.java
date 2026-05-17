package com.yuan.ai.service.impl;

import com.yuan.ai.api.dto.ChatExecuteResult;
import com.yuan.ai.core.chat.ChatProvider;
import com.yuan.ai.core.chat.builder.ChatMessageBuilder;
import com.yuan.ai.core.common.AiModelRuntime;
import com.yuan.ai.core.common.TokenUsageEstimator;
import com.yuan.ai.domain.dto.ChatPrepareContext;
import com.yuan.ai.domain.dto.ChatRequest;
import com.yuan.ai.domain.dto.KbRetrievalRequest;
import com.yuan.ai.domain.dto.KbRetrievalResponse;
import com.yuan.ai.service.ChatMessageService;
import com.yuan.ai.service.ChatPrepareService;
import com.yuan.ai.service.ChatService;
import com.yuan.ai.service.KbRetrievalService;
import com.yuan.ai.service.LlmInvocationService;
import com.yuan.ai.support.SseHelper;
import com.yuan.common.core.utils.StringUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.messages.Message;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import reactor.core.publisher.Flux;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class ChatServiceImpl implements ChatService {
    private final ChatProvider chatProvider;
    private final SseHelper sse;
    private final ChatPrepareService chatPrepareService;
    private final KbRetrievalService kbRetrievalService;
    private final ChatMessageService messageService;
    private final LlmInvocationService invocationService;
    private final ChatMessageBuilder messageBuilder;

    @Override
    public SseEmitter chat(ChatRequest req) {
        SseEmitter emitter = new SseEmitter(5 * 60 * 1000L);
        sse.init(emitter);
        try {
            ChatPrepareContext ctx = chatPrepareService.prepare(req);
            enrichWithKnowledgeBase(req, ctx);
            streamToEmitter(req, ctx, emitter);
        } catch (Exception e) {
            sse.error(emitter, e);
        }
        return emitter;
    }

    @Override
    public ChatExecuteResult execute(ChatRequest req) {
        ChatPrepareContext ctx = chatPrepareService.prepare(req);
        enrichWithKnowledgeBase(req, ctx);

        long t0 = System.currentTimeMillis();
        AiModelRuntime runtime = Objects.requireNonNull(ctx.getRuntime(), "AiModelRuntime is required");
        int tokenIn = estimateTokenIn(req);
        Long invId = beginInvocation(req, ctx, false);
        bindInvocation(ctx, invId);

        try {
            String content = Objects.toString(chatProvider.call(req, ctx), "");
            if (ctx.getAssistantMsgId() != null) {
                messageService.finishAssistant(ctx.getAssistantMsgId(), content);
            }
            int latencyMs = (int) (System.currentTimeMillis() - t0);
            int tokenOut = TokenUsageEstimator.estimateText(content);
            invocationService.success(invId, content, latencyMs, tokenIn, tokenOut);
            return ChatExecuteResult.builder()
                    .success(true)
                    .content(content)
                    .invocationId(invId)
                    .endpointCode(runtime.endpointCode())
                    .providerCode(runtime.getProviderCode())
                    .modelName(runtime.modelName())
                    .tokenIn(tokenIn)
                    .tokenOut(tokenOut)
                    .latencyMs(latencyMs)
                    .build();
        } catch (Exception e) {
            String err = buildErrorMessage(e);
            if (ctx.getAssistantMsgId() != null) {
                messageService.failAssistant(ctx.getAssistantMsgId(), "", err);
            }
            int latencyMs = (int) (System.currentTimeMillis() - t0);
            invocationService.fail(invId, err, latencyMs);
            return ChatExecuteResult.builder()
                    .success(false)
                    .invocationId(invId)
                    .endpointCode(runtime.endpointCode())
                    .providerCode(runtime.getProviderCode())
                    .modelName(runtime.modelName())
                    .latencyMs(latencyMs)
                    .errorMessage(err)
                    .build();
        }
    }

    private void streamToEmitter(ChatRequest req, ChatPrepareContext ctx, SseEmitter emitter) {
        long t0 = System.currentTimeMillis();
        int tokenIn = estimateTokenIn(req);
        Long invId = beginInvocation(req, ctx, true);
        bindInvocation(ctx, invId);

        try {
            sse.send(emitter, "conversationId", ctx.getConversationId());
            Flux<String> flux = chatProvider.stream(req, ctx);
            StringBuilder full = new StringBuilder();

            flux.doOnNext(delta -> {
                        if (delta == null) {
                            return;
                        }
                        full.append(delta);
                        sse.send(emitter, "delta", delta);
                    })
                    .doOnError(e -> {
                        String err = buildErrorMessage(e);
                        if (ctx.getAssistantMsgId() != null) {
                            messageService.failAssistant(ctx.getAssistantMsgId(), full.toString(), err);
                        }
                        invocationService.fail(invId, err, (int) (System.currentTimeMillis() - t0));
                        sse.error(emitter, e);
                    })
                    .doOnComplete(() -> {
                        String content = full.toString();
                        if (ctx.getAssistantMsgId() != null) {
                            messageService.finishAssistant(ctx.getAssistantMsgId(), content);
                        }
                        invocationService.success(invId, content, (int) (System.currentTimeMillis() - t0),
                                tokenIn, TokenUsageEstimator.estimateText(content));
                        sse.done(emitter);
                    })
                    .subscribe();
        } catch (Exception e) {
            String err = buildErrorMessage(e);
            if (ctx.getAssistantMsgId() != null) {
                messageService.failAssistant(ctx.getAssistantMsgId(), "", err);
            }
            invocationService.fail(invId, err, (int) (System.currentTimeMillis() - t0));
            sse.error(emitter, e);
        }
    }

    private Long beginInvocation(ChatRequest req, ChatPrepareContext ctx, boolean stream) {
        AiModelRuntime runtime = Objects.requireNonNull(ctx.getRuntime(), "AiModelRuntime is required");
        return invocationService.begin(
                ctx.getTenantId(), req.getTraceId(), runtime.endpointId(), runtime.providerId(), runtime.modelName(),
                ctx.getConversationId(), ctx.getAssistantMsgId(),
                new LinkedHashMap<>() {{
                    put("messages", req.getMessages());
                    put("systemPrompt", req.getSystemPrompt());
                    put("prompt", req.getPrompt());
                    put("stream", stream);
                    put("enableThinking", req.getEnableThinking());
                    put("sceneCode", req.getSceneCode());
                    put("bizType", req.getBizType());
                    put("bizId", req.getBizId());
                }}
        );
    }

    private void bindInvocation(ChatPrepareContext ctx, Long invId) {
        if (ctx.getAssistantMsgId() != null) {
            messageService.bindInvocation(ctx.getAssistantMsgId(), invId);
        }
        kbRetrievalService.bindInvocation(ctx.getKbRetrievalLogId(), invId);
    }

    private int estimateTokenIn(ChatRequest req) {
        List<Message> messages = messageBuilder.build(req);
        return TokenUsageEstimator.estimateMessages(messages);
    }

    private String buildErrorMessage(Throwable e) {
        if (e == null) return "Unknown error (Throwable is null)";
        String msg = e.getMessage();
        if (msg == null || msg.isBlank()) msg = e.toString();
        if (msg.length() > 2000) msg = msg.substring(0, 2000) + "...(truncated)";
        return msg;
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
