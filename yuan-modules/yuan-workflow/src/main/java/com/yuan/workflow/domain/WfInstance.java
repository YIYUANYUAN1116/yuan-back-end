package com.yuan.workflow.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.yuan.common.tenant.core.TenantEntity;
import com.yuan.workflow.domain.enums.InstanceStatus;
import com.yuan.workflow.domain.enums.WfEndReason;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * wfi对象 wf_instance
 *
 
 * @date Sun Dec 28 11:26:34 CST 2025
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("wf_instance")
public class WfInstance extends TenantEntity {


    /**
     * 流程实例ID
     */
        @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 租户ID
     */
    private String tenantId;

    /**
     * 流程定义ID
     */
    private Long definitionId;

    /**
     * 流程业务标识
     */
    private String definitionKey;

    private String definitionName;

    /**
     * 流程版本
     */
    private Integer definitionVersion;


    /**
     * 状态(RUNNING/APPROVED/REJECTED/CANCELED)
     * @see InstanceStatus
     */
    private InstanceStatus status;

    /**
     * 发起人
     */
    private Long startId;

    private String startName;

    private Long startDeptId;

    private String startDeptName;

    private Long lastOperatorId;

    private String lastOperatorName;

    /**
     * startTime
     */
    private LocalDateTime startTime;

    /**
     * 结束时间
     */
    private LocalDateTime endTime;

    private String variables;

    private WfEndReason endReason;
    private String endComment;
    private Long endBy;

}
