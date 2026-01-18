package com.yuan.workflow.cmd;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ApproveTaskCmd extends WorkflowCmd {
    @NotNull(message = "任务Id不能为空")
    private Long taskId;
}