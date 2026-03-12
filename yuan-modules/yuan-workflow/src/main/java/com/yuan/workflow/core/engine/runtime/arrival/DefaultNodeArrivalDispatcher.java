package com.yuan.workflow.core.engine.runtime.arrival;


import com.yuan.common.core.exception.workflow.WorkflowErrorCode;
import com.yuan.common.core.exception.workflow.WorkflowException;
import com.yuan.workflow.core.engine.runtime.context.NodeArrivalContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class DefaultNodeArrivalDispatcher implements NodeArrivalDispatcher {

    private final List<NodeArrivalHandler> handlers;

    @Override
    public void onArrive(NodeArrivalContext context) {
        String nodeType = context.getTargetNode().getType();

        for (NodeArrivalHandler handler : handlers) {
            if (handler.supports(nodeType)) {
                handler.handle(context);
                return;
            }
        }

        throw new WorkflowException(WorkflowErrorCode.WF_NODE_TYPE_INVALID);
    }
}