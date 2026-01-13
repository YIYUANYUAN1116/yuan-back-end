package com.yuan.common.core.exception.workflow;

import lombok.Getter;

@Getter
public class WorkflowException extends RuntimeException {

    private final WorkflowErrorCode errorCode;

    public WorkflowException(WorkflowErrorCode errorCode) {
        super(errorCode.getDefaultMessage());
        this.errorCode = errorCode;
    }

    public WorkflowException(WorkflowErrorCode errorCode, Throwable cause) {
        super(errorCode.getDefaultMessage(), cause);
        this.errorCode = errorCode;
    }
}
