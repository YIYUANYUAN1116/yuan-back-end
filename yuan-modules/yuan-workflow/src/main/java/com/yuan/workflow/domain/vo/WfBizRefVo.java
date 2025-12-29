package com.yuan.workflow.domain.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.yuan.workflow.domain.WfBizRef;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;


/**
 * wfref视图对象 wf_biz_ref
 *
 * @author yuan
 * @date Mon Dec 29 20:51:33 CST 2025
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = WfBizRef.class)
public class WfBizRefVo implements Serializable {

    private Long id;
    /**
     * 业务类型，如 LEAVE/REIMBURSE
     */
    @ExcelProperty(value = "业务类型，如 LEAVE/REIMBURSE")
    private String bizType;
    /**
     * 业务主键
     */
    @ExcelProperty(value = "业务主键")
    private String bizId;
    /**
     * 流程实例ID
     */
    @ExcelProperty(value = "流程实例ID")
    private Long instanceId;
    /**
     * RUNNING/APPROVED/REJECTED/CANCELED
     */
    @ExcelProperty(value = "RUNNING/APPROVED/REJECTED/CANCELED")
    private String status;
    /**
     * createdBy
     */
    @ExcelProperty(value = "createdBy")
    private Long createdBy;
    /**
     * createdTime
     */
    @ExcelProperty(value = "createdTime")
    private LocalDateTime createdTime;
    /**
     * updatedTime
     */
    @ExcelProperty(value = "updatedTime")
    private LocalDateTime updatedTime;

}
