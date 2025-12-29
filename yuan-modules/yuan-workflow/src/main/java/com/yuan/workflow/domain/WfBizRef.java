package com.yuan.workflow.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * wfref对象 wf_biz_ref
 *
 * @author yuan
 * @date Mon Dec 29 20:51:33 CST 2025
 */
@Data
@TableName("wf_biz_ref")
public class WfBizRef implements Serializable {


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

    /**
     * createdBy
     */
    private Long createdBy;

    /**
     * createdTime
     */
    private LocalDateTime createdTime;

    /**
     * updatedTime
     */
    private LocalDateTime updatedTime;


}
