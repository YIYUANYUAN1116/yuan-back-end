package com.yuan.workflow.core.engine.handler;

import com.yuan.workflow.cmd.ApproveCmd;
import com.yuan.workflow.core.engine.runtime.InstanceTransitionManager;
import com.yuan.workflow.core.engine.runtime.NodeInstanceStateManager;
import com.yuan.workflow.core.engine.runtime.TaskStateManager;
import com.yuan.workflow.core.engine.runtime.VariableManager;
import com.yuan.workflow.core.engine.support.*;
import com.yuan.workflow.domain.WfInstance;
import com.yuan.workflow.domain.WfNodeInstance;
import com.yuan.workflow.domain.WfTask;
import com.yuan.workflow.domain.enums.TaskAction;
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
    private final WfContextLoader contextLoader;
    private final VariableManager variableManager;
    private final TaskStateManager taskStateManager;
    private final InstanceTransitionManager instanceTransitionManager;
    private final WfOperationGuard wfOperationGuard;
    private final NodeInstanceStateManager nodeInstanceStateManager;

    @Override
    @Transactional
    public Void handle(ApproveCmd cmd) {
        Long operatorId = cmd.getOperatorId();

        // 1) load 上下文（task/node/instance/def/bizRef）
        WfContextLoader.TaskCtx ctx = contextLoader.loadTaskCtx(cmd.getTaskId());
        WfTask task = ctx.task();
        WfNodeInstance node = ctx.node();
        WfInstance instance = ctx.instance();

        // 2) 参数 + 权限校验
        wfOperationGuard.assertCanOperate(task,operatorId);

        // 3) 合并变量
        variableManager.mergeAndSave(instance, cmd.getVariables());

        // 4) 完成任务,节点
        taskStateManager.finish(task, TaskAction.ANY_APPROVE, cmd.getComment(), operatorId);

        // 6) 完成节点
        nodeInstanceStateManager.finishDone(task.getNodeInstanceId(),operatorId);

        // 7) 推进
        instanceTransitionManager.advance(node,cmd);

        return null;
    }

}
