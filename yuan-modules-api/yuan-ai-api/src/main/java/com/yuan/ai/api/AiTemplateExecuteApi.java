package com.yuan.ai.api;

import com.yuan.ai.api.dto.AiTemplateExecuteRequest;
import com.yuan.ai.api.dto.AiTemplateExecuteResult;

public interface AiTemplateExecuteApi {

    AiTemplateExecuteResult execute(AiTemplateExecuteRequest request);
}