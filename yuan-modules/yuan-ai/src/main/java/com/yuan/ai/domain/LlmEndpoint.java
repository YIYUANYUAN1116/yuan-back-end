package com.yuan.ai.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.yuan.core.domain.BaseEntity;
import lombok.Data;

/**
 * llm_endpoint对象 llm_endpoint
 *
 * @author yuan
 * @date Thu Feb 26 21:44:21 CST 2026
 */
@Data
@TableName("llm_endpoint")
public class LlmEndpoint extends BaseEntity {


    /**
     * id
     */
        @TableId(value = "id", type = IdType.AUTO)
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
    /**
     * OpenAI-compatible baseUrl like https://api.openai.com or http://localhost:11434/v1
     */
    private String baseUrl;

    private String chatCompletionsPath;
    private String embeddingsPath;
    private String authHeaderName;
    private String authHeaderPrefix;
    private String extraHeadersJson;
    private String extraParamsJson;


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



}
