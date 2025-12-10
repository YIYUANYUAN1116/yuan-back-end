package com.yuan.system.domain.bo;

import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import com.yuan.system.domain.SysRoleMenu;

import java.io.Serializable;

/**
 * 角色菜单业务对象 sys_role_menu
 *
 * @author ageerle
 * @date Wed Dec 10 17:21:43 CST 2025
 */
@Data

@AutoMapper(target = SysRoleMenu.class, reverseConvertGenerate = false)
public class SysRoleMenuBo implements Serializable {

    private Long roleId;

    private Long menuId;


}
