package com.yuan.workflow.core.engine.runtime.arrival;


import com.yuan.workflow.domain.WfInstance;
import com.yuan.workflow.domain.WfNodeInstance;
import com.yuan.workflow.model.logicflow.LfNode;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class DefaultWfAiPromptParamBuilder implements WfAiPromptParamBuilder {

    @Override
    public Map<String, Object> build(WfInstance instance,
                                     WfNodeInstance nodeInstance,
                                     LfNode targetNode,
                                     AiNodeConfig config,
                                     Map<String, Object> variables) {

        Map<String, Object> params = new HashMap<>();
        params.put("instanceId", instance.getId());
        params.put("nodeInstanceId", nodeInstance.getId());
        params.put("nodeKey", nodeInstance.getNodeKey());
        params.put("nodeName", nodeInstance.getNodeName());
        params.put("nodeType", targetNode.getType());
        params.put("aiMode", config.getAiMode());

        // 当前流程变量
        params.put("variables", variables);

        // 这里后面可以补：
        // bizData / formData / attachmentTexts / operator / 发起人信息等
        params.put("bizData", variables);
        params.put("attachmentsText", "");

        return params;
    }
}