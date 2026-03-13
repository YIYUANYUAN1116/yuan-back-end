package com.yuan.workflow.core.parser;


import com.yuan.workflow.core.engine.runtime.arrival.AiNodeConfig;
import com.yuan.workflow.domain.enums.NodeType;
import com.yuan.workflow.model.logicflow.LfNode;
import org.springframework.stereotype.Component;

@Component
public class DefaultAiNodeConfigParser implements AiNodeConfigParser {

    @Override
    public AiNodeConfig parse(LfNode targetNode) {
        AiNodeConfig config = new AiNodeConfig();

        if (targetNode == null || targetNode.getProperties() == null) {
            return config;
        }

        boolean equals = targetNode.getProperties().getWfType().equals(NodeType.AI_TASK.getCode());
        if (equals) {
            return targetNode.getProperties().getAiNodeConfig();
        }else {
            return config;
        }
    }
}