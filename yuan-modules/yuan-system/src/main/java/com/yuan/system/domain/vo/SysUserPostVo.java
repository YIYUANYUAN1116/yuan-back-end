package com.yuan.system.domain.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.yuan.system.domain.SysUserPost;
import io.github.linpeilie.annotations.AutoMapper;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;


/**
 * post-user视图对象 sys_user_post
 *
 * @author ageerle
 * @date Mon Dec 22 15:05:48 CST 2025
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = SysUserPost.class)
public class SysUserPostVo implements Serializable {
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
    private Long userId;
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
    private Long postId;

}
