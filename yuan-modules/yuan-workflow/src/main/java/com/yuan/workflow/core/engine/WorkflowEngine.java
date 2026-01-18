package com.yuan.workflow.core.engine;

import com.yuan.workflow.cmd.*;

public interface WorkflowEngine {
    Long startProcess(StartProcessCmd cmd);

    void approveTask(ApproveTaskCmd approveTaskCmd);
    void rejectTask(RejectTaskCmd rejectTaskCmd);

    void rollbackToPrev(RollbackToPrevCmd rollbackToPrevCmd);
    void rollbackToActivity(RollbackToActivityCmd rollbackToCmd);

    void withdraw(WithdrawCmd withdrawCmd);
    void transfer(TransferTaskCmd transferTaskCmd);
}