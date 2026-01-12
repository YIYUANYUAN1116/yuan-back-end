package com.yuan.system.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yuan.common.core.constant.UserConstants;
import com.yuan.common.core.exception.GlobalException;
import com.yuan.common.core.exception.ServiceException;
import com.yuan.common.core.utils.MapstructUtils;
import com.yuan.common.core.utils.StringUtils;
import com.yuan.common.satoken.utils.LoginHelper;
import com.yuan.core.page.PageQuery;
import com.yuan.core.page.TableDataInfo;
import com.yuan.system.domain.SysRole;
import com.yuan.system.domain.SysRoleMenu;
import com.yuan.system.domain.SysRolePost;
import com.yuan.system.domain.SysUser;
import com.yuan.system.domain.bo.SysRoleBo;
import com.yuan.system.domain.bo.SysUserBo;
import com.yuan.system.domain.vo.SelectRolesVo;
import com.yuan.system.domain.vo.SysRoleVo;
import com.yuan.system.domain.vo.SysUserVo;
import com.yuan.system.helper.MenuScopHelper;
import com.yuan.system.mapper.*;
import com.yuan.system.service.SysMenuService;
import com.yuan.system.service.SysRoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * 角色Service业务层处理
 *
 
 * @date Sun Dec 07 17:25:44 CST 2025
 */
@RequiredArgsConstructor
@Service
public class SysRoleServiceImpl implements SysRoleService {

    private final SysRoleMapper baseMapper;
    private final SysRoleMenuMapper roleMenuMapper;
    private final SysMenuService menuService;
    private final SysMenuMapper sysMenuMapper;
    private final SysRolePostMapper sysRolePostMapper;

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
    @Transactional
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
    @Transactional
    public Boolean updateByBo(SysRoleBo bo) {
        SysRole update = MapstructUtils.convert(bo, SysRole.class);
        validEntityBeforeSave(update);
        boolean flag = baseMapper.updateById(update) > 0;
        if (flag) {
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
            if (countPostRoleByRoleId(roleId) > 0) {
                throw new ServiceException(String.format("%1$s已分配,不能删除", role.getRoleName()));
            }
        }
        return baseMapper.deleteByIds(ids) > 0;
    }

    private Long countPostRoleByRoleId(Long roleId) {
        return sysRolePostMapper.selectCount(new LambdaQueryWrapper<SysRolePost>().eq(SysRolePost::getRoleId, roleId));
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
    public SelectRolesVo selectPostRolesVo(Long postId) {
        SelectRolesVo selectRolesVo = new SelectRolesVo();
        List<SysRoleVo> sysRoleVos = baseMapper.selectVoList();
        selectRolesVo.setRoles(sysRoleVos);
        if (postId != null) {
            selectRolesVo.setCheckedKeys(sysRolePostMapper.selectRoleIdsByPostId(postId));
        }
        return selectRolesVo;
    }

    @Override
    public TableDataInfo<SysUserVo> selectAllocatedUserList(SysUserBo user, PageQuery pageQuery) {
        QueryWrapper<SysUser> wrapper = Wrappers.query();
        wrapper.eq("u.del_flag", UserConstants.USER_NORMAL)
                .like(StringUtils.isNotBlank(user.getUserName()), "u.user_name", user.getUserName())
                .like(StringUtils.isNotBlank(user.getNickName()), "u.nick_name", user.getNickName())
                .eq(StringUtils.isNotBlank(user.getStatus()), "u.status", user.getStatus());
        Page<SysUserVo> page = baseMapper.selectAllocatedUserList(pageQuery.build(), wrapper,user.getRoleId());
        return TableDataInfo.build(page);
    }



    @Override
    public void checkRoleDataScope(Long roleId) {
        if (ObjectUtil.isNull(roleId)) {
            return;
        }
        if (LoginHelper.isSuperAdmin()) {
            return;
        }
        List<SysRoleVo> roles = this.selectRoleList(new SysRoleBo(roleId));
        if (CollUtil.isEmpty(roles)) {
            throw new ServiceException("没有权限访问角色数据！");
        }
    }

    @Override
    public Set<String> selectRolePermissionByUserId(Long userId) {
        List<SysRoleVo> perms = selectRoleVoByUserId(userId);
        Set<String> permsSet = new HashSet<>();
        for (SysRoleVo perm : perms) {
            if (ObjectUtil.isNotNull(perm)) {
                permsSet.addAll(StringUtils.splitList(perm.getRoleKey().trim()));
            }
        }
        return permsSet;
    }

    @Override
    public List<SysRoleVo> selectRoleList(SysRoleBo role) {
        return baseMapper.selectRoleList(this.buildQueryWrapper(role));
    }

    private void insertRoleMenu(SysRoleBo role) {
        if (ObjectUtil.isNull(role) || role.getMenuIds() == null || role.getMenuIds().length == 0)return;
        // 新增用户与角色管理
        Set<Long> menus = menuService.addParentIds(role.getMenuIds());
        checkMenuScop(role.getRoleId(),menus);
        roleMenuMapper.delete(new LambdaQueryWrapper<SysRoleMenu>().eq(SysRoleMenu::getRoleId, role.getRoleId()));
        List<SysRoleMenu> list = new ArrayList<SysRoleMenu>();
        for (Long menuId : menus) {
            SysRoleMenu rm = new SysRoleMenu();
            rm.setRoleId(role.getRoleId());
            rm.setMenuId(menuId);
            list.add(rm);
        }
        if (!list.isEmpty()) {
            roleMenuMapper.insertBatch(list);
        }
    }

    @Override
    public List<SysRoleVo> selectRoleVoByUserId(Long userId) {
        List<SysRoleVo> res = new ArrayList<>();
        res.addAll(baseMapper.selectRoleVoByRoleUser(userId));
        res.addAll(baseMapper.selectRoleVoByPostUser(userId));
        return res;
    }

    private void checkMenuScop(Long roleId, Set<Long> menuIds) {
        //todo 返回信息告诉用户 哪些是不能分配的

        // 1. scope 校验（平台 / 租户）
        List<String> allowedScopes = MenuScopHelper.getAssignableMenuScopes();
        List<String> menuScopes = sysMenuMapper.selectScopesBymenuIds(menuIds);
        if (!new HashSet<>(allowedScopes).containsAll(menuScopes)) {
            throw new GlobalException("包含无权分配的菜单");
        }

        // 2. 如果不是平台级操作者，必须做“权限子集校验”
        if (!LoginHelper.isPlatAdmin() && !LoginHelper.isSuperAdmin()) {

            Set<Long> myMenuIds = sysMenuMapper.selectMenuIdsByUserId(LoginHelper.getUserId());

            if (!myMenuIds.containsAll(menuIds)) {
                throw new GlobalException("不能分配超过自身权限范围的菜单");
            }
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
