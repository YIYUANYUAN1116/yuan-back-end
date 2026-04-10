package com.yuan.workflow.core.engine.handler;

import com.yuan.workflow.cmd.ApproveCmd;
import com.yuan.workflow.core.engine.runtime.NodeInstanceStateManager;
import com.yuan.workflow.core.engine.runtime.ProcessAdvancer;
import com.yuan.workflow.core.engine.runtime.TaskStateManager;
import com.yuan.workflow.core.engine.runtime.VariableManager;
import com.yuan.workflow.core.engine.runtime.context.RuntimeContextLoader;
import com.yuan.workflow.domain.WfNodeInstance;
import com.yuan.workflow.domain.WfTask;
import com.yuan.workflow.domain.guard.WfOperationGuard;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * 审批通过处理器
 */
@Component
@RequiredArgsConstructor
public class ApproveHandler implements CommandHandler<ApproveCmd,Void>{
    private final RuntimeContextLoader contextLoader;
    private final VariableManager variableManager;
    private final TaskStateManager taskStateManager;
    private final ProcessAdvancer processAdvancer;
    private final WfOperationGuard wfOperationGuard;
    private final NodeInstanceStateManager nodeInstanceStateManager;


    @Override
    @Transactional
    public Void handle(ApproveCmd cmd) {
        Long operatorId = cmd.getOperatorId();

        // 1) load 上下文（task/node/instance/def/bizRef）
        RuntimeContextLoader.TaskCtx ctx = contextLoader.loadTaskCtx(cmd.getTaskId());
        WfTask task = ctx.task();
        WfNodeInstance node = ctx.node();

        // 2) 参数 + 权限校验
        wfOperationGuard.assertCanOperate(task,operatorId);

        // 3) 完成任务,节点
        taskStateManager.anyApprove(task, cmd);

        // 4) 完成节点
        nodeInstanceStateManager.approve(node,cmd);

        // 5) 推进
        processAdvancer.advance(node,cmd);

        return null;
    }
}
