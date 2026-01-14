package com.yuan.workflow.core.event;

import com.yuan.workflow.api.enums.WfDecision;
import com.yuan.workflow.api.enums.WfEventType;
import com.yuan.workflow.domain.enums.WfEndReason;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;

public final class WfEventFactory {

    private WfEventFactory() {}

    public static WfEvent buildTaskApproved(WfEventContext ctx, String comment, Map<String, Object> ext) {
        return base(ctx)
                .eventType(WfEventType.TASK_APPROVED)
                .decision(WfDecision.APPROVED)
                .comment(comment)
                .ext(ext)
                .build();
    }

    public static WfEvent buildTaskRejected(WfEventContext ctx, String comment, Map<String, Object> ext) {
        return base(ctx)
                .eventType(WfEventType.TASK_REJECTED)
                .decision(WfDecision.REJECTED)
                .comment(comment)
                .ext(ext)
                .build();
    }

    public static WfEvent buildRollbackToPrev(WfEventContext ctx, String comment, Map<String, Object> ext) {
        return base(ctx)
                .eventType(WfEventType.TASK_ROLLBACK_PREV)
                .decision(WfDecision.ROLLBACK_TO)
                .comment(comment)
                .ext(ext)
                .build();
    }

    public static WfEvent buildRollbackTo(WfEventContext ctx, String targetNodeId, String targetNodeName,
                                          String comment, Map<String, Object> ext) {
        return base(ctx)
                .eventType(WfEventType.TASK_ROLLBACK_TO)
                .decision(WfDecision.ROLLBACK_TO)
                .targetNodeId(targetNodeId)
                .targetNodeName(targetNodeName)
                .comment(comment)
                .ext(ext)
                .build();
    }

    public static WfEvent buildTaskTransferred(WfEventContext ctx, Long toUserId, String reason, Map<String, Object> ext) {
        return base(ctx)
                .eventType(WfEventType.TASK_TRANSFERRED)
                .decision(WfDecision.TRANSFER)
                .toUserId(toUserId)
                .comment(reason)
                .ext(ext)
                .build();
    }

    public static WfEvent buildInstanceWithdrawn(WfEventContext ctx, String comment, Map<String, Object> ext) {
        return base(ctx)
                .eventType(WfEventType.INSTANCE_WITHDRAWN)
                .decision(WfDecision.WITHDRAW)
                .comment(comment)
                .ext(ext)
                .build();
    }

    public static WfEvent buildInstanceEnded(WfEventContext ctx, WfEndReason reason, Map<String, Object> ext) {
        return base(ctx)
                .eventType(WfEventType.INSTANCE_ENDED)
                .endReason(reason)
                .ext(ext)
                .build();
    }

    private static WfEvent.WfEventBuilder base(WfEventContext ctx) {
        return WfEvent.builder()
                .eventId(UUID.randomUUID().toString())
                .occurredAt(LocalDateTime.now())
                .tenantId(ctx.getTenantId())
                .bizType(ctx.getBizType())
                .bizId(ctx.getBizId())
                .definitionId(ctx.getDefinitionId())
                .instanceId(ctx.getInstanceId())
                .taskId(ctx.getTaskId())
                .nodeId(ctx.getNodeId())
                .nodeName(ctx.getNodeName())
                .starterId(ctx.getStarterId())
                .operatorId(ctx.getOperatorId());
    }
}