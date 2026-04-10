package com.yuan.workflow.service.impl;


import com.yuan.workflow.domain.dto.WfAiStructuredResult;
import com.yuan.workflow.service.WfAiOutputVariableService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class DefaultWfAiOutputVariableService implements WfAiOutputVariableService {

    @Override
    public Map<String, Object> mapToVariables(WfAiStructuredResult result) {
        Map<String, Object> vars = new HashMap<>();
        if (result == null) {
            return vars;
        }

        vars.put("aiRiskLevel", result.getRiskLevel());
        vars.put("aiSuggestion", result.getSuggestion());
        vars.put("aiSummary", result.getSummary());
        vars.put("aiReason", result.getReason());
        vars.put("aiHitRules", result.getHitRules());

        if (result.getExt() != null && !result.getExt().isEmpty()) {
            result.getExt().forEach((k, v) -> vars.put("aiExt_" + k, v));
        }

        return vars;
    }
}