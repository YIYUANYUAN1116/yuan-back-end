package com.yuan.workflow.core.engine.support;

import com.yuan.workflow.api.enums.TaskAction;
import com.yuan.workflow.api.enums.TaskStatus;
import com.yuan.workflow.core.resolver.AssigneeResolver;
import com.yuan.workflow.domain.WfTask;
import com.yuan.workflow.domain.WfTaskLog;
import com.yuan.workflow.mapper.WfTaskLogMapper;
import com.yuan.workflow.mapper.WfTaskMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class TaskLifecycle {
  private final WfTaskMapper taskMapper;
  private final WfTaskLogMapper taskLogMapper;
  private final AssigneeResolver assigneeResolver;

  public void finish(WfTask task, TaskAction action, String comment, Long operatorId) {
    task.setStatus(TaskStatus.DONE.getCode());
    task.setAction(action.getCode());
    task.setComment(comment);
    task.setFinishTime(LocalDateTime.now());
    taskMapper.updateById(task);

    WfTaskLog log = new WfTaskLog();
    log.setTaskId(task.getId());
    log.setInstanceId(task.getInstanceId());
    log.setAction(action.getCode());
    log.setOperatorId(operatorId);
    log.setComment(comment);
    taskLogMapper.insert(log);
  }

  /** 或签：审批一个人后，把同节点其他人的 TODO 任务取消 */
  public void cancelOtherTodoTasks(Long nodeInstanceId, Long keepTaskId) {
    taskMapper.cancelOtherTodoTasks(nodeInstanceId, keepTaskId, TaskStatus.CANCELED.getCode());
  }

  /** 会签：判断该节点所有任务是否都 DONE */
  public boolean allDone(Long nodeInstanceId) {
    return taskMapper.countTodoByNode(nodeInstanceId, TaskStatus.TODO.getCode()) == 0;
  }
}
