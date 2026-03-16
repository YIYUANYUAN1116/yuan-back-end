package com.yuan.ai.api.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AiTemplateExecuteResult {

    private Boolean success;

    /**
     * 最终渲染后的 prompt
     */
    private String prompt;

    /**
     * 模型返回内容
     */
    private String content;

    /**
     * 调用记录ID
     */
    private Long invocationId;

    /**
     * 使用的 endpoint
     */
    private String endpointKey;

    /**
     * 使用的 provider
     */
    private String providerCode;

    /**
     * 使用的 model
     */
    private String modelName;

    /**
     * 耗时
     */
    private Integer latencyMs;

    /**
     * 错误信息
     */
    private String errorMessage;
}