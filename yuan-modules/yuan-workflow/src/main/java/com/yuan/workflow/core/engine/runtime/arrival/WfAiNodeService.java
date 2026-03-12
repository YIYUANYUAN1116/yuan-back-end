package com.yuan.workflow.core.engine.runtime.arrival;


import com.yuan.workflow.domain.WfInstance;
import com.yuan.workflow.domain.WfNodeInstance;
import com.yuan.workflow.model.logicflow.LfNode;

import java.util.Map;

public interface WfAiNodeService {

    /**
     * 执行 AI 节点，返回需要写回流程的变量
     */
    Map<String, Object> execute(WfInstance instance,
                                WfNodeInstance nodeInstance,
                                LfNode targetNode,
                                Map<String, Object> variables);
}