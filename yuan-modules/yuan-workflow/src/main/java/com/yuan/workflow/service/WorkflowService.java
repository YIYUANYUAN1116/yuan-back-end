package com.yuan.workflow.service;

import com.yuan.workflow.api.cmd.ApproveTaskCmd;
import com.yuan.workflow.api.cmd.RejectTaskCmd;
import com.yuan.workflow.api.cmd.RollbackToCmd;
import com.yuan.workflow.api.cmd.RollbackToPrevCmd;
import com.yuan.workflow.api.cmd.StartProcessCmd;
import com.yuan.workflow.api.cmd.TransferTaskCmd;
import com.yuan.workflow.api.cmd.WithdrawCmd;

public interface WorkflowService {
    Long startProcess(StartProcessCmd cmd);

    void approveTask(ApproveTaskCmd approveTaskCmd);
    void rejectTask(RejectTaskCmd rejectTaskCmd);

    void rollbackToPrev(RollbackToPrevCmd rollbackToPrevCmd);
    void rollbackTo(RollbackToCmd rollbackToCmd);

    void withdraw(WithdrawCmd withdrawCmd);
    void transfer(TransferTaskCmd transferTaskCmd);
}
