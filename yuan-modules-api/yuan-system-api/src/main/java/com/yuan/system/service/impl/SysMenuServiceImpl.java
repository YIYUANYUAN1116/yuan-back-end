package com.yuan.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.RequiredArgsConstructor;
import com.yuan.common.core.utils.MapstructUtils;
import com.yuan.common.core.utils.StringUtils;
import com.yuan.core.page.PageQuery;
import com.yuan.core.page.TableDataInfo;
import com.yuan.system.domain.SysMenu;
import com.yuan.system.domain.bo.SysMenuBo;
import com.yuan.system.domain.vo.SysMenuVo;
import com.yuan.system.mapper.SysMenuMapper;
import com.yuan.system.service.SysMenuService;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

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
        SysMenu add = MapstructUtils.convert(bo, SysMenu. class);
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
        SysMenu update = MapstructUtils.convert(bo, SysMenu. class);
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
}
