package com.yuan.system.domain.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.yuan.common.excel.annotation.ExcelDictFormat;
import com.yuan.common.excel.convert.ExcelDictConvert;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import com.yuan.system.domain.SysMenu;

import java.io.Serializable;
import java.time.LocalDateTime;


/**
 * 菜单视图对象 sys_menu
 *
 * @author ageerle
 * @date Wed Dec 10 17:20:04 CST 2025
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = SysMenu.class)
public class SysMenuVo implements Serializable {

    private Long menuId;
    /**
     * 菜单名称
     */
    @ExcelProperty(value = "菜单名称")
    private String menuName;
    /**
     * 父菜单ID
     */
    @ExcelProperty(value = "父菜单ID")
    private Long parentId;
    /**
     * 显示顺序
     */
    @ExcelProperty(value = "显示顺序")
    private Integer orderNum;
    /**
     * 路由地址
     */
    @ExcelProperty(value = "路由地址")
    private String path;
    /**
     * 组件路径
     */
    @ExcelProperty(value = "组件路径")
    private String component;
    /**
     * 路由参数
     */
    @ExcelProperty(value = "路由参数")
    private String queryParam;
    /**
     * 是否为外链（0是 1否）
     */
    @ExcelProperty(value = "是否为外链", converter = ExcelDictConvert.class)
    @ExcelDictFormat(readConverterExp = "$column.readConverterExp()")
    private Integer isFrame;
    /**
     * 是否缓存（0缓存 1不缓存）
     */
    @ExcelProperty(value = "是否缓存", converter = ExcelDictConvert.class)
    @ExcelDictFormat(readConverterExp = "$column.readConverterExp()")
    private Integer isCache;
    /**
     * 菜单类型（M目录 C菜单 F按钮）
     */
    @ExcelProperty(value = "菜单类型", converter = ExcelDictConvert.class)
    @ExcelDictFormat(readConverterExp = "$column.readConverterExp()")
    private String menuType;
    /**
     * 显示状态（0显示 1隐藏）
     */
    @ExcelProperty(value = "显示状态", converter = ExcelDictConvert.class)
    @ExcelDictFormat(readConverterExp = "$column.readConverterExp()")
    private String visible;
    /**
     * 菜单状态（0正常 1停用）
     */
    @ExcelProperty(value = "菜单状态", converter = ExcelDictConvert.class)
    @ExcelDictFormat(readConverterExp = "$column.readConverterExp()")
    private String status;
    /**
     * 权限标识
     */
    @ExcelProperty(value = "权限标识")
    private String perms;
    /**
     * 菜单图标
     */
    @ExcelProperty(value = "菜单图标")
    private String icon;
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
