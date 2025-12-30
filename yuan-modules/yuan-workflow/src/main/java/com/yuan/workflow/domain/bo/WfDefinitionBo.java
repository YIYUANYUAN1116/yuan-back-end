package com.yuan.workflow.domain.bo;

import com.yuan.common.core.validate.AddGroup;
import com.yuan.common.core.validate.EditGroup;
import com.yuan.workflow.domain.WfDefinition;
import io.github.linpeilie.annotations.AutoMapper;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

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
    private String definitionName;
    /**
     * 版本号(递增)
     */
    private Integer version;
    /**
     * 状态(DRAFT/PUBLISHED)
     */
    private String status;
    /**
     * 表单定义(JSON Schema)
     */
    private String formSchema;
    /**
     * 流程定义JSON
     */
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
