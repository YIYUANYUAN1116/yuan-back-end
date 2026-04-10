package com.yuan.ai.domain.bo;

import com.yuan.ai.domain.LlmPolicy;
import com.yuan.core.domain.BaseEntity;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import lombok.EqualsAndHashCode;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.io.Serializable;
import com.yuan.common.core.validate.AddGroup;
import com.yuan.common.core.validate.EditGroup;

/**
 * llm_policy业务对象 llm_policy
 *
 * @author yuan
 * @date Thu Feb 26 21:44:28 CST 2026
 */
@Data

@AutoMapper(target = LlmPolicy.class, reverseConvertGenerate = false)
public class LlmPolicyBo implements Serializable {

    private Long id;

    /**
     * tenantId
     */
    @NotBlank(message = "tenantId不能为空", groups = { AddGroup.class, EditGroup.class })
    private String tenantId;
    /**
     * TENANT/USER/APP
     */
    @NotBlank(message = "TENANT/USER/APP不能为空", groups = { AddGroup.class, EditGroup.class })
    private String scopeType;
    /**
     * scopeId
     */
    @NotBlank(message = "scopeId不能为空", groups = { AddGroup.class, EditGroup.class })
    private String scopeId;
    /**
     * dailyCalls
     */
    @NotNull(message = "dailyCalls不能为空", groups = { AddGroup.class, EditGroup.class })
    private Integer dailyCalls;
    /**
     * dailyTokens
     */
    @NotNull(message = "dailyTokens不能为空", groups = { AddGroup.class, EditGroup.class })
    private Integer dailyTokens;
    /**
     * concurrency
     */
    @NotNull(message = "concurrency不能为空", groups = { AddGroup.class, EditGroup.class })
    private Integer concurrency;
    /**
     * allowEndpoints
     */
    private String allowEndpoints;
    /**
     * enabled
     */
    @NotNull(message = "enabled不能为空", groups = { AddGroup.class, EditGroup.class })
    private Integer enabled;

}
