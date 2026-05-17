package com.yuan.ai.core.kb.embedding;

import com.yuan.ai.domain.LlmEndpoint;
import com.yuan.ai.domain.LlmModel;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class VolcengineMultimodalEmbeddingClient implements KbEmbeddingClient{

    private final RestClient.Builder restClientBuilder;

    @Override
    public boolean supports(String providerCode) {
        return "VOLCENGINE".equals(providerCode);
    }

    @Override
    public float[] embed(LlmEndpoint endpoint, LlmModel model, String text) {
        RestClient restClient = restClientBuilder
                .baseUrl(endpoint.getBaseUrl())
                .defaultHeader("Content-Type", "application/json")
                .defaultHeader("Authorization", "Bearer " + endpoint.getApiKey())
                .build();

        Map<String, Object> body = Map.of(
                "model", model.getModelName(),
                "input", List.of(
                        Map.of(
                                "type", "text",
                                "text", text
                        )
                )
        );

        VolcEmbeddingResponse response = restClient.post()
                .uri(endpoint.getEmbeddingsPath())
                .body(body)
                .retrieve()
                .body(VolcEmbeddingResponse.class);

        if (response == null
                || response.getData() == null
                || response.getData().getEmbedding() == null) {
            throw new IllegalStateException("Volcengine embedding response is empty");
        }

        return toFloatArray(response.getData().getEmbedding());
    }

    private float[] toFloatArray(List<Double> list) {
        float[] arr = new float[list.size()];
        for (int i = 0; i < list.size(); i++) {
            arr[i] = list.get(i).floatValue();
        }
        return arr;
    }


    @Data
    public static class VolcEmbeddingResponse {
        private EmbeddingData data;
    }

    @Data
    public static class EmbeddingData {
        private List<Double> embedding;
    }
}