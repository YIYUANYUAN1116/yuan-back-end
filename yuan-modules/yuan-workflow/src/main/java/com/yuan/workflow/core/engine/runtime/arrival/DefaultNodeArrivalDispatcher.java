package com.yuan.workflow.core.engine.runtime.arrival;


import com.yuan.common.core.exception.workflow.WorkflowErrorCode;
import com.yuan.common.core.exception.workflow.WorkflowException;
import com.yuan.workflow.core.engine.runtime.context.NodeArrivalContext;
import com.yuan.workflow.model.logicflow.LfNode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class DefaultNodeArrivalDispatcher implements NodeArrivalDispatcher {

    private final List<NodeArrivalHandler> handlers;

    @Override
    public List<LfNode> onArrive(NodeArrivalContext context) {
        String nodeType = context.getTargetNode().getProperties().getWfType();

        for (NodeArrivalHandler handler : handlers) {
            if (handler.supports(nodeType)) {
                return handler.handle(context);
            }
        }

        throw new WorkflowException(WorkflowErrorCode.WF_NODE_TYPE_INVALID);
    }
}