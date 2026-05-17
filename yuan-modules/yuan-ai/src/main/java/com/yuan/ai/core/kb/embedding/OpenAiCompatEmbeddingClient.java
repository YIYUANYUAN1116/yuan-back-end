package com.yuan.ai.core.kb.embedding;

import com.yuan.ai.domain.LlmEndpoint;
import com.yuan.ai.domain.LlmModel;
import com.yuan.common.core.utils.StringUtils;
import io.micrometer.observation.ObservationRegistry;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.document.MetadataMode;
import org.springframework.ai.model.SimpleApiKey;
import org.springframework.ai.openai.OpenAiEmbeddingModel;
import org.springframework.ai.openai.OpenAiEmbeddingOptions;
import org.springframework.ai.openai.api.OpenAiApi;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.ResponseErrorHandler;
import org.springframework.web.client.RestClient;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.concurrent.ConcurrentHashMap;

@Component
@RequiredArgsConstructor
public class OpenAiCompatEmbeddingClient implements KbEmbeddingClient {

    private final RestClient.Builder restClientBuilder;
    private final WebClient.Builder webClientBuilder;
    private final ResponseErrorHandler responseErrorHandler;
    private final RetryTemplate retryTemplate;
    private final ObservationRegistry observationRegistry;
    private final ConcurrentHashMap<String, OpenAiEmbeddingModel> cache = new ConcurrentHashMap<>();

    @Override
    public boolean supports(String providerCode) {
        return false;
    }

    @Override
    public float[] embed(LlmEndpoint endpoint, LlmModel model, String text) {
        return getOrCreate(endpoint, model).embed(text);
    }

    private OpenAiEmbeddingModel getOrCreate(LlmEndpoint endpoint, LlmModel model) {
        String key = buildCacheKey(endpoint, model);
        return cache.computeIfAbsent(key, ignored -> create(endpoint, model));
    }

    private OpenAiEmbeddingModel create(LlmEndpoint endpoint, LlmModel model) {
        String baseUrl = endpoint.getBaseUrl();
        String apiKey = endpoint.getApiKey();
        String modelName = model.getModelName();
        if (StringUtils.isBlank(baseUrl)) {
            throw new IllegalArgumentException("Embedding endpoint baseUrl is blank");
        }
        if (StringUtils.isBlank(modelName)) {
            throw new IllegalArgumentException("Embedding modelName is blank");
        }

        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        if (StringUtils.isNotBlank(apiKey)) {
            String headerName = StringUtils.defaultIfBlank(endpoint.getAuthHeaderName(), "Authorization");
            String prefix = StringUtils.defaultIfBlank(endpoint.getAuthHeaderPrefix(), "Bearer ");
            headers.add(headerName, prefix + apiKey);
        }

        String completionsPath = StringUtils.defaultIfBlank(endpoint.getChatCompletionsPath(), "/api/v3/chat/completions");
        String embeddingsPath = StringUtils.defaultIfBlank(endpoint.getEmbeddingsPath(), "/api/v3/embeddings");
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
        OpenAiEmbeddingOptions options = OpenAiEmbeddingOptions.builder()
                .model(modelName)
                .build();
        return new OpenAiEmbeddingModel(openAiApi, MetadataMode.EMBED, options, retryTemplate, observationRegistry);
    }

    private String buildCacheKey(LlmEndpoint endpoint, LlmModel model) {
        String apiKeyHash = endpoint.getApiKey() == null ? "" : Integer.toHexString(endpoint.getApiKey().hashCode());
        return StringUtils.defaultString(endpoint.getBaseUrl()) + "|"
                + StringUtils.defaultString(endpoint.getEmbeddingsPath()) + "|"
                + StringUtils.defaultString(endpoint.getAuthHeaderName()) + "|"
                + StringUtils.defaultString(endpoint.getAuthHeaderPrefix()) + "|"
                + StringUtils.defaultString(model.getModelName()) + "|"
                + apiKeyHash;
    }
}
