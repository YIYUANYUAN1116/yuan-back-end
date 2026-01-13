package com.yuan.workflow.core.engine.support;

import com.yuan.workflow.core.parser.FlowParser;
import com.yuan.workflow.core.resolver.AssigneeResolver;
import com.yuan.workflow.domain.WfDefinition;
import com.yuan.workflow.domain.WfInstance;
import com.yuan.workflow.domain.WfNodeInstance;
import com.yuan.workflow.domain.enums.NodeStatus;
import com.yuan.workflow.domain.enums.NodeType;
import com.yuan.workflow.domain.exception.RollbackTargetInvalidException;
import com.yuan.workflow.mapper.WfDefinitionMapper;
import com.yuan.workflow.mapper.WfInstanceMapper;
import com.yuan.workflow.model.logicflow.LfNode;
import com.yuan.workflow.service.WfNodeInstanceService;
import com.yuan.workflow.service.WfTaskService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Set;

@Slf4j
@Component
@RequiredArgsConstructor
public class FlowAdvanceService {
    private final WfInstanceMapper instanceMapper;
    private final WfDefinitionMapper definitionMapper;
    private final VariableService variableService;
    private final FlowParser flowParser;
    private final InstanceLifecycle instanceLifecycle;
    private final WfNodeInstanceService nodeInstanceService;
    private final WfTaskService wfTaskService;
    private final AssigneeResolver assigneeResolver;

    public void advance(WfNodeInstance currentNode,Long operatorId) {
        WfInstance instance = instanceMapper.selectById(currentNode.getInstanceId());

        WfDefinition def = definitionMapper.selectById(instance.getDefinitionId());

        LfNode currentFlowNode = flowParser.getNode(def, currentNode.getNodeKey());

        // 用最新变量（finish 时已 mergeAndSave）
        Map<String, Object> vars = variableService.getVars(instance);
        LfNode next = flowParser.getNextNode(def, currentFlowNode, vars);
        advanceToTarget(instance,next,operatorId);
    }

    public void advanceToTarget(WfInstance instance,LfNode lfNode,Long operatorId) {
        if (lfNode == null) {
            throw new RollbackTargetInvalidException(); // 或 WF_NODE_NOT_FOUND
        }

        if (NodeType.END.getCode().equals(lfNode.getProperties().getWfType())) {
            instanceLifecycle.finishApproved(instance, operatorId);
            return;
        }

        int nextOrderNo = nodeInstanceService.nextOrderNo(instance.getId());
        WfNodeInstance nextNodeIns =
                nodeInstanceService.createNodeInstance(instance.getId(), lfNode, NodeStatus.WAIT, nextOrderNo);

        Set<Long> userIds = assigneeResolver.resolve(lfNode);
        wfTaskService.createTasks(instance, nextNodeIns, userIds);
    }
}
