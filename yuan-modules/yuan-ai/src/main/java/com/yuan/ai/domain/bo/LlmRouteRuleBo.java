package com.yuan.ai.domain.bo;

import com.yuan.ai.domain.LlmRouteRule;
import com.yuan.common.core.validate.AddGroup;
import com.yuan.common.core.validate.EditGroup;
import io.github.linpeilie.annotations.AutoMapper;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serializable;

/**
 * llm_route_rule业务对象 llm_route_rule
 *
 * @author yuan
 * @date Thu Feb 26 21:44:32 CST 2026
 */
@Data

@AutoMapper(target = LlmRouteRule.class, reverseConvertGenerate = false)
public class LlmRouteRuleBo implements Serializable {

    private Long id;

    /**
     * tenantId
     */
    @NotBlank(message = "tenantId不能为空", groups = { AddGroup.class, EditGroup.class })
    private String tenantId;
    /**
     * ruleName
     */
    @NotBlank(message = "ruleName不能为空", groups = { AddGroup.class, EditGroup.class })
    private String ruleName;
    /**
     * {"enableThinking":true,"stream":true}
     */
    @NotBlank(message = "matchJson不能为空", groups = { AddGroup.class, EditGroup.class })
    private String matchJson;
    /**
     * ["tenantA_openai_4o","tenantA_deepseek_r1"]
     */
    @NotBlank(message = "candidateEndpoints不能为空", groups = { AddGroup.class, EditGroup.class })
    private String candidateEndpoints;
    /**
     * PRIORITY/COST_MIN/LATENCY_MIN
     */
    @NotBlank(message = "PRIORITY/COST_MIN/LATENCY_MIN不能为空", groups = { AddGroup.class, EditGroup.class })
    private String strategy;
    /**
     * enabled
     */
    @NotNull(message = "enabled不能为空", groups = { AddGroup.class, EditGroup.class })
    private Integer enabled;
    /**
     * priority
     */
    @NotNull(message = "priority不能为空", groups = { AddGroup.class, EditGroup.class })
    private Integer priority;

}
