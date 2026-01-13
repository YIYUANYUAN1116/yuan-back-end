package com.yuan.workflow.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.yuan.common.tenant.core.TenantEntity;
import com.yuan.workflow.domain.enums.AssigneeType;
import com.yuan.workflow.domain.enums.NodeStatus;
import com.yuan.workflow.domain.enums.NodeType;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * wfn对象 wf_node_instance
 *
 
 * @date Sun Dec 28 11:26:37 CST 2025
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("wf_node_instance")
public class WfNodeInstance extends TenantEntity {


    /**
     * 节点实例ID
     */
        @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 流程实例ID
     */
    private Long instanceId;

    /**
     * 节点标识(start/approve_1)
     */
    private String nodeKey;

    /**
     * 节点类型(START/APPROVAL/GATEWAY/END)
     */
    private NodeType nodeType;

    /**
     * 审批人类型(USER/ROLE/DEPT)
     */
    private AssigneeType assigneeType;

    /**
     * 审批人值
     */
    private String assigneeValue;

    /**
     * 状态(WAIT/DONE)
     */
    private NodeStatus status;

    /**
     * 执行顺序
     */
    private Integer orderNo;

}
