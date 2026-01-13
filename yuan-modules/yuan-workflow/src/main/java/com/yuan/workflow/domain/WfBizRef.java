package com.yuan.workflow.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.yuan.core.domain.BaseEntity;
import com.yuan.workflow.domain.enums.InstanceStatus;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * wfref对象 wf_biz_ref
 * 为了让 workflow 支持 一对多/多对多业务绑定，并把 workflow 和业务模块解耦。
 * @author yuan
 * @date Mon Dec 29 20:51:33 CST 2025
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("wf_biz_ref")
public class WfBizRef extends BaseEntity {


    /**
     * id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private String tenantId;
    /**
     * 业务类型，如 LEAVE/REIMBURSE
     */
    private String bizType;

    /**
     * 业务主键
     */
    private Long bizId;

    private String bizNo;
    /**
     * 流程实例ID
     */
    private Long instanceId;

    /**
     * RUNNING/APPROVED/REJECTED/CANCELED
     */
    private InstanceStatus status;
    /**
     * PRIMARY/RELATED  多个业务对一个流程时，区分主业务和关联业务
     */
    private String ref_type;
}
