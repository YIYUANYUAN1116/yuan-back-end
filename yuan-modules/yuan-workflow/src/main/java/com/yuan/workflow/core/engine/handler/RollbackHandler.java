package com.yuan.workflow.core.engine.handler;

import com.yuan.common.core.exception.workflow.WorkflowErrorCode;
import com.yuan.workflow.cmd.RollbackCmd;
import com.yuan.workflow.core.engine.runtime.InstanceTransitionManager;
import com.yuan.workflow.core.engine.runtime.NodeInstanceStateManager;
import com.yuan.workflow.core.engine.runtime.TaskStateManager;
import com.yuan.workflow.core.engine.runtime.VariableManager;
import com.yuan.workflow.core.engine.support.WfContextLoader;
import com.yuan.workflow.core.exception.ProcessDefinitionParseException;
import com.yuan.workflow.core.parser.FlowParser;
import com.yuan.workflow.domain.WfDefinition;
import com.yuan.workflow.domain.WfInstance;
import com.yuan.workflow.domain.WfNodeInstance;
import com.yuan.workflow.domain.WfTask;
import com.yuan.workflow.domain.enums.TaskAction;
import com.yuan.workflow.domain.guard.WfOperationGuard;
import com.yuan.workflow.model.logicflow.LfNode;
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

    private final WfContextLoader contextLoader;
    private final VariableManager variableManager;
    private final TaskStateManager taskStateManager;
    private final WfOperationGuard wfOperationGuard;
    private final FlowParser flowParser;
    private final NodeInstanceStateManager nodeInstanceStateManager;
    private final InstanceTransitionManager instanceTransitionManager;


    @Override
    @Transactional
    public Void handle(RollbackCmd cmd) {

        WfContextLoader.TaskCtx taskCtx = contextLoader.loadTaskCtx(cmd.getTaskId());
        WfTask task = taskCtx.task();
        WfDefinition def = taskCtx.def();
        WfNodeInstance currentNode = taskCtx.node();
        WfInstance instance = taskCtx.instance();

        //找到退回到的节点
        LfNode target = flowParser.getNode(def, cmd.getTargetActivityId());
        if (target == null) {
            log.error("Target activity id not found,defId={},defVersion={},targetActivityId={}",def.getId(),def.getVersion(),cmd.getTargetActivityId());
            throw new ProcessDefinitionParseException(WorkflowErrorCode.WF_DEFINITION_NODE_NOT_FOUND,def.getId(),def.getVersion());
        }

        //判断当前操作人是否可操作该任务
        wfOperationGuard.assertCanOperate(task,cmd.getOperatorId());

        variableManager.mergeAndSave(taskCtx.instance(), cmd.getVariables());

        //完成当前任务
        taskStateManager.finish(task, TaskAction.ROLLBACK,cmd.getComment(),cmd.getOperatorId());

        //修改节点状态
        nodeInstanceStateManager.finishCancel(currentNode.getId(),cmd.getOperatorId());

        instanceTransitionManager.advanceToTarget(instance,def,target,cmd,cmd.getVariables());

        return null;
    }
}
