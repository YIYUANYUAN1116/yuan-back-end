package com.yuan.workflow.core.engine.support;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.yuan.workflow.cmd.TransferTaskCmd;
import com.yuan.workflow.domain.WfTask;
import com.yuan.workflow.domain.WfTaskLog;
import com.yuan.workflow.domain.enums.TaskAction;
import com.yuan.workflow.domain.enums.TaskStatus;
import com.yuan.workflow.domain.exception.TaskAlreadyHandledException;
import com.yuan.workflow.mapper.WfTaskLogMapper;
import com.yuan.workflow.mapper.WfTaskMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Slf4j
@Component
@RequiredArgsConstructor
public class TaskLifecycle {
    private final WfTaskMapper taskMapper;
    private final WfTaskLogMapper taskLogMapper;

    public void finish(WfTask task, TaskAction action, String comment, Long operatorId) {
        int updated = taskMapper.update(Wrappers.<WfTask>lambdaUpdate()
                .eq(WfTask::getId, task.getId())
                .eq(WfTask::getStatus, TaskStatus.TODO)
                .set(WfTask::getStatus, TaskStatus.DONE)
                .set(WfTask::getAction, action)
                .set(WfTask::getFinishTime, LocalDateTime.now())
                .set(WfTask::getOperatorId, operatorId)
                .set(WfTask::getComment, comment));
        if (updated == 0) {
            log.error("[finish] update wfTask fail. taskId={},operatorId={},action={}", task.getId(), operatorId, action.getCode());
            throw new TaskAlreadyHandledException(task.getId(), operatorId);
        }
        cancelOtherTodoTasks(task.getNodeInstanceId(), task.getId(), action);
        insertTaskLog(task, action, operatorId, comment);
    }

    /**
     * 或签：审批一个人后，把同节点其他人的 TODO 任务取消
     */
    public void cancelOtherTodoTasks(Long nodeInstanceId, Long keepTaskId, TaskAction action) {
        int updated = taskMapper.updateOtherTodoTasks(nodeInstanceId,
                keepTaskId,
                action.getCode(),
                TaskStatus.TODO.getCode(),
                TaskStatus.CANCELED.getCode());
//        if (updated == 0) {
//            log.error("[cancelOtherTodoTasks] update wfTask fail. nodeInstanceId={},action={}", nodeInstanceId, action.getCode());
//            throw new TaskAlreadyHandledException();
//        }
    }

    public void cancelAllTodoTasks(Long nodeInstanceId, TaskAction action) {
        int updated = taskMapper.updateAllTodoTasks(nodeInstanceId,
                action.getCode(),
                TaskStatus.TODO.getCode(),
                TaskStatus.CANCELED.getCode());
        if (updated == 0) {
            log.error("[cancelAllTodoTasks] update wfTask fail. nodeInstanceId={},action={}", nodeInstanceId, action.getCode());
            throw new TaskAlreadyHandledException();
        }
    }

    /**
     * 会签：判断该节点所有任务是否都 DONE
     */
    public boolean allDone(Long nodeInstanceId) {
        return taskMapper.countTodoByNode(nodeInstanceId, TaskStatus.TODO.getCode()) == 0;
    }

    /**
     * 转交
     *
     * @param task
     */
    public void transfer(WfTask task, TransferTaskCmd cmd) {
        int update = taskMapper.update(Wrappers.<WfTask>lambdaUpdate()
                .eq(WfTask::getId, task.getId())
                .eq(WfTask::getStatus, TaskStatus.TODO.getCode())
                .set(WfTask::getAssigneeId, cmd.getToUserId())
                .set(WfTask::getTransferFrom, cmd.getOperatorId())
                .set(WfTask::getTransferTime, LocalDateTime.now())
                .set(WfTask::getComment, cmd.getComment()));
        if (update == 0) {
            log.error("[transfer] update wfTask fail. taskId={}, toUserId={}, operatorId={}", task.getId(),  cmd.getToUserId(), cmd.getOperatorId());
            throw new TaskAlreadyHandledException(task.getId(), cmd.getOperatorId());
        }
        insertTaskLog(task, TaskAction.TRANSFER,  cmd.getOperatorId(), cmd.getComment());

    }

    public void insertTaskLog(WfTask wfTask, TaskAction action, Long operatorId, String comment) {
        WfTaskLog wfTaskLog = new WfTaskLog();
        wfTaskLog.setTaskId(wfTask.getId());
        wfTaskLog.setInstanceId(wfTask.getInstanceId());
        wfTaskLog.setAction(action);
        wfTaskLog.setOperatorId(operatorId);
        wfTaskLog.setComment(comment);
        taskLogMapper.insert(wfTaskLog);
    }

}
