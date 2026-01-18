package com.yuan.workflow.core.engine.handler;

import com.yuan.workflow.cmd.RejectTaskCmd;
import com.yuan.workflow.core.engine.support.*;
import com.yuan.workflow.core.event.SpringWfEventPublisher;
import com.yuan.workflow.core.event.WfEventContext;
import com.yuan.workflow.core.event.WfEventFactory;
import com.yuan.workflow.domain.WfBizRef;
import com.yuan.workflow.domain.WfInstance;
import com.yuan.workflow.domain.WfNodeInstance;
import com.yuan.workflow.domain.WfTask;
import com.yuan.workflow.domain.enums.TaskAction;
import com.yuan.workflow.domain.guard.WfOperationGuard;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

/**
 * 审批拒绝处理器
 */
@Component
@RequiredArgsConstructor
public class RejectTaskHandler implements CommandHandler<RejectTaskCmd,Void>{
    private final WfContextLoader contextLoader;
    private final VariableService variableService;
    private final TaskLifecycle taskLifecycle;
    private final SpringWfEventPublisher eventPublisher;
    private final InstanceLifecycle instanceLifecycle;
    private final WfOperationGuard wfOperationGuard;
    private final NodeInstanceLifeCycle nodeLifeCycle;

    @Override
    @Transactional
    public Void handle(RejectTaskCmd cmd) {
        Long operatorId = cmd.getOperatorId();

        //  load 上下文
        WfContextLoader.TaskCtx ctx = contextLoader.loadTaskCtx(cmd.getTaskId());
        WfTask task = ctx.task();
        WfInstance instance = ctx.instance();
        WfBizRef wfBizRef = ctx.bizRef();
        WfNodeInstance node = ctx.node();

        // 校验
        wfOperationGuard.assertCanOperate(task, operatorId);

        // 合并变量（驳回也可能写变量）
        variableService.mergeAndSave(instance, cmd.getVariables());

        // 完成任务
        taskLifecycle.finish(task, TaskAction.REJECT, cmd.getComment(), operatorId);

        // 完成节点
        nodeLifeCycle.finishCancel(node.getId());


        // 发布“任务驳回”事件 afterCommit
        WfEventContext wfEventContext = buildEventContextByTask(instance,wfBizRef,task);
        eventPublisher.publishAfterCommit(WfEventFactory.buildTaskRejected(wfEventContext, cmd.getComment(), Map.of()));

        // 业务规则：驳回直接结束
        boolean instanceEnded = isRejectEnd(task);
        if (!instanceEnded) {
            return null;
        }

        // 结束实例
        instanceLifecycle.finishRejected(instance,cmd);
        return null;
    }

    private boolean isRejectEnd(WfTask task) {
        return true;
    }

    private WfEventContext buildEventContextByTask(WfInstance instance,WfBizRef bizRef,WfTask task) {
        return WfEventContext.builder()
                .tenantId(instance.getTenantId())
                .bizId(bizRef != null ? bizRef.getBizId() : null)
                .bizType(bizRef != null ? bizRef.getBizType() : null)
                .definitionId(instance.getDefinitionId())
                .instanceId(instance.getId())
                .taskId(task.getId())
                .nodeId(task.getNodeInstanceId())
                .starterId(instance.getStarterId())
                .operatorId(task.getAssigneeId())
                .build();
    }
}
