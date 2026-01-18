package com.yuan.oa.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.yuan.common.core.enums.apply.ApplyStatus;
import com.yuan.common.tenant.core.TenantEntity;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * oa_leave对象 oa_leave_apply
 *
 * @author yuan
 * @date Fri Jan 16 13:46:51 CST 2026
 */
@Data
@TableName("oa_leave_apply")
public class OaLeaveApply extends TenantEntity {


    /**
     * id
     */
        @TableId(value = "id", type = IdType.AUTO)
    private Long id;


    /**
     * 请假单号
     */
    private String applyNo;

    /**
     * 申请人ID
     */
    private Long applicantId;

    /**
     * 申请人姓名（冗余）
     */
    private String applicantName;

    /**
     * 申请人部门ID
     */
    private Long applicantDept;

    private String applicantDeptName;

    /**
     * 请假类型
     */
    private String leaveType;

    /**
     * 请假原因
     */
    private String reason;

    /**
     * 开始时间
     */
    private LocalDateTime startTime;

    /**
     * 结束时间
     */
    private LocalDateTime endTime;

    /**
     * 请假天数
     */
    private BigDecimal leaveDays;

    /**
     * 请假小时数（可选）
     */
    private BigDecimal leaveHours;

    /**
     * 状态：DRAFT/APPROVING/APPROVED/REJECTED/CANCELED
     */
    private ApplyStatus status;

    @TableLogic
    private String delFlag;

}
