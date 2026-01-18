package com.yuan.workflow.core.engine;

import com.yuan.workflow.cmd.ApproveTaskCmd;
import com.yuan.workflow.cmd.RejectTaskCmd;
import com.yuan.workflow.cmd.RollbackToActivityCmd;
import com.yuan.workflow.cmd.RollbackToPrevCmd;
import com.yuan.workflow.cmd.StartProcessCmd;
import com.yuan.workflow.cmd.TransferTaskCmd;
import com.yuan.workflow.cmd.WithdrawCmd;
import com.yuan.workflow.core.engine.handler.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class SimpleWorkflowEngine implements WorkflowEngine {

    private final StartProcessHandler startProcessHandler;
    private final ApproveTaskHandler approveTaskHandler;
    private final RejectTaskHandler rejectTaskHandler;
    private final RollbackToActivityHandler rollbackToActivityHandler;
    private final WithdrawHandler withdrawHandler;
    private final TransferHandler transferHandler;

    /**
     * start
     * @param cmd
     * @return
     */
    @Override
    public Long startProcess(StartProcessCmd cmd) {
        return startProcessHandler.handle(cmd);
    }

    /**
     * finish
     * @param cmd
     */
    @Override
    public void approveTask(ApproveTaskCmd cmd) {
        approveTaskHandler.handle(cmd);
    }

    /**
     * reject
     * @param cmd
     */
    @Override
    public void rejectTask(RejectTaskCmd cmd) {
        rejectTaskHandler.handle(cmd);
    }

    @Override public void rollbackToPrev(RollbackToPrevCmd cmd) { }
    @Override public void rollbackToActivity(RollbackToActivityCmd cmd) {
        rollbackToActivityHandler.handle(cmd);
    }
    @Override public void withdraw(WithdrawCmd cmd) {
        withdrawHandler.handle(cmd);
    }
    @Override public void transfer(TransferTaskCmd cmd) {
        transferHandler.handle(cmd);
    }

}
