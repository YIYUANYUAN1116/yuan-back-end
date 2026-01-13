package com.yuan.workflow.core.engine.handler;

import com.yuan.workflow.api.cmd.ApproveTaskCmd;
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
public class ApproveTaskHandler implements CommandHandler<ApproveTaskCmd,Void>{
    private final WfContextLoader contextLoader;
    private final VariableService variableService;
    private final TaskLifecycle taskLifecycle;
    private final FlowAdvanceService flowAdvanceService;
    private final WfOperationGuard wfOperationGuard;
    private final NodeInstanceLifeCycle nodeInstanceLifeCycle;

    @Override
    @Transactional
    public Void handle(ApproveTaskCmd cmd) {
        Long operatorId = cmd.getOperatorUserId();

        // 1) load 上下文（task/node/instance/def/bizRef）
        WfContextLoader.TaskCtx ctx = contextLoader.loadTaskCtx(cmd.getTaskId());
        WfTask task = ctx.task();
        WfNodeInstance node = ctx.node();
        WfInstance instance = ctx.instance();

        // 2) 参数 + 权限校验
        wfOperationGuard.assertCanOperate(task,operatorId);

        // 3) 合并变量
        variableService.mergeAndSave(instance, cmd.getVariables());

        // 4) 完成任务,节点
        taskLifecycle.finish(task, TaskAction.ANY_APPROVE, cmd.getComment(), operatorId);

        // 6) 完成节点
        nodeInstanceLifeCycle.finishDone(task.getNodeInstanceId());

        // 7) 推进
        flowAdvanceService.advance(node,cmd.getOperatorUserId());

        return null;
    }

}
