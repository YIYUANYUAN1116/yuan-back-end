package com.yuan.workflow.domain.bo;

import com.yuan.common.core.validate.AddGroup;
import com.yuan.common.core.validate.EditGroup;
import com.yuan.workflow.domain.WfBizRef;
import io.github.linpeilie.annotations.AutoMapper;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * wfref业务对象 wf_biz_ref
 *
 * @author yuan
 * @date Mon Dec 29 20:51:33 CST 2025
 */
@Data

@AutoMapper(target = WfBizRef.class, reverseConvertGenerate = false)
public class WfBizRefBo implements Serializable {

    private Long id;

    private String tenantId;

    /**
     * 业务类型，如 LEAVE/REIMBURSE
     */
    @NotBlank(message = "业务类型，如 LEAVE/REIMBURSE不能为空", groups = { AddGroup.class, EditGroup.class })
    private String bizType;
    /**
     * 业务主键
     */
    @NotBlank(message = "业务主键不能为空", groups = { AddGroup.class, EditGroup.class })
    private String bizId;

    @NotBlank(message = "业务单号不能为空", groups = { AddGroup.class, EditGroup.class })
    private String bizNo;
    /**
     * 流程实例ID
     */
    @NotNull(message = "流程实例ID不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long instanceId;
    /**
     * RUNNING/APPROVED/REJECTED/CANCELED
     */
    @NotBlank(message = "RUNNING/APPROVED/REJECTED/CANCELED不能为空", groups = { AddGroup.class, EditGroup.class })
    private String status;
    /**
     * createBy
     */
    private Long createBy;
    /**
     * createdTime
     */
    @NotNull(message = "createdTime不能为空", groups = { AddGroup.class, EditGroup.class })
    private Date createTime;
    /**
     * updatedTime
     */
    @NotNull(message = "updatedTime不能为空", groups = { AddGroup.class, EditGroup.class })
    private Date updateTime;

    private String ref_type;

}
