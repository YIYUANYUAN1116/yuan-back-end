package com.yuan.workflow.core.exception;

import com.yuan.common.core.exception.workflow.WorkflowErrorCode;
import com.yuan.common.core.exception.workflow.WorkflowException;
import lombok.Getter;

@Getter
public class WorkflowEngineException extends WorkflowException {
    public WorkflowEngineException() {
        super(WorkflowErrorCode.WF_ENGINE_ERROR);
    }

    public WorkflowEngineException(String message) {
        super(message);
    }
}
