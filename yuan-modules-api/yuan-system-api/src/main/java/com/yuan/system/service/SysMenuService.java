package com.yuan.system.service;

import cn.hutool.core.lang.tree.Tree;
import com.yuan.core.page.PageQuery;
import com.yuan.core.page.TableDataInfo;
import com.yuan.system.domain.bo.SysMenuBo;
import com.yuan.system.domain.vo.SysMenuVo;

import java.util.Collection;
import java.util.List;

/**
 * 菜单Service接口
 *
 * @author ageerle
 * @date Wed Dec 10 17:20:04 CST 2025
 */
public interface SysMenuService {

    /**
     * 查询菜单
     */
        SysMenuVo queryById(Long menuId);

        /**
         * 查询菜单列表
         */
        TableDataInfo<SysMenuVo> queryPageList(SysMenuBo bo, PageQuery pageQuery);

    /**
     * 查询菜单列表
     */
    List<SysMenuVo> queryList(SysMenuBo bo);

    /**
     * 新增菜单
     */
    Boolean insertByBo(SysMenuBo bo);

    /**
     * 修改菜单
     */
    Boolean updateByBo(SysMenuBo bo);

    /**
     * 校验并批量删除菜单信息
     */
    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);

    List<SysMenuVo> selectMenuList(SysMenuBo menu, Long userId);

    List<SysMenuVo> selectMenuList(Long userId);

    List<Tree<Long>> buildMenuTreeSelect(List<SysMenuVo> menus);

    List<Long> selectMenuListByRoleId(Long roleId);

    List<Long> selectMenuListByUserId(Long userId);
}
