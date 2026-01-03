package com.yuan.workflow.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.yuan.common.tenant.core.TenantEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * wfcc对象 wf_cc
 *
 
 * @date Sun Dec 28 11:26:25 CST 2025
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("wf_cc")
public class WfCc extends TenantEntity {


    /**
     * 抄送ID
     */
        @TableId(value = "id", type = IdType.AUTO)
    private Long id;


    /**
     * 流程实例ID
     */
    private Long instanceId;

    /**
     * 被抄送人
     */
    private Long userId;

    /**
     * 是否已读(0未读 1已读)
     */
    private String readFlag;


}
