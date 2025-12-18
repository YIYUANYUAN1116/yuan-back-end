package com.yuan.system.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yuan.common.core.exception.ServiceException;
import com.yuan.common.core.utils.MapstructUtils;
import com.yuan.common.core.utils.StringUtils;
import com.yuan.core.page.PageQuery;
import com.yuan.core.page.TableDataInfo;
import com.yuan.system.domain.SysRole;
import com.yuan.system.domain.SysRoleMenu;
import com.yuan.system.domain.SysUserRole;
import com.yuan.system.domain.bo.SysRoleBo;
import com.yuan.system.domain.vo.SelectRolesVo;
import com.yuan.system.domain.vo.SysRoleVo;
import com.yuan.system.mapper.SysRoleMapper;
import com.yuan.system.mapper.SysRoleMenuMapper;
import com.yuan.system.mapper.SysUserRoleMapper;
import com.yuan.system.service.SysRoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * 角色Service业务层处理
 *
 * @author ageerle
 * @date Sun Dec 07 17:25:44 CST 2025
 */
@RequiredArgsConstructor
@Service
public class SysRoleServiceImpl implements SysRoleService {

    private final SysRoleMapper baseMapper;
    private final SysUserRoleMapper sysUserRoleMapper;
    private final SysRoleMenuMapper sysRoleMenuMapper;

    /**
     * 查询角色
     */
    @Override
    public SysRoleVo queryById(Long roleId) {
        return baseMapper.selectVoById(roleId);
    }

    /**
     * 查询角色列表
     */
    @Override
    public TableDataInfo<SysRoleVo> queryPageList(SysRoleBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<SysRole> lqw = buildQueryWrapper(bo);
        Page<SysRoleVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }

    /**
     * 查询角色列表
     */
    @Override
    public List<SysRoleVo> queryList(SysRoleBo bo) {
        LambdaQueryWrapper<SysRole> lqw = buildQueryWrapper(bo);
        return baseMapper.selectVoList(lqw);
    }



    /**
     * 新增角色
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean insertByBo(SysRoleBo bo) {
        SysRole add = MapstructUtils.convert(bo, SysRole.class);
        validEntityBeforeSave(add);
        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            bo.setRoleId(add.getRoleId());
            insertRoleMenu(bo);
        }
        return flag;
    }

    /**
     * 修改角色
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean updateByBo(SysRoleBo bo) {
        SysRole update = MapstructUtils.convert(bo, SysRole.class);
        validEntityBeforeSave(update);
        boolean flag = baseMapper.updateById(update) > 0;
        if (flag) {
            sysRoleMenuMapper.delete(new LambdaQueryWrapper<SysRoleMenu>().eq(SysRoleMenu::getRoleId, bo.getRoleId()));
            insertRoleMenu(bo);
        }
        return flag;
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(SysRole entity) {
        //TODO 做一些数据校验,如唯一约束
    }

    /**
     * 批量删除角色
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid) {
        if (isValid) {
            //TODO 做一些业务上的校验,判断是否需要校验
        }
        for (Long roleId : ids) {
            SysRole role = baseMapper.selectById(roleId);
            if (countUserRoleByRoleId(roleId) > 0) {
                throw new ServiceException(String.format("%1$s已分配,不能删除", role.getRoleName()));
            }
        }
        return baseMapper.deleteByIds(ids) > 0;
    }

    private Long countUserRoleByRoleId(Long roleId) {
        return sysUserRoleMapper.selectCount(new LambdaQueryWrapper<SysUserRole>().eq(SysUserRole::getRoleId, roleId));
    }

    @Override
    public List<SysRoleVo> selectRoleAll() {
        return baseMapper.selectVoList();
    }

    @Override
    public boolean checkRoleNameUnique(SysRoleBo role) {
        boolean exist = baseMapper.exists(new LambdaQueryWrapper<SysRole>()
                .eq(SysRole::getRoleName, role.getRoleName())
                .ne(ObjectUtil.isNotNull(role.getRoleId()), SysRole::getRoleId, role.getRoleId()));
        return !exist;
    }

    @Override
    public boolean checkRoleKeyUnique(SysRoleBo role) {
        boolean exist = baseMapper.exists(new LambdaQueryWrapper<SysRole>()
                .eq(SysRole::getRoleKey, role.getRoleKey())
                .ne(ObjectUtil.isNotNull(role.getRoleId()), SysRole::getRoleId, role.getRoleId()));
        return !exist;
    }

    @Override
    public List<SysRoleVo> selectRolesByUserId(Long userId) {
        return baseMapper.selectRolesByUserId(userId);
    }

    @Override
    public SelectRolesVo selectSelectRolesVo(Long userId) {
        SelectRolesVo selectRolesVo = new SelectRolesVo();
        List<SysRoleVo> sysRoleVos = baseMapper.selectVoList();
        selectRolesVo.setRoles(sysRoleVos);
        if (userId != null) {
            selectRolesVo.setCheckedKeys(sysUserRoleMapper.selectRoleIdsByUserId(userId));
        }
        return selectRolesVo;
    }

    private void insertRoleMenu(SysRoleBo role) {
        if (ObjectUtil.isNull(role) || role.getMenuIds() == null)return;
        // 新增用户与角色管理
        List<SysRoleMenu> list = new ArrayList<SysRoleMenu>();
        for (Long menuId : role.getMenuIds()) {
            SysRoleMenu rm = new SysRoleMenu();
            rm.setRoleId(role.getRoleId());
            rm.setMenuId(menuId);
            list.add(rm);
        }
        if (!list.isEmpty()) {
            sysRoleMenuMapper.insertBatch(list);
        }
    }

    private LambdaQueryWrapper<SysRole> buildQueryWrapper(SysRoleBo bo) {
        LambdaQueryWrapper<SysRole> lqw = Wrappers.lambdaQuery();
        lqw.eq(bo.getRoleId() != null, SysRole::getRoleId, bo.getRoleId());
        lqw.eq(StringUtils.isNotBlank(bo.getTenantId()), SysRole::getTenantId, bo.getTenantId());
        lqw.eq(StringUtils.isNotBlank(bo.getRoleName()), SysRole::getRoleName, bo.getRoleName());
        lqw.eq(StringUtils.isNotBlank(bo.getRoleKey()), SysRole::getRoleKey, bo.getRoleKey());
        lqw.eq(bo.getRoleSort() != null, SysRole::getRoleSort, bo.getRoleSort());
        lqw.eq(StringUtils.isNotBlank(bo.getDataScope()), SysRole::getDataScope, bo.getDataScope());
        lqw.eq(bo.getMenuCheckStrictly() != null, SysRole::getMenuCheckStrictly, bo.getMenuCheckStrictly());
        lqw.eq(bo.getDeptCheckStrictly() != null, SysRole::getDeptCheckStrictly, bo.getDeptCheckStrictly());
        lqw.eq(StringUtils.isNotBlank(bo.getStatus()), SysRole::getStatus, bo.getStatus());
        lqw.eq(StringUtils.isNotBlank(bo.getDelFlag()), SysRole::getDelFlag, bo.getDelFlag());
        lqw.eq(bo.getCreateDept() != null, SysRole::getCreateDept, bo.getCreateDept());
        lqw.eq(bo.getCreateBy() != null, SysRole::getCreateBy, bo.getCreateBy());
        lqw.eq(bo.getCreateTime() != null, SysRole::getCreateTime, bo.getCreateTime());
        lqw.eq(bo.getUpdateBy() != null, SysRole::getUpdateBy, bo.getUpdateBy());
        lqw.eq(bo.getUpdateTime() != null, SysRole::getUpdateTime, bo.getUpdateTime());
        lqw.eq(StringUtils.isNotBlank(bo.getRemark()), SysRole::getRemark, bo.getRemark());
        return lqw;
    }
}
