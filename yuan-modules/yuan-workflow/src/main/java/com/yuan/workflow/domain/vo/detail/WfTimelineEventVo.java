package com.yuan.workflow.domain.vo.detail;

import com.yuan.workflow.enums.OperatorType;
import com.yuan.workflow.enums.TransitionAction;
import com.yuan.workflow.enums.TransitionResult;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class WfTimelineEventVo {
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
    private Long id;

    private LocalDateTime time;

    /** START / APPROVE / ROLLBACK / TRANSFER / GATEWAY / END ... */
    private TransitionAction action;

    private OperatorType operatorType;   // USER / SYSTEM
    private Long operatorId;
    private String operatorName;   // 展示用（可空）

    /** 节点信息 */
    private String fromNodeKey;
    private String fromNodeName;
    private String toNodeKey;
    private String toNodeName;

    /** 审批意见 / 转交说明 / 退回原因 */
    private String comment;

    /** 网关命中条件 */
    private String conditionExpr;

    /** SUCCESS / FAIL */
    private TransitionResult result;
}
