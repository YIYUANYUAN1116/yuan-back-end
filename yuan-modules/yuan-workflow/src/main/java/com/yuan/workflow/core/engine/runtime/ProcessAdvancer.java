package com.yuan.workflow.core.engine.runtime;

import com.yuan.common.core.exception.workflow.WorkflowErrorCode;
import com.yuan.workflow.cmd.AddSignCmd;
import com.yuan.workflow.cmd.RejectCmd;
import com.yuan.workflow.cmd.RollbackCmd;
import com.yuan.workflow.cmd.StartCmd;
import com.yuan.workflow.cmd.TransferTaskCmd;
import com.yuan.workflow.cmd.WithdrawCmd;
import com.yuan.workflow.cmd.WorkflowCmd;
import com.yuan.workflow.core.engine.runtime.arrival.NodeArrivalDispatcher;
import com.yuan.workflow.core.engine.runtime.context.NodeArrivalContext;
import com.yuan.workflow.core.exception.ProcessDefinitionParseException;
import com.yuan.workflow.core.parser.FlowParser;
import com.yuan.workflow.domain.WfDefinition;
import com.yuan.workflow.domain.WfInstance;
import com.yuan.workflow.domain.WfNodeInstance;
import com.yuan.workflow.domain.enums.NodeStatus;
import com.yuan.workflow.domain.exception.NodeInstanceException;
import com.yuan.workflow.enums.OperatorType;
import com.yuan.workflow.enums.TransitionAction;
import com.yuan.workflow.mapper.WfDefinitionMapper;
import com.yuan.workflow.mapper.WfInstanceMapper;
import com.yuan.workflow.model.logicflow.LfNode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor
public class ProcessAdvancer {
    private final WfInstanceMapper instanceMapper;
    private final WfDefinitionMapper definitionMapper;
    private final VariableManager variableManager;
    private final FlowParser flowParser;
    private final InstanceStateManager instanceStateManager;
    private final NodeInstanceStateManager nodeInstanceStateManager;
    private final WfEventManager wfEventManager;
    private final NodeArrivalDispatcher nodeArrivalDispatcher;
    private final TransitionLogManager  transitionLogManager;

    public void advance(WfNodeInstance currentNode, WorkflowCmd cmd) {
        WfInstance instance = instanceMapper.selectById(currentNode.getInstanceId());
        variableManager.mergeAndSave(instance, cmd.getVariables());
        Map<String, Object> vars = variableManager.getVars(instance);
        advance(currentNode, cmd, vars);
    }

    public void advance(WfNodeInstance currentNode, WorkflowCmd cmd,Map<String, Object> vars) {
        WfInstance instance = instanceMapper.selectById(currentNode.getInstanceId());
        WfDefinition def = definitionMapper.selectById(instance.getDefinitionId());
        LfNode currentFlowNode = flowParser.getNode(def, currentNode.getNodeKey());
        List<LfNode> nodeList = flowParser.getNextNode(def, currentFlowNode, vars);
        for (LfNode next : nodeList) {
            transitionLogManager.transitionLog(instance, currentNode, next, TransitionAction.APPROVE, OperatorType.USER, cmd, null, null);
            advanceToTarget(instance, def, next, cmd, vars);
        }
    }

    public void advanceToTarget(WfInstance instance, WfDefinition def, LfNode lfNode, WorkflowCmd cmd, Map<String, Object> vars) {
        if (lfNode == null) {
            throw new NodeInstanceException(WorkflowErrorCode.WF_NODE_NOT_FOUND);
        }

        // 1. 创建目标节点实例
        WfNodeInstance targetNodeInstance =
                nodeInstanceStateManager.createNodeInstance(instance.getId(), lfNode, NodeStatus.WAIT);

        // 2. 封装到达上下文
        NodeArrivalContext context = NodeArrivalContext.builder()
                .instance(instance)
                .definition(def)
                .targetNode(lfNode)
                .targetNodeInstance(targetNodeInstance)
                .triggerCmd(cmd)
                .variables(vars)
                .build();

        // 3. 分发处理
        nodeArrivalDispatcher.onArrive(context);

//        if (flowParser.isGateway(lfNode)) {
//            List<LfNode> nodeList = flowParser.getNextNode(def, lfNode, vars);
//            nodeInstanceStateManager.autoApprove(currentNode, cmd);
//            for (LfNode next : nodeList) {
//                cmd.setComment(null);
//                String conditionExpr = flowParser.parseConditionExpr(next);
//                transitionLogManager.transitionLog(instance, currentNode, next, TransitionAction.GATEWAY, OperatorType.SYSTEM, cmd, conditionExpr, vars);
//                advanceToTarget(instance, def, next, cmd, vars);
//            }
//            return;
//        }
//
//
//        if (flowParser.isUserTask(lfNode)) {
//            Set<Long> userIds = assigneeResolver.resolve(lfNode);
//            wfTaskService.createTasks(instance, currentNode, userIds);
//            return;
//        }
//
//        if (flowParser.isEnd(lfNode)) {
//            nodeInstanceStateManager.autoApprove(currentNode, cmd);
//            instanceStateManager.approve(instance, cmd);
//            wfEventManager.approve(instance,cmd.getOperatorId());
//            cmd.setComment(null);
//            transitionLogManager.transitionLog(instance, currentNode, null, TransitionAction.END, OperatorType.SYSTEM, cmd, null, null);
//            return;
//        }
//
//        if (flowParser.isSystem(lfNode)) {
//            //todo 系统节点
//            log.info("[advanceToTarget] LfNode type is system_task instance id:{}, lfNode:{}"
//                    , instance.getId(), lfNode);
//            return;
//        }
//
//        log.error("[advanceToTarget] LfNode type invalid instance id:{}, lfNode:{}"
//                , instance.getId(), lfNode);
//        throw new WorkflowException(WorkflowErrorCode.WF_NODE_TYPE_INVALID);

    }


    public void start(WfDefinition def, WfInstance instance,StartCmd cmd) {
        // 1. 保存启动变量
        variableManager.mergeAndSave(instance, cmd.getVariables());

        // 2. 找开始节点
        LfNode startLfNode = flowParser.getStartNode(def);

        // 3. 创建开始节点实例（直接完成）
        WfNodeInstance startNode = nodeInstanceStateManager.createNodeInstance(
                instance.getId(),
                startLfNode,
                NodeStatus.DONE
        );

        // 4. 取最新变量
        Map<String, Object> vars = variableManager.getVars(instance);

        // 5. 找开始节点后续节点
        List<LfNode> nodeList = flowParser.getNextNode(def, startLfNode, vars);

        if (nodeList.isEmpty()) {
            log.warn("no first finish node. defId={},defVersion={}", def.getId(), def.getVersion());

            instanceStateManager.approve(instance, cmd);
            wfEventManager.approve(instance, cmd.getOperatorId());
            transitionLogManager.transitionLog(
                    instance, startNode, null,
                    TransitionAction.END, OperatorType.SYSTEM, cmd, null, null
            );
            return;
        }

        // 6. 推进到后续节点
        for (LfNode next : nodeList) {
            transitionLogManager.transitionLog(
                    instance, startNode, next,
                    TransitionAction.START, OperatorType.USER, cmd, null, null
            );
            advanceToTarget(instance, def, next, cmd, vars);
        }

    }


    public void reject(WfInstance instance, WfNodeInstance node, LfNode lfNode, RejectCmd cmd) {
        transitionLogManager.transitionLog(instance, node, lfNode,
                TransitionAction.REJECT, OperatorType.USER, cmd, null, null);
    }


    public void rollback(WfDefinition def, WfInstance instance, WfNodeInstance currentNode, RollbackCmd cmd) {
        //找到退回到的节点
        LfNode target = flowParser.getNode(def, cmd.getTargetActivityId());
        if (target == null) {
            log.error("Target activity id not found,defId={},defVersion={},targetActivityId={}", def.getId(), def.getVersion(), cmd.getTargetActivityId());
            throw new ProcessDefinitionParseException(WorkflowErrorCode.WF_DEFINITION_NODE_NOT_FOUND, def.getId(), def.getVersion());
        }
        transitionLogManager.transitionLog(instance, currentNode, target, TransitionAction.ROLLBACK, OperatorType.USER, cmd, null, null);
        advanceToTarget(instance, def, target, cmd, cmd.getVariables());
    }

    public void transfer(WfInstance instance, WfNodeInstance node, TransferTaskCmd cmd) {
        transitionLogManager.transitionLog(instance, node, null,
                TransitionAction.TRANSFER, OperatorType.USER, cmd, null, null);
    }


    public void withDraw(WfInstance instance, WfNodeInstance from, LfNode to, WithdrawCmd cmd) {
        transitionLogManager.transitionLog(instance, from, to,
                TransitionAction.WITHDRAW, OperatorType.USER, cmd, null, null);
    }

    public void addSign(WfInstance instance, WfNodeInstance node, AddSignCmd cmd) {
        transitionLogManager.transitionLog(instance, node, null,
                TransitionAction.ADD_SIGN, OperatorType.USER, cmd, null, null);
    }

    


}
