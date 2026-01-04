package com.yuan.workflow.core.engine.support;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.yuan.workflow.api.enums.InstanceStatus;
import com.yuan.workflow.api.enums.NodeStatus;
import com.yuan.workflow.api.enums.TaskStatus;
import com.yuan.workflow.api.enums.WfEndReason;
import com.yuan.workflow.api.event.WfEventContext;
import com.yuan.workflow.api.event.WfEventFactory;
import com.yuan.workflow.core.event.SpringWfEventPublisher;
import com.yuan.workflow.domain.WfBizRef;
import com.yuan.workflow.domain.WfInstance;
import com.yuan.workflow.mapper.WfBizRefMapper;
import com.yuan.workflow.mapper.WfInstanceMapper;
import com.yuan.workflow.mapper.WfNodeInstanceMapper;
import com.yuan.workflow.mapper.WfTaskMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

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

  public void finishRejected(Long instanceId,Long operatorId) {
    WfInstance instance = instanceMapper.selectById(instanceId);
    Assert.notNull(instance, "实例不存在");
    instance.setStatus(InstanceStatus.REJECTED.getCode());
    instance.setEndTime(LocalDateTime.now());
    instanceMapper.updateById(instance);
    afterFinishProcess(instanceId, InstanceStatus.REJECTED.getCode());
    nodeInstanceMapper.finishAll(instanceId, NodeStatus.CANCELED.getCode());
    taskMapper.finishAll(instanceId, TaskStatus.CANCELED.getCode());

    // 发结束事件
    WfEventContext ctx = buildWfEventContext(instance,operatorId);
    eventPublisher.publishAfterCommit(WfEventFactory.buildInstanceEnded(ctx, WfEndReason.REJECTED, Map.of()));
  }



  public void finishApproved(Long instanceId,Long operatorId) {
    WfInstance instance = instanceMapper.selectById(instanceId);
    Assert.notNull(instance, "实例不存在");

    instance.setStatus(InstanceStatus.APPROVED.getCode());
    instance.setEndTime(LocalDateTime.now());
    instanceMapper.updateById(instance);

    afterFinishProcess(instanceId, InstanceStatus.APPROVED.getCode());

    // 发结束事件
    WfEventContext ctx = buildWfEventContext(instance,operatorId);
    eventPublisher.publishAfterCommit(WfEventFactory.buildInstanceEnded(ctx, WfEndReason.APPROVED, Map.of()));
  }

  private void afterFinishProcess(Long instanceId, String status) {
    WfBizRef ref = bizRefMapper.selectOne(
            new LambdaQueryWrapper<WfBizRef>().eq(WfBizRef::getInstanceId, instanceId)
    );
    if (ref != null) {
      ref.setStatus(status);
      ref.setUpdateTime(new Date());
      bizRefMapper.updateById(ref);
    }
  }

  private WfEventContext buildWfEventContext(WfInstance instance,Long operatorId) {
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
