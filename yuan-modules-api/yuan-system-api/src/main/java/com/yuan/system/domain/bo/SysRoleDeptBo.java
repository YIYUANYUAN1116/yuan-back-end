package com.yuan.system.domain.bo;

import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import com.yuan.system.domain.SysRoleDept;

import java.io.Serializable;

/**
 * 部门角色业务对象 sys_role_dept
 *
 * @author ageerle
 * @date Wed Dec 10 17:21:37 CST 2025
 */
@Data

@AutoMapper(target = SysRoleDept.class, reverseConvertGenerate = false)
public class SysRoleDeptBo implements Serializable {

    private Long roleId;

    private Long deptId;


}
