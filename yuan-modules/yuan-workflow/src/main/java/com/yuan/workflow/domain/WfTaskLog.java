package com.yuan.workflow.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.yuan.common.tenant.core.TenantEntity;
import com.yuan.workflow.domain.enums.TaskAction;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * wftl对象 wf_task_log
 *
 
 * @date Sun Dec 28 11:26:44 CST 2025
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("wf_task_log")
public class WfTaskLog extends TenantEntity {


    /**
     * 日志ID
     */
        @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 任务ID
     */
    private Long taskId;

    /**
     * 流程实例ID
     */
    private Long instanceId;

    /**
     * 操作(APPROVE/REJECT/TRANSFER)
     */
    private TaskAction action;

    /**
     * 操作人
     */
    private Long operatorId;

    /**
     * 操作意见
     */
    private String comment;

    /**
     * operateTime
     */
    private LocalDateTime operateTime;

    @TableLogic
    private String delFlag;
}
