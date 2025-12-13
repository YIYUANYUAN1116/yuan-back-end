package com.yuan.system.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.lang.tree.Tree;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yuan.common.core.utils.CommonTreeUtils;
import com.yuan.common.core.utils.MapstructUtils;
import com.yuan.common.core.utils.StringUtils;
import com.yuan.common.core.utils.TreeBuildUtils;
import com.yuan.common.satoken.utils.LoginHelper;
import com.yuan.core.page.PageQuery;
import com.yuan.core.page.TableDataInfo;
import com.yuan.system.domain.SysMenu;
import com.yuan.system.domain.SysRole;
import com.yuan.system.domain.bo.SysMenuBo;
import com.yuan.system.domain.vo.SysMenuVo;
import com.yuan.system.mapper.SysMenuMapper;
import com.yuan.system.mapper.SysRoleMapper;
import com.yuan.system.mapper.SysUserRoleMapper;
import com.yuan.system.service.SysMenuService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 菜单Service业务层处理
 *
 * @author ageerle
 * @date Wed Dec 10 17:20:04 CST 2025
 */
@RequiredArgsConstructor
@Service
public class SysMenuServiceImpl implements SysMenuService {

    private final SysMenuMapper baseMapper;

    private final SysRoleMapper roleMapper;

    private final SysUserRoleMapper sysUserRoleMapper;

    /**
     * 查询菜单
     */
    @Override
    public SysMenuVo queryById(Long menuId) {
        return baseMapper.selectVoById(menuId);
    }

    /**
     * 查询菜单列表
     */
    @Override
    public TableDataInfo<SysMenuVo> queryPageList(SysMenuBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<SysMenu> lqw = buildQueryWrapper(bo);
        Page<SysMenuVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }

    /**
     * 查询菜单列表
     */
    @Override
    public List<SysMenuVo> queryList(SysMenuBo bo) {
        LambdaQueryWrapper<SysMenu> lqw = buildQueryWrapper(bo);
        return baseMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<SysMenu> buildQueryWrapper(SysMenuBo bo) {
        LambdaQueryWrapper<SysMenu> lqw = Wrappers.lambdaQuery();
        lqw.eq(bo.getMenuId() != null, SysMenu::getMenuId, bo.getMenuId());
        lqw.eq(StringUtils.isNotBlank(bo.getMenuName()), SysMenu::getMenuName, bo.getMenuName());
        lqw.eq(bo.getParentId() != null, SysMenu::getParentId, bo.getParentId());
        lqw.eq(bo.getOrderNum() != null, SysMenu::getOrderNum, bo.getOrderNum());
        lqw.eq(StringUtils.isNotBlank(bo.getPath()), SysMenu::getPath, bo.getPath());
        lqw.eq(StringUtils.isNotBlank(bo.getComponent()), SysMenu::getComponent, bo.getComponent());
        lqw.eq(StringUtils.isNotBlank(bo.getQueryParam()), SysMenu::getQueryParam, bo.getQueryParam());
        lqw.eq(bo.getIsFrame() != null, SysMenu::getIsFrame, bo.getIsFrame());
        lqw.eq(bo.getIsCache() != null, SysMenu::getIsCache, bo.getIsCache());
        lqw.eq(StringUtils.isNotBlank(bo.getMenuType()), SysMenu::getMenuType, bo.getMenuType());
        lqw.eq(StringUtils.isNotBlank(bo.getVisible()), SysMenu::getVisible, bo.getVisible());
        lqw.eq(StringUtils.isNotBlank(bo.getStatus()), SysMenu::getStatus, bo.getStatus());
        lqw.eq(StringUtils.isNotBlank(bo.getPerms()), SysMenu::getPerms, bo.getPerms());
        lqw.eq(StringUtils.isNotBlank(bo.getIcon()), SysMenu::getIcon, bo.getIcon());
        lqw.eq(bo.getCreateDept() != null, SysMenu::getCreateDept, bo.getCreateDept());
        lqw.eq(bo.getCreateBy() != null, SysMenu::getCreateBy, bo.getCreateBy());
        lqw.eq(bo.getCreateTime() != null, SysMenu::getCreateTime, bo.getCreateTime());
        lqw.eq(bo.getUpdateBy() != null, SysMenu::getUpdateBy, bo.getUpdateBy());
        lqw.eq(bo.getUpdateTime() != null, SysMenu::getUpdateTime, bo.getUpdateTime());
        lqw.eq(StringUtils.isNotBlank(bo.getRemark()), SysMenu::getRemark, bo.getRemark());
        return lqw;
    }

    /**
     * 新增菜单
     */
    @Override
    public Boolean insertByBo(SysMenuBo bo) {
        SysMenu add = MapstructUtils.convert(bo, SysMenu.class);
        validEntityBeforeSave(add);
        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            bo.setMenuId(add.getMenuId());
        }
        return flag;
    }

    /**
     * 修改菜单
     */
    @Override
    public Boolean updateByBo(SysMenuBo bo) {
        SysMenu update = MapstructUtils.convert(bo, SysMenu.class);
        validEntityBeforeSave(update);
        return baseMapper.updateById(update) > 0;
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(SysMenu entity) {
        //TODO 做一些数据校验,如唯一约束
    }

    /**
     * 批量删除菜单
     */
    @Override
    public Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid) {
        if (isValid) {
            //TODO 做一些业务上的校验,判断是否需要校验
        }
        return baseMapper.deleteBatchIds(ids) > 0;
    }

    @Override
    public List<SysMenuVo> selectMenuList(SysMenuBo menu, Long userId) {

        List<SysMenuVo> menuList;
        // 管理员显示所有菜单信息
        if (LoginHelper.isSuperAdmin(userId)) {
            menuList = baseMapper.selectVoList(new LambdaQueryWrapper<SysMenu>()
                    .like(StringUtils.isNotBlank(menu.getMenuName()), SysMenu::getMenuName, menu.getMenuName())
                    .eq(StringUtils.isNotBlank(menu.getVisible()), SysMenu::getVisible, menu.getVisible())
                    .eq(StringUtils.isNotBlank(menu.getStatus()), SysMenu::getStatus, menu.getStatus())
                    .in(menu.getMenuTypes()!=null && !menu.getMenuTypes().isEmpty(), SysMenu::getMenuType, menu.getMenuTypes())
                    .orderByAsc(SysMenu::getParentId)
                    .orderByAsc(SysMenu::getOrderNum));
        } else {
            QueryWrapper<SysMenu> wrapper = Wrappers.query();
            wrapper.eq("sur.user_id", userId)
                    .like(StringUtils.isNotBlank(menu.getMenuName()), "m.menu_name", menu.getMenuName())
                    .eq(StringUtils.isNotBlank(menu.getVisible()), "m.visible", menu.getVisible())
                    .eq(StringUtils.isNotBlank(menu.getStatus()), "m.status", menu.getStatus())
                    .in(menu.getMenuTypes() != null && !menu.getMenuTypes().isEmpty(),
                            "m.menu_type", menu.getMenuTypes())
                    .orderByAsc("m.parent_id")
                    .orderByAsc("m.order_num");
            List<SysMenu> list = baseMapper.selectMenuListByUserId(wrapper);
            menuList = MapstructUtils.convert(list, SysMenuVo.class);
        }
        return menuList;
    }

    @Override
    public List<SysMenuVo> selectMenuList(Long userId) {
        return selectMenuList(new SysMenuBo(), userId);
    }

    @Override
    public List<Tree<Long>> buildMenuTreeSelect(List<SysMenuVo> menus) {
        if (CollUtil.isEmpty(menus)) {
            return CollUtil.newArrayList();
        }
        return TreeBuildUtils.build(menus, (menu, tree) ->
                tree.setId(menu.getMenuId())
                        .setParentId(menu.getParentId())
                        .setName(menu.getMenuName())
                        .setWeight(menu.getOrderNum()));
    }

    @Override
    public List<Long> selectMenuListByRoleId(Long roleId) {
        if (roleId == null) return null;
        SysRole role = roleMapper.selectById(roleId);
        if (role == null ) return null;
        return baseMapper.selectMenuListByRoleId(roleId, role.getMenuCheckStrictly());
    }

    @Override
    public List<Long> selectMenuListByUserId(Long userId) {
        if (userId == null) return null;
        Long[] roleIds = sysUserRoleMapper.selectRoleIdsByUserId(userId);
        if (roleIds == null || roleIds.length == 0) return null;
        return baseMapper.selectMenuListByRoleIds(roleIds);
    }

    @Override
    public List<SysMenuVo> listTree(SysMenuBo bo) {
        // 1. 查询匹配的菜单（根据menuName）
        LambdaQueryWrapper<SysMenu> lqw = buildQueryWrapper(bo);
        List<SysMenuVo> matchedMenus = baseMapper.selectVoList(lqw);

        if (matchedMenus.isEmpty()) {
            return Collections.emptyList();
        }

        // 2. 查询所有菜单
        List<SysMenuVo> allMenus = baseMapper.selectVoList(new LambdaQueryWrapper<>());


        List<SysMenuVo> resultMenus = null;
        if (!Objects.equals(allMenus.size(),matchedMenus.size())) {
            // 3. 构建子菜单集合（只保留匹配的菜单及其子菜单）
            Set<Long> includedIds = new HashSet<>();
            for (SysMenuVo menu : matchedMenus) {
                collectChildren(menu.getMenuId(), allMenus, includedIds);
            }

            resultMenus = allMenus.stream()
                    .filter(m -> includedIds.contains(m.getMenuId()))
                    .collect(Collectors.toList());
        }

        resultMenus = resultMenus==null?matchedMenus:resultMenus;
        // 构建树
        return CommonTreeUtils.buildTree(
                resultMenus,
                SysMenuVo::getMenuId,      // id 获取器
                SysMenuVo::getParentId,    // parentId 获取器
                SysMenuVo::setChildren,    // children 设置器
                0L                       // 根节点 parentId = 0
        );
    }

    // 递归收集子菜单
    private void collectChildren(Long parentId, List<SysMenuVo> allMenus, Set<Long> includedIds) {
        includedIds.add(parentId);
        for (SysMenuVo menu : allMenus) {
            if (parentId.equals(menu.getParentId()) && !includedIds.contains(menu.getMenuId())) {
                collectChildren(menu.getMenuId(), allMenus, includedIds);
            }
        }
    }
}
