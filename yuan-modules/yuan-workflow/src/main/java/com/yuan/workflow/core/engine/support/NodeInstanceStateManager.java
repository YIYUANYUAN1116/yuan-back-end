package com.yuan.workflow.core.engine.support;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.yuan.workflow.core.exception.InstanceStatusInvalidException;
import com.yuan.workflow.domain.WfNodeInstance;
import com.yuan.workflow.domain.enums.NodeStatus;
import com.yuan.workflow.mapper.WfNodeInstanceMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
@Slf4j
public class NodeInstanceStateManager {
    private final WfNodeInstanceMapper nodeInstanceMapper;

    public void finishDone(WfNodeInstance node) {
        node.setStatus(NodeStatus.DONE);
        node.setOperatorId(0L);
        node.setFinishedTime(LocalDateTime.now());
        nodeInstanceMapper.updateById(node);
    }

    public void finishDone(Long nodeInstanceId,Long operatorId) {
        int update = nodeInstanceMapper.update(Wrappers.<WfNodeInstance>lambdaUpdate()
                .set(WfNodeInstance::getStatus, NodeStatus.DONE)
                .set(WfNodeInstance::getOperatorId, operatorId)
                .set(WfNodeInstance::getFinishedTime, LocalDateTime.now())
                .eq(WfNodeInstance::getId, nodeInstanceId)
                .eq(WfNodeInstance::getStatus, NodeStatus.WAIT));
        if (update == 0){
            log.error("[finishDone]: don't finish done nodeInstanceId={},expectStatus={}", nodeInstanceId,NodeStatus.WAIT);
            throw new InstanceStatusInvalidException(nodeInstanceId, NodeStatus.WAIT);
        }
    }

    public void finishCancel(WfNodeInstance node) {
        node.setStatus(NodeStatus.CANCELED);
        nodeInstanceMapper.updateById(node);
    }

    public void finishCancel(Long nodeInstanceId,Long operatorId) {
        int update = nodeInstanceMapper.update(Wrappers.<WfNodeInstance>lambdaUpdate()
                .set(WfNodeInstance::getStatus, NodeStatus.CANCELED)
                .set(WfNodeInstance::getOperatorId, operatorId)
                .set(WfNodeInstance::getFinishedTime, LocalDateTime.now())
                .eq(WfNodeInstance::getInstanceId, nodeInstanceId)
                .eq(WfNodeInstance::getStatus, NodeStatus.WAIT));
        if (update == 0){
            log.error("[finishCancel]: don't finish cancel nodeInstanceId={},expectStatus={}", nodeInstanceId,NodeStatus.WAIT);
            throw new InstanceStatusInvalidException(nodeInstanceId, NodeStatus.WAIT);
        }
    }

    public void cancelAllWaitByInstance(Long instanceId,Long operatorId) {
        nodeInstanceMapper.update(Wrappers.<WfNodeInstance>lambdaUpdate()
                .eq(WfNodeInstance::getInstanceId, instanceId)
                .eq(WfNodeInstance::getStatus, NodeStatus.WAIT)
                .set(WfNodeInstance::getStatus, NodeStatus.CANCELED)
                .set(WfNodeInstance::getFinishedTime, LocalDateTime.now())
                .set(WfNodeInstance::getOperatorId, operatorId));

    }
}
