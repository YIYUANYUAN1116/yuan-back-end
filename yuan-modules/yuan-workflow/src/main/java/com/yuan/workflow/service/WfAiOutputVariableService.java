package com.yuan.workflow.service;


import com.yuan.workflow.domain.dto.WfAiStructuredResult;

import java.util.Map;

public interface WfAiOutputVariableService {

    Map<String, Object> mapToVariables(WfAiStructuredResult result);

}