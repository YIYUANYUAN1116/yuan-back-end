package com.yuan.system.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * sys_role_post对象 sys_role_post
 *
 * @author yuan
 * @date Mon Jan 05 20:10:39 CST 2026
 */
@Data
@TableName("sys_role_post")
public class SysRolePost implements Serializable {


    /**
     * 用户ID
     */
    private Long postId;

    /**
     * 岗位ID
     */

    private Long roleId;


}
