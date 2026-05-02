package com.yuan.ai.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yuan.ai.domain.KbBase;
import com.yuan.ai.domain.bo.KbBaseBo;
import com.yuan.ai.domain.vo.KbBaseVo;
import com.yuan.ai.mapper.KbBaseMapper;
import com.yuan.ai.service.KbBaseService;
import com.yuan.common.core.domain.model.SelectModel;
import com.yuan.common.core.utils.MapstructUtils;
import com.yuan.common.core.utils.StringUtils;
import com.yuan.core.page.PageQuery;
import com.yuan.core.page.TableDataInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

/**
 * 知识库主表Service业务层处理
 *
 * @author yuan
 * @date Fri May 01 16:08:21 CST 2026
 */
@RequiredArgsConstructor
@Service
public class KbBaseServiceImpl implements KbBaseService {

    private final KbBaseMapper baseMapper;

    /**
     * 查询知识库主表
     */
    @Override
    public KbBaseVo queryById(Long kbId) {
        return baseMapper.selectVoById(kbId);
    }

        /**
         * 查询知识库主表列表
         */
        @Override
        public TableDataInfo<KbBaseVo> queryPageList(KbBaseBo bo, PageQuery pageQuery) {
            Page<KbBaseVo> result = baseMapper.selectKbBaseVoPage(bo, pageQuery.build());
            return TableDataInfo.build(result);
        }

    /**
     * 查询知识库主表列表
     */
    @Override
    public List<KbBaseVo> queryList(KbBaseBo bo) {
        LambdaQueryWrapper<KbBase> lqw = buildQueryWrapper(bo);
        return baseMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<KbBase> buildQueryWrapper(KbBaseBo bo) {
        LambdaQueryWrapper<KbBase> lqw = Wrappers.lambdaQuery();
                    lqw.eq(bo.getKbId() != null, KbBase::getKbId, bo.getKbId());
                    lqw.eq(StringUtils.isNotBlank(bo.getTenantId()), KbBase::getTenantId, bo.getTenantId());
                    lqw.eq(StringUtils.isNotBlank(bo.getKbCode()), KbBase::getKbCode, bo.getKbCode());
                    lqw.eq(StringUtils.isNotBlank(bo.getKbName()), KbBase::getKbName, bo.getKbName());
                    lqw.eq(StringUtils.isNotBlank(bo.getDescription()), KbBase::getDescription, bo.getDescription());
                    lqw.eq(StringUtils.isNotBlank(bo.getVisibility()), KbBase::getVisibility, bo.getVisibility());
                    lqw.eq(StringUtils.isNotBlank(bo.getOwnerId()), KbBase::getOwnerId, bo.getOwnerId());
                    lqw.eq(bo.getEmbeddingModelId() != null, KbBase::getEmbeddingModelId, bo.getEmbeddingModelId());
                    lqw.eq(StringUtils.isNotBlank(bo.getChunkStrategy()), KbBase::getChunkStrategy, bo.getChunkStrategy());
                    lqw.eq(bo.getChunkSize() != null, KbBase::getChunkSize, bo.getChunkSize());
                    lqw.eq(bo.getChunkOverlap() != null, KbBase::getChunkOverlap, bo.getChunkOverlap());
                    lqw.eq(StringUtils.isNotBlank(bo.getStatus()), KbBase::getStatus, bo.getStatus());

        return lqw;
    }

    /**
     * 新增知识库主表
     */
    @Override
    public Boolean insertByBo(KbBaseBo bo) {
        KbBase add = MapstructUtils.convert(bo, KbBase. class);
        validEntityBeforeSave(add);
        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            bo.setKbId(add.getKbId());
        }
        return flag;
    }

    /**
     * 修改知识库主表
     */
    @Override
    public Boolean updateByBo(KbBaseBo bo) {
        KbBase update = MapstructUtils.convert(bo, KbBase. class);
        validEntityBeforeSave(update);
        return baseMapper.updateById(update) > 0;
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(KbBase entity) {
        //TODO 做一些数据校验,如唯一约束
    }

    /**
     * 批量删除知识库主表
     */
    @Override
    public Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid) {
        if (isValid) {
            //TODO 做一些业务上的校验,判断是否需要校验
        }
        return baseMapper.deleteBatchIds(ids) > 0;
    }

    @Override
    public List<SelectModel> selectKb() {
        return baseMapper.selectKb();
    }
}
