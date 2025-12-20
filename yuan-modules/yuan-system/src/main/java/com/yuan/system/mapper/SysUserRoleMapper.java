package com.yuan.system.mapper;

import com.yuan.core.mapper.BaseMapperPlus;
import com.yuan.system.domain.SysUserRole;
import com.yuan.system.domain.vo.SysUserRoleVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;


/**
 * 用户角色Mapper接口
 *
 * @author ageerle
 * @date Sun Dec 07 17:25:51 CST 2025
 */
@Mapper
public interface SysUserRoleMapper extends BaseMapperPlus<SysUserRole, SysUserRoleVo> {

    Long[] selectRoleIdsByUserId(Long userId);

    List<Long> selectUserIdsByRoleId(Long roleId);
}
