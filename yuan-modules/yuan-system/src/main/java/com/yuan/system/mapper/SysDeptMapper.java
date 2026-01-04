package com.yuan.system.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yuan.core.mapper.BaseMapperPlus;
import com.yuan.system.domain.SysDept;
import com.yuan.system.domain.SysUser;
import com.yuan.system.domain.vo.SysDeptVo;
import com.yuan.system.domain.vo.SysUserVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;


/**
 * 部门Mapper接口
 *
 
 * @date Wed Dec 10 17:08:31 CST 2025
 */
@Mapper
public interface SysDeptMapper extends BaseMapperPlus<SysDept, SysDeptVo> {
    Page<SysUserVo> selectAllocatedUserList(@Param("page") Page<SysUser> page, @Param(Constants.WRAPPER) QueryWrapper<SysUser> wrapper);

    Page<SysUserVo> selectUnallocatedUserList(@Param("page") Page<SysUser> page, @Param(Constants.WRAPPER) QueryWrapper<SysUser> wrapper);

    Page<SysDeptVo> selectPageDeptList(@Param("page") Page<SysDept> page, @Param(Constants.WRAPPER) QueryWrapper<SysDept> wrapper);

    List<SysDeptVo> selectDeptList(@Param(Constants.WRAPPER) QueryWrapper<SysDept> wrapper);

    SysDept selectByUserId(@Param("userId") Long userId);
}
