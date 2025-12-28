package com.yuan.system.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * 角色菜单对象 sys_role_menu
 *
 
 * @date Wed Dec 10 17:21:43 CST 2025
 */
@Data
@TableName("sys_role_menu")
public class SysRoleMenu implements Serializable {


    /**
     * 角色ID
     */
    @TableId(value = "role_id",type = IdType.INPUT)
    private Long roleId;

    /**
     * 菜单ID
     */

    private Long menuId;


}
