package com.yuan.system.mapper;

import com.yuan.system.domain.SysRoleMenu;
import org.apache.ibatis.annotations.Mapper;
import com.yuan.core.mapper.BaseMapperPlus;
import com.yuan.system.domain.vo.SysRoleMenuVo;

/**
 * 角色菜单Mapper接口
 *
 
 * @date Wed Dec 10 17:21:43 CST 2025
 */
@Mapper
public interface SysRoleMenuMapper extends BaseMapperPlus<SysRoleMenu, SysRoleMenuVo> {

}
