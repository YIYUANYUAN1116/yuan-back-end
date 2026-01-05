package com.yuan.system.mapper;

import com.yuan.core.mapper.BaseMapperPlus;
import com.yuan.system.domain.SysRolePost;
import com.yuan.system.domain.vo.SysRolePostVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * sys_role_postMapper接口
 *
 * @author yuan
 * @date Mon Jan 05 20:10:39 CST 2026
 */
@Mapper
public interface SysRolePostMapper extends BaseMapperPlus<SysRolePost, SysRolePostVo> {

    Long[] selectRoleIdsByPostId(@Param("postId") Long postId);
}
