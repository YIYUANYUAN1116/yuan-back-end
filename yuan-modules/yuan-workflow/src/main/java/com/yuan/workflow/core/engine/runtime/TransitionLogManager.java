package com.yuan.workflow.core.engine.runtime;

import com.yuan.workflow.cmd.RecordTransitionCmd;
import com.yuan.workflow.cmd.WorkflowCmd;
import com.yuan.workflow.domain.WfInstance;
import com.yuan.workflow.domain.WfNodeInstance;
import com.yuan.workflow.enums.OperatorType;
import com.yuan.workflow.enums.TransitionAction;
import com.yuan.workflow.service.WfTransitionLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TransitionLogManager {
    private final WfTransitionLogService transitionLogService;

    private void transitionLog(WfInstance instance, WfNodeInstance fromNode,
                               WfNodeInstance toNode , WorkflowCmd cmd,
                               TransitionAction action, OperatorType operatorType) {
        transitionLogService.recordSuccess(RecordTransitionCmd.builder()
                .tenantId(instance.getTenantId())
                .defId(instance.getDefinitionId())
                .defVersion(instance.getDefinitionVersion())
                .instanceId(instance.getId())
                .nodeInstanceId(fromNode.getId())
                .fromNodeKey(fromNode.getNodeKey())
                .toNodeKey(toNode.getNodeKey())
                .action(action)
                .operatorType(operatorType)
                .operatorId(cmd.getOperatorId())
                .comment(cmd.getComment())
                .build());
    }
}
