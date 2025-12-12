package com.yuan.system.mapper;

import com.yuan.core.mapper.BaseMapperPlus;
import com.yuan.system.domain.SysRole;
import com.yuan.system.domain.vo.SysRoleVo;
import org.apache.ibatis.annotations.Mapper;

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

}
