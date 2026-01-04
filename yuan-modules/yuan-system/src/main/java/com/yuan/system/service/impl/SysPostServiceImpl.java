package com.yuan.system.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yuan.common.core.constant.UserConstants;
import com.yuan.common.core.utils.MapstructUtils;
import com.yuan.common.core.utils.StreamUtils;
import com.yuan.common.core.utils.StringUtils;
import com.yuan.core.page.PageQuery;
import com.yuan.core.page.TableDataInfo;
import com.yuan.system.domain.SysPost;
import com.yuan.system.domain.SysUser;
import com.yuan.system.domain.SysUserPost;
import com.yuan.system.domain.bo.SysPostBo;
import com.yuan.system.domain.bo.SysUserBo;
import com.yuan.system.domain.vo.SysPostVo;
import com.yuan.system.domain.vo.SysUserVo;
import com.yuan.system.mapper.SysPostMapper;
import com.yuan.system.mapper.SysUserPostMapper;
import com.yuan.system.service.SysPostService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;
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
            LambdaQueryWrapper<SysPost> lqw = buildQueryWrapper(bo);
            Page<SysPostVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
            return TableDataInfo.build(result);
        }

    /**
     * 查询post列表
     */
    @Override
    public List<SysPostVo> queryList(SysPostBo bo) {
        LambdaQueryWrapper<SysPost> lqw = buildQueryWrapper(bo);
        return baseMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<SysPost> buildQueryWrapper(SysPostBo bo) {
        LambdaQueryWrapper<SysPost> lqw = Wrappers.lambdaQuery();
                    lqw.eq(bo.getPostId() != null, SysPost::getPostId, bo.getPostId());
                    lqw.eq(StringUtils.isNotBlank(bo.getTenantId()), SysPost::getTenantId, bo.getTenantId());
                    lqw.eq(StringUtils.isNotBlank(bo.getPostCode()), SysPost::getPostCode, bo.getPostCode());
                    lqw.eq(StringUtils.isNotBlank(bo.getPostName()), SysPost::getPostName, bo.getPostName());
                    lqw.eq(bo.getPostSort() != null, SysPost::getPostSort, bo.getPostSort());
                    lqw.eq(StringUtils.isNotBlank(bo.getStatus()), SysPost::getStatus, bo.getStatus());
                    lqw.eq(bo.getCreateDept() != null, SysPost::getCreateDept, bo.getCreateDept());
                    lqw.eq(bo.getCreateBy() != null, SysPost::getCreateBy, bo.getCreateBy());
                    lqw.eq(bo.getCreateTime() != null, SysPost::getCreateTime, bo.getCreateTime());
                    lqw.eq(bo.getUpdateBy() != null, SysPost::getUpdateBy, bo.getUpdateBy());
                    lqw.eq(bo.getUpdateTime() != null, SysPost::getUpdateTime, bo.getUpdateTime());
                    lqw.eq(StringUtils.isNotBlank(bo.getRemark()), SysPost::getRemark, bo.getRemark());
        return lqw;
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
                .eq(StringUtils.isNotBlank(user.getStatus()), "u.status", user.getStatus())
                .like(StringUtils.isNotBlank(user.getPhonenumber()), "u.phonenumber", user.getPhonenumber());
        Page<SysUserVo> page = baseMapper.selectAllocatedUserList(pageQuery.build(), wrapper);
        return TableDataInfo.build(page);
    }

    @Override
    public TableDataInfo<SysUserVo> selectUnallocatedUserList(SysUserBo user, PageQuery pageQuery) {
        List<Long> userIds = userPostMapper.selectUserIdsByPostId(user.getPostId());
        QueryWrapper<SysUser> wrapper = Wrappers.query();
        wrapper.eq("u.del_flag", UserConstants.USER_NORMAL)
                .and(w -> w.ne("sp.post_id", user.getPostId()).or().isNull("sp.post_id"))
                .notIn(CollUtil.isNotEmpty(userIds), "u.user_id", userIds)
                .like(StringUtils.isNotBlank(user.getNickName()), "u.nick_name", user.getNickName())
                .like(StringUtils.isNotBlank(user.getUserName()), "u.user_name", user.getUserName())
                .like(StringUtils.isNotBlank(user.getPhonenumber()), "u.phonenumber", user.getPhonenumber());
        Page<SysUserVo> page = baseMapper.selectUnallocatedUserList(pageQuery.build(), wrapper);
        return TableDataInfo.build(page);
    }

    @Override
    public Boolean cancelUserAll(Long postId, Long[] userIds) {
        int rows = userPostMapper.delete(new LambdaQueryWrapper<SysUserPost>()
                .eq(SysUserPost::getPostId, postId)
                .in(SysUserPost::getUserId, Arrays.asList(userIds)));
//        if (rows > 0) {
//            cleanOnlineUserByRole(roleId);
//        }
        return rows>0;
    }

    @Override
    public Boolean selectUserAll(Long postId, Long[] userIds) {
        int rows = 0;
        List<SysUserPost> list = StreamUtils.toList(List.of(userIds), userId -> {
            SysUserPost ur = new SysUserPost();
            ur.setUserId(userId);
            ur.setPostId(postId);
            return ur;
        });
        if (CollUtil.isNotEmpty(list)) {
            rows = userPostMapper.insertBatch(list) ? list.size() : 0;
        }
//        if (rows > 0) {
//            cleanOnlineUserByRole(roleId);
//        }
        return rows>0;
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
}
