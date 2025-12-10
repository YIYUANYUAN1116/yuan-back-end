package com.yuan.system.domain.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.yuan.common.excel.annotation.ExcelDictFormat;
import com.yuan.common.excel.convert.ExcelDictConvert;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import com.yuan.system.domain.SysTenant;

import java.io.Serializable;
import java.time.LocalDateTime;


/**
 * 多租户视图对象 sys_tenant
 *
 * @author ageerle
 * @date Wed Dec 10 17:18:08 CST 2025
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = SysTenant.class)
public class SysTenantVo implements Serializable {

    private Long id;
    /**
     * 租户编号
     */
    @ExcelProperty(value = "租户编号")
    private String tenantId;
    /**
     * 联系人
     */
    @ExcelProperty(value = "联系人")
    private String contactUserName;
    /**
     * 联系电话
     */
    @ExcelProperty(value = "联系电话")
    private String contactPhone;
    /**
     * 企业名称
     */
    @ExcelProperty(value = "企业名称")
    private String companyName;
    /**
     * 统一社会信用代码
     */
    @ExcelProperty(value = "统一社会信用代码")
    private String licenseNumber;
    /**
     * 地址
     */
    @ExcelProperty(value = "地址")
    private String address;
    /**
     * 企业简介
     */
    @ExcelProperty(value = "企业简介")
    private String intro;
    /**
     * 域名
     */
    @ExcelProperty(value = "域名")
    private String domain;
    /**
     * 备注
     */
    @ExcelProperty(value = "备注")
    private String remark;
    /**
     * 租户套餐编号
     */
    @ExcelProperty(value = "租户套餐编号")
    private Long packageId;
    /**
     * 过期时间
     */
    @ExcelProperty(value = "过期时间")
    private LocalDateTime expireTime;
    /**
     * 用户数量（-1不限制）
     */
    @ExcelProperty(value = "用户数量", converter = ExcelDictConvert.class)
    @ExcelDictFormat(readConverterExp = "$column.readConverterExp()")
    private Integer accountCount;
    /**
     * 租户状态（0正常 1停用）
     */
    @ExcelProperty(value = "租户状态", converter = ExcelDictConvert.class)
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

}
