package com.yuan.workflow.domain.exception;

import com.yuan.common.core.exception.workflow.WorkflowErrorCode;
import com.yuan.common.core.exception.workflow.WorkflowException;
import lombok.Getter;

@Getter
public class TaskPermissionDeniedException extends WorkflowException {
    private final Long taskId;
    private final Long operatorId;
    public TaskPermissionDeniedException(Long taskId, Long operatorId) {
        super(WorkflowErrorCode.WF_TASK_NO_PERMISSION);
        this.taskId = taskId;
        this.operatorId = operatorId;
    }
}