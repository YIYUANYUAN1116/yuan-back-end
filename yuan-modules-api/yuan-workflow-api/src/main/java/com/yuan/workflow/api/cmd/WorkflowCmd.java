package com.yuan.workflow.api.cmd;

import lombok.Data;

import java.util.Map;

@Data
public abstract class WorkflowCmd {

    /** 操作人（当前用户） */
    private Long operatorId;

    private String operatorName;

    private String tenantId;

    /** 备注 / 审批意见 */
    private String comment;

    Map<String, Object> variables;
}

