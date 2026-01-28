package com.yuan.workflow.cmd;

import com.yuan.workflow.enums.OperatorType;
import com.yuan.workflow.enums.TransitionAction;
import lombok.Builder;
import lombok.Data;

import java.util.Map;

@Data
@Builder
public class RecordTransitionCmd {

    /** 从 instance 冗余写入 */
    private String tenantId;
    private Long defId;
    private Integer defVersion;
    private Long instanceId;

    private Long nodeInstanceId;
    private Long taskId;

    private String fromNodeKey;
    private String toNodeKey;

    private TransitionAction action;

    private OperatorType operatorType;
    private Long operatorId; // USER 时必填，SYSTEM 可空或填 null

    private String comment;

    private String conditionExpr;
    /** 已经挑选过的变量快照 */
    private Map<String, Object> variablesSnapshot;
}