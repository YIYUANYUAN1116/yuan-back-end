package com.yuan.workflow.core.engine;

import com.yuan.workflow.domain.StartProcessCmd;

public interface WorkflowEngine {
    Long startProcess(StartProcessCmd cmd);

    void approveTask(Long taskId, String comment);
    void rejectTask(Long taskId, String comment);

    void rollbackToPrev(Long taskId, String comment);
    void rollbackTo(Long taskId, String targetNodeId, String comment);

    void withdraw(Long instanceId, String comment);
    void transfer(Long taskId, Long toUserId, String reason);
}