package com.yuan.workflow.core.engine.runtime.arrival;

import com.yuan.workflow.cmd.WorkflowCmd;
import com.yuan.workflow.core.engine.runtime.NodeInstanceStateManager;
import com.yuan.workflow.core.engine.runtime.ProcessAdvancer;
import com.yuan.workflow.core.engine.runtime.TransitionLogManager;
import com.yuan.workflow.core.engine.runtime.context.NodeArrivalContext;
import com.yuan.workflow.core.parser.FlowParser;
import com.yuan.workflow.domain.WfDefinition;
import com.yuan.workflow.domain.WfInstance;
import com.yuan.workflow.domain.WfNodeInstance;
import com.yuan.workflow.domain.enums.NodeType;
import com.yuan.workflow.enums.OperatorType;
import com.yuan.workflow.enums.TransitionAction;
import com.yuan.workflow.model.logicflow.LfNode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class GatewayArrivalHandler implements NodeArrivalHandler {

    private final FlowParser flowParser;
    private final NodeInstanceStateManager nodeInstanceStateManager;
    private final TransitionLogManager  transitionLogManager;
    private final ProcessAdvancer processAdvancer;

    @Override
    public boolean supports(String nodeType) {
        return NodeType.GATEWAY.getCode().equals(nodeType);
    }

    @Override
    public void handle(NodeArrivalContext context) {
        WfDefinition def = context.getDefinition();
        LfNode lfNode = context.getTargetNode();
        Map<String, Object> vars = context.getVariables();
        WfNodeInstance nodeInstance = context.getTargetNodeInstance();
        WfInstance instance = context.getInstance();
        WorkflowCmd cmd = context.getTriggerCmd();

        List<LfNode> nodeList = flowParser.getNextNode(def, lfNode, vars);
        nodeInstanceStateManager.sysAutoApprove(nodeInstance, cmd);
        for (LfNode next : nodeList) {
            cmd.setComment(null);
            String conditionExpr = flowParser.parseConditionExpr(next);
            transitionLogManager.transitionLog(instance, nodeInstance, next, TransitionAction.GATEWAY, OperatorType.SYSTEM, cmd, conditionExpr, vars);
            processAdvancer.advanceToTarget(instance, def, next, cmd, vars);
        }
    }
}
