package com.yuan.ai.domain.bo;

import com.yuan.ai.domain.LlmProvider;
import com.yuan.core.domain.BaseEntity;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import lombok.EqualsAndHashCode;
import jakarta.validation.constraints.*;
import java.time.LocalDateTime;
import java.io.Serializable;
import com.yuan.common.core.validate.AddGroup;
import com.yuan.common.core.validate.EditGroup;
import java.io.Serializable;
import java.io.Serializable;
import com.yuan.common.core.validate.AddGroup;
import com.yuan.common.core.validate.EditGroup;

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
    @NotBlank(message = "OPENAI_COMPAT/AZURE/OLLAMA/SELF_HOST不能为空", groups = { AddGroup.class, EditGroup.class })
    private String code;
    /**
     * name
     */
    @NotBlank(message = "name不能为空", groups = { AddGroup.class, EditGroup.class })
    private String name;
    /**
     * openai_compat/azure/ollama/...
     */
    @NotBlank(message = "openai_compat/azure/ollama/...不能为空", groups = { AddGroup.class, EditGroup.class })
    private String protocol;
    /**
     * createTime
     */
    @NotNull(message = "createTime不能为空", groups = { AddGroup.class, EditGroup.class })
    private LocalDateTime createTime;

}
