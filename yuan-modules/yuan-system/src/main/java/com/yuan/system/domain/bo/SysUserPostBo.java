package com.yuan.system.domain.bo;

import com.yuan.system.domain.SysUserPost;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;

import java.io.Serializable;

/**
 * post-user业务对象 sys_user_post
 *
 * @author ageerle
 * @date Mon Dec 22 15:05:48 CST 2025
 */
@Data

@AutoMapper(target = SysUserPost.class, reverseConvertGenerate = false)
public class SysUserPostBo implements Serializable {

    private Long userId;

    private Long postId;


}
