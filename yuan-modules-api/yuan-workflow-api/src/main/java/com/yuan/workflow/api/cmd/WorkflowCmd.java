package com.yuan.workflow.api.cmd;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public abstract class WorkflowCmd {

    /** 操作人（当前用户） */
    @NotBlank(message = "操作人不能为空")
    private Long operatorUserId;

    /** 备注 / 审批意见 */
    private String comment;
}

