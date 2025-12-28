package com.yuan.system.domain.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.yuan.common.excel.annotation.ExcelDictFormat;
import com.yuan.common.excel.convert.ExcelDictConvert;
import com.yuan.system.domain.SysDept;
import io.github.linpeilie.annotations.AutoMapper;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;


/**
 * dept视图对象 sys_dept
 *
 
 * @date Mon Dec 22 15:20:54 CST 2025
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = SysDept.class)
public class SysDeptVo implements Serializable {
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
    private Long deptId;
    /**
     * 租户编号
     */
    @ExcelProperty(value = "租户编号")
    private String tenantId;
    /**
     * 父部门id
     */
    @ExcelProperty(value = "父部门id")
    private Long parentId;
    /**
     * 祖级列表
     */
    @ExcelProperty(value = "祖级列表")
    private String ancestors;
    /**
     * 部门名称
     */
    @ExcelProperty(value = "部门名称")
    private String deptName;
    /**
     * 显示顺序
     */
    @ExcelProperty(value = "显示顺序")
    private Integer orderNum;
    /**
     * 负责人
     */
    @ExcelProperty(value = "负责人")
    private String leader;
    /**
     * 联系电话
     */
    @ExcelProperty(value = "联系电话")
    private String phone;
    /**
     * 邮箱
     */
    @ExcelProperty(value = "邮箱")
    private String email;
    /**
     * 部门状态（0正常 1停用）
     */
    @ExcelProperty(value = "部门状态", converter = ExcelDictConvert.class)
    @ExcelDictFormat(readConverterExp = "$column.readConverterExp()")
    private String status;
    /**
     * 删除标志（0代表存在 2代表删除）
     */
    @ExcelProperty(value = "删除标志", converter = ExcelDictConvert.class)
    @ExcelDictFormat(readConverterExp = "$column.readConverterExp()")
    private String delFlag;
    /**
     * 创建部门
     */
    @ExcelProperty(value = "创建部门")
    private Long createDept;
    /**
     * 创建者
     */
    @ExcelProperty(value = "创建者")
    private Long createBy;
    /**
     * 创建时间
     */
    @ExcelProperty(value = "创建时间")
    private LocalDateTime createTime;
    /**
     * 更新者
     */
    @ExcelProperty(value = "更新者")
    private Long updateBy;
    /**
     * 更新时间
     */
    @ExcelProperty(value = "更新时间")
    private LocalDateTime updateTime;

    private List<SysDeptVo> children;

}
