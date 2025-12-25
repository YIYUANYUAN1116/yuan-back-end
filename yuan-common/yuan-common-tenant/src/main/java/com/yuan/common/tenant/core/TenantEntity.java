package com.yuan.common.tenant.core;

import lombok.Data;
import lombok.EqualsAndHashCode;
import com.yuan.core.domain.BaseEntity;


/**
 * 租户基类
 *
 * 
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class TenantEntity extends BaseEntity {

    /**
     * 租户编号
     */
    private String tenantId;

}
