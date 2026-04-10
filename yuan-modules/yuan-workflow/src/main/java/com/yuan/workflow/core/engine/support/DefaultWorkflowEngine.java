package com.yuan.workflow.core.engine.support;

import com.yuan.workflow.cmd.AddSignCmd;
import com.yuan.workflow.cmd.ApproveCmd;
import com.yuan.workflow.cmd.RejectCmd;
import com.yuan.workflow.cmd.RollbackCmd;
import com.yuan.workflow.cmd.RollbackToPreviousCmd;
import com.yuan.workflow.cmd.StartCmd;
import com.yuan.workflow.cmd.TransferTaskCmd;
import com.yuan.workflow.cmd.WithdrawCmd;
import com.yuan.workflow.core.engine.handler.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class DefaultWorkflowEngine implements WorkflowEngine {

    private final StartHandler startHandler;
    private final ApproveHandler approveHandler;
    private final RejectHandler rejectHandler;
    private final RollbackHandler rollbackHandler;
    private final RollbackToPreviousHandler rollbackToPreviousHandler;
    private final WithdrawHandler withdrawHandler;
    private final TransferHandler transferHandler;

    /**
     * start
     * @param cmd
     * @return
     */
    @Override
    public Long startProcess(StartCmd cmd) {
        return startHandler.handle(cmd);
    }

    /**
     * finish
     * @param cmd
     */
    @Override
    public void approveTask(ApproveCmd cmd) {
        approveHandler.handle(cmd);
    }

    /**
     * reject
     * @param cmd
     */
    @Override
    public void rejectTask(RejectCmd cmd) {
        rejectHandler.handle(cmd);
    }

    @Override
    public void rollbackToPrev(RollbackToPreviousCmd cmd) {
        rollbackToPreviousHandler.handle(cmd);
    }

    @Override
    public void rollbackToActivity(RollbackCmd cmd) {
        rollbackHandler.handle(cmd);
    }

    @Override
    public void withdraw(WithdrawCmd cmd) {
        withdrawHandler.handle(cmd);
    }

    @Override
    public void transfer(TransferTaskCmd cmd) {
        transferHandler.handle(cmd);
    }

    @Override
    public void addSign(AddSignCmd addSignCmd) {

    }

}
