package com.yuan.workflow.core.engine.runtime.arrival;


import com.yuan.workflow.domain.WfInstance;
import com.yuan.workflow.domain.WfNodeInstance;
import com.yuan.workflow.model.logicflow.LfNode;

import java.util.Map;

public interface WfAiPromptParamBuilder {

    Map<String, Object> build(WfInstance instance,
                              WfNodeInstance nodeInstance,
                              LfNode targetNode,
                              AiNodeConfig config,
                              Map<String, Object> variables);
}