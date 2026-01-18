package com.yuan.workflow.core.engine.handler;

import com.yuan.workflow.cmd.TransferTaskCmd;
import com.yuan.workflow.core.engine.support.TaskLifecycle;
import com.yuan.workflow.core.engine.support.WfContextLoader;
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
    private final WfContextLoader contextLoader;
    private final TaskLifecycle taskLifecycle;
    private final WfOperationGuard wfOperationGuard;


    @Override
    @Transactional
    public Void handle(TransferTaskCmd cmd) {
        Long operatorId = cmd.getOperatorId();

        WfContextLoader.TaskCtx ctx = contextLoader.loadTaskCtx(cmd.getTaskId());
        WfTask task = ctx.task();

        // 1) 校验
        wfOperationGuard.assertCanOperate(task, operatorId);

        // 2) 执行转交：task 只是换 assignee
        taskLifecycle.transfer(
                task,
                cmd
        );

        return null;
    }
}
