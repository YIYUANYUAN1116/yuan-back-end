package com.yuan.workflow.domain.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.yuan.common.excel.annotation.ExcelDictFormat;
import com.yuan.common.excel.convert.ExcelDictConvert;
import com.yuan.workflow.domain.WfTransitionLog;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;


/**
 * wf_transition_log视图对象 wf_transition_log
 *
 * @author yuan
 * @date Wed Jan 28 21:54:22 CST 2026
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = WfTransitionLog.class)
public class WfTransitionLogVo implements Serializable {

    private Long id;
    /**
     * tenantId
     */
    @ExcelProperty(value = "tenantId")
    private String tenantId;
    /**
     * 流程定义Id
     */
    @ExcelProperty(value = "流程定义Id")
    private Long defId;
    /**
     * 流程定义版本
     */
    @ExcelProperty(value = "流程定义版本")
    private Integer defVersion;
    /**
     * 流程实例ID
     */
    @ExcelProperty(value = "流程实例ID")
    private Long instanceId;
    /**
     * 本次动作关联的节点实例ID（可选）
     */
    @ExcelProperty(value = "本次动作关联的节点实例ID", converter = ExcelDictConvert.class)
    @ExcelDictFormat(readConverterExp = "$column.readConverterExp()")
    private Long nodeInstanceId;
    /**
     * 本次动作关联的任务ID（可选）
     */
    @ExcelProperty(value = "本次动作关联的任务ID", converter = ExcelDictConvert.class)
    @ExcelDictFormat(readConverterExp = "$column.readConverterExp()")
    private Long taskId;
    /**
     * 来源节点Key（START时可为空）
     */
    @ExcelProperty(value = "来源节点Key", converter = ExcelDictConvert.class)
    @ExcelDictFormat(readConverterExp = "$column.readConverterExp()")
    private String fromNodeKey;
    /**
     * 目标节点Key（END时可为空）
     */
    @ExcelProperty(value = "目标节点Key", converter = ExcelDictConvert.class)
    @ExcelDictFormat(readConverterExp = "$column.readConverterExp()")
    private String toNodeKey;
    /**
     * START/APPROVE/REJECT/GATEWAY/ROLLBACK/WITHDRAW/TRANSFER/END
     */
    @ExcelProperty(value = "START/APPROVE/REJECT/GATEWAY/ROLLBACK/WITHDRAW/TRANSFER/END")
    private String action;
    /**
     * SYSTEM/USER
     */
    @ExcelProperty(value = "SYSTEM/USER")
    private String operatorType;
    /**
     * operator_type=USER时为用户ID
     */
    @ExcelProperty(value = "operator_type=USER时为用户ID")
    private Long operatorId;
    /**
     * 审批意见/退回原因/转办备注（前端展示用）
     */
    @ExcelProperty(value = "审批意见/退回原因/转办备注", converter = ExcelDictConvert.class)
    @ExcelDictFormat(readConverterExp = "$column.readConverterExp()")
    private String comment;
    /**
     * 网关命中条件表达式快照
     */
    @ExcelProperty(value = "网关命中条件表达式快照")
    private String conditionExpr;
    /**
     * 参与条件判断/展示的变量快照
     */
    @ExcelProperty(value = "参与条件判断/展示的变量快照")
    private String variablesSnapshot;
    /**
     * SUCCESS/FAIL
     */
    @ExcelProperty(value = "SUCCESS/FAIL")
    private String result;
    /**
     * createTime
     */
    @ExcelProperty(value = "createTime")
    private LocalDateTime createTime;

}
