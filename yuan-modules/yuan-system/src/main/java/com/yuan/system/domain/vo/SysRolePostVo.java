package com.yuan.system.domain.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.yuan.system.domain.SysRolePost;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;

import java.io.Serializable;


/**
 * sys_role_post视图对象 sys_role_post
 *
 * @author yuan
 * @date Mon Jan 05 20:10:39 CST 2026
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = SysRolePost.class)
public class SysRolePostVo implements Serializable {

    private Long postId;
    private Long roleId;

}
