package com.yuan.ai.api.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Data
public class AiTemplateExecuteResult {

    /**
     * PASS / REVIEW / REJECT
     */
    private String decision;

    /**
     * 评分
     */
    private BigDecimal score;

    /**
     * 摘要
     */
    private String summary;

    /**
     * 问题列表
     */
    private List<Map<String, Object>> issues;

    /**
     * 原始文本
     */
    private String rawText;

    /**
     * 原始结构化 JSON
     */
    private Map<String, Object> rawJson;

    /**
     * 实际命中的 provider / model，便于审计和排查
     */
    private String providerCode;

    private String modelCode;

    /**
     * 命中来源：REQUEST / TENANT_SCENE / PLATFORM_SCENE / TEMPLATE_DEFAULT / GLOBAL_DEFAULT
     */
    private String resolveSource;
}