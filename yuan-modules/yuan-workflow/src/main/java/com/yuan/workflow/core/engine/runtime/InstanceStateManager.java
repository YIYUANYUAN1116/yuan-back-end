package com.yuan.workflow.core.engine.runtime;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.yuan.workflow.cmd.RejectCmd;
import com.yuan.workflow.cmd.WithdrawCmd;
import com.yuan.workflow.cmd.WorkflowCmd;
import com.yuan.workflow.core.event.SpringWfEventPublisher;
import com.yuan.workflow.core.event.WfEventContext;
import com.yuan.workflow.core.event.WfEventFactory;
import com.yuan.workflow.domain.WfBizRef;
import com.yuan.workflow.domain.WfInstance;
import com.yuan.workflow.domain.enums.InstanceStatus;
import com.yuan.workflow.domain.enums.TaskAction;
import com.yuan.workflow.enums.WfEndReason;
import com.yuan.workflow.domain.exception.InstanceNotFoundException;
import com.yuan.workflow.mapper.WfBizRefMapper;
import com.yuan.workflow.mapper.WfInstanceMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class InstanceStateManager {

    private final WfInstanceMapper instanceMapper;
    private final WfBizRefMapper bizRefMapper;
    private final SpringWfEventPublisher eventPublisher;
    private final TaskStateManager taskStateManager;
    private final NodeInstanceStateManager nodeInstanceStateManager;

    @Transactional
    public void finishRejected(Long instanceId, RejectCmd cmd) {
        WfInstance instance = instanceMapper.selectById(instanceId);
        if (instance == null) throw new InstanceNotFoundException();
        finishRejected(instance, cmd);
    }

    @Transactional
    public void finishRejected(WfInstance instance, RejectCmd cmd) {
        instance.setStatus(InstanceStatus.REJECTED);
        instance.setEndTime(LocalDateTime.now());
        instance.setLastOperatorId(cmd.getOperatorId());
        instance.setLastOperatorName(cmd.getOperatorName());
        instanceMapper.updateById(instance);
        afterFinishProcess(instance.getId(), InstanceStatus.REJECTED);
        // 发结束事件
        WfEventContext ctx = buildWfEventContext(instance, cmd.getOperatorId());
        eventPublisher.publishAfterCommit(WfEventFactory.buildInstanceEnded(ctx, WfEndReason.REJECTED, Map.of()));
    }

    @Transactional
    public void finishApproved(Long instanceId, WorkflowCmd cmd) {
        WfInstance instance = instanceMapper.selectById(instanceId);
        if (instance == null) throw new InstanceNotFoundException();
        finishApproved(instance, cmd);
    }

    @Transactional
    public void finishApproved(WfInstance instance, WorkflowCmd cmd) {
        instance.setStatus(InstanceStatus.APPROVED);
        instance.setLastOperatorId(cmd.getOperatorId());
        instance.setLastOperatorName(cmd.getOperatorName());
        instance.setEndBy(cmd.getOperatorId());
        instance.setEndTime(LocalDateTime.now());
        instanceMapper.updateById(instance);
        afterFinishProcess(instance.getId(), InstanceStatus.APPROVED);
        // 发结束事件
        WfEventContext ctx = buildWfEventContext(instance, cmd.getOperatorId());
        eventPublisher.publishAfterCommit(WfEventFactory.buildInstanceEnded(ctx, WfEndReason.APPROVED, Map.of()));
    }

    @Transactional
    public void finishWithDraw(Long instanceId, WithdrawCmd cmd) {
        WfInstance instance = instanceMapper.selectById(instanceId);
        if (instance == null) throw new InstanceNotFoundException();
        finishWithDraw(instance, cmd);
    }

    @Transactional
    public void finishWithDraw(WfInstance instance, WithdrawCmd cmd) {

        // 取消所有待办任务（实例级）
        taskStateManager.cancelAllTodoTasks(instance.getId(), TaskAction.WITHDRAW,cmd.getOperatorId());

        //  取消所有 WAIT 节点
        nodeInstanceStateManager.cancelAllWaitByInstance(instance.getId(),cmd.getOperatorId());

        //更新实例
        instance.setStatus(InstanceStatus.CANCELED);
        instance.setEndTime(LocalDateTime.now());
        instance.setEndComment(cmd.getComment());
        instance.setEndBy(cmd.getOperatorId());
        instance.setEndReason(WfEndReason.WITHDRAWN);
        instance.setLastOperatorId(cmd.getOperatorId());
        instance.setLastOperatorName(cmd.getOperatorName());
        instanceMapper.updateById(instance);

        afterFinishProcess(instance.getId(), InstanceStatus.CANCELED);
        // 发结束事件
        WfEventContext ctx = buildWfEventContext(instance, cmd.getOperatorId());
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
                .starterId(instance.getStarterId())
                .operatorId(operatorId)
                .build();
    }
}
