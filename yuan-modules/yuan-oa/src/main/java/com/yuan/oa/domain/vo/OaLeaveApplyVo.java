package com.yuan.oa.domain.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.yuan.common.excel.annotation.ExcelDictFormat;
import com.yuan.common.excel.convert.ExcelDictConvert;
import com.yuan.oa.domain.OaLeaveApply;
import io.github.linpeilie.annotations.AutoMapper;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;


/**
 * oa_leave视图对象 oa_leave_apply
 *
 * @author yuan
 * @date Fri Jan 16 13:46:51 CST 2026
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = OaLeaveApply.class)
public class OaLeaveApplyVo implements Serializable {
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
    private Long id;
    /**
     * 租户ID
     */
    @ExcelProperty(value = "租户ID")
    private String tenantId;
    /**
     * 请假单号
     */
    @ExcelProperty(value = "请假单号")
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
    private String applyNo;
    /**
     * 申请人ID
     */
    @ExcelProperty(value = "申请人ID")
    private Long applicantId;
    /**
     * 申请人姓名（冗余）
     */
    @ExcelProperty(value = "申请人姓名", converter = ExcelDictConvert.class)
    @ExcelDictFormat(readConverterExp = "$column.readConverterExp()")
    private String applicantName;
    /**
     * 申请人部门ID
     */
    @ExcelProperty(value = "申请人部门ID")
    private Long applicantDept;

    private String applicantDeptName;
    /**
     * 请假类型
     */
    @ExcelProperty(value = "请假类型")
    private String leaveType;
    /**
     * 请假原因
     */
    @ExcelProperty(value = "请假原因")
    private String reason;
    /**
     * 开始时间
     */
    @ExcelProperty(value = "开始时间")
    private LocalDateTime startTime;
    /**
     * 结束时间
     */
    @ExcelProperty(value = "结束时间")
    private LocalDateTime endTime;
    /**
     * 请假天数
     */
    @ExcelProperty(value = "请假天数")
    private BigDecimal leaveDays;
    /**
     * 请假小时数（可选）
     */
    @ExcelProperty(value = "请假小时数", converter = ExcelDictConvert.class)
    @ExcelDictFormat(readConverterExp = "$column.readConverterExp()")
    private BigDecimal leaveHours;
    /**
     * 状态：DRAFT/APPROVING/APPROVED/REJECTED/CANCELED
     */
    @ExcelProperty(value = "状态：DRAFT/APPROVING/APPROVED/REJECTED/CANCELED")
    private String status;
    /**
     * createDept
     */
    @ExcelProperty(value = "createDept")
    private Long createDept;
    /**
     * createBy
     */
    @ExcelProperty(value = "createBy")
    private Long createBy;
    /**
     * createTime
     */
    @ExcelProperty(value = "createTime")
    private LocalDateTime createTime;
    /**
     * updateBy
     */
    @ExcelProperty(value = "updateBy")
    private Long updateBy;
    /**
     * updateTime
     */
    @ExcelProperty(value = "updateTime")
    private LocalDateTime updateTime;

}
