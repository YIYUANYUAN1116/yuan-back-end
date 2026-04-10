package com.yuan.workflow.core.engine.runtime;

import com.yuan.workflow.cmd.RecordTransitionCmd;
import com.yuan.workflow.cmd.WorkflowCmd;
import com.yuan.workflow.domain.WfInstance;
import com.yuan.workflow.domain.WfNodeInstance;
import com.yuan.workflow.enums.OperatorType;
import com.yuan.workflow.enums.TransitionAction;
import com.yuan.workflow.model.logicflow.LfNode;
import com.yuan.workflow.service.WfTransitionLogService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Map;

@RequiredArgsConstructor
@Component
@Slf4j
public class TransitionLogManager {

    private final WfTransitionLogService transitionLogService;

    public void transitionLog(WfInstance instance,
                               WfNodeInstance from,
                               LfNode to,
                               TransitionAction action,
                               OperatorType operatorType,
                               WorkflowCmd cmd,
                               String conditionExpr,
                               Map<String, Object> varSnapshot) {
        transitionLogService.recordSuccess(RecordTransitionCmd.builder()
                .tenantId(instance.getTenantId())
                .defId(instance.getDefinitionId())
                .defVersion(instance.getDefinitionVersion())
                .instanceId(instance.getId())
                .nodeInstanceId(from != null ? from.getId() : null)
                .fromNodeKey(from != null ? from.getNodeKey() : null)
                .toNodeKey(to != null ? to.getId() : null)
                .action(action)
                .operatorType(operatorType)
                .operatorId(operatorType.equals(OperatorType.USER) ? cmd.getOperatorId() : null)
                .conditionExpr(conditionExpr)
                .variablesSnapshot(varSnapshot)
                .comment(cmd.getComment())
                .build());
    }
}
