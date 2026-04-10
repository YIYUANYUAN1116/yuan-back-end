package com.yuan.ai.domain.bo;

import com.yuan.ai.domain.LlmModel;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;

import java.io.Serializable;

/**
 * llm_model业务对象 llm_model
 *
 * @author yuan
 * @date Thu Feb 26 21:44:17 CST 2026
 */
@Data

@AutoMapper(target = LlmModel.class, reverseConvertGenerate = false)
public class LlmModelBo implements Serializable {

    private Long id;

    private Long providerId;
    private Long endpointId;
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

    private String providerName;
    private String endpointName;

}
