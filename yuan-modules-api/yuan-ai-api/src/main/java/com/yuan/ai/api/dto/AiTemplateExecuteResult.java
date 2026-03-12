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

    private BigDecimal score;

    private String summary;

    private List<Map<String, Object>> issues;

    /**
     * 原始文本
     */
    private String rawText;

    /**
     * 原始结构化结果
     */
    private Map<String, Object> rawJson;
}