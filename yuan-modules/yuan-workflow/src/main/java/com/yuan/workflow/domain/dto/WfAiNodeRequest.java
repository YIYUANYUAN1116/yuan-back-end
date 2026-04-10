package com.yuan.workflow.domain.dto;

import lombok.Data;

import java.util.Map;

@Data
public class WfAiNodeRequest {

    private Long instanceId;
    private Long nodeInstanceId;
    private Long operatorId;
    private String traceId;

    private String templateCode;
    private String templateContent;
    private String systemPrompt;

    private String endpointCode;
    private Boolean autoSelectModel = Boolean.TRUE;
    private Boolean enableThinking;

    /**
     * 给模板渲染的变量
     */
    private Map<String, Object> variables;
}