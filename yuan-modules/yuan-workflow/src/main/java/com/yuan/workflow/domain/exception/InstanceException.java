package com.yuan.workflow.domain.exception;

import com.yuan.common.core.exception.workflow.WorkflowErrorCode;
import com.yuan.common.core.exception.workflow.WorkflowException;
import lombok.Getter;

@Getter
public class InstanceException extends WorkflowException {
  public InstanceException(WorkflowErrorCode workflowErrorCode) {
    super(workflowErrorCode);
  }
}