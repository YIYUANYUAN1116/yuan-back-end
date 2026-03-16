package com.yuan.workflow.domain.dto;

import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class WfAiStructuredResult {

    /**
     * 风险等级：LOW / MEDIUM / HIGH
     */
    private String riskLevel;

    /**
     * AI建议：PASS / REVIEW / REJECT
     */
    private String suggestion;

    /**
     * AI摘要
     */
    private String summary;

    /**
     * 详细说明
     */
    private String reason;

    /**
     * 命中的规则/关注点
     */
    private List<String> hitRules;

    /**
     * 扩展字段，留给不同模板
     */
    private Map<String, Object> ext;
}