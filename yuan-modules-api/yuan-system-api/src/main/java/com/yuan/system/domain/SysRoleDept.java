package com.yuan.system.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * 部门角色对象 sys_role_dept
 *
 * @author ageerle
 * @date Wed Dec 10 17:21:37 CST 2025
 */
@Data
@TableName("sys_role_dept")
public class SysRoleDept implements Serializable {


    /**
     * 角色ID
     */
    @TableId(type = IdType.INPUT)
    private Long roleId;


    private Long deptId;


}
