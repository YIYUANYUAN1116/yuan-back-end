package com.yuan.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yuan.common.core.utils.MapstructUtils;
import com.yuan.common.core.utils.StringUtils;
import com.yuan.core.page.PageQuery;
import com.yuan.core.page.TableDataInfo;
import com.yuan.system.domain.SysBizNoSeq;
import com.yuan.system.domain.bo.SysBizNoSeqBo;
import com.yuan.system.domain.vo.SysBizNoSeqVo;
import com.yuan.system.mapper.SysBizNoSeqMapper;
import com.yuan.system.service.SysBizNoSeqService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

/**
 * 业务序列表Service业务层处理
 *
 * @author yuan
 * @date Fri Jan 16 16:53:43 CST 2026
 */
@RequiredArgsConstructor
@Service
public class SysBizNoSeqServiceImpl implements SysBizNoSeqService {

    private final SysBizNoSeqMapper baseMapper;

    /**
     * 查询业务序列表
     */
    @Override
    public SysBizNoSeqVo queryById(Long id) {
        return baseMapper.selectVoById(id);
    }

        /**
         * 查询业务序列表列表
         */
        @Override
        public TableDataInfo<SysBizNoSeqVo> queryPageList(SysBizNoSeqBo bo, PageQuery pageQuery) {
            LambdaQueryWrapper<SysBizNoSeq> lqw = buildQueryWrapper(bo);
            Page<SysBizNoSeqVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
            return TableDataInfo.build(result);
        }

    /**
     * 查询业务序列表列表
     */
    @Override
    public List<SysBizNoSeqVo> queryList(SysBizNoSeqBo bo) {
        LambdaQueryWrapper<SysBizNoSeq> lqw = buildQueryWrapper(bo);
        return baseMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<SysBizNoSeq> buildQueryWrapper(SysBizNoSeqBo bo) {
        LambdaQueryWrapper<SysBizNoSeq> lqw = Wrappers.lambdaQuery();
                    lqw.eq(bo.getId() != null, SysBizNoSeq::getId, bo.getId());
                    lqw.eq(StringUtils.isNotBlank(bo.getTenantId()), SysBizNoSeq::getTenantId, bo.getTenantId());
                    lqw.eq(StringUtils.isNotBlank(bo.getBizPrefix()), SysBizNoSeq::getBizPrefix, bo.getBizPrefix());
                    lqw.eq(StringUtils.isNotBlank(bo.getBizDate()), SysBizNoSeq::getBizDate, bo.getBizDate());
                    lqw.eq(bo.getCurrentNo() != null, SysBizNoSeq::getCurrentNo, bo.getCurrentNo());
                    lqw.eq(bo.getUpdateTime() != null, SysBizNoSeq::getUpdateTime, bo.getUpdateTime());
                    lqw.eq(bo.getCreateDept() != null, SysBizNoSeq::getCreateDept, bo.getCreateDept());
                    lqw.eq(bo.getCreateBy() != null, SysBizNoSeq::getCreateBy, bo.getCreateBy());
                    lqw.eq(bo.getCreateTime() != null, SysBizNoSeq::getCreateTime, bo.getCreateTime());
                    lqw.eq(bo.getUpdateBy() != null, SysBizNoSeq::getUpdateBy, bo.getUpdateBy());
        return lqw;
    }

    /**
     * 新增业务序列表
     */
    @Override
    public Boolean insertByBo(SysBizNoSeqBo bo) {
        SysBizNoSeq add = MapstructUtils.convert(bo, SysBizNoSeq. class);
        validEntityBeforeSave(add);
        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            bo.setId(add.getId());
        }
        return flag;
    }

    /**
     * 修改业务序列表
     */
    @Override
    public Boolean updateByBo(SysBizNoSeqBo bo) {
        SysBizNoSeq update = MapstructUtils.convert(bo, SysBizNoSeq. class);
        validEntityBeforeSave(update);
        return baseMapper.updateById(update) > 0;
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(SysBizNoSeq entity) {
        //TODO 做一些数据校验,如唯一约束
    }

    /**
     * 批量删除业务序列表
     */
    @Override
    public Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid) {
        if (isValid) {
            //TODO 做一些业务上的校验,判断是否需要校验
        }
        return baseMapper.deleteBatchIds(ids) > 0;
    }
}
