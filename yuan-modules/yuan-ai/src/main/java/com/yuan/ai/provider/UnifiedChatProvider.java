package com.yuan.ai.provider;

import com.yuan.ai.domain.LlmEndpoint;
import com.yuan.ai.domain.LlmModel;
import com.yuan.ai.domain.dto.ChatRequest;
import com.yuan.ai.domain.vo.LlmProviderVo;
import com.yuan.ai.mapper.LlmProviderMapper;
import com.yuan.ai.provider.builder.ChatMessageBuilder;
import com.yuan.ai.provider.invoker.InvokerRegistry;
import com.yuan.ai.provider.invoker.ProviderInvoker;
import com.yuan.ai.service.ChatMessageService;
import com.yuan.ai.service.LlmInvocationService;
import com.yuan.ai.support.SseHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.messages.Message;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import reactor.core.publisher.Flux;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Objects;

@Component
@RequiredArgsConstructor
public class UnifiedChatProvider implements ChatProvider {

    private final TaskExecutor chatTaskExecutor;
    private final SseHelper sse;
    private final ChatMessageService messageService;
    private final LlmInvocationService invocationService;
    private final ChatMessageBuilder messageBuilder;
    private final InvokerRegistry invokerRegistry;
    private final LlmProviderMapper providerMapper;
    @Override
    public SseEmitter chat(ChatRequest req, LlmEndpoint ep, LlmModel model,
                           long conversationId, long assistantMsgId, SseEmitter emitter) {

        sse.init(emitter);

        chatTaskExecutor.execute(() -> {
            long t0 = System.currentTimeMillis();
            String tenantId = req.getTenantId();
            String traceId = req.getTraceId();
            String endpointKey = ep.getEndpointKey();
            String providerCode = ep.getProviderCode();
            String modelName = model.getModelName();

            long invId = invocationService.begin(
                    tenantId, traceId, endpointKey, providerCode, modelName,
                    conversationId, assistantMsgId,
                    new LinkedHashMap<>() {{
                        put("messages", req.getMessages());
                        put("systemPrompt", req.getSystemPrompt());
                        put("prompt", req.getPrompt());
                        put("stream", req.getStream());
                        put("enableThinking", req.getEnableThinking());
                    }}
            );
            messageService.bindInvocation(assistantMsgId, invId);

            try {
                ProviderInvoker invoker = invokerRegistry.resolve(providerCode);
                List<Message> messages = messageBuilder.build(req);

                boolean stream = req.getStream() == null || req.getStream();

                if (stream) {
                    Flux<String> flux = invoker.stream(req, ep, model, messages);
                    StringBuilder full = new StringBuilder();

                    flux.doOnNext(delta -> {
                                if (delta == null) return;
                                full.append(delta);
                                sse.send(emitter, "delta", delta);
                            })
                            .doOnError(e -> {
                                String err = buildErrorMessage(e);
                                messageService.failAssistant(assistantMsgId, full.toString(), err);
                                invocationService.fail(invId, err, (int) (System.currentTimeMillis() - t0));
                                sse.error(emitter, e);
                            })
                            .doOnComplete(() -> {
                                messageService.finishAssistant(assistantMsgId, full.toString());
                                invocationService.success(invId, full.toString(), (int) (System.currentTimeMillis() - t0));
                                sse.done(emitter);
                            })
                            .subscribe();
                } else {
                    String content = Objects.toString(invoker.call(req, ep, model, messages), "");
                    messageService.finishAssistant(assistantMsgId, content);
                    invocationService.success(invId, content, (int) (System.currentTimeMillis() - t0));
                    sse.send(emitter, "message", content);
                    sse.done(emitter);
                }

            } catch (Exception e) {
                String err = buildErrorMessage(e);
                messageService.failAssistant(assistantMsgId, "", err);
                invocationService.fail(invId, err, (int) (System.currentTimeMillis() - t0));
                sse.error(emitter, e);
            }
        });

        return emitter;
    }

    private String buildErrorMessage(Throwable e) {
        if (e == null) return "Unknown error (Throwable is null)";
        String msg = e.getMessage();
        if (msg == null || msg.isBlank()) msg = e.toString();
        if (msg.length() > 2000) msg = msg.substring(0, 2000) + "...(truncated)";
        return msg;
    }
}