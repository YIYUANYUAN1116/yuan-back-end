package com.yuan.workflow.domain.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import io.github.linpeilie.annotations.AutoMapper;
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
    private Integer version;
    /**
     * 业务单号(请假单ID等)
     */
    @ExcelProperty(value = "业务单号(请假单ID等)")
    private String businessKey;
    /**
     * 状态(RUNNING/APPROVED/REJECTED/CANCELED)
     */
    @ExcelProperty(value = "状态(RUNNING/APPROVED/REJECTED/CANCELED)")
    private String status;
    /**
     * 发起人
     */
    @ExcelProperty(value = "发起人")
    private Long startUserId;
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

    private String variables;

}
