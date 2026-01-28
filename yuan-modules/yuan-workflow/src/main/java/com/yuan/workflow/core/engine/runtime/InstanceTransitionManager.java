package com.yuan.workflow.core.engine.runtime;

import com.yuan.common.core.exception.workflow.WorkflowErrorCode;
import com.yuan.common.core.exception.workflow.WorkflowException;
import com.yuan.workflow.cmd.ApproveCmd;
import com.yuan.workflow.cmd.WorkflowCmd;
import com.yuan.workflow.core.parser.FlowParser;
import com.yuan.workflow.core.resolver.AssigneeResolver;
import com.yuan.workflow.domain.WfDefinition;
import com.yuan.workflow.domain.WfInstance;
import com.yuan.workflow.domain.WfNodeInstance;
import com.yuan.workflow.domain.enums.NodeStatus;
import com.yuan.workflow.domain.exception.NodeInstanceException;
import com.yuan.workflow.mapper.WfDefinitionMapper;
import com.yuan.workflow.mapper.WfInstanceMapper;
import com.yuan.workflow.model.logicflow.LfNode;
import com.yuan.workflow.service.WfNodeInstanceService;
import com.yuan.workflow.service.WfTaskService;
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

    public void advance(WfNodeInstance currentNode, ApproveCmd cmd) {
        WfInstance instance = instanceMapper.selectById(currentNode.getInstanceId());

        WfDefinition def = definitionMapper.selectById(instance.getDefinitionId());

        LfNode currentFlowNode = flowParser.getNode(def, currentNode.getNodeKey());

        // 用最新变量（finish 时已 mergeAndSave）
        Map<String, Object> vars = variableManager.getVars(instance);
        List<LfNode> nodeList = flowParser.getNextNode(def, currentFlowNode, vars);
        for (LfNode next : nodeList) {
            advanceToTarget(instance,def,next,cmd,vars);
        }
    }

    public void advanceToTarget(WfInstance instance,WfDefinition def, LfNode lfNode, WorkflowCmd cmd,Map<String, Object> vars) {
        if (lfNode == null) {
            throw new NodeInstanceException(WorkflowErrorCode.WF_NODE_NOT_FOUND);
        }

        int nextOrderNo = nodeInstanceService.nextOrderNo(instance.getId());
        WfNodeInstance nextNodeIns =
                nodeInstanceService.createNodeInstance(instance.getId(), lfNode, NodeStatus.WAIT, nextOrderNo);

        if (flowParser.isGateway(lfNode)) {
            List<LfNode> nodeList = flowParser.getNextNode(def, lfNode, vars);
            nextNodeIns.setSelectedNextKeys(
                    nodeList.stream().map(LfNode::getId).toList()
            );
            nodeInstanceStateManager.finishDone(nextNodeIns);
            for (LfNode next : nodeList) {
                advanceToTarget(instance,def, next,cmd, vars);
            }
        }


        if (flowParser.isUserTask(lfNode)) {
            Set<Long> userIds = assigneeResolver.resolve(lfNode);
            wfTaskService.createTasks(instance, nextNodeIns, userIds);
            return;
        }

        if (flowParser.isEnd(lfNode)) {
            instanceStateManager.finishApproved(instance, cmd);
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
}
