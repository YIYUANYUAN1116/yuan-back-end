package com.yuan.system.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.yuan.core.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * 多租户对象 sys_tenant
 *
 * @author ageerle
 * @date Wed Dec 10 17:18:08 CST 2025
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("sys_tenant")
public class SysTenant extends BaseEntity {


    /**
     * id
     */
        @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 租户编号
     */
    private String tenantId;

    /**
     * 联系人
     */
    private String contactUserName;

    /**
     * 联系电话
     */
    private String contactPhone;

    /**
     * 企业名称
     */
    private String companyName;

    /**
     * 统一社会信用代码
     */
    private String licenseNumber;

    /**
     * 地址
     */
    private String address;

    /**
     * 企业简介
     */
    private String intro;

    /**
     * 域名
     */
    private String domain;

    /**
     * 备注
     */
    private String remark;

    /**
     * 租户套餐编号
     */
    private Long packageId;

    /**
     * 过期时间
     */
    private LocalDateTime expireTime;

    /**
     * 用户数量（-1不限制）
     */
    private Integer accountCount;

    /**
     * 租户状态（0正常 1停用）
     */
    private String status;

    /**
     * 删除标志（0代表存在 2代表删除）
     */
        @TableLogic
    private String delFlag;




}
