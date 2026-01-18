package com.yuan.workflow.domain;

import com.baomidou.mybatisplus.annotation.*;
import com.yuan.common.tenant.core.TenantEntity;
import com.yuan.workflow.domain.enums.DefinitionStatus;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * wfd对象 wf_definition
 *
 
 * @date Sun Dec 28 11:26:30 CST 2025
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("wf_definition")
public class WfDefinition extends TenantEntity {

    /**
     * 流程定义ID
     */
        @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 流程业务标识(leave, expense)
     */
    private String definitionKey;

    /**
     * 流程名称
     */
    private String definitionName;

    /**
     * 版本号(递增)
     */
        @Version
    private Integer version;

    /**
     * 状态(DRAFT/PUBLISHED)
     */
    private DefinitionStatus status;

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

    @TableLogic
    private String delFlag;
}
