package com.yuan.workflow.domain.bo;

import com.yuan.common.core.validate.AddGroup;
import com.yuan.common.core.validate.EditGroup;
import io.github.linpeilie.annotations.AutoMapper;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import com.yuan.workflow.domain.WfTask;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * wft业务对象 wf_task
 *

 * @date Sun Dec 28 11:26:41 CST 2025
 */
@Data

@AutoMapper(target = WfTask.class, reverseConvertGenerate = false)
public class WfTaskBo implements Serializable {

    private Long id;

    /**
     * 租户ID
     */
    @NotNull(message = "租户ID不能为空", groups = { AddGroup.class, EditGroup.class })
    private String tenantId;
    /**
     * 流程实例ID
     */
    @NotNull(message = "流程实例ID不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long instanceId;
    /**
     * 节点实例ID
     */
    @NotNull(message = "节点实例ID不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long nodeInstanceId;
    /**
     * 审批人ID
     */
    @NotNull(message = "审批人ID不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long assigneeId;
    /**
     * 状态(TODO/DONE/TRANSFERRED)
     */
    @NotBlank(message = "状态(TODO/DONE/TRANSFERRED)不能为空", groups = { AddGroup.class, EditGroup.class })
    private String status;
    /**
     * 操作(APPROVE/REJECT/TRANSFER)
     */
    private String action;
    /**
     * 审批意见
     */
    private String comment;
    /**
     * createTime
     */
    private LocalDateTime createTime;
    /**
     * 完成时间
     */
    private LocalDateTime finishTime;

}
