package com.yuan.system.domain.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import com.yuan.system.domain.SysUserRole;

import java.io.Serializable;


/**
 * 用户角色视图对象 sys_user_role
 *
 * @author ageerle
 * @date Sun Dec 07 17:25:51 CST 2025
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = SysUserRole.class)
public class SysUserRoleVo implements Serializable {

    private Long userId;
    private Long roleId;

}
