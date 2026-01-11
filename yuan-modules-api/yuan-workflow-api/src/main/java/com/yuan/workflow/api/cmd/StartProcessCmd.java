package com.yuan.workflow.api.cmd;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.Map;

@Data
public class StartProcessCmd extends WorkflowCmd{
    @NotBlank(message = "流程定义key不能为空")
    private String definitionKey;
    private String bizType;
    private Long bizId;
    private String bizNo;
    /** 业务发起人（可选，代发场景用） */
    private Long starterUserId;
    private String starterUserName;
    private Long starterDeptId;
    private String starterDeptName;
    private Map<String, Object> variables;
}