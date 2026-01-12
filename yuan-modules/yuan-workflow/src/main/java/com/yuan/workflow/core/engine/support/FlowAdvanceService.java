package com.yuan.workflow.core.engine.support;

import com.yuan.workflow.api.enums.NodeStatus;
import com.yuan.workflow.api.enums.NodeType;
import com.yuan.workflow.core.parser.FlowParser;
import com.yuan.workflow.core.resolver.AssigneeResolver;
import com.yuan.workflow.domain.WfDefinition;
import com.yuan.workflow.domain.WfInstance;
import com.yuan.workflow.domain.WfNodeInstance;
import com.yuan.workflow.mapper.WfDefinitionMapper;
import com.yuan.workflow.mapper.WfInstanceMapper;
import com.yuan.workflow.model.logicflow.LfNode;
import com.yuan.workflow.service.WfNodeInstanceService;
import com.yuan.workflow.service.WfTaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.util.Map;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class FlowAdvanceService {
    private final WfInstanceMapper instanceMapper;
    private final WfDefinitionMapper definitionMapper;
    private final VariableService variableService;
    private final FlowParser flowParser;
    private final InstanceLifecycle instanceLifecycle;
    private final TaskLifecycle taskLifecycle;
    private final WfNodeInstanceService nodeInstanceService;
    private final WfTaskService wfTaskService;
    private final AssigneeResolver assigneeResolver;

    public void advance(WfNodeInstance currentNode,Long operatorId) {
        WfInstance instance = instanceMapper.selectById(currentNode.getInstanceId());
        Assert.notNull(instance, "实例不存在");

        WfDefinition def = definitionMapper.selectById(instance.getDefinitionId());
        Assert.notNull(def, "流程定义不存在");

        LfNode currentFlowNode = flowParser.getNode(def, currentNode.getNodeKey());

        // 用最新变量（approve 时已 mergeAndSave）
        Map<String, Object> vars = variableService.getVars(instance);
        LfNode next = flowParser.getNextNode(def, currentFlowNode, vars);

        // 没有下一个节点或 next 是 END -> 结束
        if (next == null || NodeType.END.getCode().equals(next.getProperties().getWfType())) {
            instanceLifecycle.finishApproved(instance.getId(),operatorId);
            return;
        }
        // 创建下一个节点 + 任务
        WfNodeInstance nextNodeIns =
                nodeInstanceService.createNodeInstance(instance.getId(), next, NodeStatus.WAIT, currentNode.getOrderNo() + 1);

        Set<Long> userIds = assigneeResolver.resolve(next, instance);
        wfTaskService.createTasks(instance, nextNodeIns,userIds);

    }
}
