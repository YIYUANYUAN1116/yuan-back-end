package com.yuan.workflow.api;

import com.yuan.workflow.cmd.ApproveTaskCmd;
import com.yuan.workflow.cmd.RejectTaskCmd;
import com.yuan.workflow.cmd.RollbackToActivityCmd;
import com.yuan.workflow.cmd.RollbackToPrevCmd;
import com.yuan.workflow.cmd.StartProcessCmd;
import com.yuan.workflow.cmd.TransferTaskCmd;
import com.yuan.workflow.cmd.WithdrawCmd;

public interface WorkflowService {
    Long startProcess(StartProcessCmd cmd);

    void approveTask(ApproveTaskCmd approveTaskCmd);
    void rejectTask(RejectTaskCmd rejectTaskCmd);

    void rollbackToPrev(RollbackToPrevCmd rollbackToPrevCmd);
    void rollbackTo(RollbackToActivityCmd rollbackToCmd);

    void withdraw(WithdrawCmd withdrawCmd);
    void transfer(TransferTaskCmd transferTaskCmd);
}
