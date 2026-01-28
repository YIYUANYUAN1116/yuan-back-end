package com.yuan.workflow.domain.guard;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.yuan.common.core.exception.workflow.WorkflowErrorCode;
import com.yuan.common.core.exception.workflow.WorkflowException;
import com.yuan.workflow.cmd.StartCmd;
import com.yuan.workflow.domain.WfBizRef;
import com.yuan.workflow.domain.WfDefinition;
import com.yuan.workflow.domain.WfInstance;
import com.yuan.workflow.domain.WfTask;
import com.yuan.workflow.domain.enums.TaskStatus;
import com.yuan.workflow.domain.exception.InstanceNotFoundException;
import com.yuan.workflow.domain.exception.InstancePermissionDeniedException;
import com.yuan.workflow.domain.exception.TaskAlreadyHandledException;
import com.yuan.workflow.domain.exception.TaskNotFoundException;
import com.yuan.workflow.domain.exception.TaskPermissionDeniedException;
import com.yuan.workflow.mapper.WfBizRefMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
@Slf4j
@RequiredArgsConstructor
public class WfOperationGuard {

    private final WfBizRefMapper wfBizRefMapper;

    public void assertTodoTask(WfTask task, Long operatorId) {
        if (Objects.isNull(task)) {
            log.error("[assertTodoTask]: task is null");
            throw new TaskNotFoundException();
        }

        if (!Objects.equals(task.getStatus(), TaskStatus.TODO)) {
            log.error("[assertTodoTask]: The task status is not todo. taskId={},taskStatus={}", task.getId(),task.getStatus());
            throw new TaskAlreadyHandledException(task.getId(), operatorId);
        }
    }

    public void assertAssignee(WfTask task, Long operatorId) {
        if (!Objects.equals(task.getAssigneeId(), operatorId)) {
            throw new TaskPermissionDeniedException(task.getId(), operatorId);
        }
    }

    public void assertCanOperate(WfTask task, Long operatorId) {
        assertTodoTask(task, operatorId);
        assertAssignee(task, operatorId);
    }


    public void assertStartInstance(WfDefinition definition, StartCmd cmd) {
        if (definition == null) {
            log.error("[assertStartInstance]: definition is null");
            throw new WorkflowException(WorkflowErrorCode.WF_DEFINITION_NOT_FOUND);
        }

        boolean exists = wfBizRefMapper.exists(Wrappers.<WfBizRef>lambdaQuery()
                .eq(WfBizRef::getBizNo, cmd.getBizNo()));
        if (exists){
            log.error("[assertStartInstance]: bizNo already exists,bizNo={}", cmd.getBizNo());
            throw new WorkflowException(WorkflowErrorCode.WF_BIZ_ALREADY_EXISTS);
        }
    }

    public void assertWithDraw(WfInstance instance, Long operatorId) {
        if (instance == null) {
            log.error("[assertWithDraw]:Instance is null");
            throw new InstanceNotFoundException();
        }
        if (!Objects.equals(operatorId, instance.getStarterId())) {
            throw new InstancePermissionDeniedException(instance.getId(),operatorId);
        }
    }
}
