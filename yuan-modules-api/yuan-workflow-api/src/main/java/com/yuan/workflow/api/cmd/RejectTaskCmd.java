package com.yuan.workflow.api.cmd;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class RejectTaskCmd extends WorkflowCmd {
    @NotNull(message = "任务Id不能为空")
    private Long taskId;
}