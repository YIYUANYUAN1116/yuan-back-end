package com.yuan.ai.domain.bo;

import com.yuan.ai.domain.LlmEndpoint;
import com.yuan.common.core.validate.AddGroup;
import com.yuan.common.core.validate.EditGroup;
import io.github.linpeilie.annotations.AutoMapper;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * llm_endpoint业务对象 llm_endpoint
 *
 * @author yuan
 * @date Thu Feb 26 21:44:21 CST 2026
 */
@Data

@AutoMapper(target = LlmEndpoint.class, reverseConvertGenerate = false)
public class LlmEndpointBo implements Serializable {

    private Long id;

    /**
     * tenantId
     */
    private String tenantId;
    /**
     * front-end selection key
     */
    private String endpointCode;

    private String endpointName;

    private Long providerId;

    private String providerName;
    /**
     * OpenAI-compatible baseUrl like https://api.openai.com or http://localhost:11434/v1
     */
    @NotBlank(message = "接入地址不能为空", groups = { AddGroup.class, EditGroup.class })
    private String baseUrl;
    /**
     * DEMO ONLY; prod should use api_key_ref + secret manager
     */
    private String apiKey;
    /**
     * defaultModelId
     */
    private Long defaultModelId;
    /**
     * enabled
     */
    private String status;
    /**
     * priority
     */
    private Integer priority;
    /**
     * {"cheap":true,"think":true}
     */
    private String tagsJson;
    /**
     * createTime
     */
    private LocalDateTime createTime;
    /**
     * updateTime
     */
    private LocalDateTime updateTime;

    private String chatCompletionsPath;
    private String embeddingsPath;
    private String authHeaderName;
    private String authHeaderPrefix;
    private String extraHeadersJson;
    private String extraParamsJson;
}
