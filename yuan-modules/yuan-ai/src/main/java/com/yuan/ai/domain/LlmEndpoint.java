package com.yuan.ai.domain;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import java.time.LocalDateTime;
import java.io.Serializable;

/**
 * llm_endpoint对象 llm_endpoint
 *
 * @author yuan
 * @date Thu Feb 26 21:44:21 CST 2026
 */
@Data
@TableName("llm_endpoint")
public class LlmEndpoint implements Serializable {


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
    private String endpointKey;

    /**
     * providerCode
     */
    private String providerCode;

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
    private Integer enabled;

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


}
