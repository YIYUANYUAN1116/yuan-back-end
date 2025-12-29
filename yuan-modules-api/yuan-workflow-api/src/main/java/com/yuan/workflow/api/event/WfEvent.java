package com.yuan.workflow.api.event;

import com.yuan.workflow.api.enums.WfDecision;
import com.yuan.workflow.api.enums.WfEndReason;
import com.yuan.workflow.api.enums.WfEventType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Map;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WfEvent implements Serializable {

    // ===== 事件基础信息 =====
    private String eventId;              // 全局唯一（UUID/雪花）
    private LocalDateTime occurredAt;     // 发生时间
    private WfEventType eventType;        // 事件类型：TASK_DECIDED / TASK_TRANSFERRED / INSTANCE_WITHDRAWN / INSTANCE_ENDED 等

    // ===== 工作流定位 =====
    private Long instanceId;             // 流程实例ID
    private Long taskId;                 // 任务ID（有些事件没有任务：如 INSTANCE_ENDED 可为空）
    private String definitionKey;        // 流程定义Key（你定义的 wfKey / code）
    private Long definitionId;
    private Integer definitionVersion;   // 版本（可选）
    private String bizKey;               // 业务Key（例如 "expense:123" / "leave:456"）
    private String tenantId;             // 多租户（可选）

    // ===== 节点信息（方便业务判断）=====
    private Long nodeId;               // 当前节点（处理时的节点）
    private String nodeName;             // 节点名（可选）
    private String targetNodeId;         // 目标节点（退回到哪里、转到哪里这种场景）
    private String targetNodeName;       // 可选

    // ===== 操作人/被操作人 =====
    private Long starterId;
    private Long operatorId;             // 当前操作人（谁点了同意/退回/撤回/转办）
    private String operatorName;         // 可选
    private Long assigneeId;             // 当前任务办理人（审批时一般=operator，转办前的原办理人）
    private Long toUserId;               // 转办目标人（transfer 专用）

    // ===== 决策/原因/备注 =====
    private WfDecision decision;         // 对“任务”的决策：APPROVE/REJECT/ROLLBACK/TRANSFER/WITHDRAW...
    private WfEndReason endReason;       // 对“实例结束”的原因：APPROVED_END/REJECTED_END/WITHDRAWN_END/TERMINATED...
    private String comment;              // 审批意见/退回原因/撤回说明
    private String reason;               // 转办原因/结束原因（可与 comment 合并，用一个也行）

    // ===== 业务扩展（非常关键）=====
    private String bizType;              // 业务类型：LEAVE / EXPENSE / CONTRACT...
    private Long bizId;                  // 业务主键（可选，如果你业务是 Long）
    private Map<String, Object> ext;     // 扩展字段：金额、天数、表单摘要、原节点等（避免频繁改表/改DTO）

    // ===== 幂等与追踪 =====
    private String traceId;              // 链路追踪（可从网关/请求上下文带进来）
    private Long sequence;               // 实例内序号（可选，用于消费端有序处理）
    private String dedupKey;             // 去重Key（推荐：instanceId + ":" + taskId + ":" + eventType + ":" + sequence）
}
