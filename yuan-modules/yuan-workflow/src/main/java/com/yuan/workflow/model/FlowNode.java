package com.yuan.workflow.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.yuan.workflow.domain.enums.NodeType;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class FlowNode {

    /** 节点唯一标识 */
    private String id;

    /** 节点类型：START / APPROVAL / GATEWAY / END */
    private NodeType type;

    /** ====== 审批节点专用 ====== */
    private Assignee assignee;

    /** ====== 网关节点专用 ====== */
    private List<GatewayCondition> conditions;

    /** 网关默认流向（条件都不命中时） */
    private String defaultTarget;
}

