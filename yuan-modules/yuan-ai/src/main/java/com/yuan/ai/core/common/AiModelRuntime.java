package com.yuan.ai.core.common;

import com.yuan.ai.domain.LlmEndpoint;
import com.yuan.ai.domain.LlmModel;
import lombok.Builder;
import lombok.Data;

/**
 * Runtime model configuration shared by chat and embedding execution.
 */
@Data
@Builder
public class AiModelRuntime {

    private String tenantId;

    private String providerCode;

    private LlmEndpoint endpoint;

    private LlmModel model;

    public Long endpointId() {
        return endpoint == null ? null : endpoint.getId();
    }

    public Long providerId() {
        return endpoint == null ? null : endpoint.getProviderId();
    }

    public String endpointCode() {
        return endpoint == null ? null : endpoint.getEndpointCode();
    }

    public String modelName() {
        return model == null ? null : model.getModelName();
    }
}
