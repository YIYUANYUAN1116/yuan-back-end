package com.yuan.system.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yuan.common.core.constant.UserConstants;
import com.yuan.common.core.exception.ServiceException;
import com.yuan.common.core.utils.MapstructUtils;
import com.yuan.common.core.utils.StreamUtils;
import com.yuan.common.core.utils.StringUtils;
import com.yuan.common.satoken.utils.LoginHelper;
import com.yuan.core.page.PageQuery;
import com.yuan.core.page.TableDataInfo;
import com.yuan.system.domain.SysUser;
import com.yuan.system.domain.SysUserRole;
import com.yuan.system.domain.bo.SysUserBo;
import com.yuan.system.domain.vo.SysPostVo;
import com.yuan.system.domain.vo.SysRoleVo;
import com.yuan.system.domain.vo.SysUserVo;
import com.yuan.system.mapper.SysPostMapper;
import com.yuan.system.mapper.SysRoleMapper;
import com.yuan.system.mapper.SysUserMapper;
import com.yuan.system.mapper.SysUserRoleMapper;
import com.yuan.system.service.SysUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;

/**
 * 用户Service业务层处理
 *
 
 * @date Sun Dec 07 17:25:38 CST 2025
 */
@RequiredArgsConstructor
@Service
public class SysUserServiceImpl implements SysUserService {

    private final SysUserMapper baseMapper;
    private final SysRoleMapper sysRoleMapper;
    private final SysUserRoleMapper sysUserRoleMapper;
    private final SysPostMapper sysPostMapper;

    /**
     * 查询用户
     */
    @Override
    public SysUserVo queryById(Long userId) {
        return baseMapper.selectVoById(userId);
    }

    /**
     * 查询用户列表
     */
    @Override
    public TableDataInfo<SysUserVo> queryPageList(SysUserBo bo, PageQuery pageQuery) {
        Page<SysUserVo> page = baseMapper.selectPageUserList(pageQuery.build(), this.buildQueryWrapper(bo));
        return TableDataInfo.build(page);
    }

    /**
     * 查询用户列表
     */
    @Override
    public List<SysUserVo> queryList(SysUserBo bo) {
        LambdaQueryWrapper<SysUser> lqw = buildQueryWrapper(bo);
        return baseMapper.selectVoList(lqw);
    }


    /**
     * 新增用户
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean insertByBo(SysUserBo bo) {
        SysUser add = MapstructUtils.convert(bo, SysUser.class);
        validEntityBeforeSave(add);
        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            bo.setUserId(add.getUserId());
        }
        return flag;
    }

    /**
     * 修改用户
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean updateByBo(SysUserBo bo) {
        SysUser update = MapstructUtils.convert(bo, SysUser.class);
        validEntityBeforeSave(update);
        return baseMapper.updateById(update) > 0;
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(SysUser entity) {
        //TODO 做一些数据校验,如唯一约束
    }

    /**
     * 批量删除用户
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid) {
        // 删除用户与角色关联
        sysUserRoleMapper.delete(new LambdaQueryWrapper<SysUserRole>().in(SysUserRole::getUserId, ids));
        return baseMapper.deleteByIds(ids) > 0;
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public void insertUserAuth(Long userId, Long[] roleIds) {
        insertUserRole(userId, roleIds, true);
    }

    @Override
    public SysUserVo selectUserById(Long userId) {
        return baseMapper.selectVoById(userId);
    }

    /**
     * 查询用户所属角色组
     *
     * @return 结果
     */
    @Override
    public String selectUserRoleGroup(Long userId) {
        List<SysRoleVo> list = sysRoleMapper.selectRolesByUserId(userId);
        if (CollUtil.isEmpty(list)) {
            return StringUtils.EMPTY;
        }
        return StreamUtils.join(list, SysRoleVo::getRoleName);
    }

    /**
     * 查询用户所属岗位组
     *
     * @return 结果
     */
    @Override
    public String selectUserPostGroup(Long userId) {
        List<SysPostVo> list = sysPostMapper.selectPostsByUserId(userId);
        if (CollUtil.isEmpty(list)) {
            return StringUtils.EMPTY;
        }
        return StreamUtils.join(list, SysPostVo::getPostName);
    }

    @Override
    public int updateUserProfile(SysUserBo user) {
        return baseMapper.update(null,
                new LambdaUpdateWrapper<SysUser>()
                        .set(ObjectUtil.isNotNull(user.getNickName()), SysUser::getNickName, user.getNickName())
                        .set(SysUser::getPhonenumber, user.getPhonenumber())
                        .set(SysUser::getEmail, user.getEmail())
                        .set(SysUser::getSex, user.getSex())
                        .eq(SysUser::getUserId, user.getUserId()));
    }

    @Override
    public int resetUserPwd(Long userId, String password) {
        return baseMapper.update(null,
                new LambdaUpdateWrapper<SysUser>()
                        .set(SysUser::getPassword, password)
                        .eq(SysUser::getUserId, userId));
    }

    private void insertUserRole(Long userId, Long[] roleIds, boolean clear) {
        if (clear) {
            // 删除用户与角色关联
            sysUserRoleMapper.delete(new LambdaQueryWrapper<SysUserRole>().eq(SysUserRole::getUserId, userId));
        }
        if (ArrayUtil.isNotEmpty(roleIds)) {
            // 判断是否具有此角色的操作权限
            List<SysRoleVo> roles = sysRoleMapper.selectVoList(new LambdaQueryWrapper<>());
            if (CollUtil.isEmpty(roles)) {
                throw new ServiceException("没有权限访问角色的数据");
            }
            List<Long> roleList = StreamUtils.toList(roles, SysRoleVo::getRoleId);
            if (!LoginHelper.isSuperAdmin(userId)) {
                roleList.remove(UserConstants.SUPER_ADMIN_ID);
            }
            List<Long> canDoRoleList = StreamUtils.filter(List.of(roleIds), roleList::contains);
            if (CollUtil.isEmpty(canDoRoleList)) {
                throw new ServiceException("没有权限访问角色的数据");
            }
            // 新增用户与角色管理
            List<SysUserRole> list = StreamUtils.toList(canDoRoleList, roleId -> {
                SysUserRole ur = new SysUserRole();
                ur.setUserId(userId);
                ur.setRoleId(roleId);
                return ur;
            });
            sysUserRoleMapper.insertBatch(list);
        }
    }


    @Override
    public boolean checkUserNameUnique(SysUserBo user) {
        boolean exist = baseMapper.exists(new LambdaQueryWrapper<SysUser>()
                .eq(SysUser::getUserName, user.getUserName())
                .ne(ObjectUtil.isNotNull(user.getUserId()), SysUser::getUserId, user.getUserId()));
        return !exist;
    }

    @Override
    public boolean checkPhoneUnique(SysUserBo user) {
        boolean exist = baseMapper.exists(new LambdaQueryWrapper<SysUser>()
                .eq(SysUser::getPhonenumber, user.getPhonenumber())
                .ne(ObjectUtil.isNotNull(user.getUserId()), SysUser::getUserId, user.getUserId()));
        return !exist;
    }

    @Override
    public boolean checkEmailUnique(SysUserBo user) {
        boolean exist = baseMapper.exists(new LambdaQueryWrapper<SysUser>()
                .eq(SysUser::getEmail, user.getEmail())
                .ne(ObjectUtil.isNotNull(user.getUserId()), SysUser::getUserId, user.getUserId()));
        return !exist;
    }

    private LambdaQueryWrapper<SysUser> buildQueryWrapper(SysUserBo bo) {
        LambdaQueryWrapper<SysUser> lqw = Wrappers.lambdaQuery();
        lqw.eq(bo.getUserId() != null, SysUser::getUserId, bo.getUserId());
        lqw.eq(StringUtils.isNotBlank(bo.getOpenId()), SysUser::getOpenId, bo.getOpenId());
        lqw.eq(StringUtils.isNotBlank(bo.getUserGrade()), SysUser::getUserGrade, bo.getUserGrade());
        lqw.eq(bo.getUserBalance() != null, SysUser::getUserBalance, bo.getUserBalance());
        lqw.eq(StringUtils.isNotBlank(bo.getTenantId()), SysUser::getTenantId, bo.getTenantId());
        lqw.eq(bo.getDeptId() != null, SysUser::getDeptId, bo.getDeptId());
        lqw.eq(StringUtils.isNotBlank(bo.getUserName()), SysUser::getUserName, bo.getUserName());
        lqw.like(StringUtils.isNotBlank(bo.getNickName()), SysUser::getNickName, bo.getNickName());
        lqw.eq(StringUtils.isNotBlank(bo.getUserType()), SysUser::getUserType, bo.getUserType());
        lqw.eq(StringUtils.isNotBlank(bo.getUserPlan()), SysUser::getUserPlan, bo.getUserPlan());
        lqw.eq(StringUtils.isNotBlank(bo.getEmail()), SysUser::getEmail, bo.getEmail());
        lqw.eq(StringUtils.isNotBlank(bo.getPhonenumber()), SysUser::getPhonenumber, bo.getPhonenumber());
        lqw.eq(StringUtils.isNotBlank(bo.getSex()), SysUser::getSex, bo.getSex());
        lqw.eq(StringUtils.isNotBlank(bo.getAvatar()), SysUser::getAvatar, bo.getAvatar());
        lqw.eq(StringUtils.isNotBlank(bo.getWxAvatar()), SysUser::getWxAvatar, bo.getWxAvatar());
        lqw.eq(StringUtils.isNotBlank(bo.getPassword()), SysUser::getPassword, bo.getPassword());
        lqw.eq(StringUtils.isNotBlank(bo.getStatus()), SysUser::getStatus, bo.getStatus());
        lqw.eq(StringUtils.isNotBlank(bo.getDelFlag()), SysUser::getDelFlag, bo.getDelFlag());
        lqw.eq(StringUtils.isNotBlank(bo.getLoginIp()), SysUser::getLoginIp, bo.getLoginIp());
        lqw.eq(bo.getLoginDate() != null, SysUser::getLoginDate, bo.getLoginDate());
        lqw.eq(StringUtils.isNotBlank(bo.getDomainName()), SysUser::getDomainName, bo.getDomainName());
        lqw.eq(bo.getCreateDept() != null, SysUser::getCreateDept, bo.getCreateDept());
        lqw.eq(bo.getCreateBy() != null, SysUser::getCreateBy, bo.getCreateBy());
        lqw.eq(bo.getCreateTime() != null, SysUser::getCreateTime, bo.getCreateTime());
        lqw.eq(bo.getUpdateBy() != null, SysUser::getUpdateBy, bo.getUpdateBy());
        lqw.eq(bo.getUpdateTime() != null, SysUser::getUpdateTime, bo.getUpdateTime());
        lqw.eq(StringUtils.isNotBlank(bo.getRemark()), SysUser::getRemark, bo.getRemark());
        lqw.eq(StringUtils.isNotBlank(bo.getKroleGroupType()), SysUser::getKroleGroupType, bo.getKroleGroupType());
        lqw.eq(StringUtils.isNotBlank(bo.getKroleGroupIds()), SysUser::getKroleGroupIds, bo.getKroleGroupIds());
        return lqw;
    }
}
