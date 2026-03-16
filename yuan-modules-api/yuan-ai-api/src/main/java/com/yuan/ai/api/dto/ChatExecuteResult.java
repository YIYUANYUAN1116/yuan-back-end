package com.yuan.ai.api.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ChatExecuteResult {

    private Boolean success;

    private String content;

    private Long invocationId;

    private String endpointKey;

    private String providerCode;

    private String modelName;

    private Integer latencyMs;

    private String errorMessage;
}