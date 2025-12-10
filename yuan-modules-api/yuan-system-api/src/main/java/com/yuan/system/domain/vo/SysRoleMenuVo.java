package com.yuan.system.domain.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import com.yuan.system.domain.SysRoleMenu;

import java.io.Serializable;


/**
 * 角色菜单视图对象 sys_role_menu
 *
 * @author ageerle
 * @date Wed Dec 10 17:21:43 CST 2025
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = SysRoleMenu.class)
public class SysRoleMenuVo implements Serializable {

    private Long roleId;
    private Long menuId;

}
