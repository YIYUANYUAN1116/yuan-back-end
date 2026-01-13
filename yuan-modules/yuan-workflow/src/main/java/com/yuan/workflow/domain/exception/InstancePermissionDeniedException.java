package com.yuan.workflow.domain.exception;

import com.yuan.common.core.exception.workflow.WorkflowErrorCode;
import com.yuan.common.core.exception.workflow.WorkflowException;
import lombok.Getter;

@Getter
public class InstancePermissionDeniedException extends WorkflowException {
    private final Long instanceId;
    private final Long operatorId;
    public InstancePermissionDeniedException(Long instanceId, Long operatorId) {
        super(WorkflowErrorCode.WF_INSTANCE_NOT_STARTER);
        this.instanceId = instanceId;
        this.operatorId = operatorId;
    }
}