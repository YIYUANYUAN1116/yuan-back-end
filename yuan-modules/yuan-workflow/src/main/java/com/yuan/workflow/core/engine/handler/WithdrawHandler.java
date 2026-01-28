package com.yuan.workflow.core.engine.handler;


import com.yuan.workflow.cmd.RecordTransitionCmd;
import com.yuan.workflow.cmd.TransferTaskCmd;
import com.yuan.workflow.cmd.WithdrawCmd;
import com.yuan.workflow.core.engine.runtime.InstanceStateManager;
import com.yuan.workflow.domain.WfInstance;
import com.yuan.workflow.domain.WfNodeInstance;
import com.yuan.workflow.domain.guard.WfOperationGuard;
import com.yuan.workflow.enums.OperatorType;
import com.yuan.workflow.enums.TransitionAction;
import com.yuan.workflow.mapper.WfInstanceMapper;
import com.yuan.workflow.service.WfTransitionLogService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * 撤回申请
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class WithdrawHandler implements  CommandHandler<WithdrawCmd,Void>{
    private final WfInstanceMapper wfInstanceMapper;
    private final WfOperationGuard wfOperationGuard;
    private final InstanceStateManager instanceStateManager;
    private final WfTransitionLogService transitionLogService;

    @Override
    @Transactional
    public Void handle(WithdrawCmd cmd) {
        Long instanceId = cmd.getInstanceId();
        WfInstance wfInstance = wfInstanceMapper.selectById(instanceId);
        //校验
        wfOperationGuard.assertWithDraw(wfInstance,cmd.getOperatorId());

        instanceStateManager.finishWithDraw(wfInstance,cmd);


        return null;
    }

    private void transitionLog(WfInstance instance, WfNodeInstance node, TransferTaskCmd cmd) {
        transitionLogService.recordSuccess(RecordTransitionCmd.builder()
                .tenantId(instance.getTenantId())
                .defId(instance.getDefinitionId())
                .defVersion(instance.getDefinitionVersion())
                .instanceId(instance.getId())
                .nodeInstanceId(node.getId())
                .fromNodeKey(null)                 // START 可空
                .toNodeKey(node.getNodeKey())
                .action(TransitionAction.TRANSFER)
                .operatorType(OperatorType.USER)
                .operatorId(cmd.getOperatorId())
                .comment(cmd.getComment())
                .build());
    }
}
