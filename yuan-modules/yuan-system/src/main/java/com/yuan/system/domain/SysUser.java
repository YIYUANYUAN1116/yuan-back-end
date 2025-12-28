package com.yuan.system.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.yuan.core.domain.BaseEntity;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 用户对象 sys_user
 *
 
 * @date Sun Dec 07 17:25:38 CST 2025
 */
@Data
@TableName("sys_user")
public class SysUser extends BaseEntity {

    /**
     * 用户ID
     */
        @TableId(value = "user_id", type = IdType.AUTO)
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
    private String userName;

    /**
     * 用户昵称
     */
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
        @TableLogic
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


}
