package com.yuan.workflow.core.engine.handler;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.yuan.workflow.cmd.RollbackToPreviousCmd;
import com.yuan.workflow.core.engine.runtime.InstanceTransitionManager;
import com.yuan.workflow.core.engine.runtime.NodeInstanceStateManager;
import com.yuan.workflow.core.engine.runtime.VariableManager;
import com.yuan.workflow.core.engine.support.*;
import com.yuan.workflow.core.engine.runtime.TaskStateManager;
import com.yuan.workflow.core.parser.FlowParser;
import com.yuan.workflow.domain.WfDefinition;
import com.yuan.workflow.domain.WfInstance;
import com.yuan.workflow.domain.WfNodeInstance;
import com.yuan.workflow.domain.WfTask;
import com.yuan.workflow.domain.enums.NodeStatus;
import com.yuan.workflow.domain.enums.TaskAction;
import com.yuan.workflow.domain.exception.RollbackTargetInvalidException;
import com.yuan.workflow.domain.guard.WfOperationGuard;
import com.yuan.workflow.mapper.WfNodeInstanceMapper;
import com.yuan.workflow.model.logicflow.LfNode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * 退回到上一节点处理器
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class RollbackToPreviousHandler implements CommandHandler<RollbackToPreviousCmd, Void> {

    private final WfContextLoader contextLoader;
    private final VariableManager variableManager;
    private final TaskStateManager taskStateManager;
    private final WfOperationGuard wfOperationGuard;
    private final FlowParser flowParser;
    private final NodeInstanceStateManager nodeInstanceStateManager;
    private final InstanceTransitionManager instanceTransitionManager;
    private final WfNodeInstanceMapper nodeInstanceMapper;

    @Override
    @Transactional
    public Void handle(RollbackToPreviousCmd cmd) {
        WfContextLoader.TaskCtx taskCtx = contextLoader.loadTaskCtx(cmd.getTaskId());
        WfTask task = taskCtx.task();
        WfDefinition def = taskCtx.def();
        WfNodeInstance currentNode = taskCtx.node();
        WfInstance instance = taskCtx.instance();

        // 找到上一节点
        LfNode prevNode = findPreviousNode(def, instance.getId(), currentNode.getOrderNo());
        if (prevNode == null) {
            log.error("Previous node not found, instanceId={}, currentNodeId={}, orderNo={}",
                instance.getId(), currentNode.getNodeKey(), currentNode.getOrderNo());
            throw new RollbackTargetInvalidException();
        }

        // 判断当前操作人是否可操作该任务
        wfOperationGuard.assertCanOperate(task, cmd.getOperatorId());

        variableManager.mergeAndSave(taskCtx.instance(), cmd.getVariables());

        // 完成当前任务
        taskStateManager.finish(task, TaskAction.ROLLBACK, cmd.getComment(), cmd.getOperatorId());

        // 修改节点状态
        nodeInstanceStateManager.finishCancel(currentNode.getId(), cmd.getOperatorId());

        // 推进到上一节点
        instanceTransitionManager.advanceToTarget(instance,def, prevNode, cmd,cmd.getVariables());

        return null;
    }

    /**
     * 找到上一节点
     * 根据orderNo查找已完成且orderNo比当前节点小的最近节点
     */
    private LfNode findPreviousNode(WfDefinition def, Long instanceId, Integer currentOrderNo) {
        // 查找已完成且orderNo小于当前节点的节点实例
        LambdaQueryWrapper<WfNodeInstance> wrapper =
            new LambdaQueryWrapper<>();
        wrapper.eq(WfNodeInstance::getInstanceId, instanceId)
            .eq(WfNodeInstance::getStatus, NodeStatus.DONE)
            .lt(WfNodeInstance::getOrderNo, currentOrderNo)
            .orderByDesc(WfNodeInstance::getOrderNo)
            .last("LIMIT 1");

        WfNodeInstance prevNodeInstance = nodeInstanceMapper.selectOne(wrapper);

        if (prevNodeInstance == null) {
            return null;
        }

        // 从流程定义中获取节点信息
        return flowParser.getNode(def, prevNodeInstance.getNodeKey());
    }
}
