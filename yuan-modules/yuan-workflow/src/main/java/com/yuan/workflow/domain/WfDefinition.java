package com.yuan.workflow.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.Version;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * wfd对象 wf_definition
 *
 
 * @date Sun Dec 28 11:26:30 CST 2025
 */
@Data
@TableName("wf_definition")
public class WfDefinition implements Serializable {


    /**
     * 流程定义ID
     */
        @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 租户ID
     */
    private String tenantId;

    /**
     * 流程业务标识(leave, expense)
     */
    private String processKey;

    /**
     * 流程名称
     */
    private String processName;

    /**
     * 版本号(递增)
     */
        @Version
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
