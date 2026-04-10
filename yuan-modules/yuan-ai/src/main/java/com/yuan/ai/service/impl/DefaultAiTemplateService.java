package com.yuan.ai.service.impl;

import com.yuan.ai.service.AiTemplateService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Collections;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class DefaultAiTemplateService implements AiTemplateService {

    @Override
    public String getTemplateContent(String templateCode) {
        // todo 这里后面可以改成查表
        // 例如 ai_prompt_template
        if ("wf_risk_analyze".equals(templateCode)) {
            return """
                    你是一个流程审批分析助手。
                    
                               请根据以下申请信息进行分析：
                    
                               标题：${title}
                               发起人：${starterName}
                               申请金额：${amount}
                               申请原因：${reason}
                    
                               请输出 JSON，必须满足以下要求：
                               1. 只输出 JSON 本身
                               2. 不要输出 markdown 代码块
                               3. 不要输出额外解释说明
                               4. 字段结构如下：
                    
                               {
                                 "riskLevel": "LOW|MEDIUM|HIGH",
                                 "suggestion": "PASS|REVIEW|REJECT",
                                 "summary": "不超过100字的摘要",
                                 "reason": "详细分析理由",
                                 "hitRules": ["规则1", "规则2"],
                                 "ext": {}
                               }
                    """;
        }
        throw new IllegalArgumentException("Template not found: " + templateCode);
    }

    @Override
    public String render(String templateContent, Map<String, Object> variables) {
        if (!StringUtils.hasText(templateContent)) {
            return "";
        }

        Map<String, Object> safeVariables =
                variables == null ? Collections.emptyMap() : variables;

        String result = templateContent;
        for (Map.Entry<String, Object> entry : safeVariables.entrySet()) {
            String key = "${" + entry.getKey() + "}";
            String value = entry.getValue() == null ? "" : String.valueOf(entry.getValue());
            result = result.replace(key, value);
        }
        return result;
    }
}