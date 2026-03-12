package com.yuan.workflow.core.engine.runtime.arrival;

import com.yuan.workflow.core.engine.runtime.context.NodeArrivalContext;

public interface NodeArrivalHandler {

    /**
     * 是否支持该节点类型
     */
    boolean supports(String nodeType);

    /**
     * 到达节点后的处理逻辑
     */
    void handle(NodeArrivalContext context);
}