package com.yuan.system.mapper;

import com.yuan.core.mapper.BaseMapperPlus;
import com.yuan.system.domain.SysUser;
import com.yuan.system.domain.vo.SysUserVo;
import org.apache.ibatis.annotations.Mapper;


/**
 * 用户Mapper接口
 *
 * @author ageerle
 * @date Sun Dec 07 17:25:38 CST 2025
 */
@Mapper
public interface SysUserMapper extends BaseMapperPlus<SysUser, SysUserVo> {

    SysUserVo selectUserByUserName(String username);

    SysUserVo selectTenantUserByUserName(String username, String tenantId);
}
