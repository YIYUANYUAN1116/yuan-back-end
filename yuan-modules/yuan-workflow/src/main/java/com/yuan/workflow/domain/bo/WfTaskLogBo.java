package com.yuan.workflow.domain.bo;

import com.yuan.common.core.validate.AddGroup;
import com.yuan.common.core.validate.EditGroup;
import io.github.linpeilie.annotations.AutoMapper;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import com.yuan.workflow.domain.WfTaskLog;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * wftl业务对象 wf_task_log
 *
 
 * @date Sun Dec 28 11:26:44 CST 2025
 */
@Data

@AutoMapper(target = WfTaskLog.class, reverseConvertGenerate = false)
public class WfTaskLogBo implements Serializable {

    private Long id;

    /**
     * 任务ID
     */
    @NotNull(message = "任务ID不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long taskId;
    /**
     * 流程实例ID
     */
    @NotNull(message = "流程实例ID不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long instanceId;
    /**
     * 操作(APPROVE/REJECT/TRANSFER)
     */
    @NotBlank(message = "操作(APPROVE/REJECT/TRANSFER)不能为空", groups = { AddGroup.class, EditGroup.class })
    private String action;
    /**
     * 操作人
     */
    @NotNull(message = "操作人不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long operatorId;
    /**
     * 操作意见
     */
    private String comment;
    /**
     * operateTime
     */
    private LocalDateTime operateTime;

}
