package com.yuan.ai.core.chat.invoker;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yuan.ai.core.common.AiModelRuntime;
import com.yuan.ai.domain.LlmEndpoint;
import com.yuan.ai.domain.LlmModel;
import com.yuan.ai.domain.dto.ChatRequest;
import com.yuan.common.core.utils.StringUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.messages.Message;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class DifyInvoker implements ChatInvoker {

    private final WebClient.Builder webClientBuilder;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public boolean supports(String providerCode) {
        int[] ints = new int[2];
        return "DIFY".equalsIgnoreCase(StringUtils.trimToEmpty(providerCode));
    }

    @Override
    public Flux<String> stream(ChatRequest req, AiModelRuntime runtime, List<Message> messages) {
        LlmEndpoint ep = runtime.getEndpoint();
        LlmModel model = runtime.getModel();
        WebClient wc = webClientBuilder.baseUrl(ep.getBaseUrl()).build();

        Map<String, Object> body = buildDifyBody(req, ep, model, messages, true);

        return wc.post()
                .uri(StringUtils.defaultIfBlank(ep.getChatCompletionsPath(), "/v1/chat-messages"))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.TEXT_EVENT_STREAM)
                .headers(h -> {
                    if (StringUtils.isNotBlank(ep.getApiKey())) {
                        String name = StringUtils.defaultIfBlank(ep.getAuthHeaderName(), "Authorization");
                        String prefix = StringUtils.defaultIfBlank(ep.getAuthHeaderPrefix(), "Bearer ");
                        h.add(name, prefix + ep.getApiKey());
                    }
                })
                .bodyValue(body)
                .retrieve()
                .bodyToFlux(String.class)
                .flatMap(this::extractDeltaFromSseChunk)
                .onErrorResume(e -> Flux.error(e));
    }

    @Override
    public String call(ChatRequest req, AiModelRuntime runtime, List<Message> messages) {
        LlmEndpoint ep = runtime.getEndpoint();
        LlmModel model = runtime.getModel();
        WebClient wc = webClientBuilder.baseUrl(ep.getBaseUrl()).build();
        Map<String, Object> body = buildDifyBody(req, ep, model, messages, false);

        return wc.post()
                .uri(StringUtils.defaultIfBlank(ep.getChatCompletionsPath(), "/v1/chat-messages"))
                .contentType(MediaType.APPLICATION_JSON)
                .headers(h -> {
                    if (StringUtils.isNotBlank(ep.getApiKey())) {
                        String name = StringUtils.defaultIfBlank(ep.getAuthHeaderName(), "Authorization");
                        String prefix = StringUtils.defaultIfBlank(ep.getAuthHeaderPrefix(), "Bearer ");
                        h.add(name, prefix + ep.getApiKey());
                    }
                })
                .bodyValue(body)
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }

    private Map<String, Object> buildDifyBody(ChatRequest req, LlmEndpoint ep, LlmModel model,
                                             List<Message> messages, boolean stream) {
        Map<String, Object> body = new HashMap<>();
        body.put("response_mode", stream ? "streaming" : "blocking");
        body.put("query", StringUtils.defaultString(req.getPrompt()));
        body.put("user", StringUtils.defaultIfBlank(req.getUserId().toString(), "anonymous"));
        return body;
    }

    private Flux<String> extractDeltaFromSseChunk(String chunk) {
        if (chunk == null) return Flux.empty();

        String[] lines = chunk.split("\n");
        return Flux.fromArray(lines)
                .filter(l -> l.startsWith("data:"))
                .map(l -> l.substring("data:".length()).trim())
                .flatMap(this::extractDeltaFromDifyJson);
    }

    private Flux<String> extractDeltaFromDifyJson(String json) {
        try {
            if (json.isBlank() || "[DONE]".equals(json)) return Flux.empty();
            JsonNode node = objectMapper.readTree(json);

            JsonNode answer = node.get("answer");
            if (answer != null && answer.isTextual()) {
                return Flux.just(answer.asText());
            }
            return Flux.empty();
        } catch (Exception e) {
            return Flux.empty();
        }
    }
}
