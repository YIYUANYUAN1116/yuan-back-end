package com.yuan.workflow.core.engine.runtime.arrival;

import com.yuan.workflow.core.engine.runtime.context.NodeArrivalContext;
import com.yuan.workflow.model.logicflow.LfNode;

import java.util.List;

public interface NodeArrivalHandler {

    /**
     * 是否支持该节点类型
     */
    boolean supports(String nodeType);

    /**
     * 到达节点后的处理逻辑
     */
    List<LfNode> handle(NodeArrivalContext context);
}