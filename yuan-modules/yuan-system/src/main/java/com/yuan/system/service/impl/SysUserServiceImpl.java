package com.yuan.system.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yuan.common.core.constant.UserConstants;
import com.yuan.common.core.utils.MapstructUtils;
import com.yuan.common.core.utils.StreamUtils;
import com.yuan.common.core.utils.StringUtils;
import com.yuan.core.page.PageQuery;
import com.yuan.core.page.TableDataInfo;
import com.yuan.system.domain.SysUser;
import com.yuan.system.domain.SysUserPost;
import com.yuan.system.domain.bo.SysUserBo;
import com.yuan.system.domain.vo.SysPostVo;
import com.yuan.system.domain.vo.SysRoleVo;
import com.yuan.system.domain.vo.SysUserVo;
import com.yuan.system.mapper.SysPostMapper;
import com.yuan.system.mapper.SysRoleMapper;
import com.yuan.system.mapper.SysUserMapper;
import com.yuan.system.mapper.SysUserPostMapper;
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
    private final SysPostMapper sysPostMapper;
    private final SysUserPostMapper userPostMapper;


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
        QueryWrapper<SysUser> lqw = buildQueryWrapper(bo);
        return baseMapper.selectUserList(lqw);
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
        bo.setUserName(null);
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
        userPostMapper.delete(new LambdaQueryWrapper<SysUserPost>().in(SysUserPost::getUserId, ids));
        return baseMapper.deleteByIds(ids) > 0;
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



    @Override
    public boolean checkNickNameUnique(SysUserBo user) {
        boolean exist = baseMapper.exists(new LambdaQueryWrapper<SysUser>()
                .eq(SysUser::getNickName, user.getNickName())
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

    private QueryWrapper<SysUser> buildQueryWrapper(SysUserBo user) {
        QueryWrapper<SysUser> wrapper = Wrappers.query();
        wrapper.eq("u.del_flag", UserConstants.USER_NORMAL)
                .eq(ObjectUtil.isNotNull(user.getUserId()), "u.user_id", user.getUserId())
                .eq(ObjectUtil.isNotNull(user.getUserGrade()), "u.user_grade", user.getUserGrade())
                .like(StringUtils.isNotBlank(user.getUserName()), "u.user_name", user.getUserName())
                .like(StringUtils.isNotBlank(user.getNickName()), "u.nick_name", user.getNickName())
                .eq(StringUtils.isNotBlank(user.getStatus()), "u.status", user.getStatus())
                .like(StringUtils.isNotBlank(user.getPhonenumber()), "u.phonenumber", user.getPhonenumber())
                .like(StringUtils.isNotBlank(user.getEmail()), "u.email", user.getEmail())
                .eq(user.getDeptId() != null, "sd.dept_id", user.getDeptId())
                .eq(user.getRoleId() != null, "sr.role_id", user.getRoleId());

        return wrapper;
    }
}
