package com.yuan.ai.service;


import com.yuan.ai.api.dto.AiTemplateExecuteRequest;
import com.yuan.ai.domain.dto.AiModelResolved;

public interface AiModelResolveService {

    AiModelResolved resolve(AiTemplateExecuteRequest request);
}