package com.yuan.workflow.core.exception;

import com.yuan.common.core.exception.workflow.WorkflowErrorCode;
import com.yuan.common.core.exception.workflow.WorkflowException;
import lombok.Getter;

@Getter
public class ProcessDefinitionParseException extends WorkflowException {
    private final Long wfDefinitionId;
    private final Integer version;
    private final String nodeId;
    public ProcessDefinitionParseException(WorkflowErrorCode errorCode,Long wfDefinitionId, Integer version) {
        super(errorCode);
        this.wfDefinitionId = wfDefinitionId;
        this.version = version;
        this.nodeId = null;
    }

    public ProcessDefinitionParseException(Long wfDefinitionId, Integer version) {
        super(WorkflowErrorCode.WF_DEFINITION_PARSE_ERROR);
        this.wfDefinitionId = wfDefinitionId;
        this.version = version;
        this.nodeId = null;
    }
    public ProcessDefinitionParseException(WorkflowErrorCode errorCode,Long wfDefinitionId, Integer version,String nodeId) {
        super(errorCode);
        this.wfDefinitionId = wfDefinitionId;
        this.version = version;
        this.nodeId = nodeId;
    }
}
