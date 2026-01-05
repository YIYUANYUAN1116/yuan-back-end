package com.yuan.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yuan.common.core.utils.MapstructUtils;
import com.yuan.core.page.PageQuery;
import com.yuan.core.page.TableDataInfo;
import com.yuan.system.domain.SysRolePost;
import com.yuan.system.domain.bo.SysRolePostBo;
import com.yuan.system.domain.vo.SysRolePostVo;
import com.yuan.system.mapper.SysRolePostMapper;
import com.yuan.system.service.SysRolePostService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

/**
 * sys_role_postService业务层处理
 *
 * @author yuan
 * @date Mon Jan 05 20:10:39 CST 2026
 */
@RequiredArgsConstructor
@Service
public class SysRolePostServiceImpl implements SysRolePostService {

    private final SysRolePostMapper baseMapper;

    /**
     * 查询sys_role_post
     */
    @Override
    public SysRolePostVo queryById(Long roleId) {
        return baseMapper.selectVoById(roleId);
    }

        /**
         * 查询sys_role_post列表
         */
        @Override
        public TableDataInfo<SysRolePostVo> queryPageList(SysRolePostBo bo, PageQuery pageQuery) {
            LambdaQueryWrapper<SysRolePost> lqw = buildQueryWrapper(bo);
            Page<SysRolePostVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
            return TableDataInfo.build(result);
        }

    /**
     * 查询sys_role_post列表
     */
    @Override
    public List<SysRolePostVo> queryList(SysRolePostBo bo) {
        LambdaQueryWrapper<SysRolePost> lqw = buildQueryWrapper(bo);
        return baseMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<SysRolePost> buildQueryWrapper(SysRolePostBo bo) {
        LambdaQueryWrapper<SysRolePost> lqw = Wrappers.lambdaQuery();
                    lqw.eq(bo.getPostId() != null, SysRolePost::getPostId, bo.getPostId());
                    lqw.eq(bo.getRoleId() != null, SysRolePost::getRoleId, bo.getRoleId());
        return lqw;
    }

    /**
     * 新增sys_role_post
     */
    @Override
    public Boolean insertByBo(SysRolePostBo bo) {
        SysRolePost add = MapstructUtils.convert(bo, SysRolePost. class);
        validEntityBeforeSave(add);
        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            bo.setRoleId(add.getRoleId());
        }
        return flag;
    }

    /**
     * 修改sys_role_post
     */
    @Override
    public Boolean updateByBo(SysRolePostBo bo) {
        SysRolePost update = MapstructUtils.convert(bo, SysRolePost. class);
        validEntityBeforeSave(update);
        return baseMapper.updateById(update) > 0;
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(SysRolePost entity) {
        //TODO 做一些数据校验,如唯一约束
    }

    /**
     * 批量删除sys_role_post
     */
    @Override
    public Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid) {
        if (isValid) {
            //TODO 做一些业务上的校验,判断是否需要校验
        }
        return baseMapper.deleteBatchIds(ids) > 0;
    }
}
