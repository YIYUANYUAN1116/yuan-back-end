package com.yuan.workflow.api;

import com.yuan.workflow.cmd.ApproveCmd;
import com.yuan.workflow.cmd.RejectCmd;
import com.yuan.workflow.cmd.RollbackCmd;
import com.yuan.workflow.cmd.RollbackToPreviousCmd;
import com.yuan.workflow.cmd.StartCmd;
import com.yuan.workflow.cmd.TransferTaskCmd;
import com.yuan.workflow.cmd.WithdrawCmd;

public interface WorkflowService {
    Long startProcess(StartCmd cmd);

    void approveTask(ApproveCmd approveCmd);
    void rejectTask(RejectCmd rejectCmd);

    void rollbackToPrev(RollbackToPreviousCmd rollbackToPreviousCmd);
    void rollbackTo(RollbackCmd rollbackToCmd);

    void withdraw(WithdrawCmd withdrawCmd);
    void transfer(TransferTaskCmd transferTaskCmd);
}
