package com.yuan.system.domain.bo;

import com.yuan.system.domain.SysRolePost;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;

import java.io.Serializable;

/**
 * sys_role_post业务对象 sys_role_post
 *
 * @author yuan
 * @date Mon Jan 05 20:10:39 CST 2026
 */
@Data

@AutoMapper(target = SysRolePost.class, reverseConvertGenerate = false)
public class SysRolePostBo implements Serializable {

    private Long postId;

    private Long roleId;


}
