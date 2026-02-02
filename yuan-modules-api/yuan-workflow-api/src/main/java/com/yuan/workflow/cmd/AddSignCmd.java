package com.yuan.workflow.cmd;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddSignCmd extends WorkflowCmd{
    @NotNull(message = "任务Id不能为空")
    private Long taskId;

    @NotNull(message = "加签ID不能为空")
    private Long addUserId;
}
