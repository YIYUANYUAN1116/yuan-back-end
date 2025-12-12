package com.yuan.system.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import org.apache.ibatis.annotations.Mapper;
import com.yuan.core.mapper.BaseMapperPlus;
import com.yuan.system.domain.SysMenu;
import com.yuan.system.domain.vo.SysMenuVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 菜单Mapper接口
 *
 * @author ageerle
 * @date Wed Dec 10 17:20:04 CST 2025
 */
@Mapper
public interface SysMenuMapper extends BaseMapperPlus<SysMenu, SysMenuVo> {

    List<SysMenu> selectMenuListByUserId(@Param(Constants.WRAPPER) Wrapper<SysMenu> queryWrapper);

    List<Long> selectMenuListByRoleId(Long roleId, Boolean menuCheckStrictly);
}
