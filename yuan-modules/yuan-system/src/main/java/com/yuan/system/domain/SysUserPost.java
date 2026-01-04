package com.yuan.system.domain;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import java.io.Serializable;

/**
 * post-user对象 sys_user_post
 *

 * @date Mon Dec 22 15:05:48 CST 2025
 */
@Data
@TableName("sys_user_post")
public class SysUserPost implements Serializable {


    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 岗位ID
     */
    private Long postId;

    private Boolean  isPrimary;

}
