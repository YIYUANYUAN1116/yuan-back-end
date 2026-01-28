package com.yuan.workflow.cmd;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class RollbackCmd extends WorkflowCmd {
    @NotNull(message = "任务Id不能为空")
    private Long taskId;

    @NotBlank(message = "目标节点不能为空")
    private String targetActivityId;
}