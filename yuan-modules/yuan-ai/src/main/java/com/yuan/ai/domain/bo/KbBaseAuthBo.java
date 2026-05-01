package com.yuan.ai.domain.bo;

import com.yuan.ai.domain.KbBaseAuth;
import com.yuan.core.domain.BaseEntity;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import lombok.EqualsAndHashCode;
import jakarta.validation.constraints.*;
import java.time.LocalDateTime;
import java.io.Serializable;
import com.yuan.common.core.validate.AddGroup;
import com.yuan.common.core.validate.EditGroup;
import java.io.Serializable;
import java.io.Serializable;
import com.yuan.common.core.validate.AddGroup;
import com.yuan.common.core.validate.EditGroup;

/**
 * 知识库授权表业务对象 kb_base_auth
 *
 * @author yuan
 * @date Fri May 01 16:08:26 CST 2026
 */
@Data

@AutoMapper(target = KbBaseAuth.class, reverseConvertGenerate = false)
public class KbBaseAuthBo implements Serializable {

    private Long authId;

    /**
     * 租户ID
     */
    private String tenantId;
    /**
     * 知识库ID
     */
    @NotNull(message = "知识库ID不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long kbId;
    /**
     * 授权对象类型：USER-用户，ROLE-角色，DEPT-部门，TENANT-租户
     */
    @NotBlank(message = "授权对象类型：USER-用户，ROLE-角色，DEPT-部门，TENANT-租户不能为空", groups = { AddGroup.class, EditGroup.class })
    private String subjectType;
    /**
     * 授权对象ID
     */
    @NotBlank(message = "授权对象ID不能为空", groups = { AddGroup.class, EditGroup.class })
    private String subjectId;
    /**
     * 权限：READ-读取，WRITE-编辑，MANAGE-管理
     */
    @NotBlank(message = "权限：READ-读取，WRITE-编辑，MANAGE-管理不能为空", groups = { AddGroup.class, EditGroup.class })
    private String permission;
    /**
     * 状态：ENABLED-启用，DISABLED-禁用
     */
    @NotBlank(message = "状态：ENABLED-启用，DISABLED-禁用不能为空", groups = { AddGroup.class, EditGroup.class })
    private String status;
    /**
     * 创建人
     */
    private String createBy;
    /**
     * 创建时间
     */
    @NotNull(message = "创建时间不能为空", groups = { AddGroup.class, EditGroup.class })
    private LocalDateTime createTime;
    /**
     * 更新人
     */
    private String updateBy;
    /**
     * 更新时间
     */
    private LocalDateTime updateTime;
    /**
     * 逻辑删除：0-未删除，2-已删除
     */
    @NotBlank(message = "逻辑删除：0-未删除，2-已删除不能为空", groups = { AddGroup.class, EditGroup.class })
    private String delFlag;

}
