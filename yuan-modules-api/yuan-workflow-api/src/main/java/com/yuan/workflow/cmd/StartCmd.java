package com.yuan.workflow.cmd;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class StartCmd extends WorkflowCmd{
    @NotBlank(message = "流程定义key不能为空")
    private String definitionKey;
    @NotBlank(message = "业务类型不能为空")
    private String bizType;
    @NotBlank(message = "业务Id不能为空")
    private Long bizId;
    @NotBlank(message = "业务u编号不能为空")
    private String bizNo;
    /** 业务发起人（可选，代发场景用） */
    private Long starterId;
    private String starterName;
    private Long starterDeptId;
    private String starterDeptName;
    private String title;
}