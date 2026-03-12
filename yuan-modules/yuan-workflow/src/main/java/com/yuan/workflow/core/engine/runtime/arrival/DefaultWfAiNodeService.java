package com.yuan.workflow.core.engine.runtime.arrival;


import com.yuan.workflow.domain.WfInstance;
import com.yuan.workflow.domain.WfNodeInstance;
import com.yuan.workflow.model.logicflow.LfNode;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class DefaultWfAiNodeService implements WfAiNodeService {

    @Override
    public Map<String, Object> execute(WfInstance instance,
                                       WfNodeInstance nodeInstance,
                                       LfNode targetNode,
                                       Map<String, Object> variables) {

        // 这里后面接已有的 LLM 统一调用能力
        // 先给个示例返回

        Map<String, Object> result = new HashMap<>();
        result.put("aiDecision", "PASS");
        result.put("aiScore", 90);
        result.put("aiSummary", "AI节点执行成功");

        Map<String, Object> aiResult = new HashMap<>();
        aiResult.put("decision", "PASS");
        aiResult.put("score", 90);
        aiResult.put("summary", "AI节点执行成功");

        result.put("aiResult", aiResult);
        return result;
    }
}