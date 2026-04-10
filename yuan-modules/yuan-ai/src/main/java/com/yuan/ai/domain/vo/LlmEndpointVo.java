package com.yuan.ai.domain.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.yuan.ai.domain.LlmEndpoint;
import io.github.linpeilie.annotations.AutoMapper;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;


/**
 * llm_endpoint视图对象 llm_endpoint
 *
 * @author yuan
 * @date Thu Feb 26 21:44:21 CST 2026
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = LlmEndpoint.class)
public class LlmEndpointVo implements Serializable {
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
     private Long id;
    /**
     * tenantId
     */
    @ExcelProperty(value = "tenantId")
    private String tenantId;
    /**
     * front-end selection key
     */
    @ExcelProperty(value = "front-end selection key")
    private String endpointCode;

    private String endpointName;
    /**
     * providerCode
     */
    private String providerName;
    private Long providerId;
    /**
     * OpenAI-compatible baseUrl like https://api.openai.com or http://localhost:11434/v1
     */
    @ExcelProperty(value = "OpenAI-compatible baseUrl like https://api.openai.com or http://localhost:11434/v1")
    private String baseUrl;
    /**
     * DEMO ONLY; prod should use api_key_ref + secret manager
     */
    @ExcelProperty(value = "DEMO ONLY; prod should use api_key_ref + secret manager")
    private String apiKey;
    /**
     * defaultModelId
     */
    @ExcelProperty(value = "defaultModelId")
    private Long defaultModelId;
    /**
     * enabled
     */
    @ExcelProperty(value = "status")
    private String status;
    /**
     * priority
     */
    @ExcelProperty(value = "priority")
    private Integer priority;
    /**
     * {"cheap":true,"think":true}
     */
    @ExcelProperty(value = "tagsJson")
    private String tagsJson;
    /**
     * createTime
     */
    @ExcelProperty(value = "createTime")
    private LocalDateTime createTime;
    /**
     * updateTime
     */
    @ExcelProperty(value = "updateTime")
    private LocalDateTime updateTime;

    private String chatCompletionsPath;
    private String embeddingsPath;
    private String authHeaderName;
    private String authHeaderPrefix;
    private String extraHeadersJson;
    private String extraParamsJson;
}
