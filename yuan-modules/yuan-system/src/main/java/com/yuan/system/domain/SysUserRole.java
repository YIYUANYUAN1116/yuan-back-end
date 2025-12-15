package com.yuan.system.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * 用户角色对象 sys_user_role
 *
 * @author ageerle
 * @date Sun Dec 07 17:25:51 CST 2025
 */
@Data
@TableName("sys_user_role")
public class SysUserRole implements Serializable {


    /**
     * 用户ID
     */
    @TableId(value = "user_id", type = IdType.INPUT)
    private Long userId;

    /**
     * 角色ID
     */
    private Long roleId;


}
