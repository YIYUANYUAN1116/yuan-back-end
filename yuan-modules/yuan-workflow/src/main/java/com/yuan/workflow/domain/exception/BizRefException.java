package com.yuan.workflow.domain.exception;

import com.yuan.common.core.exception.workflow.WorkflowErrorCode;
import com.yuan.common.core.exception.workflow.WorkflowException;
import lombok.Getter;

@Getter
public class BizRefException extends WorkflowException {
    public BizRefException(WorkflowErrorCode workflowErrorCode) {
        super(workflowErrorCode);
    }
}
