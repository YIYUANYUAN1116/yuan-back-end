package com.yuan.system.domain.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.yuan.common.excel.annotation.ExcelDictFormat;
import com.yuan.common.excel.convert.ExcelDictConvert;
import com.yuan.system.domain.SysPost;
import io.github.linpeilie.annotations.AutoMapper;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;


/**
 * post视图对象 sys_post
 *

 * @date Mon Dec 22 15:05:40 CST 2025
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = SysPost.class)
public class SysPostVo implements Serializable {
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
    private Long postId;
    /**
     * 租户编号
     */
    @ExcelProperty(value = "租户编号")
    private String tenantId;
    /**
     * 岗位编码
     */
    @ExcelProperty(value = "岗位编码")
    private String postCode;
    /**
     * 岗位名称
     */
    @ExcelProperty(value = "岗位名称")
    private String postName;
    /**
     * 显示顺序
     */
    @ExcelProperty(value = "显示顺序")
    private Integer postSort;
    /**
     * 状态（0正常 1停用）
     */
    @ExcelProperty(value = "状态", converter = ExcelDictConvert.class)
    @ExcelDictFormat(readConverterExp = "$column.readConverterExp()")
    private String status;
    /**
     * 数据范围（1：所有数据权限；2：自定义数据权限；3：本部门数据权限；4：本部门及以下数据权限；5：仅本人数据权限）
     */
    private String dataScope;
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
    /**
     * 备注
     */
    @ExcelProperty(value = "备注")
    private String remark;

}
