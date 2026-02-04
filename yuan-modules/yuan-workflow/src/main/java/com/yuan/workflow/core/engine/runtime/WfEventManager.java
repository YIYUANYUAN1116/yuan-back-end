package com.yuan.workflow.core.engine.runtime;

import com.yuan.workflow.core.event.WfEventContext;
import com.yuan.workflow.core.event.WfEventFactory;
import com.yuan.workflow.core.event.WfEventPublisher;
import com.yuan.workflow.domain.WfBizRef;
import com.yuan.workflow.domain.WfInstance;
import com.yuan.workflow.enums.WfEndReason;
import com.yuan.workflow.mapper.WfBizRefMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@RequiredArgsConstructor
public class WfEventManager {
    private final WfBizRefMapper bizRefMapper;
    private final WfEventPublisher eventPublisher;

    public void approve(WfInstance instance,Long operatorId){
        WfEventContext eventContext = buildWfEventContext(instance, operatorId);
        eventPublisher.publishAfterCommit(WfEventFactory.buildInstanceEnded(eventContext, WfEndReason.APPROVED, Map.of()));
    }

    public void reject(WfInstance instance,Long operatorId){
        WfEventContext eventContext = buildWfEventContext(instance, operatorId);
        eventPublisher.publishAfterCommit(WfEventFactory.buildInstanceEnded(eventContext, WfEndReason.REJECTED, Map.of()));
    }

    public void withdraw(WfInstance instance,Long operatorId){
        WfEventContext eventContext = buildWfEventContext(instance, operatorId);
        eventPublisher.publishAfterCommit(WfEventFactory.buildInstanceEnded(eventContext, WfEndReason.WITHDRAWN, Map.of()));
    }


    private WfEventContext buildWfEventContext(WfInstance instance, Long operatorId) {
        WfBizRef bizRef = bizRefMapper.selectByInstanceId(instance.getId());
        return WfEventContext.builder()
                .tenantId(instance.getTenantId())
                .bizId(bizRef != null ? bizRef.getBizId() : null)
                .bizType(bizRef != null ? bizRef.getBizType() : null)
                .definitionId(instance.getDefinitionId())
                .instanceId(instance.getId())
                .starterId(instance.getStarterId())
                .operatorId(operatorId)
                .build();
    }
}
