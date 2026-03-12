package com.yuan.workflow.core.engine.runtime;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.yuan.workflow.cmd.ApproveCmd;
import com.yuan.workflow.cmd.RejectCmd;
import com.yuan.workflow.cmd.RollbackCmd;
import com.yuan.workflow.cmd.WithdrawCmd;
import com.yuan.workflow.cmd.WorkflowCmd;
import com.yuan.workflow.core.exception.NodeInstanceConcurrentChangedException;
import com.yuan.workflow.domain.WfInstance;
import com.yuan.workflow.domain.WfNodeInstance;
import com.yuan.workflow.domain.enums.NodeStatus;
import com.yuan.workflow.domain.enums.NodeType;
import com.yuan.workflow.mapper.WfNodeInstanceMapper;
import com.yuan.workflow.model.logicflow.LfNode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
@Slf4j
public class NodeInstanceStateManager {
    private final WfNodeInstanceMapper nodeInstanceMapper;
    private final InstanceStateManager instanceStateManager;

    public void approve(WfNodeInstance node, ApproveCmd cmd){
        update(node,cmd);
    }

    public void reject(WfNodeInstance node, RejectCmd cmd){
        update(node,cmd);
    }

    public void rollback(WfNodeInstance node, RollbackCmd cmd){
        update(node,cmd);
    }

    public void sysAutoApprove(WfNodeInstance node, WorkflowCmd cmd){
        cmd.setOperatorId(0L);
        cmd.setOperatorName("系统处理");
        update(node,cmd);
    }

    public void aiCompleteAuto(WfNodeInstance node, WorkflowCmd cmd){
        cmd.setOperatorId(0L);
        cmd.setOperatorName("AI处理");
        cmd.setComment("AI 处理成功");
        update(node,cmd);
    }

    public void aiFailAuto(WfNodeInstance node, WorkflowCmd cmd){
        cmd.setOperatorId(0L);
        cmd.setOperatorName("AI处理");
        cmd.setComment("AI 处理失败");
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



    public WfNodeInstance createNodeInstance(Long instanceId, LfNode lfNode, NodeStatus nodeStatus) {
        WfNodeInstance ni = new WfNodeInstance();
        ni.setInstanceId(instanceId);
        ni.setNodeKey(lfNode.getId());
        ni.setNodeType(NodeType.of(lfNode.getProperties().getWfType()));
        ni.setNodeName(lfNode.getText().getValue());
        ni.setStatus(nodeStatus);
        ni.setOrderNo(instanceStateManager.nextNodeOrderNo(instanceId));
        nodeInstanceMapper.insert(ni);
        return ni;
    }

}
