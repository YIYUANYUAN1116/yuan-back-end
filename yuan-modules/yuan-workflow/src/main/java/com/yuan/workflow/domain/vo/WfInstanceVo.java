package com.yuan.workflow.domain.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.yuan.workflow.domain.enums.InstanceStatus;
import com.yuan.workflow.enums.WfEndReason;
import io.github.linpeilie.annotations.AutoMapper;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import com.yuan.workflow.domain.WfInstance;

import java.io.Serializable;
import java.time.LocalDateTime;


/**
 * wfi视图对象 wf_instance
 *
 
 * @date Sun Dec 28 11:26:34 CST 2025
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = WfInstance.class)
public class WfInstanceVo implements Serializable {
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
    private Long id;
    /**
     * 租户ID
     */
    @ExcelProperty(value = "租户ID")
    private String tenantId;
    /**
     * 流程定义ID
     */
    @ExcelProperty(value = "流程定义ID")
    private Long definitionId;
    /**
     * 流程业务标识
     */
    @ExcelProperty(value = "流程业务标识")
    private String definitionKey;

    /**
     * 流程版本
     */
    @ExcelProperty(value = "流程版本")
    private Integer definitionVersion;

    /**
     * 状态(RUNNING/APPROVED/REJECTED/CANCELED)
     */
    @ExcelProperty(value = "状态(RUNNING/APPROVED/REJECTED/CANCELED)")
    private InstanceStatus status;
    /**
     * 发起人
     */
    @ExcelProperty(value = "发起人Id")
    private Long starterId;
    @ExcelProperty(value = "发起人")
    private String starterName;

    @ExcelProperty(value = "发起人部门Id")
    private Long starterDeptId;
    @ExcelProperty(value = "发起人部门")
    private String starterDeptName;
    @ExcelProperty(value = "操作人Id")
    private Long lastOperatorId;
    @ExcelProperty(value = "操作人")
    private String lastOperatorName;
    /**
     * startTime
     */
    @ExcelProperty(value = "startTime")
    private LocalDateTime startTime;
    /**
     * 结束时间
     */
    @ExcelProperty(value = "结束时间")
    private LocalDateTime endTime;
    private WfEndReason endReason;
    private String endComment;
    private Long endBy;

    /*******************非数据库字段********************/
    private String bizType;
    private String definitionName;
    private String bizNo;
    private String variables;

}
