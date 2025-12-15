package com.yuan.system.domain.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.yuan.system.domain.SysRoleDept;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;

import java.io.Serializable;


/**
 * 部门角色视图对象 sys_role_dept
 *
 * @author ageerle
 * @date Wed Dec 10 17:21:37 CST 2025
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = SysRoleDept.class)
public class SysRoleDeptVo implements Serializable {

    private Long roleId;
    private Long deptId;

}
