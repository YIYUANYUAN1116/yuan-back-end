package com.yuan.system.service.impl;

import com.yuan.common.core.utils.MapstructUtils;
    import com.yuan.core.page.TableDataInfo;
    import com.yuan.core.page.PageQuery;
    import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.yuan.system.domain.bo.SysUserPostBo;
import com.yuan.system.domain.vo.SysUserPostVo;
import com.yuan.system.domain.SysUserPost;
import com.yuan.system.mapper.SysUserPostMapper;
import com.yuan.system.service.SysUserPostService;
import com.yuan.common.core.utils.StringUtils;

import java.util.List;
import java.util.Map;
import java.util.Collection;

/**
 * post-userService业务层处理
 *
 
 * @date Mon Dec 22 15:05:48 CST 2025
 */
@RequiredArgsConstructor
@Service
public class SysUserPostServiceImpl implements SysUserPostService {

    private final SysUserPostMapper baseMapper;

    /**
     * 查询post-user
     */
    @Override
    public SysUserPostVo queryById(Long postId) {
        return baseMapper.selectVoById(postId);
    }

        /**
         * 查询post-user列表
         */
        @Override
        public TableDataInfo<SysUserPostVo> queryPageList(SysUserPostBo bo, PageQuery pageQuery) {
            LambdaQueryWrapper<SysUserPost> lqw = buildQueryWrapper(bo);
            Page<SysUserPostVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
            return TableDataInfo.build(result);
        }

    /**
     * 查询post-user列表
     */
    @Override
    public List<SysUserPostVo> queryList(SysUserPostBo bo) {
        LambdaQueryWrapper<SysUserPost> lqw = buildQueryWrapper(bo);
        return baseMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<SysUserPost> buildQueryWrapper(SysUserPostBo bo) {
        LambdaQueryWrapper<SysUserPost> lqw = Wrappers.lambdaQuery();
                    lqw.eq(bo.getUserId() != null, SysUserPost::getUserId, bo.getUserId());
                    lqw.eq(bo.getPostId() != null, SysUserPost::getPostId, bo.getPostId());
        return lqw;
    }

    /**
     * 新增post-user
     */
    @Override
    public Boolean insertByBo(SysUserPostBo bo) {
        SysUserPost add = MapstructUtils.convert(bo, SysUserPost. class);
        validEntityBeforeSave(add);
        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            bo.setPostId(add.getPostId());
        }
        return flag;
    }

    /**
     * 修改post-user
     */
    @Override
    public Boolean updateByBo(SysUserPostBo bo) {
        SysUserPost update = MapstructUtils.convert(bo, SysUserPost. class);
        validEntityBeforeSave(update);
        return baseMapper.updateById(update) > 0;
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(SysUserPost entity) {
        //TODO 做一些数据校验,如唯一约束
    }

    /**
     * 批量删除post-user
     */
    @Override
    public Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid) {
        if (isValid) {
            //TODO 做一些业务上的校验,判断是否需要校验
        }
        return baseMapper.deleteBatchIds(ids) > 0;
    }
}
