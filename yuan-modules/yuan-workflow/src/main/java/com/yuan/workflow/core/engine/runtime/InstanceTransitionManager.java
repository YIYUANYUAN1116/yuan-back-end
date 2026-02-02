package com.yuan.workflow.core.engine.runtime;

import com.yuan.common.core.exception.workflow.WorkflowErrorCode;
import com.yuan.common.core.exception.workflow.WorkflowException;
import com.yuan.workflow.cmd.*;
import com.yuan.workflow.core.exception.ProcessDefinitionParseException;
import com.yuan.workflow.core.parser.FlowParser;
import com.yuan.workflow.core.resolver.AssigneeResolver;
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
import com.yuan.workflow.service.WfNodeInstanceService;
import com.yuan.workflow.service.WfTaskService;
import com.yuan.workflow.service.WfTransitionLogService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Set;

@Slf4j
@Component
@RequiredArgsConstructor
public class InstanceTransitionManager {
    private final WfInstanceMapper instanceMapper;
    private final WfDefinitionMapper definitionMapper;
    private final VariableManager variableManager;
    private final FlowParser flowParser;
    private final InstanceStateManager instanceStateManager;
    private final WfNodeInstanceService nodeInstanceService;
    private final WfTaskService wfTaskService;
    private final AssigneeResolver assigneeResolver;
    private final NodeInstanceStateManager nodeInstanceStateManager;
    private final WfTransitionLogService transitionLogService;


    public void advance(WfNodeInstance currentNode, ApproveCmd cmd) {
        WfInstance instance = instanceMapper.selectById(currentNode.getInstanceId());

        WfDefinition def = definitionMapper.selectById(instance.getDefinitionId());

        LfNode currentFlowNode = flowParser.getNode(def, currentNode.getNodeKey());

        variableManager.mergeAndSave(instance, cmd.getVariables());

        Map<String, Object> vars = variableManager.getVars(instance);

        List<LfNode> nodeList = flowParser.getNextNode(def, currentFlowNode, vars);
        for (LfNode next : nodeList) {
            transitionLog(instance, currentNode, next, TransitionAction.APPROVE, OperatorType.USER, cmd,null,null);
            advanceToTarget(instance, def, next, cmd, vars);
        }
    }

    public void advanceToTarget(WfInstance instance, WfDefinition def, LfNode lfNode, WorkflowCmd cmd, Map<String, Object> vars) {
        if (lfNode == null) {
            throw new NodeInstanceException(WorkflowErrorCode.WF_NODE_NOT_FOUND);
        }

        int nextOrderNo = nodeInstanceService.nextOrderNo(instance.getId());
        WfNodeInstance currentNode =
                nodeInstanceService.createNodeInstance(instance.getId(), lfNode, NodeStatus.WAIT, nextOrderNo);

        if (flowParser.isGateway(lfNode)) {
            List<LfNode> nodeList = flowParser.getNextNode(def, lfNode, vars);
            nodeInstanceStateManager.finishDone(currentNode);
            for (LfNode next : nodeList) {
                cmd.setComment(null);
                String conditionExpr =  flowParser.parseConditionExpr(next);
                transitionLog(instance, currentNode, next, TransitionAction.GATEWAY, OperatorType.SYSTEM, cmd,conditionExpr,vars);
                advanceToTarget(instance, def, next, cmd, vars);
            }
            return;
        }


        if (flowParser.isUserTask(lfNode)) {
            Set<Long> userIds = assigneeResolver.resolve(lfNode);
            wfTaskService.createTasks(instance, currentNode, userIds);
            return;
        }

        if (flowParser.isEnd(lfNode)) {
            nodeInstanceStateManager.finishDone(currentNode);
            instanceStateManager.finishApproved(instance, cmd);
            cmd.setComment(null);
            transitionLog(instance, currentNode, null, TransitionAction.END, OperatorType.SYSTEM, cmd,null,null);
            return;
        }

        if (flowParser.isSystem(lfNode)) {
            //todo 系统节点
            log.info("[advanceToTarget] LfNode type is system_task instance id:{}, lfNode:{}"
                    , instance.getId(), lfNode);
            return;
        }

        log.error("[advanceToTarget] LfNode type invalid instance id:{}, lfNode:{}"
                , instance.getId(), lfNode);
        throw new WorkflowException(WorkflowErrorCode.WF_NODE_TYPE_INVALID);

    }


    public void start(WfDefinition def, WfInstance instance, WfNodeInstance node, LfNode lfNode, StartCmd cmd) {
        variableManager.mergeAndSave(instance, cmd.getVariables());
        Map<String, Object> vars = variableManager.getVars(instance);
        List<LfNode> nodeList = flowParser.getNextNode(def, lfNode, vars);

        if (nodeList.isEmpty()) {
            log.warn("no first finish node. defId={},defVersion={}", def.getId(), def.getVersion());
            instanceStateManager.finishApproved(instance, cmd);
            transitionLog(instance, node, null,
                    TransitionAction.END, OperatorType.SYSTEM, cmd,null,null);
        }
        for (LfNode next : nodeList) {
            transitionLog(instance, node, next,
                    TransitionAction.START, OperatorType.USER, cmd,null,null);
            advanceToTarget(instance, def, next, cmd, cmd.getVariables());
        }

    }


    public void reject(WfInstance instance, WfNodeInstance node, LfNode lfNode, RejectCmd cmd) {
        transitionLog(instance, node, lfNode,
                TransitionAction.REJECT, OperatorType.USER, cmd,null,null);
    }


    public void rollback(WfDefinition def, WfInstance instance, WfNodeInstance currentNode, RollbackCmd cmd) {
        //找到退回到的节点
        LfNode target = flowParser.getNode(def, cmd.getTargetActivityId());
        if (target == null) {
            log.error("Target activity id not found,defId={},defVersion={},targetActivityId={}", def.getId(), def.getVersion(), cmd.getTargetActivityId());
            throw new ProcessDefinitionParseException(WorkflowErrorCode.WF_DEFINITION_NODE_NOT_FOUND, def.getId(), def.getVersion());
        }
        transitionLog(instance, currentNode, target, TransitionAction.ROLLBACK, OperatorType.USER, cmd,null,null);
        advanceToTarget(instance, def, target, cmd, cmd.getVariables());
    }

    public void transfer(WfInstance instance, WfNodeInstance node, TransferTaskCmd cmd) {
        transitionLog(instance, node, null,
                TransitionAction.TRANSFER, OperatorType.USER, cmd,null,null);
    }


    public void withDraw(WfInstance instance, WfNodeInstance from, LfNode to, WithdrawCmd cmd) {
        transitionLog(instance, from, to,
                TransitionAction.WITHDRAW, OperatorType.USER, cmd,null,null);
    }

    public void addSign(WfInstance instance, WfNodeInstance node, AddSignCmd cmd) {
        transitionLog(instance, node, null,
                TransitionAction.ADD_SIGN, OperatorType.USER, cmd,null,null);
    }

    private void transitionLog(WfInstance instance,
                               WfNodeInstance from,
                               LfNode to,
                               TransitionAction action,
                               OperatorType operatorType,
                               WorkflowCmd cmd,
                               String conditionExpr,
                               Map<String,Object> varSnapshot) {
        transitionLogService.recordSuccess(RecordTransitionCmd.builder()
                .tenantId(instance.getTenantId())
                .defId(instance.getDefinitionId())
                .defVersion(instance.getDefinitionVersion())
                .instanceId(instance.getId())
                .nodeInstanceId(from != null ? from.getId() : null)
                .fromNodeKey(from != null ? from.getNodeKey() : null)
                .toNodeKey(to != null ? to.getId() : null)
                .action(action)
                .operatorType(operatorType)
                .operatorId(operatorType.equals(OperatorType.USER) ? cmd.getOperatorId() : null)
                .conditionExpr(conditionExpr)
                .variablesSnapshot(varSnapshot)
                .comment(cmd.getComment())
                .build());
    }


}
