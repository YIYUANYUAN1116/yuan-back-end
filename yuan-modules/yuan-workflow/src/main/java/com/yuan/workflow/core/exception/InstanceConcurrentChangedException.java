package com.yuan.workflow.core.exception;

import com.yuan.common.core.exception.workflow.WorkflowErrorCode;
import com.yuan.common.core.exception.workflow.WorkflowException;
import com.yuan.workflow.domain.enums.InstanceStatus;
import lombok.Getter;

@Getter
public class InstanceConcurrentChangedException extends WorkflowException {
    private final Long instanceId;
    private final InstanceStatus expectStatus;

    public InstanceConcurrentChangedException(Long instanceId, InstanceStatus expectStatus) {
        super(WorkflowErrorCode.WF_INSTANCE_STATUS_INVALID);
        this.instanceId = instanceId;
        this.expectStatus = expectStatus;
    }

    public InstanceConcurrentChangedException(InstanceStatus expectStatus) {
        super(WorkflowErrorCode.WF_INSTANCE_STATUS_INVALID);
        this.instanceId = null;
        this.expectStatus = expectStatus;
    }
}
