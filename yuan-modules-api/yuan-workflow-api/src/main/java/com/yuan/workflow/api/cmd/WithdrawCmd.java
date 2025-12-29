package com.yuan.workflow.api.cmd;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class WithdrawCmd extends WorkflowCmd {
    @NotNull(message = "实例ID 不能为空")
    private Long instanceId;
}