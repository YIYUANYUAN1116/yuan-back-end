package com.yuan.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.RequiredArgsConstructor;
import com.yuan.common.core.utils.MapstructUtils;
import com.yuan.core.page.PageQuery;
import com.yuan.core.page.TableDataInfo;
import com.yuan.system.domain.SysRoleMenu;
import com.yuan.system.domain.bo.SysRoleMenuBo;
import com.yuan.system.domain.vo.SysRoleMenuVo;
import com.yuan.system.mapper.SysRoleMenuMapper;
import com.yuan.system.service.SysRoleMenuService;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

/**
 * 角色菜单Service业务层处理
 *
 * @author ageerle
 * @date Wed Dec 10 17:21:43 CST 2025
 */
@RequiredArgsConstructor
@Service
public class SysRoleMenuServiceImpl implements SysRoleMenuService {

    private final SysRoleMenuMapper baseMapper;

    /**
     * 查询角色菜单
     */
    @Override
    public SysRoleMenuVo queryById(Long menuId) {
        return baseMapper.selectVoById(menuId);
    }

        /**
         * 查询角色菜单列表
         */
        @Override
        public TableDataInfo<SysRoleMenuVo> queryPageList(SysRoleMenuBo bo, PageQuery pageQuery) {
            LambdaQueryWrapper<SysRoleMenu> lqw = buildQueryWrapper(bo);
            Page<SysRoleMenuVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
            return TableDataInfo.build(result);
        }

    /**
     * 查询角色菜单列表
     */
    @Override
    public List<SysRoleMenuVo> queryList(SysRoleMenuBo bo) {
        LambdaQueryWrapper<SysRoleMenu> lqw = buildQueryWrapper(bo);
        return baseMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<SysRoleMenu> buildQueryWrapper(SysRoleMenuBo bo) {
        LambdaQueryWrapper<SysRoleMenu> lqw = Wrappers.lambdaQuery();
                    lqw.eq(bo.getRoleId() != null, SysRoleMenu::getRoleId, bo.getRoleId());
                    lqw.eq(bo.getMenuId() != null, SysRoleMenu::getMenuId, bo.getMenuId());
        return lqw;
    }

    /**
     * 新增角色菜单
     */
    @Override
    public Boolean insertByBo(SysRoleMenuBo bo) {
        SysRoleMenu add = MapstructUtils.convert(bo, SysRoleMenu. class);
        validEntityBeforeSave(add);
        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            bo.setMenuId(add.getMenuId());
        }
        return flag;
    }

    /**
     * 修改角色菜单
     */
    @Override
    public Boolean updateByBo(SysRoleMenuBo bo) {
        SysRoleMenu update = MapstructUtils.convert(bo, SysRoleMenu. class);
        validEntityBeforeSave(update);
        return baseMapper.updateById(update) > 0;
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(SysRoleMenu entity) {
        //TODO 做一些数据校验,如唯一约束
    }

    /**
     * 批量删除角色菜单
     */
    @Override
    public Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid) {
        if (isValid) {
            //TODO 做一些业务上的校验,判断是否需要校验
        }
        return baseMapper.deleteBatchIds(ids) > 0;
    }
}
