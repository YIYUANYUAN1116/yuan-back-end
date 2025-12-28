package com.yuan.workflow.domain.bo;

import com.yuan.common.core.validate.AddGroup;
import com.yuan.common.core.validate.EditGroup;
import io.github.linpeilie.annotations.AutoMapper;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import com.yuan.workflow.domain.WfCc;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * wfcc业务对象 wf_cc
 *
 
 * @date Sun Dec 28 11:26:25 CST 2025
 */
@Data

@AutoMapper(target = WfCc.class, reverseConvertGenerate = false)
public class WfCcBo implements Serializable {

    private Long id;

    /**
     * 租户ID
     */
    @NotNull(message = "租户ID不能为空", groups = { AddGroup.class, EditGroup.class })
    private String tenantId;
    /**
     * 流程实例ID
     */
    @NotNull(message = "流程实例ID不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long instanceId;
    /**
     * 被抄送人
     */
    @NotNull(message = "被抄送人不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long userId;
    /**
     * 是否已读(0未读 1已读)
     */
    private String readFlag;
    /**
     * createTime
     */
    private LocalDateTime createTime;

}
