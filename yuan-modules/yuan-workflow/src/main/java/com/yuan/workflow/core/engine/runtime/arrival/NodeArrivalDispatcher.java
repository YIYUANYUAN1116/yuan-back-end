package com.yuan.workflow.core.engine.runtime.arrival;

import com.yuan.workflow.core.engine.runtime.context.NodeArrivalContext;

public interface NodeArrivalDispatcher {

    void onArrive(NodeArrivalContext context);
}