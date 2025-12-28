package com.yuan.workflow.domain.bo;

import com.yuan.common.core.validate.AddGroup;
import com.yuan.common.core.validate.EditGroup;
import io.github.linpeilie.annotations.AutoMapper;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import com.yuan.workflow.domain.WfInstance;

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
    @NotNull(message = "租户ID不能为空", groups = { AddGroup.class, EditGroup.class })
    private String tenantId;
    /**
     * 流程定义ID
     */
    @NotNull(message = "流程定义ID不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long definitionId;
    /**
     * 流程业务标识
     */
    @NotBlank(message = "流程业务标识不能为空", groups = { AddGroup.class, EditGroup.class })
    private String processKey;
    /**
     * 流程版本
     */
    @NotNull(message = "流程版本不能为空", groups = { AddGroup.class, EditGroup.class })
    private Integer version;
    /**
     * 业务单号(请假单ID等)
     */
    private String businessKey;
    /**
     * 状态(RUNNING/APPROVED/REJECTED/CANCELED)
     */
    @NotBlank(message = "状态(RUNNING/APPROVED/REJECTED/CANCELED)不能为空", groups = { AddGroup.class, EditGroup.class })
    private String status;
    /**
     * 发起人
     */
    @NotNull(message = "发起人不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long startUserId;
    /**
     * startTime
     */
    private LocalDateTime startTime;
    /**
     * 结束时间
     */
    private LocalDateTime endTime;

    private String variables;

}
