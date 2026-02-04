package com.yuan.workflow.core.engine.runtime;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.yuan.workflow.cmd.*;
import com.yuan.workflow.domain.WfInstance;
import com.yuan.workflow.domain.WfTask;
import com.yuan.workflow.domain.WfTaskLog;
import com.yuan.workflow.domain.enums.TaskAction;
import com.yuan.workflow.domain.enums.TaskStatus;
import com.yuan.workflow.domain.exception.TaskConcurrentChangedException;
import com.yuan.workflow.mapper.WfTaskLogMapper;
import com.yuan.workflow.mapper.WfTaskMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Slf4j
@Component
@RequiredArgsConstructor
public class TaskStateManager {
    private final WfTaskMapper taskMapper;
    private final WfTaskLogMapper taskLogMapper;


    public void anyApprove(WfTask task, ApproveCmd cmd) {
        update(task, TaskAction.ANY_APPROVE, cmd);
    }

    public void reject(WfTask task, RejectCmd cmd) {
        update(task, TaskAction.REJECT, cmd);
    }

    public void rollback(WfTask task, RollbackCmd cmd) {
        update(task, TaskAction.ROLLBACK, cmd);
    }

    public void transfer(WfTask task, TransferTaskCmd cmd) {
        Long operatorId = cmd.getOperatorId();
        String comment = cmd.getComment();
        int updated = taskMapper.update(Wrappers.<WfTask>lambdaUpdate()
                .eq(WfTask::getId, task.getId())
                .eq(WfTask::getStatus, TaskStatus.TODO)
                .set(WfTask::getAction, TaskAction.TRANSFER)
                .set(WfTask::getOperatorId, operatorId)
                .set(WfTask::getAssigneeId, cmd.getToUserId())
                .set(WfTask::getTransferFrom, operatorId)
                .set(WfTask::getTransferTime, LocalDateTime.now())
                .set(WfTask::getComment, comment));
        if (updated == 0) {
            log.error("[finish] update wfTask fail. taskId={},operatorId={},action={},expect={}", task.getId(), operatorId, TaskAction.TRANSFER.getCode(), TaskStatus.TODO.getCode());
            throw new TaskConcurrentChangedException(task.getId(), operatorId);
        }
        insertTaskLog(task, TaskAction.TRANSFER, operatorId, comment);
    }


    public void withDraw(WfInstance wfInstance, WithdrawCmd cmd) {
        cancelAllTodoTasks(wfInstance, TaskAction.TRANSFER, cmd.getOperatorId());
    }

    private void update(WfTask task, TaskAction action, WorkflowCmd cmd) {
        Long operatorId = cmd.getOperatorId();
        String comment = cmd.getComment();
        Long nodeInstanceId = task.getNodeInstanceId();
        int updated = taskMapper.update(Wrappers.<WfTask>lambdaUpdate()
                .eq(WfTask::getId, task.getId())
                .eq(WfTask::getStatus, TaskStatus.TODO)
                .set(WfTask::getStatus, TaskStatus.DONE)
                .set(WfTask::getAction, action)
                .set(WfTask::getFinishTime, LocalDateTime.now())
                .set(WfTask::getOperatorId, operatorId)
                .set(WfTask::getComment, comment));
        if (updated == 0) {
            log.error("[finish] update wfTask fail. taskId={},operatorId={},action={},expect={}", task.getId(), operatorId, action.getCode(), TaskStatus.TODO.getCode());
            throw new TaskConcurrentChangedException(task.getId(), operatorId);
        }
        cancelOtherTodoTasks(nodeInstanceId, task.getId(), action);
        insertTaskLog(task, action, operatorId, comment);
    }

    /**
     * 或签：审批一个人后，把同节点其他人的 TODO 任务取消
     */
    private void cancelOtherTodoTasks(Long nodeInstanceId, Long keepTaskId, TaskAction action) {
        taskMapper.updateOtherTodoTasks(nodeInstanceId,
                keepTaskId,
                action.getCode(),
                TaskStatus.TODO.getCode(),
                TaskStatus.CANCELED.getCode());
    }

    private void cancelAllTodoTasks(WfInstance instance, TaskAction action, Long operatorId) {
        Long instanceId = instance.getId();
        int updated = taskMapper.canceledAllTodoTasks(instanceId,
                action.getCode(),
                TaskStatus.TODO.getCode(),
                TaskStatus.CANCELED.getCode());
        if (updated == 0) {
            log.error("[cancelAllTodoTasks] update wfTask fail. instanceId={},action={}", instanceId, action.getCode());
            throw new TaskConcurrentChangedException();
        }
    }

    /**
     * 会签：判断该节点所有任务是否都 DONE
     */
    private boolean allDone(Long nodeInstanceId) {
        return taskMapper.countTodoByNode(nodeInstanceId, TaskStatus.TODO.getCode()) == 0;
    }


    public void insertTaskLog(WfTask wfTask, TaskAction action, Long operatorId, String comment) {
        WfTaskLog wfTaskLog = new WfTaskLog();
        wfTaskLog.setTaskId(wfTask.getId());
        wfTaskLog.setInstanceId(wfTask.getInstanceId());
        wfTaskLog.setNodeInstanceId(wfTask.getNodeInstanceId());
        wfTaskLog.setAction(action);
        wfTaskLog.setOperatorId(operatorId);
        wfTaskLog.setComment(comment);
        taskLogMapper.insert(wfTaskLog);
    }

    public void addSign(WfTask task, AddSignCmd cmd) {
        //todo 加签，加签需要做并签
    }

}
