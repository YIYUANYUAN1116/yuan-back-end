package com.yuan.workflow.core.engine.handler;

import com.yuan.workflow.cmd.AddSignCmd;
import com.yuan.workflow.core.engine.runtime.ProcessAdvancer;
import com.yuan.workflow.core.engine.runtime.TaskStateManager;
import com.yuan.workflow.core.engine.runtime.context.RuntimeContextLoader;
import com.yuan.workflow.domain.WfTask;
import com.yuan.workflow.domain.guard.WfOperationGuard;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AddSignHandler implements CommandHandler<AddSignCmd,Void>{
    private final RuntimeContextLoader contextLoader;
    private final TaskStateManager taskStateManager;
    private final WfOperationGuard wfOperationGuard;
    private final ProcessAdvancer processAdvancer;

    @Override
    public Void handle(AddSignCmd cmd) {
        Long operatorId = cmd.getOperatorId();

        RuntimeContextLoader.TaskCtx ctx = contextLoader.loadTaskCtx(cmd.getTaskId());
        WfTask task = ctx.task();

        // 1) 校验
        wfOperationGuard.assertCanOperate(task, operatorId);

        // 2) 执行转交：task 只是换 assignee
        taskStateManager.addSign(
                task,
                cmd
        );

        processAdvancer.addSign(ctx.instance(),ctx.node(),cmd);

        return null;
    }
}
