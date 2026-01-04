package com.yuan.workflow.service.impl;

import com.yuan.workflow.annotation.FillWfCmd;
import com.yuan.workflow.api.cmd.ApproveTaskCmd;
import com.yuan.workflow.api.cmd.RejectTaskCmd;
import com.yuan.workflow.api.cmd.RollbackToCmd;
import com.yuan.workflow.api.cmd.RollbackToPrevCmd;
import com.yuan.workflow.api.cmd.StartProcessCmd;
import com.yuan.workflow.api.cmd.TransferTaskCmd;
import com.yuan.workflow.api.cmd.WithdrawCmd;
import com.yuan.workflow.core.engine.WorkflowEngine;
import com.yuan.workflow.service.WorkflowService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FillWfCmd
public class WorkflowServiceImpl implements WorkflowService {
    private final WorkflowEngine workflowEngine;

    @Override
    public Long startProcess(StartProcessCmd cmd) {
        return workflowEngine.startProcess(cmd);
    }

    @Override
    public void approveTask(ApproveTaskCmd cmd) {
        workflowEngine.approveTask(cmd);
    }

    @Override
    public void rejectTask(RejectTaskCmd cmd) {
        workflowEngine.rejectTask(cmd);
    }

    @Override
    public void rollbackToPrev(RollbackToPrevCmd cmd) {
        workflowEngine.rollbackToPrev(cmd);
    }

    @Override
    public void rollbackTo(RollbackToCmd cmd) {
        workflowEngine.rollbackTo(cmd);
    }

    @Override
    public void withdraw(WithdrawCmd cmd) {
        workflowEngine.withdraw(cmd);
    }

    @Override
    public void transfer(TransferTaskCmd cmd) {
        workflowEngine.transfer(cmd);
    }
}
