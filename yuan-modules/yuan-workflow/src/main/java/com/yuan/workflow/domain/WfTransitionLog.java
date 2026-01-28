package com.yuan.workflow.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.yuan.workflow.enums.OperatorType;
import com.yuan.workflow.enums.TransitionAction;
import com.yuan.workflow.enums.TransitionResult;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * wf_transition_log对象 wf_transition_log
 *
 * @author yuan
 * @date Wed Jan 28 21:54:22 CST 2026
 */
@Data
@TableName("wf_transition_log")
public class WfTransitionLog implements Serializable {


    /**
     * id
     */
        @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * tenantId
     */
    private String tenantId;

    /**
     * 流程定义Id
     */
    private Long defId;

    /**
     * 流程定义版本
     */
    private Integer defVersion;

    /**
     * 流程实例ID
     */
    private Long instanceId;

    /**
     * 本次动作关联的节点实例ID（可选）
     */
    private Long nodeInstanceId;

    /**
     * 本次动作关联的任务ID（可选）
     */
    private Long taskId;

    /**
     * 来源节点Key（START时可为空）
     */
    private String fromNodeKey;

    /**
     * 目标节点Key（END时可为空）
     */
    private String toNodeKey;

    /**
     * START/APPROVE/REJECT/GATEWAY/ROLLBACK/WITHDRAW/TRANSFER/END
     */
    private TransitionAction action;

    /**
     * SYSTEM/USER
     */
    private OperatorType operatorType;

    /**
     * operator_type=USER时为用户ID
     */
    private Long operatorId;

    /**
     * 审批意见/退回原因/转办备注（前端展示用）
     */
    private String comment;

    /**
     * 网关命中条件表达式快照
     */
    private String conditionExpr;

    /**
     * 参与条件判断/展示的变量快照
     */
    private String variablesSnapshot;

    /**
     * SUCCESS/FAIL
     */
    private TransitionResult result;

    /**
     * createTime
     */
    private LocalDateTime createTime;


}
