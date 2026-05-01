package com.yuan.ai.domain;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import java.time.LocalDateTime;
import java.io.Serializable;

/**
 * 知识库授权表对象 kb_base_auth
 *
 * @author yuan
 * @date Fri May 01 16:08:26 CST 2026
 */
@Data
@TableName("kb_base_auth")
public class KbBaseAuth implements Serializable {


    /**
     * 授权ID
     */
        @TableId(value = "authId", type = IdType.AUTO)
    private Long authId;

    /**
     * 租户ID
     */
    private String tenantId;

    /**
     * 知识库ID
     */
    private Long kbId;

    /**
     * 授权对象类型：USER-用户，ROLE-角色，DEPT-部门，TENANT-租户
     */
    private String subjectType;

    /**
     * 授权对象ID
     */
    private String subjectId;

    /**
     * 权限：READ-读取，WRITE-编辑，MANAGE-管理
     */
    private String permission;

    /**
     * 状态：ENABLED-启用，DISABLED-禁用
     */
    private String status;

    /**
     * 创建人
     */
    private String createBy;

    /**
     * 创建时间
     */
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
        @TableLogic
    private String delFlag;


}
