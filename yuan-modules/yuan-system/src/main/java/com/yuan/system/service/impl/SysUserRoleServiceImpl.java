package com.yuan.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yuan.common.core.utils.MapstructUtils;
import com.yuan.core.page.PageQuery;
import com.yuan.core.page.TableDataInfo;
import com.yuan.system.domain.SysUserRole;
import com.yuan.system.domain.bo.SysUserRoleBo;
import com.yuan.system.domain.vo.SysUserRoleVo;
import com.yuan.system.mapper.SysUserRoleMapper;
import com.yuan.system.service.SysUserRoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

/**
 * 用户角色Service业务层处理
 *

 * @date Sun Dec 07 17:25:51 CST 2025
 */
@RequiredArgsConstructor
@Service
public class SysUserRoleServiceImpl implements SysUserRoleService {

    private final SysUserRoleMapper baseMapper;

    /**
     * 查询用户角色
     */
    @Override
    public SysUserRoleVo queryById(Long roleId) {
        return baseMapper.selectVoById(roleId);
    }

        /**
         * 查询用户角色列表
         */
        @Override
        public TableDataInfo<SysUserRoleVo> queryPageList(SysUserRoleBo bo, PageQuery pageQuery) {
            LambdaQueryWrapper<SysUserRole> lqw = buildQueryWrapper(bo);
            Page<SysUserRoleVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
            return TableDataInfo.build(result);
        }

    /**
     * 查询用户角色列表
     */
    @Override
    public List<SysUserRoleVo> queryList(SysUserRoleBo bo) {
        LambdaQueryWrapper<SysUserRole> lqw = buildQueryWrapper(bo);
        return baseMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<SysUserRole> buildQueryWrapper(SysUserRoleBo bo) {
        LambdaQueryWrapper<SysUserRole> lqw = Wrappers.lambdaQuery();
                    lqw.eq(bo.getUserId() != null, SysUserRole::getUserId, bo.getUserId());
                    lqw.eq(bo.getRoleId() != null, SysUserRole::getRoleId, bo.getRoleId());
        return lqw;
    }

    /**
     * 新增用户角色
     */
    @Override
    public Boolean insertByBo(SysUserRoleBo bo) {
        SysUserRole add = MapstructUtils.convert(bo, SysUserRole. class);
        validEntityBeforeSave(add);
        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            bo.setRoleId(add.getRoleId());
        }
        return flag;
    }

    /**
     * 修改用户角色
     */
    @Override
    public Boolean updateByBo(SysUserRoleBo bo) {
        SysUserRole update = MapstructUtils.convert(bo, SysUserRole. class);
        validEntityBeforeSave(update);
        return baseMapper.updateById(update) > 0;
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(SysUserRole entity) {
        //TODO 做一些数据校验,如唯一约束
    }

    /**
     * 批量删除用户角色
     */
    @Override
    public Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid) {
        if (isValid) {
            //TODO 做一些业务上的校验,判断是否需要校验
        }
        return baseMapper.deleteBatchIds(ids) > 0;
    }
}
