package com.yuan.workflow.core.engine.handler;

import com.yuan.workflow.cmd.TransferTaskCmd;
import com.yuan.workflow.core.engine.runtime.ProcessAdvancer;
import com.yuan.workflow.core.engine.runtime.TaskStateManager;
import com.yuan.workflow.core.engine.runtime.context.RuntimeContextLoader;
import com.yuan.workflow.domain.WfTask;
import com.yuan.workflow.domain.guard.WfOperationGuard;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Component
@RequiredArgsConstructor
public class TransferHandler implements  CommandHandler<TransferTaskCmd,Void>{
    private final RuntimeContextLoader contextLoader;
    private final TaskStateManager taskStateManager;
    private final WfOperationGuard wfOperationGuard;
    private final ProcessAdvancer processAdvancer;

    @Override
    @Transactional
    public Void handle(TransferTaskCmd cmd) {
        Long operatorId = cmd.getOperatorId();

        RuntimeContextLoader.TaskCtx ctx = contextLoader.loadTaskCtx(cmd.getTaskId());
        WfTask task = ctx.task();

        // 1) 校验
        wfOperationGuard.assertCanOperate(task, operatorId);

        // 2) 执行转交：task 只是换 assignee
        taskStateManager.transfer(task, cmd);

        processAdvancer.transfer(ctx.instance(),ctx.node(),cmd);

        return null;
    }
}
