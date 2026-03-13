package com.yuan.workflow.core.engine.runtime.arrival;


import com.yuan.ai.api.AiTemplateExecuteApi;
import com.yuan.ai.api.dto.AiTemplateExecuteRequest;
import com.yuan.ai.api.dto.AiTemplateExecuteResult;
import com.yuan.common.core.constant.AiBizTypeConstants;
import com.yuan.common.core.constant.AiSceneCodeConstants;
import com.yuan.common.core.constant.WfAiConstants;
import com.yuan.common.core.utils.StringUtils;
import com.yuan.workflow.core.exception.WorkflowEngineException;
import com.yuan.workflow.core.parser.AiNodeConfigParser;
import com.yuan.workflow.domain.WfInstance;
import com.yuan.workflow.domain.WfNodeInstance;
import com.yuan.workflow.model.logicflow.LfNode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class DefaultWfAiNodeService implements WfAiNodeService {
    private final AiTemplateExecuteApi aiTemplateExecuteApi;
    private final AiNodeConfigParser  aiNodeConfigParser;
    private final WfAiPromptParamBuilder wfAiPromptParamBuilder;

    @Override
    public Map<String, Object> execute(WfInstance instance,
                                       WfNodeInstance nodeInstance,
                                       LfNode targetNode,
                                       Map<String, Object> variables) {

        // 1. 解析节点配置
        AiNodeConfig config = aiNodeConfigParser.parse(targetNode);
        validateConfig(config, targetNode);

        // 2. 构建 prompt 参数
        Map<String, Object> params = wfAiPromptParamBuilder.build(
                instance, nodeInstance, targetNode, config, variables
        );

        // 3. 调 AI 模块通用接口
        AiTemplateExecuteRequest request = new AiTemplateExecuteRequest();
        request.setTenantId(instance.getTenantId());
        request.setBizType(AiBizTypeConstants.WORKFLOW);
        request.setSceneCode(AiSceneCodeConstants.WORKFLOW_NODE);
        request.setTemplateCode(config.getTemplateCode());
        request.setBizId(String.valueOf(instance.getId()));
        request.setOperatorId(null);
        request.setParams(params);
        request.setJsonOutput(Boolean.TRUE.equals(config.getJsonOutput()));
        request.setTimeoutMs(config.getTimeoutMs());

        // 这里不设置 modelCode，让 ai 模块按策略解析
        AiTemplateExecuteResult result = aiTemplateExecuteApi.execute(request);
        if (result == null) {
            throw new WorkflowEngineException("AI节点执行失败：未返回结果");
        }

        // 4. 转成流程变量
        return buildOutputVars(config, result);
    }

    private void validateConfig(AiNodeConfig config, LfNode targetNode) {
        if (config == null) {
            throw new WorkflowEngineException("AI节点配置不能为空, nodeId=" + targetNode.getId());
        }
        if (StringUtils.isBlank(config.getTemplateCode())) {
            throw new WorkflowEngineException("AI节点 templateCode 不能为空, nodeId=" + targetNode.getId());
        }
    }

    private Map<String, Object> buildOutputVars(AiNodeConfig config, AiTemplateExecuteResult result) {
        String prefix = StringUtils.defaultIfBlank(
                config.getOutputVarPrefix(),
                WfAiConstants.DEFAULT_OUTPUT_PREFIX
        );

        String aiMode = StringUtils.defaultIfBlank(
                config.getAiMode(),
                WfAiConstants.AI_MODE_SUGGEST
        );

        Map<String, Object> output = new HashMap<>();

        // 1. 节点前缀变量
        output.put(prefix + "Mode", aiMode);
        output.put(prefix + "Decision", normalizeDecision(result.getDecision()));
        output.put(prefix + "Score", normalizeScore(result.getScore()));
        output.put(prefix + "Summary", defaultString(result.getSummary()));
        output.put(prefix + "RawText", defaultString(result.getRawText()));
        output.put(prefix + "ProviderCode", result.getProviderCode());
        output.put(prefix + "ModelCode", result.getModelCode());
        output.put(prefix + "ResolveSource", result.getResolveSource());

        Map<String, Object> fullResult = new HashMap<>();
        fullResult.put("mode", aiMode);
        fullResult.put("decision", normalizeDecision(result.getDecision()));
        fullResult.put("score", normalizeScore(result.getScore()));
        fullResult.put("summary", defaultString(result.getSummary()));
        fullResult.put("issues", result.getIssues());
        fullResult.put("rawText", defaultString(result.getRawText()));
        fullResult.put("rawJson", result.getRawJson());
        fullResult.put("providerCode", result.getProviderCode());
        fullResult.put("modelCode", result.getModelCode());
        fullResult.put("resolveSource", result.getResolveSource());

        output.put(prefix + "Result", fullResult);

        // 2. 通用变量，便于网关统一判断
        output.put("aiMode", aiMode);
        output.put("aiDecision", normalizeDecision(result.getDecision()));
        output.put("aiScore", normalizeScore(result.getScore()));
        output.put("aiSummary", defaultString(result.getSummary()));
        output.put("aiProviderCode", result.getProviderCode());
        output.put("aiModelCode", result.getModelCode());
        output.put("aiResolveSource", result.getResolveSource());
        output.put("aiResult", fullResult);

        return output;
    }

    private String normalizeDecision(String decision) {
        if (StringUtils.isBlank(decision)) {
            return "REVIEW";
        }
        String upper = decision.trim().toUpperCase();
        return switch (upper) {
            case "PASS", "APPROVE" -> "PASS";
            case "REJECT", "DENY" -> "REJECT";
            default -> "REVIEW";
        };
    }

    private BigDecimal normalizeScore(BigDecimal score) {
        return score == null ? BigDecimal.ZERO : score;
    }

    private String defaultString(String text) {
        return text == null ? "" : text;
    }
}