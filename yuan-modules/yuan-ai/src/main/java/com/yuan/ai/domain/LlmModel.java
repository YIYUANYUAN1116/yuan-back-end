package com.yuan.ai.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.yuan.core.domain.BaseEntity;
import lombok.Data;

/**
 * llm_model对象 llm_model
 *
 * @author yuan
 * @date Thu Feb 26 21:44:17 CST 2026
 */
@Data
@TableName("llm_model")
public class LlmModel extends BaseEntity {


    /**
     * id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private String tenantId;
    private Long endpointId;
    private Long providerId;

    /**
     * remote model name, e.g. gpt-4o-mini
     */
    private String modelName;

    /**
     * displayName
     */
    private String displayName;

    /**
     * {"stream":true,"json":true,"tools":true,"vision":false,"thinking":false}
     */
    private String capabilityJson;

    /**
     * contextWindow
     */
    private Integer contextWindow;

    /**
     * enabled
     */
    private String status;


}
