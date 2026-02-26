package com.yuan.ai.domain.bo;

import com.yuan.ai.domain.LlmModel;
import com.yuan.common.core.validate.AddGroup;
import com.yuan.common.core.validate.EditGroup;
import io.github.linpeilie.annotations.AutoMapper;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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

    /**
     * providerCode
     */
    @NotBlank(message = "providerCode不能为空", groups = { AddGroup.class, EditGroup.class })
    private String providerCode;
    /**
     * remote model name, e.g. gpt-4o-mini
     */
    @NotBlank(message = "remote model name, e.g. gpt-4o-mini不能为空", groups = { AddGroup.class, EditGroup.class })
    private String modelName;
    /**
     * displayName
     */
    private String displayName;
    /**
     * {"stream":true,"json":true,"tools":true,"vision":false,"thinking":false}
     */
    @NotBlank(message = "capabilityJson不能为空", groups = { AddGroup.class, EditGroup.class })
    private String capabilityJson;
    /**
     * contextWindow
     */
    private Integer contextWindow;
    /**
     * enabled
     */
    @NotNull(message = "enabled不能为空", groups = { AddGroup.class, EditGroup.class })
    private Integer enabled;

}
