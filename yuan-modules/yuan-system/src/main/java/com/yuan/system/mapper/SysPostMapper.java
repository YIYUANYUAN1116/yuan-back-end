package com.yuan.system.mapper;

import com.yuan.system.domain.SysPost;
import com.yuan.system.domain.vo.SysPostVo;
import com.yuan.core.mapper.BaseMapperPlus;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * postMapper接口
 *
 
 * @date Mon Dec 22 15:05:40 CST 2025
 */
@Mapper
public interface SysPostMapper extends BaseMapperPlus<SysPost, SysPostVo> {

    List<SysPostVo> selectPostsByUserId(Long userId);
}
