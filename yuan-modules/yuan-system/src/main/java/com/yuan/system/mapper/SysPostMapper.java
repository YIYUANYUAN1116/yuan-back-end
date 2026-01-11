package com.yuan.system.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yuan.common.core.domain.model.SelectModel;
import com.yuan.system.domain.SysPost;
import com.yuan.system.domain.SysUser;
import com.yuan.system.domain.vo.SysPostVo;
import com.yuan.core.mapper.BaseMapperPlus;
import com.yuan.system.domain.vo.SysUserVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * postMapper接口
 *
 
 * @date Mon Dec 22 15:05:40 CST 2025
 */
@Mapper
public interface SysPostMapper extends BaseMapperPlus<SysPost, SysPostVo> {

    List<SysPostVo> selectPostsByUserId(Long userId);

    Page<SysUserVo> selectAllocatedUserList(@Param("page") Page<SysUser> page, @Param(Constants.WRAPPER) QueryWrapper<SysUser> wrapper);

    Page<SysPostVo> selectPagePostList(@Param("page") Page<Object> build, @Param(Constants.WRAPPER) QueryWrapper<SysPost> wrapper);

    List<SysPostVo> selectPostList( @Param(Constants.WRAPPER) QueryWrapper<SysPost> wrapper);

    List<SysPostVo> selectByUserId(@Param("userId") Long userId);

    List<SysPostVo> selectByDeptId(@Param("deptId") Long deptId);

    List<SelectModel> sysPostSelect();
}
