package com.yuan.system.domain.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.yuan.system.domain.SysRoleMenu;
import io.github.linpeilie.annotations.AutoMapper;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;


/**
 * 角色菜单视图对象 sys_role_menu
 *
 
 * @date Wed Dec 10 17:21:43 CST 2025
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = SysRoleMenu.class)
public class SysRoleMenuVo implements Serializable {
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
    private Long roleId;
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
    private Long menuId;

}
