package com.yuan.system.domain.bo;

import com.yuan.system.domain.SysTenant;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 多租户业务对象 sys_tenant
 *
 
 * @date Wed Dec 10 17:18:08 CST 2025
 */
@Data

@AutoMapper(target = SysTenant.class, reverseConvertGenerate = false)
public class SysTenantBo implements Serializable {

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
    private String delFlag;
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

}
