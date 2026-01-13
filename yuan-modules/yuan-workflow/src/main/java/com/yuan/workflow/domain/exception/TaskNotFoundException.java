package com.yuan.workflow.domain.exception;

import com.yuan.common.core.exception.workflow.WorkflowErrorCode;
import com.yuan.common.core.exception.workflow.WorkflowException;
import lombok.Getter;

@Getter
public class TaskNotFoundException extends WorkflowException {
    public TaskNotFoundException() {
        super(WorkflowErrorCode.WF_TASK_NOT_FOUND);
    }
}
