package com.yuan.workflow.domain.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import com.yuan.workflow.domain.WfDefinition;

import java.io.Serializable;
import java.time.LocalDateTime;


/**
 * wfd视图对象 wf_definition
 *
 
 * @date Sun Dec 28 11:26:30 CST 2025
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = WfDefinition.class)
public class WfDefinitionVo implements Serializable {

    private Long id;
    /**
     * 租户ID
     */
    @ExcelProperty(value = "租户ID")
    private String tenantId;
    /**
     * 流程业务标识(leave, expense)
     */
    @ExcelProperty(value = "流程业务标识(leave, expense)")
    private String definitionKey;
    /**
     * 流程名称
     */
    @ExcelProperty(value = "流程名称")
    private String processName;
    /**
     * 版本号(递增)
     */
    @ExcelProperty(value = "版本号(递增)")
    private Integer version;
    /**
     * 状态(DRAFT/PUBLISHED)
     */
    @ExcelProperty(value = "状态(DRAFT/PUBLISHED)")
    private String status;
    /**
     * 表单定义(JSON Schema)
     */
    @ExcelProperty(value = "表单定义(JSON Schema)")
    private String formSchema;
    /**
     * 流程定义JSON
     */
    @ExcelProperty(value = "流程定义JSON")
    private String flowJson;
    /**
     * 备注
     */
    @ExcelProperty(value = "备注")
    private String remark;
    /**
     * createBy
     */
    @ExcelProperty(value = "createBy")
    private Long createBy;
    /**
     * createTime
     */
    @ExcelProperty(value = "createTime")
    private LocalDateTime createTime;
    /**
     * updateTime
     */
    @ExcelProperty(value = "updateTime")
    private LocalDateTime updateTime;

}
