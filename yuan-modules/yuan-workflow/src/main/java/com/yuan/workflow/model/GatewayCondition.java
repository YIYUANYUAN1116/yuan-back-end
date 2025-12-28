package com.yuan.workflow.model;

import lombok.Data;

@Data
public class GatewayCondition {

    /** 条件表达式 */
    private Expression expression;

    /** 命中后流向的节点 ID */
    private String target;
}
