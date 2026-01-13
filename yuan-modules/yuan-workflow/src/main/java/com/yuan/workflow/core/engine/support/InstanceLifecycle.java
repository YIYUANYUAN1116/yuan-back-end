package com.yuan.workflow.core.engine.support;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.yuan.workflow.api.enums.WfEndReason;
import com.yuan.workflow.api.event.WfEventContext;
import com.yuan.workflow.api.event.WfEventFactory;
import com.yuan.workflow.core.event.SpringWfEventPublisher;
import com.yuan.workflow.domain.WfBizRef;
import com.yuan.workflow.domain.WfInstance;
import com.yuan.workflow.domain.enums.InstanceStatus;
import com.yuan.workflow.domain.enums.TaskAction;
import com.yuan.workflow.domain.exception.InstanceNotFoundException;
import com.yuan.workflow.mapper.WfBizRefMapper;
import com.yuan.workflow.mapper.WfInstanceMapper;
import com.yuan.workflow.mapper.WfNodeInstanceMapper;
import com.yuan.workflow.mapper.WfTaskMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class InstanceLifecycle {

    private final WfInstanceMapper instanceMapper;
    private final WfBizRefMapper bizRefMapper;
    private final WfNodeInstanceMapper nodeInstanceMapper;
    private final WfTaskMapper taskMapper;
    private final SpringWfEventPublisher eventPublisher;
    private final TaskLifecycle taskLifecycle;
    private final NodeInstanceLifeCycle nodeInstanceLifeCycle;

    @Transactional
    public void finishRejected(Long instanceId, Long operatorId) {
        WfInstance instance = instanceMapper.selectById(instanceId);
        if (instance == null) throw new InstanceNotFoundException();
        finishRejected(instance, operatorId);
    }

    @Transactional
    public void finishRejected(WfInstance instance, Long operatorId) {
        instance.setStatus(InstanceStatus.REJECTED);
        instance.setEndTime(LocalDateTime.now());
        instanceMapper.updateById(instance);
        afterFinishProcess(instance.getId(), InstanceStatus.REJECTED);
        // 发结束事件
        WfEventContext ctx = buildWfEventContext(instance, operatorId);
        eventPublisher.publishAfterCommit(WfEventFactory.buildInstanceEnded(ctx, WfEndReason.REJECTED, Map.of()));
    }

    @Transactional
    public void finishApproved(Long instanceId, Long operatorId) {
        WfInstance instance = instanceMapper.selectById(instanceId);
        if (instance == null) throw new InstanceNotFoundException();
    }

    @Transactional
    public void finishApproved(WfInstance instance, Long operatorId) {
        instance.setStatus(InstanceStatus.APPROVED);
        instance.setEndTime(LocalDateTime.now());
        instanceMapper.updateById(instance);
        afterFinishProcess(instance.getId(), InstanceStatus.APPROVED);
        // 发结束事件
        WfEventContext ctx = buildWfEventContext(instance, operatorId);
        eventPublisher.publishAfterCommit(WfEventFactory.buildInstanceEnded(ctx, WfEndReason.APPROVED, Map.of()));
    }

    @Transactional
    public void finishWithDraw(Long instanceId, Long operatorId, String comment) {
        WfInstance instance = instanceMapper.selectById(instanceId);
        if (instance == null) throw new InstanceNotFoundException();
        finishWithDraw(instance, operatorId, comment);
    }

    @Transactional
    public void finishWithDraw(WfInstance instance, Long operatorId, String comment) {


        // 取消所有待办任务（实例级）
        taskLifecycle.cancelAllTodoTasks(instance.getId(), TaskAction.WITHDRAW);

        //  取消所有 WAIT 节点
        nodeInstanceLifeCycle.cancelAllWaitByInstance(instance.getId());

        //更新实例
        instance.setStatus(InstanceStatus.CANCELED);
        instance.setEndTime(LocalDateTime.now());
        instanceMapper.updateById(instance);


        afterFinishProcess(instance.getId(), InstanceStatus.CANCELED);
        // 发结束事件
        WfEventContext ctx = buildWfEventContext(instance, operatorId);
        eventPublisher.publishAfterCommit(WfEventFactory.buildInstanceEnded(ctx, WfEndReason.WITHDRAWN, Map.of()));
    }

    @Transactional
    public void afterFinishProcess(Long instanceId, InstanceStatus status) {
        WfBizRef ref = bizRefMapper.selectOne(
                new LambdaQueryWrapper<WfBizRef>().eq(WfBizRef::getInstanceId, instanceId)
        );
        if (ref != null) {
            ref.setStatus(status);
            ref.setUpdateTime(new Date());
            bizRefMapper.updateById(ref);
        }
    }

    private WfEventContext buildWfEventContext(WfInstance instance, Long operatorId) {
        WfBizRef bizRef = bizRefMapper.selectByInstanceId(instance.getId());
        return WfEventContext.builder()
                .tenantId(instance.getTenantId())
                .bizId(bizRef != null ? bizRef.getBizId() : null)
                .bizType(bizRef != null ? bizRef.getBizType() : null)
                .definitionId(instance.getDefinitionId())
                .instanceId(instance.getId())
                .starterUserId(instance.getStartUserId())
                .operatorUserId(operatorId)
                .build();
    }
}
