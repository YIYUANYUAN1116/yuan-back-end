package com.yuan.workflow.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.yuan.common.tenant.core.TenantEntity;
import com.yuan.workflow.domain.enums.TaskAction;
import com.yuan.workflow.domain.enums.TaskStatus;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * wft对象 wf_task
 *
 
 * @date Sun Dec 28 11:26:41 CST 2025
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("wf_task")
public class WfTask extends TenantEntity {


    /**
     * 任务ID
     */
        @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 流程实例ID
     */
    private Long instanceId;

    /**
     * 节点实例ID
     */
    private Long nodeInstanceId;

    /**
     * 审批人ID
     */
    private Long assigneeId;

    /**
     * 状态(TODO/DONE/TRANSFERRED)
     * @see TaskStatus
     */
    private TaskStatus status;

    /**
     * 操作(APPROVE/REJECT/TRANSFER)
     * @see TaskAction
     */
    private TaskAction action;

    /**
     * 审批意见
     */
    private String comment;


    /**
     * 完成时间
     */
    private LocalDateTime finishTime;


}
