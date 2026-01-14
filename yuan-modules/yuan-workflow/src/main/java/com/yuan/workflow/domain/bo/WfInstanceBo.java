package com.yuan.workflow.domain.bo;

import com.yuan.common.core.validate.AddGroup;
import com.yuan.common.core.validate.EditGroup;
import com.yuan.workflow.domain.WfInstance;
import io.github.linpeilie.annotations.AutoMapper;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * wfi业务对象 wf_instance
 *

 * @date Sun Dec 28 11:26:34 CST 2025
 */
@Data

@AutoMapper(target = WfInstance.class, reverseConvertGenerate = false)
public class WfInstanceBo implements Serializable {

    private Long id;

    /**
     * 租户ID
     */
    private String tenantId;
    /**
     * 流程定义ID
     */
    @NotNull(message = "流程定义ID不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long definitionId;
    /**
     * 流程业务标识
     */
    private String definitionKey;

    private String definitionName;
    /**
     * 流程版本
     */
    private Integer definitionVersion;

    /**
     * 状态(RUNNING/APPROVED/REJECTED/CANCELED)
     */

    private String status;

    /**
     * startTime
     */
    private LocalDateTime startTime;
    /**
     * 结束时间
     */
    private LocalDateTime endTime;

    private String variables;

    private Long startId;

    private String startName;

    private Long startDeptId;

    private String startDeptName;

    private Long lastOperatorId;

    private String lastOperatorName;

    private String endReason;
    private String endComment;
    private Long endBy;


    /*******************非数据库字段********************/
    private String bizType;
    private String bizNo;

}
