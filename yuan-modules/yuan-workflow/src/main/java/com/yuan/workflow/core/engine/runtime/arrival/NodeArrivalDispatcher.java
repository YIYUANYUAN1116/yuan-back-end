package com.yuan.workflow.core.engine.runtime.arrival;

import com.yuan.workflow.core.engine.runtime.context.NodeArrivalContext;
import com.yuan.workflow.model.logicflow.LfNode;

import java.util.List;

public interface NodeArrivalDispatcher {

    List<LfNode> onArrive(NodeArrivalContext context);
}