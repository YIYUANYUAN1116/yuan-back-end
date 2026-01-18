package com.yuan.workflow.cmd;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class TransferTaskCmd extends WorkflowCmd {
    @NotNull(message = "任务Id不能为空")
    private Long taskId;

    @NotNull(message = "转办ID不能为空")
    private Long toUserId;

    private String reason;
}