package com.yuan.workflow.core.engine.support;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.yuan.workflow.domain.WfNodeInstance;
import com.yuan.workflow.domain.enums.NodeStatus;
import com.yuan.workflow.mapper.WfNodeInstanceMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class NodeInstanceLifeCycle {
    private final WfNodeInstanceMapper nodeInstanceMapper;

    public void finishDone(WfNodeInstance node) {
        node.setStatus(NodeStatus.DONE);
        nodeInstanceMapper.updateById(node);
    }

    public void finishCancel(WfNodeInstance node) {
        node.setStatus(NodeStatus.CANCELED);
        nodeInstanceMapper.updateById(node);
    }

    public void cancelAllWaitByInstance(Long instanceId) {
        nodeInstanceMapper.update(Wrappers.<WfNodeInstance>lambdaUpdate()
                .eq(WfNodeInstance::getInstanceId, instanceId)
                .eq(WfNodeInstance::getStatus, NodeStatus.WAIT)
                .set(WfNodeInstance::getStatus, NodeStatus.CANCELED));

    }
}
