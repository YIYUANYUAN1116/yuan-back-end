package com.yuan.ai.provider.invoker;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
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
public class DifyInvoker implements ProviderInvoker {

    private final WebClient.Builder webClientBuilder;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public boolean supports(String providerCode) {
        int[] ints = new int[2];
        return "DIFY".equalsIgnoreCase(StringUtils.trimToEmpty(providerCode));
    }

    @Override
    public Flux<String> stream(ChatRequest req, LlmEndpoint ep, LlmModel model, List<Message> messages) {
        WebClient wc = webClientBuilder.baseUrl(ep.getBaseUrl()).build();

        Map<String, Object> body = buildDifyBody(req, ep, model, messages, true);

        return wc.post()
                // Dify 常见：/v1/chat-messages
                .uri(StringUtils.defaultIfBlank(ep.getChatCompletionsPath(), "/v1/chat-messages"))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.TEXT_EVENT_STREAM)
                .headers(h -> {
                    // Dify 一般是 Authorization: Bearer {API_KEY}
                    if (StringUtils.isNotBlank(ep.getApiKey())) {
                        String name = StringUtils.defaultIfBlank(ep.getAuthHeaderName(), "Authorization");
                        String prefix = StringUtils.defaultIfBlank(ep.getAuthHeaderPrefix(), "Bearer ");
                        h.add(name, prefix + ep.getApiKey());
                    }
                })
                .bodyValue(body)
                // 这里直接拿 SSE 的 text chunks（每条一般像 "data: {...}\n\n"）
                .retrieve()
                .bodyToFlux(String.class)
                // 解析成 delta（你按 Dify 事件格式补一下）
                .flatMap(this::extractDeltaFromSseChunk)
                .onErrorResume(e -> Flux.error(e));
    }

    @Override
    public String call(ChatRequest req, LlmEndpoint ep, LlmModel model, List<Message> messages) {
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
                // 你可以改为解析 JSON 后取 answer 字段
                .block();
    }

    private Map<String, Object> buildDifyBody(ChatRequest req, LlmEndpoint ep, LlmModel model,
                                             List<Message> messages, boolean stream) {
        Map<String, Object> body = new HashMap<>();
        // ⚠️ Dify 的字段你按你的 Dify “chat-messages” 接口补齐：
        // e.g. inputs, query, response_mode(streaming/blocking), conversation_id, user 等
        body.put("response_mode", stream ? "streaming" : "blocking");
        body.put("query", StringUtils.defaultString(req.getPrompt()));
        body.put("user", StringUtils.defaultIfBlank(req.getUserId().toString(), "anonymous"));
        // 如果你要把历史塞进去，Dify 可能需要 conversation_id 或者自己维护上下文
        return body;
    }

    private Flux<String> extractDeltaFromSseChunk(String chunk) {
        // chunk 可能包含多行：event: ...\n data: {...}\n\n
        // 这里写一个最保守解析：找到所有 "data:" 行，解析 JSON，抽出增量字段
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

            // 这里按 Dify 实际返回字段改：
            // 常见是 node.get("answer") 或者 node.get("delta") / node.get("text")
            JsonNode answer = node.get("answer");
            if (answer != null && answer.isTextual()) {
                return Flux.just(answer.asText());
            }
            return Flux.empty();
        } catch (Exception e) {
            // 不要因为单条解析失败就中断整个流
            return Flux.empty();
        }
    }
}