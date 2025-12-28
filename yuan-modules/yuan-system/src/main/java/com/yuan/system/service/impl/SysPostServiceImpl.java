package com.yuan.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yuan.common.core.utils.MapstructUtils;
import com.yuan.common.core.utils.StringUtils;
import com.yuan.core.page.PageQuery;
import com.yuan.core.page.TableDataInfo;
import com.yuan.system.domain.SysPost;
import com.yuan.system.domain.bo.SysPostBo;
import com.yuan.system.domain.vo.SysPostVo;
import com.yuan.system.mapper.SysPostMapper;
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
}
