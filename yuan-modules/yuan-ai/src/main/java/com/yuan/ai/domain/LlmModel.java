package com.yuan.ai.domain;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import java.io.Serializable;

/**
 * llm_model对象 llm_model
 *
 * @author yuan
 * @date Thu Feb 26 21:44:17 CST 2026
 */
@Data
@TableName("llm_model")
public class LlmModel implements Serializable {


    /**
     * id
     */
        @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * providerCode
     */
    private String providerCode;

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
    private Integer enabled;


}
