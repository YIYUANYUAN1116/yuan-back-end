package com.yuan.ai.core.chat.invoker;

import com.yuan.ai.core.chat.factory.SpringAiChatClientFactory;
import com.yuan.ai.core.common.AiModelRuntime;
import com.yuan.ai.domain.LlmEndpoint;
import com.yuan.ai.domain.LlmModel;
import com.yuan.ai.domain.dto.ChatRequest;
import com.yuan.common.core.utils.StringUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.messages.Message;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

@Component
@RequiredArgsConstructor
public class SpringAiChatClientInvoker implements ChatInvoker {

    private final List<SpringAiChatClientFactory> factories;
    private final ConcurrentHashMap<String, ChatClient> cache = new ConcurrentHashMap<>();

    @Override
    public boolean supports(String providerCode) {
        String pc = StringUtils.trimToEmpty(providerCode).toUpperCase();
        return factories.stream().anyMatch(f -> f.supports(pc));
    }

    @Override
    public Flux<String> stream(ChatRequest req, AiModelRuntime runtime, List<Message> messages) {
        ChatClient client = getOrCreate(runtime);
        ChatClient.ChatClientRequestSpec pb = client.prompt().messages(messages);
        pb = applyOptions(pb, runtime, req);
        return pb.stream().content();
    }

    @Override
    public String call(ChatRequest req, AiModelRuntime runtime, List<Message> messages) {
        ChatClient client = getOrCreate(runtime);
        ChatClient.ChatClientRequestSpec pb = client.prompt().messages(messages);
        pb = applyOptions(pb, runtime, req);
        return pb.call().content();
    }

    private ChatClient getOrCreate(AiModelRuntime runtime) {
        String providerCode = StringUtils.trimToEmpty(runtime.getProviderCode()).toUpperCase();
        SpringAiChatClientFactory factory = factories.stream()
                .filter(f -> f.supports(providerCode))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("No SpringAiChatClientFactory for " + providerCode));

        String key = buildCacheKey(runtime);
        return cache.computeIfAbsent(key, k -> factory.create(runtime));
    }

    private String buildCacheKey(AiModelRuntime runtime) {
        LlmEndpoint ep = runtime.getEndpoint();
        LlmModel model = runtime.getModel();
        String baseUrl = StringUtils.defaultString(ep.getBaseUrl());
        String modelName = StringUtils.defaultString(model.getModelName());
        String apiKeyHash = ep.getApiKey() == null ? "" : Integer.toHexString(ep.getApiKey().hashCode());

        String completionsPath = StringUtils.defaultString(ep.getChatCompletionsPath());
        String embeddingsPath = StringUtils.defaultString(ep.getEmbeddingsPath());
        String authHeaderName = StringUtils.defaultString(ep.getAuthHeaderName());
        String authHeaderPrefix = StringUtils.defaultString(ep.getAuthHeaderPrefix());

        return baseUrl + "|" + completionsPath + "|" + embeddingsPath + "|"
                + authHeaderName + "|" + authHeaderPrefix + "|"
                + modelName + "|" + apiKeyHash;
    }

    private ChatClient.ChatClientRequestSpec applyOptions(ChatClient.ChatClientRequestSpec pb,
                                                         AiModelRuntime runtime, ChatRequest req) {
        return pb;
    }
}
