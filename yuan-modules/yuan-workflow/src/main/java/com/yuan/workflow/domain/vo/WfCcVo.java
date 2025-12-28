package com.yuan.workflow.domain.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import com.yuan.workflow.domain.WfCc;

import java.io.Serializable;
import java.time.LocalDateTime;


/**
 * wfcc视图对象 wf_cc
 *
 
 * @date Sun Dec 28 11:26:25 CST 2025
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = WfCc.class)
public class WfCcVo implements Serializable {

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
     * 被抄送人
     */
    @ExcelProperty(value = "被抄送人")
    private Long userId;
    /**
     * 是否已读(0未读 1已读)
     */
    @ExcelProperty(value = "是否已读(0未读 1已读)")
    private String readFlag;
    /**
     * createTime
     */
    @ExcelProperty(value = "createTime")
    private LocalDateTime createTime;

}
