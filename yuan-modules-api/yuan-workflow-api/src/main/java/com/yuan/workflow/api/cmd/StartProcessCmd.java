package com.yuan.workflow.api.cmd;

import lombok.Data;

import java.util.Map;

@Data
public class StartProcessCmd extends WorkflowCmd{
    private String definitionKey;
    private String bizType;
    private Long bizId;
    /** 流程发起人（业务意义上的发起人） */
    private Long starterUserId;
    private String tenantId;
    private Map<String, Object> variables;
}