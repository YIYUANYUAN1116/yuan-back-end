package com.yuan.workflow.domain.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.yuan.workflow.domain.enums.NodeStatus;
import com.yuan.workflow.domain.enums.NodeType;
import io.github.linpeilie.annotations.AutoMapper;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import com.yuan.workflow.domain.WfNodeInstance;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;


/**
 * wfn视图对象 wf_node_instance
 *
 
 * @date Sun Dec 28 11:26:37 CST 2025
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = WfNodeInstance.class)
public class WfNodeInstanceVo implements Serializable {
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
    private Long id;
    /**
     * 流程实例ID
     */
    @ExcelProperty(value = "流程实例ID")
    private Long instanceId;
    /**
     * 节点标识(start/approve_1)
     */
    @ExcelProperty(value = "节点标识(start/approve_1)")
    private String nodeKey;
    @ExcelProperty(value = "节点标识名称")
    private String nodeName;
    /**
     * 节点类型(START/APPROVAL/GATEWAY/END)
     */
    @ExcelProperty(value = "节点类型(START/APPROVAL/GATEWAY/END)")
    private NodeType nodeType;
    /**
     * 审批人类型(USER/ROLE/DEPT)
     */
    @ExcelProperty(value = "审批人类型(USER/ROLE/DEPT)")
    private String assigneeType;
    /**
     * 审批人值
     */
    @ExcelProperty(value = "审批人值")
    private String assigneeValue;

    @ExcelProperty(value = "操作人Id")
    private Long operatorId;

    /**
     * 状态(WAIT/DONE)
     */
    @ExcelProperty(value = "状态(WAIT/DONE)")
    private NodeStatus status;
    /**
     * 执行顺序
     */
    @ExcelProperty(value = "执行顺序")
    private Integer orderNo;
    /**
     * createTime
     */
    @ExcelProperty(value = "createTime")
    private LocalDateTime createTime;

    private LocalDateTime finishedTime;


    /**********************非数据库字段*******************************/
    private String operatorName;
    List<WfTaskVo> tasks;

    private String selectNodeKey;


    /** 上一个节点 */
    private List<String> prevNodeKeys;

    /** 下一个节点 */
    private List<String> nextNodeKeys;

    private WfTransitionLogVo transitionLogVo;
}
