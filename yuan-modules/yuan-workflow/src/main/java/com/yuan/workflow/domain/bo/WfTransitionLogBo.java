package com.yuan.workflow.domain.bo;

import com.yuan.common.core.validate.AddGroup;
import com.yuan.common.core.validate.EditGroup;
import com.yuan.workflow.domain.WfTransitionLog;
import io.github.linpeilie.annotations.AutoMapper;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * wf_transition_log业务对象 wf_transition_log
 *
 * @author yuan
 * @date Wed Jan 28 21:54:22 CST 2026
 */
@Data

@AutoMapper(target = WfTransitionLog.class, reverseConvertGenerate = false)
public class WfTransitionLogBo implements Serializable {

    private Long id;

    /**
     * tenantId
     */
    @NotBlank(message = "tenantId不能为空", groups = { AddGroup.class, EditGroup.class })
    private String tenantId;
    /**
     * 流程定义Id
     */
    @NotNull(message = "流程定义Id不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long defId;
    /**
     * 流程定义版本
     */
    @NotNull(message = "流程定义版本不能为空", groups = { AddGroup.class, EditGroup.class })
    private Integer defVersion;
    /**
     * 流程实例ID
     */
    @NotNull(message = "流程实例ID不能为空", groups = { AddGroup.class, EditGroup.class })
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
    @NotBlank(message = "START/APPROVE/REJECT/GATEWAY/ROLLBACK/WITHDRAW/TRANSFER/END不能为空", groups = { AddGroup.class, EditGroup.class })
    private String action;
    /**
     * SYSTEM/USER
     */
    @NotBlank(message = "SYSTEM/USER不能为空", groups = { AddGroup.class, EditGroup.class })
    private String operatorType;
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
    @NotBlank(message = "SUCCESS/FAIL不能为空", groups = { AddGroup.class, EditGroup.class })
    private String result;
    /**
     * createTime
     */
    @NotNull(message = "createTime不能为空", groups = { AddGroup.class, EditGroup.class })
    private LocalDateTime createTime;

}
