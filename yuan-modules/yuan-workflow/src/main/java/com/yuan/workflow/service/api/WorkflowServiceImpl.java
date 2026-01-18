package com.yuan.workflow.service.api;

import com.yuan.workflow.annotation.FillWfCmd;
import com.yuan.workflow.cmd.ApproveTaskCmd;
import com.yuan.workflow.cmd.RejectTaskCmd;
import com.yuan.workflow.cmd.RollbackToActivityCmd;
import com.yuan.workflow.cmd.RollbackToPrevCmd;
import com.yuan.workflow.cmd.StartProcessCmd;
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
    public void rollbackTo(RollbackToActivityCmd cmd) {
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
