package com.yuan.system.mapper;

import com.yuan.system.domain.SysUserPost;
import com.yuan.system.domain.vo.SysUserPostVo;
import com.yuan.core.mapper.BaseMapperPlus;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * post-userMapper接口
 *

 * @date Mon Dec 22 15:05:48 CST 2025
 */
@Mapper
public interface SysUserPostMapper extends BaseMapperPlus<SysUserPost, SysUserPostVo> {

    List<Long> selectUserIdsByPostId(@Param("postId") Long postId);
}
