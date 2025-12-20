package com.yuan.system.domain.bo;

import com.yuan.common.core.validate.AddGroup;
import com.yuan.common.core.validate.EditGroup;
import com.yuan.system.domain.SysUser;
import io.github.linpeilie.annotations.AutoMapper;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 用户业务对象 sys_user
 *
 * @author ageerle
 * @date Sun Dec 07 17:25:38 CST 2025
 */
@Data

@AutoMapper(target = SysUser.class, reverseConvertGenerate = false)
public class SysUserBo implements Serializable {

    private Long userId;

    /**
     * 微信用户标识
     */
    private String openId;
    /**
     * 用户等级
     */
    private String userGrade;
    /**
     * 账户余额
     */
    private BigDecimal userBalance;
    /**
     * 租户编号
     */
    private String tenantId;
    /**
     * 部门ID
     */
    private Long deptId;
    /**
     * 用户账号
     */
    @NotBlank(message = "用户账号不能为空", groups = { AddGroup.class, EditGroup.class })
    private String userName;
    /**
     * 用户昵称
     */
    @NotBlank(message = "用户昵称不能为空", groups = { AddGroup.class, EditGroup.class })
    private String nickName;
    /**
     * 用户类型（sys_user系统用户）
     */
    private String userType;
    /**
     * 用户套餐
     */
    private String userPlan;
    /**
     * 用户邮箱
     */
    @NotBlank(message = "用户邮箱不能为空", groups = { AddGroup.class, EditGroup.class })
    private String email;
    /**
     * 手机号码
     */
    private String phonenumber;
    /**
     * 用户性别（0男 1女 2未知）
     */
    private String sex;
    /**
     * 头像地址
     */
    private String avatar;
    /**
     * 微信头像地址
     */
    private String wxAvatar;
    /**
     * 密码
     */
    private String password;
    /**
     * 帐号状态（0正常 1停用）
     */
    private String status;
    /**
     * 删除标志（0代表存在 2代表删除）
     */
    private String delFlag;
    /**
     * 最后登录IP
     */
    private String loginIp;
    /**
     * 最后登录时间
     */
    private LocalDateTime loginDate;
    /**
     * 注册域名
     */
    private String domainName;
    /**
     * 创建部门
     */
    private Long createDept;
    /**
     * 创建者
     */
    private Long createBy;
    /**
     * 创建时间
     */
    private LocalDateTime createTime;
    /**
     * 更新者
     */
    private Long updateBy;
    /**
     * 更新时间
     */
    private LocalDateTime updateTime;
    /**
     * 备注
     */
    private String remark;
    /**
     * 关联知识库角色/角色组
     */
    private String kroleGroupType;
    /**
     * 关联知识库角色/角色组id
     */
    private String kroleGroupIds;

    private Long roleId;
}
