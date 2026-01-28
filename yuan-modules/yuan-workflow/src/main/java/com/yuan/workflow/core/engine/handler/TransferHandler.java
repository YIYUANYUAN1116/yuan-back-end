package com.yuan.workflow.core.engine.handler;

import com.yuan.workflow.cmd.RecordTransitionCmd;
import com.yuan.workflow.cmd.TransferTaskCmd;
import com.yuan.workflow.core.engine.runtime.TaskStateManager;
import com.yuan.workflow.core.engine.support.WfContextLoader;
import com.yuan.workflow.domain.WfInstance;
import com.yuan.workflow.domain.WfNodeInstance;
import com.yuan.workflow.domain.WfTask;
import com.yuan.workflow.domain.guard.WfOperationGuard;
import com.yuan.workflow.enums.OperatorType;
import com.yuan.workflow.enums.TransitionAction;
import com.yuan.workflow.service.WfTransitionLogService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Component
@RequiredArgsConstructor
public class TransferHandler implements  CommandHandler<TransferTaskCmd,Void>{
    private final WfContextLoader contextLoader;
    private final TaskStateManager taskStateManager;
    private final WfOperationGuard wfOperationGuard;
    private final WfTransitionLogService transitionLogService;


    @Override
    @Transactional
    public Void handle(TransferTaskCmd cmd) {
        Long operatorId = cmd.getOperatorId();

        WfContextLoader.TaskCtx ctx = contextLoader.loadTaskCtx(cmd.getTaskId());
        WfTask task = ctx.task();

        // 1) 校验
        wfOperationGuard.assertCanOperate(task, operatorId);

        // 2) 执行转交：task 只是换 assignee
        taskStateManager.transfer(
                task,
                cmd
        );

        transitionLog(ctx.instance(),ctx.node(),cmd);

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
