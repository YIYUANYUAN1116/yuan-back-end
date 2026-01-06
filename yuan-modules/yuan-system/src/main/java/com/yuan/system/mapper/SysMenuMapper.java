package com.yuan.system.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.yuan.common.core.constant.UserConstants;
import com.yuan.core.mapper.BaseMapperPlus;
import com.yuan.system.domain.SysMenu;
import com.yuan.system.domain.vo.SysMenuVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
 * 菜单Mapper接口
 *
 
 * @date Wed Dec 10 17:20:04 CST 2025
 */
@Mapper
public interface SysMenuMapper extends BaseMapperPlus<SysMenu, SysMenuVo> {

    List<SysMenu> selectMenuListByUserId(@Param(Constants.WRAPPER) Wrapper<SysMenu> queryWrapper);

    List<Long> selectMenuListByRoleId(Long roleId, Boolean menuCheckStrictly);

    List<Long> selectMenuListByRoleIds(Long[] roleIds);

    /**
     * 根据用户ID查询菜单
     *
     * @return 菜单列表
     */
    default List<SysMenu> selectMenuTreeAll() {
        LambdaQueryWrapper<SysMenu> lqw = new LambdaQueryWrapper<SysMenu>()
                .in(SysMenu::getMenuType, UserConstants.TYPE_DIR, UserConstants.TYPE_MENU)
                .eq(SysMenu::getStatus, UserConstants.MENU_NORMAL)
                .orderByAsc(SysMenu::getParentId)
                .orderByAsc(SysMenu::getOrderNum);
        return this.selectList(lqw);
    }

    /**
     * 根据用户ID查询菜单
     *
     * @param userId 用户ID
     * @return 菜单列表
     */
    List<SysMenu> selectMenuTreeByRoleUser(Long userId);

    List<String> selectMenuPermsByRoleUser(Long userId);

    List<SysMenu> selectMenuTreeByScope(@Param("scope") String scope);

    Set<Long> selectMenuIdsByUserId(Long userId);

    List<String> selectScopesBymenuIds(Set<Long> menuIds);

    List<SysMenu> selectMenuTreeByPostUser(Long userId);

    Collection<String> selectMenuPermsByPostUser(Long userId);
}
