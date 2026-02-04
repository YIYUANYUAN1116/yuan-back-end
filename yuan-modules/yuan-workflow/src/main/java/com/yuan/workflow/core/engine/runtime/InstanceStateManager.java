package com.yuan.workflow.core.engine.runtime;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.yuan.workflow.cmd.RejectCmd;
import com.yuan.workflow.cmd.WithdrawCmd;
import com.yuan.workflow.cmd.WorkflowCmd;
import com.yuan.workflow.core.exception.InstanceConcurrentChangedException;
import com.yuan.workflow.domain.WfBizRef;
import com.yuan.workflow.domain.WfInstance;
import com.yuan.workflow.domain.enums.InstanceStatus;
import com.yuan.workflow.domain.enums.WfEndReason;
import com.yuan.workflow.mapper.WfBizRefMapper;
import com.yuan.workflow.mapper.WfInstanceMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Date;

@Slf4j
@Component
@RequiredArgsConstructor
public class InstanceStateManager {

    private final WfInstanceMapper instanceMapper;

    private final WfBizRefMapper bizRefMapper;

    public void reject(WfInstance instance, RejectCmd cmd) {
        update(instance,cmd, InstanceStatus.REJECTED,WfEndReason.REJECTED);
    }

    public void approve(WfInstance instance, WorkflowCmd cmd) {
        update(instance,cmd, InstanceStatus.APPROVED,WfEndReason.APPROVED);
    }

    public void withDraw(WfInstance wfInstance, WithdrawCmd cmd) {
        update(wfInstance,cmd, InstanceStatus.CANCELED,WfEndReason.WITHDRAWN);
    }

    private void update(WfInstance instance, WorkflowCmd cmd, InstanceStatus update, WfEndReason reason) {
        Long operatorId = cmd.getOperatorId();
        Long id = instance.getId();
        String operatorName = cmd.getOperatorName();
        int row = instanceMapper.update(Wrappers.<WfInstance>lambdaUpdate()
                .set(WfInstance::getStatus, update)
                .set(WfInstance::getEndTime, LocalDateTime.now())
                .set(WfInstance::getEndBy, operatorId)
                .set(WfInstance::getEndComment,cmd.getComment())
                .set(WfInstance::getEndReason,reason)
                .set(WfInstance::getLastOperatorId, operatorId)
                .set(WfInstance::getLastOperatorName, operatorName)
                .eq(WfInstance::getId, instance.getId())
                .eq(WfInstance::getStatus, InstanceStatus.RUNNING)
        );
        updateBizRef(id,update);
        if (row == 0){
            log.error("[InstanceStateManager][update] instanceId:{},expect:{}",instance.getId(), InstanceStatus.RUNNING);
            throw new InstanceConcurrentChangedException(id, InstanceStatus.RUNNING);
        }
    }

    private void updateBizRef(Long instanceId, InstanceStatus status) {
        WfBizRef ref = bizRefMapper.selectOne(
                new LambdaQueryWrapper<WfBizRef>().eq(WfBizRef::getInstanceId, instanceId)
        );
        if (ref != null) {
            ref.setStatus(status);
            ref.setUpdateTime(new Date());
            bizRefMapper.updateById(ref);
        }
    }

}
