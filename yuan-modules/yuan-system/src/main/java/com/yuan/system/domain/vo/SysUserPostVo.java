package com.yuan.system.domain.vo;

    import java.io.Serializable;
import com.yuan.system.domain.SysUserPost;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;

import java.util.Date;


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

    private Long userId;
    private Long postId;

}
