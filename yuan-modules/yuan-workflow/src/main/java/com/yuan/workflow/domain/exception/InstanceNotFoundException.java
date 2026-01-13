package com.yuan.workflow.domain.exception;

import com.yuan.common.core.exception.workflow.WorkflowErrorCode;
import com.yuan.common.core.exception.workflow.WorkflowException;
import lombok.Getter;

@Getter
public class InstanceNotFoundException extends WorkflowException {
    public InstanceNotFoundException() {
        super(WorkflowErrorCode.WF_INSTANCE_NOT_FOUND);
    }
}
