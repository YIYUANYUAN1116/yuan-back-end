package com.yuan.workflow.core.engine.handler;

import com.yuan.workflow.cmd.RollbackCmd;
import com.yuan.workflow.core.engine.runtime.NodeInstanceStateManager;
import com.yuan.workflow.core.engine.runtime.ProcessAdvancer;
import com.yuan.workflow.core.engine.runtime.TaskStateManager;
import com.yuan.workflow.core.engine.runtime.context.RuntimeContextLoader;
import com.yuan.workflow.domain.WfDefinition;
import com.yuan.workflow.domain.WfInstance;
import com.yuan.workflow.domain.WfNodeInstance;
import com.yuan.workflow.domain.WfTask;
import com.yuan.workflow.domain.guard.WfOperationGuard;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * 退回至指定节点处理器
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class RollbackHandler implements CommandHandler<RollbackCmd,Void>{

    private final RuntimeContextLoader contextLoader;
    private final TaskStateManager taskStateManager;
    private final WfOperationGuard wfOperationGuard;
    private final NodeInstanceStateManager nodeInstanceStateManager;
    private final ProcessAdvancer processAdvancer;

    @Override
    @Transactional
    public Void handle(RollbackCmd cmd) {

        RuntimeContextLoader.TaskCtx taskCtx = contextLoader.loadTaskCtx(cmd.getTaskId());
        WfTask task = taskCtx.task();
        WfDefinition def = taskCtx.def();
        WfNodeInstance node = taskCtx.node();
        WfInstance instance = taskCtx.instance();

        //判断当前操作人是否可操作该任务
        wfOperationGuard.assertCanOperate(task,cmd.getOperatorId());

        //完成当前任务
        taskStateManager.rollback(task, cmd);

        //修改节点状态
        nodeInstanceStateManager.rollback(node,cmd);

        processAdvancer.rollback(def,instance,node,cmd);

        return null;
    }

}
