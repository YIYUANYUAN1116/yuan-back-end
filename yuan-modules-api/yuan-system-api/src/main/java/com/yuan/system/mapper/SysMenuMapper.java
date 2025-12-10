package com.yuan.system.mapper;

import org.apache.ibatis.annotations.Mapper;
import com.yuan.core.mapper.BaseMapperPlus;
import com.yuan.system.domain.SysMenu;
import com.yuan.system.domain.vo.SysMenuVo;

/**
 * 菜单Mapper接口
 *
 * @author ageerle
 * @date Wed Dec 10 17:20:04 CST 2025
 */
@Mapper
public interface SysMenuMapper extends BaseMapperPlus<SysMenu, SysMenuVo> {

}
