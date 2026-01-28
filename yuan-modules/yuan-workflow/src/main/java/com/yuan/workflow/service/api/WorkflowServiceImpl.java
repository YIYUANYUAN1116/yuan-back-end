package com.yuan.workflow.service.api;

import com.yuan.workflow.annotation.FillWfCmd;
import com.yuan.workflow.cmd.ApproveCmd;
import com.yuan.workflow.cmd.RejectCmd;
import com.yuan.workflow.cmd.RollbackCmd;
import com.yuan.workflow.cmd.RollbackToPreviousCmd;
import com.yuan.workflow.cmd.StartCmd;
import com.yuan.workflow.cmd.TransferTaskCmd;
import com.yuan.workflow.cmd.WithdrawCmd;
import com.yuan.workflow.core.engine.WorkflowEngine;
import com.yuan.workflow.api.WorkflowService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FillWfCmd
public class WorkflowServiceImpl implements WorkflowService {
    private final WorkflowEngine workflowEngine;

    @Override
    public Long startProcess(StartCmd cmd) {
        return workflowEngine.startProcess(cmd);
    }

    @Override
    public void approveTask(ApproveCmd cmd) {
        workflowEngine.approveTask(cmd);
    }

    @Override
    public void rejectTask(RejectCmd cmd) {
        workflowEngine.rejectTask(cmd);
    }

    @Override
    public void rollbackToPrev(RollbackToPreviousCmd cmd) {
        workflowEngine.rollbackToPrev(cmd);
    }

    @Override
    public void rollbackTo(RollbackCmd cmd) {
        workflowEngine.rollbackToActivity(cmd);
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
