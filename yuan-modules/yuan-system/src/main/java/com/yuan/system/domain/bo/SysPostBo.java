package com.yuan.system.domain.bo;

import com.yuan.common.core.validate.AddGroup;
import com.yuan.common.core.validate.EditGroup;
import com.yuan.system.domain.SysPost;
import io.github.linpeilie.annotations.AutoMapper;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * post业务对象 sys_post
 *
 
 * @date Mon Dec 22 15:05:40 CST 2025
 */
@Data

@AutoMapper(target = SysPost.class, reverseConvertGenerate = false)
public class SysPostBo implements Serializable {

    private Long postId;

    /**
     * 租户编号
     */
    private String tenantId;
    /**
     * 岗位编码
     */
    @NotBlank(message = "岗位编码不能为空", groups = { AddGroup.class, EditGroup.class })
    private String postCode;
    /**
     * 岗位名称
     */
    @NotBlank(message = "岗位名称不能为空", groups = { AddGroup.class, EditGroup.class })
    private String postName;
    /**
     * 显示顺序
     */
    @NotNull(message = "显示顺序不能为空", groups = { AddGroup.class, EditGroup.class })
    private Integer postSort;
    /**
     * 状态（0正常 1停用）
     */
    @NotBlank(message = "状态（0正常 1停用）不能为空", groups = { AddGroup.class, EditGroup.class })
    private String status;

    @NotNull(message = "所属部门不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long deptId;

    /**
     * 创建部门
     */
    private Long createDept;
    /**
     * 创建者
     */
    private Long createBy;
    /**
     * 创建时间
     */
    private LocalDateTime createTime;
    /**
     * 更新者
     */
    private Long updateBy;
    /**
     * 更新时间
     */
    private LocalDateTime updateTime;
    /**
     * 备注
     */
    private String remark;


    private String delFlag;
}
