package com.yuan.system.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yuan.core.mapper.BaseMapperPlus;
import com.yuan.system.domain.SysRole;
import com.yuan.system.domain.SysUser;
import com.yuan.system.domain.vo.SysRoleVo;
import com.yuan.system.domain.vo.SysUserVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;


/**
 * 角色Mapper接口
 *
 * @author ageerle
 * @date Sun Dec 07 17:25:44 CST 2025
 */
@Mapper
public interface SysRoleMapper extends BaseMapperPlus<SysRole, SysRoleVo> {

    List<SysRoleVo> selectRolesByUserId(Long userId);

    Page<SysUserVo> selectAllocatedUserList(@Param("page") Page<SysUser> page,@Param(Constants.WRAPPER) QueryWrapper<SysUser> wrapper);

    Page<SysUserVo> selectUnallocatedUserList(@Param("page") Page<SysUser> page,@Param(Constants.WRAPPER) QueryWrapper<SysUser> wrapper);

    List<SysRoleVo> selectRolePermissionByUserId(Long userId);
}
