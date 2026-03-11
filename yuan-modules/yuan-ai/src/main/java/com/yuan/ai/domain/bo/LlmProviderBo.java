package com.yuan.ai.domain.bo;

import com.yuan.ai.domain.LlmProvider;
import com.yuan.common.core.validate.AddGroup;
import com.yuan.common.core.validate.EditGroup;
import io.github.linpeilie.annotations.AutoMapper;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * llm_provider业务对象 llm_provider
 *
 * @author yuan
 * @date Thu Feb 26 21:44:12 CST 2026
 */
@Data

@AutoMapper(target = LlmProvider.class, reverseConvertGenerate = false)
public class LlmProviderBo implements Serializable {

    private Long id;

    /**
     * OPENAI_COMPAT/AZURE/OLLAMA/SELF_HOST
     */
    @NotBlank(message = "code不能为空", groups = { AddGroup.class, EditGroup.class })
    private String code;
    /**
     * name
     */
    @NotBlank(message = "name不能为空", groups = { AddGroup.class, EditGroup.class })
    private String name;
    /**
     * openai_compat/azure/ollama/...
     */
    private String protocol;

    private String remark;

    private LocalDateTime createTime;
}
