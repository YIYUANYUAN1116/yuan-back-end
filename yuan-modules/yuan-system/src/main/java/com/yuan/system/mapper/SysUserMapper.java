package com.yuan.system.mapper;

import com.baomidou.mybatisplus.annotation.InterceptorIgnore;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yuan.core.mapper.BaseMapperPlus;
import com.yuan.system.domain.SysUser;
import com.yuan.system.domain.vo.SysUserVo;
import com.yuan.system.dto.SysUserDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;


/**
 * 用户Mapper接口
 *
 
 * @date Sun Dec 07 17:25:38 CST 2025
 */
@Mapper
public interface SysUserMapper extends BaseMapperPlus<SysUser, SysUserVo> {

    SysUserVo selectUserByUserName(String username);

    SysUserVo selectTenantUserByUserName(String username, String tenantId);

    Page<SysUserVo> selectPageUserList(@Param("page") Page<SysUser> page, @Param(Constants.WRAPPER) Wrapper<SysUser> queryWrapper);

    @InterceptorIgnore(tenantLine = "true")
    SysUserVo selectUserByUsernameByLogin(String username);

    List<Long> selectUserIdsByRole(@Param("roleKey") String roleKey, @Param("tenantId") String tenantId);

    List<Long> selectUserIdsByDept(@Param("deptId") Long deptId, @Param("tenantId") String tenantId);

    SysUserDTO selectDtoById(@Param("userId") Long userId);

    List<Long> selectUserIdsBydeptId(@Param("deptId") Long deptId);
}
