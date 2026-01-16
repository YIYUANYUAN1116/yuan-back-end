package com.yuan.oa.domain.bo;

import com.yuan.common.core.validate.AddGroup;
import com.yuan.common.core.validate.EditGroup;
import com.yuan.oa.domain.OaLeaveApply;
import io.github.linpeilie.annotations.AutoMapper;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * oa_leave业务对象 oa_leave_apply
 *
 * @author yuan
 * @date Fri Jan 16 13:46:51 CST 2026
 */
@Data

@AutoMapper(target = OaLeaveApply.class, reverseConvertGenerate = false)
public class OaLeaveApplyBo implements Serializable {

    private Long id;

    private String tenantId;

    private String applyNo;
    private Long applicantId;

    private String applicantName;

    private Long applicantDept;

    private String applicantDeptName;
    /**
     * 请假类型
     */
    @NotBlank(message = "请假类型不能为空", groups = { AddGroup.class, EditGroup.class })
    private String leaveType;
    /**
     * 请假原因
     */
    private String reason;
    /**
     * 开始时间
     */
    @NotNull(message = "开始时间不能为空", groups = { AddGroup.class, EditGroup.class })
    private LocalDateTime startTime;
    /**
     * 结束时间
     */
    @NotNull(message = "结束时间不能为空", groups = { AddGroup.class, EditGroup.class })
    private LocalDateTime endTime;
    /**
     * 请假天数
     */
    @NotNull(message = "请假天数不能为空", groups = { AddGroup.class, EditGroup.class })
    private BigDecimal leaveDays;
    /**
     * 请假小时数（可选）
     */
    private BigDecimal leaveHours;
    /**
     * 状态：DRAFT/APPROVING/APPROVED/REJECTED/CANCELED
     */
    private String status;
    /**
     * createDept
     */
    private Long createDept;
    /**
     * createBy
     */
    private Long createBy;
    /**
     * createTime
     */
    private LocalDateTime createTime;
    /**
     * updateBy
     */
    private Long updateBy;
    /**
     * updateTime
     */
    private LocalDateTime updateTime;

}
