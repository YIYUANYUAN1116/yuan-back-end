package com.yuan.workflow.core.engine.runtime.arrival;


import com.yuan.ai.api.AiStructuredResultParser;
import com.yuan.ai.api.AiTemplateExecuteApi;
import com.yuan.ai.api.dto.AiTemplateExecuteRequest;
import com.yuan.ai.api.dto.AiTemplateExecuteResult;
import com.yuan.workflow.domain.dto.WfAiNodeRequest;
import com.yuan.workflow.domain.dto.WfAiNodeResult;
import com.yuan.workflow.domain.dto.WfAiStructuredResult;
import com.yuan.workflow.service.WfAiOutputVariableService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class DefaultWfAiNodeService implements WfAiNodeService {

    private final AiTemplateExecuteApi aiTemplateExecuteApi;
    private final AiStructuredResultParser aiStructuredResultParser;
    private final WfAiOutputVariableService wfAiOutputVariableService;

    @Override
    public WfAiNodeResult analyze(WfAiNodeRequest request) {
        AiTemplateExecuteRequest executeRequest = new AiTemplateExecuteRequest();
        executeRequest.setTemplateCode(request.getTemplateCode());
        executeRequest.setTemplateContent(request.getTemplateContent());
        executeRequest.setVariables(request.getVariables());
        executeRequest.setSystemPrompt(request.getSystemPrompt());
        executeRequest.setEndpointCode(request.getEndpointCode());
        executeRequest.setAutoSelectModel(request.getAutoSelectModel());
        executeRequest.setSceneCode("workflow_ai");
        executeRequest.setBizType("workflow");
        executeRequest.setBizId(request.getInstanceId());
        executeRequest.setUserId(request.getOperatorId());
        executeRequest.setAppId("workflow");
        executeRequest.setTraceId(request.getTraceId());
        executeRequest.setPersistChatMessage(Boolean.FALSE);
        executeRequest.setEnableThinking(request.getEnableThinking());

        AiTemplateExecuteResult executeResult = aiTemplateExecuteApi.execute(executeRequest);

        if (!Boolean.TRUE.equals(executeResult.getSuccess())) {
            return WfAiNodeResult.builder()
                    .success(Boolean.FALSE)
                    .rawContent(executeResult.getContent())
                    .structuredResult(null)
                    .outputVariables(null)
                    .invocationId(executeResult.getInvocationId())
                    .errorMessage(executeResult.getErrorMessage())
                    .build();
        }

        try {
            WfAiStructuredResult structuredResult =
                    aiStructuredResultParser.parse(executeResult.getContent(), WfAiStructuredResult.class);

            Map<String, Object> outputVariables =
                    wfAiOutputVariableService.mapToVariables(structuredResult);

            return WfAiNodeResult.builder()
                    .success(Boolean.TRUE)
                    .rawContent(executeResult.getContent())
                    .structuredResult(structuredResult)
                    .outputVariables(outputVariables)
                    .invocationId(executeResult.getInvocationId())
                    .errorMessage(null)
                    .build();

        } catch (Exception e) {
            log.error("Parse workflow AI result failed, instanceId={}, invocationId={}",
                    request.getInstanceId(), executeResult.getInvocationId(), e);

            Map<String, Object> fallbackVars = Map.of(
                    "aiRawContent", executeResult.getContent(),
                    "aiParseFailed", true,
                    "aiSuggestion", "REVIEW"
            );

            return WfAiNodeResult.builder()
                    .success(Boolean.TRUE)
                    .rawContent(executeResult.getContent())
                    .structuredResult(null)
                    .outputVariables(fallbackVars)
                    .invocationId(executeResult.getInvocationId())
                    .errorMessage("AI返回结果解析失败，已按兜底策略处理")
                    .build();
        }
    }
}