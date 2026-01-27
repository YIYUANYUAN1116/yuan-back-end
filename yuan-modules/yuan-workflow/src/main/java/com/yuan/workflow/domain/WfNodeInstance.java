package com.yuan.workflow.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.yuan.common.tenant.core.TenantEntity;
import com.yuan.workflow.domain.enums.AssigneeType;
import com.yuan.workflow.domain.enums.NodeStatus;
import com.yuan.workflow.domain.enums.NodeType;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;
import java.util.List;

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
     * lgFlow的ID
     */
    private String nodeKey;

    private String nodeName;

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
     * operatorId：
     * 触发该节点进入最终状态（DONE / CANCELED）的操作人
     *
     * - 单人审批：审批人
     * - 或签：第一个同意的人
     * - 会签：最后一个完成人
     * - 自动节点：SYSTEM
     * - 撤回/回滚：撤回/回滚发起人
     */
    private Long operatorId;

    private NodeStatus status;

    /**
     * 执行顺序
     */
    private Integer orderNo;
    @TableLogic
    private String delFlag;
    private LocalDateTime finishedTime;
    private List<String> selectedNextKeys;
}
