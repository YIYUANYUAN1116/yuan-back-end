package com.yuan.ai.core.kb.embedding;

import com.yuan.ai.core.common.AiModelRuntime;
import com.yuan.ai.domain.LlmEndpoint;
import com.yuan.ai.domain.LlmModel;

public interface KbEmbeddingClient {
    boolean supports(String providerCode);

    default float[] embed(AiModelRuntime runtime, String text) {
        return embed(runtime.getEndpoint(), runtime.getModel(), text);
    }

    float[] embed(LlmEndpoint endpoint, LlmModel model, String text);
}
