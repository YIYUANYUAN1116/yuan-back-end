package com.yuan.ai.provider.factory;

import com.yuan.ai.domain.LlmEndpoint;
import com.yuan.ai.domain.LlmModel;
import com.yuan.common.core.utils.StringUtils;
import io.micrometer.observation.ObservationRegistry;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.model.SimpleApiKey;
import org.springframework.ai.model.tool.ToolCallingManager;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.ai.openai.api.OpenAiApi;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.ResponseErrorHandler;
import org.springframework.web.client.RestClient;
import org.springframework.web.reactive.function.client.WebClient;

@Component
@RequiredArgsConstructor
public class OpenAiCompatChatClientFactory implements SpringAiChatClientFactory {

    private final RestClient.Builder restClientBuilder;
    private final WebClient.Builder webClientBuilder;
    private final ResponseErrorHandler responseErrorHandler;

    private final ToolCallingManager toolCallingManager;
    private final RetryTemplate retryTemplate;
    private final ObservationRegistry observationRegistry;

    @Override
    public boolean supports(String providerCode) {

        String c = StringUtils.trimToEmpty(providerCode).toUpperCase();
        return c.equals("OPENAI_COMPAT") || c.equals("OPENAI") || c.equals("DEEPSEEK") || c.equals("OLLAMA_COMPAT")
                || c.equals("VOLCENGINE");
    }

    @Override
    public ChatClient create(LlmEndpoint ep, LlmModel model) {
        String baseUrl = ep.getBaseUrl();
        String apiKey = ep.getApiKey();
        String modelName = model.getModelName();

        if (StringUtils.isBlank(baseUrl)) throw new IllegalArgumentException("baseUrl is blank");
        if (StringUtils.isBlank(modelName)) throw new IllegalArgumentException("modelName is blank");

        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();

        String completionsPath = StringUtils.defaultIfBlank(ep.getChatCompletionsPath(), "/api/v3/chat/completions");
        String embeddingsPath  = StringUtils.defaultIfBlank(ep.getEmbeddingsPath(), "/api/v3/embeddings");

        if (StringUtils.isNotBlank(apiKey)) {
            String headerName = StringUtils.defaultIfBlank(ep.getAuthHeaderName(), "Authorization");
            String prefix = StringUtils.defaultIfBlank(ep.getAuthHeaderPrefix(), "Bearer ");
            headers.add(headerName, prefix + apiKey);
        }

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
    }
}