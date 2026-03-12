package com.yuan.ai.service.impl;

import com.yuan.ai.api.AiTemplateExecuteApi;
import com.yuan.ai.api.dto.AiTemplateExecuteRequest;
import com.yuan.ai.api.dto.AiTemplateExecuteResult;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DefaultAiTemplateExecuteApi implements AiTemplateExecuteApi {

//    private final AiModelResolveService aiModelResolveService;
//    private final AiPromptTemplateService aiPromptTemplateService;
//    private final LlmRouteService llmRouteService;

    @Override
    public AiTemplateExecuteResult execute(AiTemplateExecuteRequest request) {
        // 重构
//        // 1. 模型解析
//        AiModelResolved resolved = aiModelResolveService.resolve(request);
//
//        // 2. 模板渲染
//        String systemPrompt = aiPromptTemplateService.renderSystemPrompt(
//                request.getTemplateCode(), request.getParams()
//        );
//        String userPrompt = aiPromptTemplateService.renderUserPrompt(
//                request.getTemplateCode(), request.getParams()
//        );
//
//        // 3. 调模型
//        AiTemplateExecuteResult result = llmRouteService.invoke(
//                resolved,
//                systemPrompt,
//                userPrompt,
//                request.getJsonOutput(),
//                request.getTimeoutMs()
//        );
//
//        // 4. 回填命中信息
//        result.setProviderCode(resolved.getProviderCode());
//        result.setModelCode(resolved.getModelCode());
//        result.setResolveSource(resolved.getSource());
//
//        return result;
        return null;
    }
}