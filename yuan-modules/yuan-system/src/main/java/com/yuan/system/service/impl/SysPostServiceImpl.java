package com.yuan.system.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yuan.common.core.constant.UserConstants;
import com.yuan.common.core.exception.ServiceException;
import com.yuan.common.core.utils.MapstructUtils;
import com.yuan.common.core.utils.StreamUtils;
import com.yuan.common.core.utils.StringUtils;
import com.yuan.core.page.PageQuery;
import com.yuan.core.page.TableDataInfo;
import com.yuan.system.domain.SysPost;
import com.yuan.system.domain.SysRolePost;
import com.yuan.system.domain.SysUser;
import com.yuan.system.domain.bo.SysPostBo;
import com.yuan.system.domain.bo.SysUserBo;
import com.yuan.system.domain.vo.SysPostVo;
import com.yuan.system.domain.vo.SysRoleVo;
import com.yuan.system.domain.vo.SysUserVo;
import com.yuan.system.mapper.*;
import com.yuan.system.service.SysPostService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

/**
 * postService业务层处理
 *
 
 * @date Mon Dec 22 15:05:40 CST 2025
 */
@RequiredArgsConstructor
@Service
public class SysPostServiceImpl implements SysPostService {

    private final SysPostMapper baseMapper;
    private final SysUserPostMapper userPostMapper;
    private final SysDeptMapper sysDeptMapper;
    private final SysRolePostMapper rolePostMapper;
    private final SysRoleMapper sysRoleMapper;
    /**
     * 查询post
     */
    @Override
    public SysPostVo queryById(Long postId) {
        return baseMapper.selectVoById(postId);
    }

        /**
         * 查询post列表
         */
        @Override
        public TableDataInfo<SysPostVo> queryPageList(SysPostBo bo, PageQuery pageQuery) {
            QueryWrapper<SysPost> lqw = buildQueryWrapper(bo);
            Page<SysPostVo> result = baseMapper.selectPagePostList(pageQuery.build(), lqw);
            return TableDataInfo.build(result);
        }

    /**
     * 查询post列表
     */
    @Override
    public List<SysPostVo> queryList(SysPostBo bo) {
        QueryWrapper<SysPost> lqw = buildQueryWrapper(bo);
        return baseMapper.selectPostList(lqw);
    }
    private QueryWrapper<SysPost> buildQueryWrapper(SysPostBo bo) {
        QueryWrapper<SysPost> wrapper = Wrappers.query();
        wrapper.eq("sp.del_flag", UserConstants.USER_NORMAL)
                .eq(ObjectUtil.isNotNull(bo.getPostId()), "sp.user_id", bo.getPostId())
                .like(ObjectUtil.isNotNull(bo.getPostName()), "sp.post_name", bo.getPostName())
                .like(ObjectUtil.isNotNull(bo.getPostCode()), "sp.post_code", bo.getPostCode())
                .eq(StringUtils.isNotBlank(bo.getStatus()), "sp.status", bo.getStatus())
                .eq(bo.getDeptId() != null, "sp.dept_id", bo.getDeptId());
        return wrapper;
    }

    /**
     * 新增post
     */
    @Override
    public Boolean insertByBo(SysPostBo bo) {
        SysPost add = MapstructUtils.convert(bo, SysPost. class);
        validEntityBeforeSave(add);
        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            bo.setPostId(add.getPostId());
        }
        return flag;
    }

    /**
     * 修改post
     */
    @Override
    public Boolean updateByBo(SysPostBo bo) {
        SysPost update = MapstructUtils.convert(bo, SysPost. class);
        validEntityBeforeSave(update);
        return baseMapper.updateById(update) > 0;
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(SysPost entity) {
        //TODO 做一些数据校验,如唯一约束
    }

    /**
     * 批量删除post
     */
    @Override
    public Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid) {
        if (isValid) {
            //TODO 做一些业务上的校验,判断是否需要校验
        }
        return baseMapper.deleteBatchIds(ids) > 0;
    }

    @Override
    public TableDataInfo<SysUserVo> selectAllocatedUserList(SysUserBo user, PageQuery pageQuery) {
        QueryWrapper<SysUser> wrapper = Wrappers.query();
        wrapper.eq("u.del_flag", UserConstants.USER_NORMAL)
                .eq(ObjectUtil.isNotNull(user.getPostId()), "sp.post_id", user.getPostId())
                .like(StringUtils.isNotBlank(user.getUserName()), "u.user_name", user.getUserName())
                .like(StringUtils.isNotBlank(user.getNickName()), "u.nick_name", user.getNickName())
                .eq(StringUtils.isNotBlank(user.getStatus()), "u.status", user.getStatus());
        Page<SysUserVo> page = baseMapper.selectAllocatedUserList(pageQuery.build(), wrapper);
        return TableDataInfo.build(page);
    }


    @Override
    public Boolean checkPostNameUnique(SysPostBo bo) {
        boolean exist = baseMapper.exists(new LambdaQueryWrapper<SysPost>()
                .eq(SysPost::getPostName, bo.getPostName())
                .eq(SysPost::getDeptId, bo.getDeptId())
                .ne(ObjectUtil.isNotNull(bo.getPostId()), SysPost::getPostId, bo.getPostId()));
        return !exist;

    }

    @Override
    public Boolean checkPostKeyUnique(SysPostBo bo) {
        boolean exist = baseMapper.exists(new LambdaQueryWrapper<SysPost>()
                .eq(SysPost::getPostCode, bo.getPostCode())
                .eq(SysPost::getDeptId, bo.getDeptId())
                .ne(ObjectUtil.isNotNull(bo.getPostId()), SysPost::getPostId, bo.getPostId()));
        return !exist;
    }

    @Override
    public List<SysPostVo> queryByUserId(Long userId) {
        List<SysPostVo> sysPostVos = baseMapper.selectByUserId(userId);
        if (sysPostVos.isEmpty()) {
            sysPostVos = baseMapper.selectVoList();
        }
        return sysPostVos;
    }

    @Override
    public List<SysPostVo> getByDeptId(Long deptId) {
        return baseMapper.selectByDeptId(deptId);
    }

    @Override
    public void insertPostRole(Long postId, Long[] roleIds) {
        insertPostRole(postId,roleIds,true);
    }

    private void insertPostRole(Long postId, Long[] roleIds, boolean clear) {
        if (clear) {
            // 删除用户与角色关联
            rolePostMapper.delete(new LambdaQueryWrapper<SysRolePost>().eq(SysRolePost::getPostId, postId));
        }
        if (ArrayUtil.isNotEmpty(roleIds)) {
            //todo 权限校验

            // 判断是否具有此角色的操作权限
            List<SysRoleVo> roles = sysRoleMapper.selectVoList(new LambdaQueryWrapper<>());
            if (CollUtil.isEmpty(roles)) {
                throw new ServiceException("没有权限访问角色的数据");
            }
            List<Long> roleList = StreamUtils.toList(roles, SysRoleVo::getRoleId);
            roleList.remove(UserConstants.SUPER_ADMIN_ID);

            List<Long> canDoRoleList = StreamUtils.filter(List.of(roleIds), roleList::contains);
            if (CollUtil.isEmpty(canDoRoleList)) {
                throw new ServiceException("没有权限访问角色的数据");
            }
            // 新增用户与角色管理
            List<SysRolePost> list = StreamUtils.toList(canDoRoleList, roleId -> {
                SysRolePost ur = new SysRolePost();
                ur.setPostId(postId);
                ur.setRoleId(roleId);
                return ur;
            });
            rolePostMapper.insertBatch(list);
        }
    }
}
