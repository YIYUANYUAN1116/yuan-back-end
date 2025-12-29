package com.yuan.workflow.core.engine;

import com.yuan.workflow.api.cmd.*;

public interface WorkflowEngine {
    Long startProcess(StartProcessCmd cmd);

    void approveTask(ApproveTaskCmd approveTaskCmd);
    void rejectTask(RejectTaskCmd rejectTaskCmd);

    void rollbackToPrev(RollbackToPrevCmd rollbackToPrevCmd);
    void rollbackTo(RollbackToCmd rollbackToCmd);

    void withdraw(WithdrawCmd withdrawCmd);
    void transfer(TransferTaskCmd transferTaskCmd);
}