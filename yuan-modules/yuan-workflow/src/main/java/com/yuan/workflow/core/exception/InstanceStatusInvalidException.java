package com.yuan.workflow.core.exception;

import com.yuan.common.core.exception.workflow.WorkflowErrorCode;
import com.yuan.common.core.exception.workflow.WorkflowException;
import com.yuan.workflow.domain.enums.NodeStatus;
import lombok.Getter;

@Getter
public class InstanceStatusInvalidException extends WorkflowException {
    private final Long instanceId;
    private final NodeStatus expectStatus;

    public InstanceStatusInvalidException(Long instanceId, NodeStatus expectStatus) {
        super(WorkflowErrorCode.WF_INSTANCE_STATUS_INVALID);
        this.instanceId = instanceId;
        this.expectStatus = expectStatus;
    }
}
