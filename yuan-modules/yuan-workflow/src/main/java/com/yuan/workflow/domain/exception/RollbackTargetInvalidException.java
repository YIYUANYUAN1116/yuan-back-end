package com.yuan.workflow.domain.exception;

import com.yuan.common.core.exception.workflow.WorkflowErrorCode;
import com.yuan.common.core.exception.workflow.WorkflowException;
import lombok.Getter;

@Getter
public class RollbackTargetInvalidException extends WorkflowException {

    public RollbackTargetInvalidException() {
        super(WorkflowErrorCode.WF_ROLLBACK_TARGET_INVALID);

    }
}
