package com.yuan.workflow.core.engine;

import com.yuan.workflow.cmd.*;

public interface WorkflowEngine {
    Long startProcess(StartCmd cmd);

    void approveTask(ApproveCmd approveCmd);
    void rejectTask(RejectCmd rejectCmd);

    void rollbackToPrev(RollbackToPreviousCmd rollbackToPreviousCmd);
    void rollbackToActivity(RollbackCmd rollbackToCmd);

    void withdraw(WithdrawCmd withdrawCmd);
    void transfer(TransferTaskCmd transferTaskCmd);

    void addSign(AddSignCmd addSignCmd);
}