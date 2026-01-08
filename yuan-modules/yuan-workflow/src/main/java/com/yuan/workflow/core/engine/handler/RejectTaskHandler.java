package com.yuan.workflow.core.engine.handler;

import com.yuan.workflow.api.cmd.RejectTaskCmd;
import com.yuan.workflow.api.enums.TaskAction;
import com.yuan.workflow.api.enums.TaskStatus;
import com.yuan.workflow.api.event.WfEventContext;
import com.yuan.workflow.api.event.WfEventFactory;
import com.yuan.workflow.core.engine.support.InstanceLifecycle;
import com.yuan.workflow.core.engine.support.TaskLifecycle;
import com.yuan.workflow.core.engine.support.VariableService;
import com.yuan.workflow.core.engine.support.WfContextLoader;
import com.yuan.workflow.core.event.SpringWfEventPublisher;
import com.yuan.workflow.domain.WfBizRef;
import com.yuan.workflow.domain.WfInstance;
import com.yuan.workflow.domain.WfNodeInstance;
import com.yuan.workflow.domain.WfTask;
import com.yuan.workflow.mapper.WfBizRefMapper;
import com.yuan.workflow.mapper.WfInstanceMapper;
import com.yuan.workflow.mapper.WfNodeInstanceMapper;
import com.yuan.workflow.mapper.WfTaskMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.Map;
import java.util.Objects;

@Component
@RequiredArgsConstructor
public class RejectTaskHandler implements CommandHandler<RejectTaskCmd,Void>{
    private final WfContextLoader contextLoader;
    private final VariableService variableService;
    private final TaskLifecycle taskLifecycle;
    private final WfNodeInstanceMapper nodeInstanceMapper;
    private final SpringWfEventPublisher eventPublisher;
    private final WfTaskMapper taskMapper;
    private final WfBizRefMapper bizRefMapper;
    private final WfInstanceMapper instanceMapper;
    private final InstanceLifecycle instanceLifecycle;

    @Override
    @Transactional
    public Void handle(RejectTaskCmd cmd) {
        Long operatorId = cmd.getOperatorUserId();

        // 1) load 上下文
        WfContextLoader.TaskCtx ctx = contextLoader.loadTaskCtx(cmd.getTaskId());
        WfTask task = ctx.task();
        WfNodeInstance node = ctx.node();
        WfInstance instance = ctx.instance();
        WfBizRef wfBizRef = ctx.bizRef();

        // 2) 校验
        assertCanOperate(task, operatorId);

        // 3) 合并变量（驳回也可能写变量）
        variableService.mergeAndSave(instance, cmd.getVariables());

        // 4) 完成任务
        taskLifecycle.finish(task, TaskAction.REJECT, cmd.getComment(), operatorId);

        // 5) 发布“任务驳回”事件 afterCommit
        WfEventContext wfEventContext = buildEventContextByTask(instance,wfBizRef,task);
        eventPublisher.publishAfterCommit(WfEventFactory.buildTaskRejected(wfEventContext, cmd.getComment(), Map.of()));

        // 6) 业务规则：驳回直接结束
        boolean instanceEnded = isRejectEnd(task);
        if (!instanceEnded) {
            return null;
        }

        // 7) 结束实例
        instanceLifecycle.finishRejected(instance.getId(),operatorId);
        return null;
    }

    private boolean isRejectEnd(WfTask task) {
        return true;
    }

    private void assertCanOperate(WfTask task, Long operatorId) {
        Assert.notNull(task, "任务不存在");
        Assert.isTrue(Objects.equals(task.getAssigneeId(), operatorId), "无权操作该任务");
        Assert.isTrue(Objects.equals(task.getStatus(), TaskStatus.TODO.getCode()), "任务已处理");
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
                .starterUserId(instance.getStartUserId())
                .operatorUserId(task.getAssigneeId())
                .build();
    }
}
