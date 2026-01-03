package com.yuan.workflow.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.yuan.core.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * wfref对象 wf_biz_ref
 *
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

    /**
     * 业务类型，如 LEAVE/REIMBURSE
     */
    private String bizType;

    /**
     * 业务主键
     */
    private Long bizId;

    /**
     * 流程实例ID
     */
    private Long instanceId;

    /**
     * RUNNING/APPROVED/REJECTED/CANCELED
     */
    private String status;

}
