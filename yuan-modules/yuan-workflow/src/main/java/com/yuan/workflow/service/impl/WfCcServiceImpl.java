package com.yuan.workflow.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yuan.common.core.utils.MapstructUtils;
import com.yuan.common.core.utils.StringUtils;
import com.yuan.core.page.PageQuery;
import com.yuan.core.page.TableDataInfo;
import lombok.RequiredArgsConstructor;
import com.yuan.workflow.domain.WfCc;
import com.yuan.workflow.domain.bo.WfCcBo;
import com.yuan.workflow.domain.vo.WfCcVo;
import com.yuan.workflow.mapper.WfCcMapper;
import com.yuan.workflow.service.WfCcService;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

/**
 * wfccService业务层处理
 *
 
 * @date Sun Dec 28 11:26:25 CST 2025
 */
@RequiredArgsConstructor
@Service
public class WfCcServiceImpl implements WfCcService {

    private final WfCcMapper baseMapper;

    /**
     * 查询wfcc
     */
    @Override
    public WfCcVo queryById(Long id) {
        return baseMapper.selectVoById(id);
    }

        /**
         * 查询wfcc列表
         */
        @Override
        public TableDataInfo<WfCcVo> queryPageList(WfCcBo bo, PageQuery pageQuery) {
            LambdaQueryWrapper<WfCc> lqw = buildQueryWrapper(bo);
            Page<WfCcVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
            return TableDataInfo.build(result);
        }

    /**
     * 查询wfcc列表
     */
    @Override
    public List<WfCcVo> queryList(WfCcBo bo) {
        LambdaQueryWrapper<WfCc> lqw = buildQueryWrapper(bo);
        return baseMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<WfCc> buildQueryWrapper(WfCcBo bo) {
        LambdaQueryWrapper<WfCc> lqw = Wrappers.lambdaQuery();
                    lqw.eq(bo.getId() != null, WfCc::getId, bo.getId());
                    lqw.eq(bo.getTenantId() != null, WfCc::getTenantId, bo.getTenantId());
                    lqw.eq(bo.getInstanceId() != null, WfCc::getInstanceId, bo.getInstanceId());
                    lqw.eq(bo.getUserId() != null, WfCc::getUserId, bo.getUserId());
                    lqw.eq(StringUtils.isNotBlank(bo.getReadFlag()), WfCc::getReadFlag, bo.getReadFlag());
                    lqw.eq(bo.getCreateTime() != null, WfCc::getCreateTime, bo.getCreateTime());
        return lqw;
    }

    /**
     * 新增wfcc
     */
    @Override
    public Boolean insertByBo(WfCcBo bo) {
        WfCc add = MapstructUtils.convert(bo, WfCc. class);
        validEntityBeforeSave(add);
        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            bo.setId(add.getId());
        }
        return flag;
    }

    /**
     * 修改wfcc
     */
    @Override
    public Boolean updateByBo(WfCcBo bo) {
        WfCc update = MapstructUtils.convert(bo, WfCc. class);
        validEntityBeforeSave(update);
        return baseMapper.updateById(update) > 0;
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(WfCc entity) {
        //TODO 做一些数据校验,如唯一约束
    }

    /**
     * 批量删除wfcc
     */
    @Override
    public Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid) {
        if (isValid) {
            //TODO 做一些业务上的校验,判断是否需要校验
        }
        return baseMapper.deleteBatchIds(ids) > 0;
    }
}
