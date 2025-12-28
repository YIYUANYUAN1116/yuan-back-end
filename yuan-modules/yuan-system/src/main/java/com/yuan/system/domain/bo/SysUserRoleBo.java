package com.yuan.system.domain.bo;

import com.yuan.system.domain.SysUserRole;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;

import java.io.Serializable;

/**
 * 用户角色业务对象 sys_user_role
 *
 
 * @date Sun Dec 07 17:25:51 CST 2025
 */
@Data

@AutoMapper(target = SysUserRole.class, reverseConvertGenerate = false)
public class SysUserRoleBo implements Serializable {

    private Long userId;

    private Long roleId;


}
