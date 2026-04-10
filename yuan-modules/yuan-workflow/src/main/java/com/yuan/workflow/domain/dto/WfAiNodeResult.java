package com.yuan.workflow.domain.dto;

import lombok.Builder;
import lombok.Data;

import java.util.Map;

@Data
@Builder
public class WfAiNodeResult {

    private Boolean success;

    /**
     * 模型原始输出
     */
    private String rawContent;

    /**
     * 结构化结果
     */
    private WfAiStructuredResult structuredResult;

    /**
     * 写回的流程变量
     */
    private Map<String, Object> outputVariables;

    private Long invocationId;

    private String errorMessage;
}