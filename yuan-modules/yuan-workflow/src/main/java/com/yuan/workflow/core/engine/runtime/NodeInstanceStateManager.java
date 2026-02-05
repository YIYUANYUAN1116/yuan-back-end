package com.yuan.workflow.core.engine.runtime;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.yuan.workflow.cmd.*;
import com.yuan.workflow.core.exception.NodeInstanceConcurrentChangedException;
import com.yuan.workflow.domain.WfInstance;
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

    public void approve(WfNodeInstance node, ApproveCmd cmd){
        update(node,cmd);
    }

    public void reject(WfNodeInstance node, RejectCmd cmd){
        update(node,cmd);
    }

    public void rollback(WfNodeInstance node, RollbackCmd cmd){
        update(node,cmd);
    }

    public void autoApprove(WfNodeInstance node, WorkflowCmd cmd){
        cmd.setOperatorId(0L);
        cmd.setOperatorName("系统处理");
        update(node,cmd);
    }

    public void withDraw(WfInstance wfInstance, WithdrawCmd cmd) {
        Long instanceId = wfInstance.getId();
        Long operatorId = cmd.getOperatorId();
        int update = nodeInstanceMapper.update(Wrappers.<WfNodeInstance>lambdaUpdate()
                .eq(WfNodeInstance::getInstanceId, instanceId)
                .eq(WfNodeInstance::getStatus, NodeStatus.WAIT)
                .setSql("version = version + 1")
                .set(WfNodeInstance::getStatus, NodeStatus.CANCELED)
                .set(WfNodeInstance::getFinishedTime, LocalDateTime.now())
                .set(WfNodeInstance::getOperatorId, operatorId));
        if (update == 0){
            log.error("[finishCancel]: don't finish cancel instanceId={},expectStatus={}", instanceId,NodeStatus.WAIT);
            throw new NodeInstanceConcurrentChangedException(NodeStatus.WAIT);
        }
    }

    private void update(WfNodeInstance node, WorkflowCmd cmd) {
        Long operatorId = cmd.getOperatorId();
        Long id = node.getId();
        int row = nodeInstanceMapper.update(Wrappers.<WfNodeInstance>lambdaUpdate()
                .set(WfNodeInstance::getStatus, NodeStatus.DONE)
                .set(WfNodeInstance::getOperatorId, operatorId)
                .set(WfNodeInstance::getFinishedTime, LocalDateTime.now())
                .setSql("version = version + 1")
                .eq(WfNodeInstance::getVersion, node.getVersion())
                .eq(WfNodeInstance::getId, id)
                .eq(WfNodeInstance::getStatus, NodeStatus.WAIT));
        if (row == 0){
            log.error("[NodeInstanceStateManager][update]: don't finish done nodeInstanceId={},expectStatus={}", id, NodeStatus.WAIT);
            throw new NodeInstanceConcurrentChangedException(id, NodeStatus.WAIT);
        }
    }
}
