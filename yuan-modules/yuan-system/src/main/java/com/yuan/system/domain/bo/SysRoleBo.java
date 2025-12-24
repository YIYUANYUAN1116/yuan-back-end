package com.yuan.system.domain.bo;

import com.yuan.common.core.validate.AddGroup;
import com.yuan.common.core.validate.EditGroup;
import com.yuan.system.domain.SysRole;
import io.github.linpeilie.annotations.AutoMapper;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 角色业务对象 sys_role
 *
 * @author ageerle
 * @date Sun Dec 07 17:25:44 CST 2025
 */
@Data

@AutoMapper(target = SysRole.class, reverseConvertGenerate = false)
public class SysRoleBo implements Serializable {

    private Long roleId;

    /**
     * 租户编号
     */
    private String tenantId;
    /**
     * 角色名称
     */
    @NotBlank(message = "角色名称不能为空", groups = { AddGroup.class, EditGroup.class })
    private String roleName;
    /**
     * 角色权限字符串
     */
    @NotBlank(message = "角色权限字符串不能为空", groups = { AddGroup.class, EditGroup.class })
    private String roleKey;
    /**
     * 显示顺序
     */
    @NotNull(message = "显示顺序不能为空", groups = { AddGroup.class, EditGroup.class })
    private Integer roleSort;
    /**
     * 数据范围（1：全部数据权限 2：自定数据权限 3：本部门数据权限 4：本部门及以下数据权限）
     */
    private String dataScope;
    /**
     * 菜单树选择项是否关联显示
     */
    private Boolean menuCheckStrictly;
    /**
     * 部门树选择项是否关联显示
     */
    private Boolean deptCheckStrictly;
    /**
     * 角色状态（0正常 1停用）
     */
    @NotBlank(message = "角色状态（0正常 1停用）不能为空", groups = { AddGroup.class, EditGroup.class })
    private String status;
    /**
     * 删除标志（0代表存在 2代表删除）
     */
    private String delFlag;
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

    /**
     * 菜单组
     */
    private Long[] menuIds;
    private List<Long> menuIdList;
    private String[] menuNames;
    private BigDecimal amount;
}
