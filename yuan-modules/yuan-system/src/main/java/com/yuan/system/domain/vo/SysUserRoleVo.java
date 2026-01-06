package com.yuan.system.domain.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.yuan.system.domain.SysUserRole;
import io.github.linpeilie.annotations.AutoMapper;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;


/**
 * 用户角色视图对象 sys_user_role
 *
 
 * @date Sun Dec 07 17:25:51 CST 2025
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = SysUserRole.class)
public class SysUserRoleVo implements Serializable {
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
    private Long userId;
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
    private Long roleId;

    private Boolean  isPrimary;
}
