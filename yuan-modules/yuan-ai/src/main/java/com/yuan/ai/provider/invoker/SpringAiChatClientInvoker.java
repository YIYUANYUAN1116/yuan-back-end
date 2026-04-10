package com.yuan.ai.provider.invoker;

import com.yuan.ai.domain.LlmEndpoint;
import com.yuan.ai.domain.LlmModel;
import com.yuan.ai.domain.dto.ChatRequest;
import com.yuan.ai.domain.vo.LlmProviderVo;
import com.yuan.ai.mapper.LlmProviderMapper;
import com.yuan.ai.provider.factory.SpringAiChatClientFactory;
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
public class SpringAiChatClientInvoker implements ProviderInvoker {

    private final List<SpringAiChatClientFactory> factories;
    private final ConcurrentHashMap<String, ChatClient> cache = new ConcurrentHashMap<>();
    private final LlmProviderMapper providerMapper;

    @Override
    public boolean supports(String providerCode) {
        String pc = StringUtils.trimToEmpty(providerCode).toUpperCase();
        return factories.stream().anyMatch(f -> f.supports(pc));
    }

    @Override
    public Flux<String> stream(ChatRequest req, LlmEndpoint ep, LlmModel model, List<Message> messages) {
        ChatClient client = getOrCreate(ep, model);
        ChatClient.ChatClientRequestSpec pb = client.prompt().messages(messages);
        pb = applyOptions(pb, ep, model, req);
        return pb.stream().content();
    }

    @Override
    public String call(ChatRequest req, LlmEndpoint ep, LlmModel model, List<Message> messages) {
        ChatClient client = getOrCreate(ep, model);
        ChatClient.ChatClientRequestSpec pb = client.prompt().messages(messages);
        pb = applyOptions(pb, ep, model, req);
        return pb.call().content();
    }

    private ChatClient getOrCreate(LlmEndpoint ep, LlmModel model) {
        LlmProviderVo providerVo = providerMapper.selectVoById(ep.getProviderId());
        String providerCode = StringUtils.trimToEmpty(providerVo.getProviderCode()).toUpperCase();
        SpringAiChatClientFactory factory = factories.stream()
                .filter(f -> f.supports(providerCode))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("No SpringAiChatClientFactory for " + providerCode));

        String key = buildCacheKey(ep, model);
        return cache.computeIfAbsent(key, k -> factory.create(ep, model));
    }

    /**
     * 不同网关路径/鉴权/headers 可能不同，必须纳入 key
     */
    private String buildCacheKey(LlmEndpoint ep, LlmModel model) {
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

    /**
     * 通用 options（先做最小：只塞 modelName；后面可按 providerCode 细分 temperature/maxTokens/tools 等）
     */
    private ChatClient.ChatClientRequestSpec applyOptions(ChatClient.ChatClientRequestSpec pb,
                                                         LlmEndpoint ep, LlmModel model, ChatRequest req) {
        // 这里先不写死 OpenAiChatOptions，因为 Ollama/Gemini options 类型不同
        // 最稳的方式：factory.create(...) 时就把 defaultOptions 放进对应 ChatModel；
        // 这里如果确实需要动态参数，再扩展为：factory 提供 “optionsApplier”。
        return pb;
    }
}