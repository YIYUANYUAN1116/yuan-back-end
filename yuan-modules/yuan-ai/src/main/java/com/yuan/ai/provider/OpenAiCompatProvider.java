package com.yuan.ai.provider;

import com.yuan.ai.domain.LlmEndpoint;
import com.yuan.ai.domain.LlmModel;
import com.yuan.ai.domain.dto.ChatMsg;
import com.yuan.ai.domain.dto.ChatRequest;
import com.yuan.ai.service.ChatMessageService;
import com.yuan.ai.service.LlmInvocationService;
import com.yuan.ai.support.SseHelper;
import com.yuan.common.core.utils.StringUtils;
import io.micrometer.observation.ObservationRegistry;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.model.SimpleApiKey;
import org.springframework.ai.model.tool.ToolCallingManager;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.ai.openai.api.OpenAiApi;
import org.springframework.core.task.TaskExecutor;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.ResponseErrorHandler;
import org.springframework.web.client.RestClient;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import reactor.core.publisher.Flux;

import java.util.LinkedHashMap;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

@Component
@RequiredArgsConstructor
public class OpenAiCompatProvider implements ChatProvider {
    private final ConcurrentHashMap<String, ChatClient> cache = new ConcurrentHashMap<>();

    private final TaskExecutor chatTaskExecutor;
    private final SseHelper sse;
    private final ChatMessageService messageService;
    private final LlmInvocationService invocationService;

    private final RestClient.Builder restClientBuilder;
    private final WebClient.Builder webClientBuilder;
    private final ResponseErrorHandler responseErrorHandler;

    private final ToolCallingManager toolCallingManager;
    private final RetryTemplate retryTemplate;
    private final ObservationRegistry observationRegistry;

    @Override
    public boolean supports(String providerCode) {
        if (providerCode == null) return false;
        String c = providerCode.trim().toUpperCase();
        return c.equals("OPENAI_COMPAT") || c.equals("OPENAI") || c.equals("DEEPSEEK") || c.equals("OLLAMA_COMPAT");
    }

    @Override
    public SseEmitter chat(ChatRequest req, LlmEndpoint ep, LlmModel model, long conversationId, long assistantMsgId, SseEmitter emitter) {
        sse.init(emitter);
        chatTaskExecutor.execute(() -> {
            long t0 = System.currentTimeMillis();
            String tenantId = req.getTenantId();
            String traceId = req.getTraceId();
            String endpointKey = ep.getEndpointKey();
            String providerCode = ep.getProviderCode();
            String modelName = model.getModelName();

            // invocation begin
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
                ChatClient client = getOrCreateClient(ep.getBaseUrl(), ep.getApiKey(), modelName);
                ChatClient.ChatClientRequestSpec pb = client.prompt();
                if (StringUtils.isNotBlank(req.getSystemPrompt())) {
                    pb = pb.system(req.getSystemPrompt());
                }
                if (StringUtils.isNotBlank(req.getPrompt())) {
                    pb = pb.user(req.getPrompt());
                }

                for (ChatMsg m : req.getMessages()) {
                    String role = m.getRole();
                    String content = m.getContent();
                    if (StringUtils.isBlank(content)) continue;
                    if ("system".equalsIgnoreCase(role)) pb = pb.system(content);
//                    else if ("assistant".equalsIgnoreCase(role)) pb = pb.assistant(content);
                    else pb = pb.user(content);
                }

                boolean stream = req.getStream() == null || req.getStream();

                OpenAiChatOptions options = OpenAiChatOptions.builder()
                        .model(modelName)
                        .build();

                if (stream) {
                    Flux<String> flux = pb.options(options).stream().content();
                    StringBuffer full = new StringBuffer();
                    flux.doOnNext(delta -> {
                                if (delta == null) return;
                                full.append(delta);
                                sse.send(emitter, "delta", delta);
                            }).doOnError(e -> {
                                String errorMessage = buildErrorMessage(e);
                                messageService.failAssistant(assistantMsgId, full.toString(), errorMessage);
                                invocationService.fail(invId, errorMessage, (int) (System.currentTimeMillis() - t0));
                                sse.error(emitter, e);
                            })
                            .doOnComplete(() -> {
                                messageService.finishAssistant(assistantMsgId, full.toString());
                                invocationService.success(invId, full.toString(), (int) (System.currentTimeMillis() - t0));
                                sse.done(emitter);
                            })
                            .subscribe();
                } else {
                    String content = Objects.toString(pb.options(options).call().content(), "");
                    messageService.finishAssistant(assistantMsgId, content);
                    invocationService.success(invId, content, (int) (System.currentTimeMillis() - t0));
                    sse.send(emitter, "message", content);
                    sse.done(emitter);
                }


            } catch (Exception e) {
                String errorMessage = buildErrorMessage(e);
                messageService.failAssistant(assistantMsgId, "", errorMessage);
                invocationService.fail(invId, errorMessage, (int) (System.currentTimeMillis() - t0));
                sse.error(emitter, e);
            }

        });
        return emitter;
    }

    public ChatClient getOrCreateClient(String baseUrl, String apiKey, String modelName) {
        if (baseUrl == null || baseUrl.isBlank()) throw new IllegalArgumentException("baseUrl is blank");
        if (modelName == null || modelName.isBlank()) throw new IllegalArgumentException("modelName is blank");

        String key = baseUrl + "|" + modelName + "|" + (apiKey == null ? "" : Integer.toHexString(apiKey.hashCode()));
        return cache.computeIfAbsent(key, k -> {

            MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();

            //OpenAI 兼容路径通常是这些
            String completionsPath = "/v1/chat/completions";
            String embeddingsPath = "/v1/embeddings";

            OpenAiApi openAiApi = new OpenAiApi(
                    baseUrl,
                    new SimpleApiKey(apiKey),
                    headers,
                    completionsPath,
                    embeddingsPath,
                    restClientBuilder,
                    webClientBuilder,
                    responseErrorHandler
            );

            OpenAiChatOptions defaultOptions = OpenAiChatOptions.builder()
                    .model(modelName)
                    .build();

            OpenAiChatModel chatModel = new OpenAiChatModel(
                    openAiApi,
                    defaultOptions,
                    toolCallingManager,
                    retryTemplate,
                    observationRegistry
            );

            return ChatClient.builder(chatModel).build();
        });
    }

    private String buildErrorMessage(Throwable e) {
        if (e == null) {
            return "Unknown error (Throwable is null)";
        }

        String msg = e.getMessage();

        if (msg == null || msg.isBlank()) {
            msg = e.toString();  // 至少带类名
        }

        // 可选：限制长度，避免数据库被撑爆
        if (msg.length() > 2000) {
            msg = msg.substring(0, 2000) + "...(truncated)";
        }

        return msg;
    }
}
