package com.yuan.workflow.domain.bo;

import com.yuan.common.core.validate.AddGroup;
import com.yuan.common.core.validate.EditGroup;
import io.github.linpeilie.annotations.AutoMapper;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import com.yuan.workflow.domain.WfDefinition;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * wfd业务对象 wf_definition
 *
 
 * @date Sun Dec 28 11:26:30 CST 2025
 */
@Data

@AutoMapper(target = WfDefinition.class, reverseConvertGenerate = false)
public class WfDefinitionBo implements Serializable {

    private Long id;

    /**
     * 租户ID
     */
    @NotNull(message = "租户ID不能为空", groups = { AddGroup.class, EditGroup.class })
    private String tenantId;
    /**
     * 流程业务标识(leave, expense)
     */
    @NotBlank(message = "流程业务标识(leave, expense)不能为空", groups = { AddGroup.class, EditGroup.class })
    private String definitionKey;
    /**
     * 流程名称
     */
    @NotBlank(message = "流程名称不能为空", groups = { AddGroup.class, EditGroup.class })
    private String processName;
    /**
     * 版本号(递增)
     */
    @NotNull(message = "版本号(递增)不能为空", groups = { AddGroup.class, EditGroup.class })
    private Integer version;
    /**
     * 状态(DRAFT/PUBLISHED)
     */
    @NotBlank(message = "状态(DRAFT/PUBLISHED)不能为空", groups = { AddGroup.class, EditGroup.class })
    private String status;
    /**
     * 表单定义(JSON Schema)
     */
    private String formSchema;
    /**
     * 流程定义JSON
     */
    @NotBlank(message = "流程定义JSON不能为空", groups = { AddGroup.class, EditGroup.class })
    private String flowJson;
    /**
     * 备注
     */
    private String remark;
    /**
     * createBy
     */
    private Long createBy;
    /**
     * createTime
     */
    private LocalDateTime createTime;
    /**
     * updateTime
     */
    private LocalDateTime updateTime;

}
