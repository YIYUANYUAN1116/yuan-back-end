package com.yuan.workflow.domain.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import io.github.linpeilie.annotations.AutoMapper;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import com.yuan.workflow.domain.WfTask;

import java.io.Serializable;
import java.time.LocalDateTime;


/**
 * wft视图对象 wf_task
 *
 
 * @date Sun Dec 28 11:26:41 CST 2025
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = WfTask.class)
public class WfTaskVo implements Serializable {
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
    private Long id;
    /**
     * 租户ID
     */
    @ExcelProperty(value = "租户ID")
    private String tenantId;
    /**
     * 流程实例ID
     */
    @ExcelProperty(value = "流程实例ID")
    private Long instanceId;
    /**
     * 节点实例ID
     */
    @ExcelProperty(value = "节点实例ID")
    private Long nodeInstanceId;
    /**
     * 审批人ID
     */
    @ExcelProperty(value = "审批人ID")
    private Long assigneeId;
    @ExcelProperty(value = "操作人Id")
    private Long operatorId;
    @ExcelProperty(value = "转交来自")
    private Long transferFrom;
    @ExcelProperty(value = "转交日期")
    private LocalDateTime transferTime;
    /**
     * 状态(TODO/DONE/TRANSFERRED)
     */
    @ExcelProperty(value = "状态(TODO/DONE/TRANSFERRED)")
    private String status;
    /**
     * 操作(APPROVE/REJECT/TRANSFER)
     */
    @ExcelProperty(value = "操作(APPROVE/REJECT/TRANSFER)")
    private String action;
    /**
     * 审批意见
     */
    @ExcelProperty(value = "审批意见")
    private String comment;
    /**
     * createTime
     */
    @ExcelProperty(value = "createTime")
    private LocalDateTime createTime;
    /**
     * 完成时间
     */
    @ExcelProperty(value = "完成时间")
    private LocalDateTime finishTime;

}
