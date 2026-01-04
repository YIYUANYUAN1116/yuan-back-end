package com.yuan.workflow.core.engine;

import com.yuan.workflow.api.cmd.ApproveTaskCmd;
import com.yuan.workflow.api.cmd.RejectTaskCmd;
import com.yuan.workflow.api.cmd.RollbackToCmd;
import com.yuan.workflow.api.cmd.RollbackToPrevCmd;
import com.yuan.workflow.api.cmd.StartProcessCmd;
import com.yuan.workflow.api.cmd.TransferTaskCmd;
import com.yuan.workflow.api.cmd.WithdrawCmd;
import com.yuan.workflow.core.engine.handler.ApproveTaskHandler;
import com.yuan.workflow.core.engine.handler.RejectTaskHandler;
import com.yuan.workflow.core.engine.handler.StartProcessHandler;
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
     * approve
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
    @Override public void rollbackTo(RollbackToCmd cmd) { }
    @Override public void withdraw(WithdrawCmd cmd) { }
    @Override public void transfer(TransferTaskCmd cmd) { }

}
