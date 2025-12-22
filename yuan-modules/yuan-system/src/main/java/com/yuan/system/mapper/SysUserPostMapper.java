package com.yuan.system.mapper;

import com.yuan.system.domain.SysUserPost;
import com.yuan.system.domain.vo.SysUserPostVo;
import com.yuan.core.mapper.BaseMapperPlus;
import org.apache.ibatis.annotations.Mapper;

/**
 * post-userMapper接口
 *
 * @author ageerle
 * @date Mon Dec 22 15:05:48 CST 2025
 */
@Mapper
public interface SysUserPostMapper extends BaseMapperPlus<SysUserPost, SysUserPostVo> {

}
