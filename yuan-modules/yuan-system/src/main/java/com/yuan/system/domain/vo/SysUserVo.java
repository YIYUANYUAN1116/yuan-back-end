package com.yuan.system.domain.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.yuan.common.excel.annotation.ExcelDictFormat;
import com.yuan.common.excel.convert.ExcelDictConvert;
import com.yuan.system.domain.SysUser;
import io.github.linpeilie.annotations.AutoMapper;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;


/**
 * 用户视图对象 sys_user
 *
 * @author ageerle
 * @date Sun Dec 07 17:25:38 CST 2025
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = SysUser.class)
public class SysUserVo implements Serializable {
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
    private Long userId;
    /**
     * 微信用户标识
     */
    @ExcelProperty(value = "微信用户标识")
    private String openId;
    /**
     * 用户等级
     */
    @ExcelProperty(value = "用户等级")
    private String userGrade;
    /**
     * 账户余额
     */
    @ExcelProperty(value = "账户余额")
    private BigDecimal userBalance;
    /**
     * 租户编号
     */
    @ExcelProperty(value = "租户编号")
    private String tenantId;
    /**
     * 部门ID
     */
    @ExcelProperty(value = "部门ID")
    private Long deptId;
    /**
     * 用户账号
     */
    @ExcelProperty(value = "用户账号")
    private String userName;
    /**
     * 用户昵称
     */
    @ExcelProperty(value = "用户昵称")
    private String nickName;
    /**
     * 用户类型（sys_user系统用户）
     */
    @ExcelProperty(value = "用户类型", converter = ExcelDictConvert.class)
    @ExcelDictFormat(readConverterExp = "$column.readConverterExp()")
    private String userType;
    /**
     * 用户套餐
     */
    @ExcelProperty(value = "用户套餐")
    private String userPlan;
    /**
     * 用户邮箱
     */
    @ExcelProperty(value = "用户邮箱")
    private String email;
    /**
     * 手机号码
     */
    @ExcelProperty(value = "手机号码")
    private String phonenumber;
    /**
     * 用户性别（0男 1女 2未知）
     */
    @ExcelProperty(value = "用户性别", converter = ExcelDictConvert.class)
    @ExcelDictFormat(readConverterExp = "$column.readConverterExp()")
    private String sex;
    /**
     * 头像地址
     */
    @ExcelProperty(value = "头像地址")
    private String avatar;
    /**
     * 微信头像地址
     */
    @ExcelProperty(value = "微信头像地址")
    private String wxAvatar;
    /**
     * 密码
     */
    @ExcelProperty(value = "密码")
    private String password;
    /**
     * 帐号状态（0正常 1停用）
     */
    @ExcelProperty(value = "帐号状态", converter = ExcelDictConvert.class)
    @ExcelDictFormat(readConverterExp = "$column.readConverterExp()")
    private String status;
    /**
     * 删除标志（0代表存在 2代表删除）
     */
    @ExcelProperty(value = "删除标志", converter = ExcelDictConvert.class)
    @ExcelDictFormat(readConverterExp = "$column.readConverterExp()")
    private String delFlag;
    /**
     * 最后登录IP
     */
    @ExcelProperty(value = "最后登录IP")
    private String loginIp;
    /**
     * 最后登录时间
     */
    @ExcelProperty(value = "最后登录时间")
    private LocalDateTime loginDate;
    /**
     * 注册域名
     */
    @ExcelProperty(value = "注册域名")
    private String domainName;
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
    /**
     * 关联知识库角色/角色组
     */
    @ExcelProperty(value = "关联知识库角色/角色组")
    private String kroleGroupType;
    /**
     * 关联知识库角色/角色组id
     */
    @ExcelProperty(value = "关联知识库角色/角色组id")
    private String kroleGroupIds;

    /**
     * 角色对象
     */
    private List<SysRoleVo> roles;

    /**
     * 部门对象
     */
    private SysDeptVo dept;

    private String deptName;
}
