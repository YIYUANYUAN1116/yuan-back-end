package com.yuan.workflow.domain.bo;

import com.yuan.common.core.validate.AddGroup;
import com.yuan.common.core.validate.EditGroup;
import com.yuan.workflow.domain.WfNodeInstance;
import io.github.linpeilie.annotations.AutoMapper;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * wfn业务对象 wf_node_instance
 *
 
 * @date Sun Dec 28 11:26:37 CST 2025
 */
@Data

@AutoMapper(target = WfNodeInstance.class, reverseConvertGenerate = false)
public class WfNodeInstanceBo implements Serializable {

    private Long id;

    /**
     * 流程实例ID
     */
    @NotNull(message = "流程实例ID不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long instanceId;
    /**
     * 节点标识(start/approve_1)
     */
    @NotBlank(message = "节点标识(start/approve_1)不能为空", groups = { AddGroup.class, EditGroup.class })
    private String nodeKey;

    private String nodeName;
    /**
     * 节点类型(START/APPROVAL/GATEWAY/END)
     */
    @NotBlank(message = "节点类型(START/APPROVAL/GATEWAY/END)不能为空", groups = { AddGroup.class, EditGroup.class })
    private String nodeType;
    /**
     * 审批人类型(USER/ROLE/DEPT)
     */
    private String assigneeType;

    /**
     * 审批人值
     */
    private String assigneeValue;
    /**
     * 状态(WAIT/DONE)
     */
    @NotBlank(message = "状态(WAIT/DONE)不能为空", groups = { AddGroup.class, EditGroup.class })
    private String status;
    /**
     * 执行顺序
     */
    @NotNull(message = "执行顺序不能为空", groups = { AddGroup.class, EditGroup.class })
    private Integer orderNo;
    /**
     * createTime
     */
    private LocalDateTime createTime;

}
