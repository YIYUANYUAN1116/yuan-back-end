package com.yuan.workflow.core.engine.handler;

import com.yuan.workflow.api.cmd.ApproveTaskCmd;
import com.yuan.workflow.api.enums.NodeStatus;
import com.yuan.workflow.api.enums.TaskAction;
import com.yuan.workflow.api.enums.TaskStatus;
import com.yuan.workflow.core.engine.support.FlowAdvanceService;
import com.yuan.workflow.core.engine.support.TaskLifecycle;
import com.yuan.workflow.core.engine.support.VariableService;
import com.yuan.workflow.core.engine.support.WfContextLoader;
import com.yuan.workflow.domain.WfInstance;
import com.yuan.workflow.domain.WfNodeInstance;
import com.yuan.workflow.domain.WfTask;
import com.yuan.workflow.mapper.WfNodeInstanceMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.util.Objects;

@Component
@RequiredArgsConstructor
public class ApproveTaskHandler implements CommandHandler<ApproveTaskCmd,Void>{
    private final WfContextLoader contextLoader;
    private final VariableService variableService;
    private final TaskLifecycle taskLifecycle;
    private final WfNodeInstanceMapper nodeInstanceMapper;
    private final FlowAdvanceService flowAdvanceService;

    @Override
    public Void handle(ApproveTaskCmd cmd) {
        Long operatorId = cmd.getOperatorUserId();

        // 1) load 上下文（task/node/instance/def/bizRef）
        WfContextLoader.TaskCtx ctx = contextLoader.loadTaskCtx(cmd.getTaskId());
        WfTask task = ctx.task();
        WfNodeInstance node = ctx.node();
        WfInstance instance = ctx.instance();

        // 2) 参数 + 权限校验
        assertCanOperate(task, operatorId);

        // 3) 合并变量
        variableService.mergeAndSave(instance, cmd.getVariables());

        // 4) 完成任务 + 日志
        taskLifecycle.finish(task, TaskAction.APPROVE, cmd.getComment(), operatorId);

        // 5) 或签：取消同节点其他待处理任务
        taskLifecycle.cancelOtherTodoTasks(node.getId(), task.getId());

        // 6) 完成节点
        node.setStatus(NodeStatus.DONE.getCode());
        nodeInstanceMapper.updateById(node);

        // 7) 推进
        flowAdvanceService.advance(node);

        return null;
    }

    private void assertCanOperate(WfTask task, Long operatorId) {
        Assert.notNull(task, "任务不存在");
        Assert.isTrue(Objects.equals(task.getAssigneeId(), operatorId), "无权操作该任务");
        Assert.isTrue(Objects.equals(task.getStatus(), TaskStatus.TODO.getCode()), "任务已处理");
    }
}
