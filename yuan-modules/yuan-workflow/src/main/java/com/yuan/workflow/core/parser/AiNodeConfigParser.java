package com.yuan.workflow.core.parser;


import com.yuan.workflow.core.engine.runtime.arrival.AiNodeConfig;
import com.yuan.workflow.model.logicflow.LfNode;

public interface AiNodeConfigParser {

    AiNodeConfig parse(LfNode targetNode);
}