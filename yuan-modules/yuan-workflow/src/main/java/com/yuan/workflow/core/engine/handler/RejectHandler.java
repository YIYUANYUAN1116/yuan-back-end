package com.yuan.workflow.core.engine.handler;

import com.yuan.workflow.cmd.RejectCmd;
import com.yuan.workflow.core.engine.runtime.InstanceStateManager;
import com.yuan.workflow.core.engine.runtime.NodeInstanceStateManager;
import com.yuan.workflow.core.engine.runtime.ProcessAdvancer;
import com.yuan.workflow.core.engine.runtime.TaskStateManager;
import com.yuan.workflow.core.engine.runtime.VariableManager;
import com.yuan.workflow.core.engine.runtime.WfEventManager;
import com.yuan.workflow.core.engine.runtime.context.RuntimeContextLoader;
import com.yuan.workflow.domain.WfInstance;
import com.yuan.workflow.domain.WfNodeInstance;
import com.yuan.workflow.domain.WfTask;
import com.yuan.workflow.domain.guard.WfOperationGuard;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * 审批拒绝处理器
 */
@Component
@RequiredArgsConstructor
public class RejectHandler implements CommandHandler<RejectCmd,Void>{
    private final RuntimeContextLoader contextLoader;
    private final VariableManager variableManager;
    private final TaskStateManager taskStateManager;
    private final InstanceStateManager instanceStateManager;
    private final WfOperationGuard wfOperationGuard;
    private final NodeInstanceStateManager nodeInstanceStateManager;
    private final ProcessAdvancer transitionManager;
    private final WfEventManager eventManager;


    @Override
    @Transactional
    public Void handle(RejectCmd cmd) {
        Long operatorId = cmd.getOperatorId();

        //  load 上下文
        RuntimeContextLoader.TaskCtx ctx = contextLoader.loadTaskCtx(cmd.getTaskId());
        WfTask task = ctx.task();
        WfInstance instance = ctx.instance();
        WfNodeInstance node = ctx.node();

        // 校验
        wfOperationGuard.assertCanOperate(task, operatorId);

        // 合并变量（驳回也可能写变量）
        variableManager.mergeAndSave(instance, cmd.getVariables());

        // 完成任务
        taskStateManager.reject(task, cmd);

        // 完成节点
        nodeInstanceStateManager.reject(node,cmd);

        // 业务规则：驳回直接结束
        boolean instanceEnded = isRejectEnd(task);
        if (!instanceEnded) {
            return null;
        }
        // 结束实例
        instanceStateManager.reject(instance,cmd);

        transitionManager.reject(instance,node,null,cmd);

        eventManager.reject(instance,cmd.getOperatorId());

        return null;
    }

    private boolean isRejectEnd(WfTask task) {
        return true;
    }

}
