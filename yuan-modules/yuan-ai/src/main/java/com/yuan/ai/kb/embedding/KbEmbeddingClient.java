package com.yuan.ai.kb.embedding;

import com.yuan.ai.domain.LlmEndpoint;
import com.yuan.ai.domain.LlmModel;

public interface KbEmbeddingClient {
    boolean supports(String providerCode);
    float[] embed(LlmEndpoint endpoint, LlmModel model, String text);
}
