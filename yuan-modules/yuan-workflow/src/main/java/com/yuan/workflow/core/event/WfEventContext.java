package com.yuan.workflow.core.event;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WfEventContext {
    private String tenantId;

    private String bizType;
    private Long bizId;

    private Long definitionId;
    private Long instanceId;
    private Long taskId;

    private Long nodeId;
    private String nodeName;

    private Long starterUserId;
    private Long operatorUserId;
}