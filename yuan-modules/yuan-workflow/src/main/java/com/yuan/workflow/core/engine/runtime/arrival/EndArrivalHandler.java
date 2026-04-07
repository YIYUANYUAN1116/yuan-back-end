package com.yuan.workflow.core.engine.runtime.arrival;


import com.yuan.workflow.cmd.WorkflowCmd;
import com.yuan.workflow.core.engine.runtime.InstanceStateManager;
import com.yuan.workflow.core.engine.runtime.NodeInstanceStateManager;
import com.yuan.workflow.core.engine.runtime.TransitionLogManager;
import com.yuan.workflow.core.engine.runtime.WfEventManager;
import com.yuan.workflow.core.engine.runtime.context.NodeArrivalContext;
import com.yuan.workflow.domain.WfInstance;
import com.yuan.workflow.domain.WfNodeInstance;
import com.yuan.workflow.domain.enums.NodeType;
import com.yuan.workflow.enums.OperatorType;
import com.yuan.workflow.enums.TransitionAction;
import com.yuan.workflow.model.logicflow.LfNode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class EndArrivalHandler implements NodeArrivalHandler {

    private final NodeInstanceStateManager nodeInstanceStateManager;
    private final InstanceStateManager instanceStateManager;
    private final WfEventManager wfEventManager;
    private final TransitionLogManager transitionLogManager;

    @Override
    public boolean supports(String nodeType) {
        return NodeType.END.getCode().equals(nodeType);
    }

    @Override
    public List<LfNode> handle(NodeArrivalContext context) {
        WfInstance instance = context.getInstance();
        WfNodeInstance nodeInstance = context.getTargetNodeInstance();
        WorkflowCmd cmd = context.getTriggerCmd();

//        // 1. 完成结束节点
//        nodeInstanceStateManager.completeAuto(nodeInstance);
//
//        // 2. 完成流程实例
//        instanceStateManager.complete(instance, context.getTriggerCmd());
//
//        // 3. 发事件
//        wfEventManager.onInstanceCompleted(instance, nodeInstance);

        nodeInstanceStateManager.sysAutoApprove(nodeInstance, cmd);
        instanceStateManager.approve(instance, cmd);
        wfEventManager.approve(instance, cmd.getOperatorId());
        cmd.setComment(null);
        transitionLogManager.transitionLog(instance, nodeInstance, null, TransitionAction.END, OperatorType.SYSTEM, cmd, null, null);
        return null;
    }
}