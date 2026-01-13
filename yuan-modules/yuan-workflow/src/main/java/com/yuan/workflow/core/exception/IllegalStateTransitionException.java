package com.yuan.workflow.core.exception;

import com.yuan.common.core.exception.workflow.WorkflowErrorCode;
import com.yuan.common.core.exception.workflow.WorkflowException;
import lombok.Getter;

@Getter
public class IllegalStateTransitionException  extends WorkflowException {
    public IllegalStateTransitionException() {
        super(WorkflowErrorCode.WF_TASK_ALREADY_TRANSFERRED);
    }
}
