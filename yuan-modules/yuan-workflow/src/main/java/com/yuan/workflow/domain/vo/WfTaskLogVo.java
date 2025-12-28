package com.yuan.workflow.domain.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import com.yuan.workflow.domain.WfTaskLog;

import java.io.Serializable;
import java.time.LocalDateTime;


/**
 * wftl视图对象 wf_task_log
 *

 * @date Sun Dec 28 11:26:44 CST 2025
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = WfTaskLog.class)
public class WfTaskLogVo implements Serializable {

    private Long id;
    /**
     * 任务ID
     */
    @ExcelProperty(value = "任务ID")
    private Long taskId;
    /**
     * 流程实例ID
     */
    @ExcelProperty(value = "流程实例ID")
    private Long instanceId;
    /**
     * 操作(APPROVE/REJECT/TRANSFER)
     */
    @ExcelProperty(value = "操作(APPROVE/REJECT/TRANSFER)")
    private String action;
    /**
     * 操作人
     */
    @ExcelProperty(value = "操作人")
    private Long operatorId;
    /**
     * 操作意见
     */
    @ExcelProperty(value = "操作意见")
    private String comment;
    /**
     * operateTime
     */
    @ExcelProperty(value = "operateTime")
    private LocalDateTime operateTime;

}
