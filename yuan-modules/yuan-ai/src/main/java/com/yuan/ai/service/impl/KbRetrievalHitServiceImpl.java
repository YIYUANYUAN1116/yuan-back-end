package com.yuan.ai.service.impl;

import com.yuan.common.core.utils.MapstructUtils;
    import com.yuan.core.page.TableDataInfo;
    import com.yuan.core.page.PageQuery;
    import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.yuan.ai.domain.bo.KbRetrievalHitBo;
import com.yuan.ai.domain.vo.KbRetrievalHitVo;
import com.yuan.ai.domain.KbRetrievalHit;
import com.yuan.ai.mapper.KbRetrievalHitMapper;
import com.yuan.ai.service.KbRetrievalHitService;
import com.yuan.common.core.utils.StringUtils;

import java.util.List;
import java.util.Map;
import java.util.Collection;

/**
 * 知识库检索命中明细表Service业务层处理
 *
 * @author yuan
 * @date Fri May 01 16:08:50 CST 2026
 */
@RequiredArgsConstructor
@Service
public class KbRetrievalHitServiceImpl implements KbRetrievalHitService {

    private final KbRetrievalHitMapper baseMapper;

    /**
     * 查询知识库检索命中明细表
     */
    @Override
    public KbRetrievalHitVo queryById(Long hitId) {
        return baseMapper.selectVoById(hitId);
    }

        /**
         * 查询知识库检索命中明细表列表
         */
        @Override
        public TableDataInfo<KbRetrievalHitVo> queryPageList(KbRetrievalHitBo bo, PageQuery pageQuery) {
            LambdaQueryWrapper<KbRetrievalHit> lqw = buildQueryWrapper(bo);
            Page<KbRetrievalHitVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
            return TableDataInfo.build(result);
        }

    /**
     * 查询知识库检索命中明细表列表
     */
    @Override
    public List<KbRetrievalHitVo> queryList(KbRetrievalHitBo bo) {
        LambdaQueryWrapper<KbRetrievalHit> lqw = buildQueryWrapper(bo);
        return baseMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<KbRetrievalHit> buildQueryWrapper(KbRetrievalHitBo bo) {
        LambdaQueryWrapper<KbRetrievalHit> lqw = Wrappers.lambdaQuery();
                    lqw.eq(bo.getHitId() != null, KbRetrievalHit::getHitId, bo.getHitId());
                    lqw.eq(StringUtils.isNotBlank(bo.getTenantId()), KbRetrievalHit::getTenantId, bo.getTenantId());
                    lqw.eq(bo.getLogId() != null, KbRetrievalHit::getLogId, bo.getLogId());
                    lqw.eq(bo.getKbId() != null, KbRetrievalHit::getKbId, bo.getKbId());
                    lqw.eq(bo.getDocId() != null, KbRetrievalHit::getDocId, bo.getDocId());
                    lqw.eq(bo.getChunkId() != null, KbRetrievalHit::getChunkId, bo.getChunkId());
                    lqw.eq(bo.getRankNo() != null, KbRetrievalHit::getRankNo, bo.getRankNo());
                    lqw.eq(bo.getScore() != null, KbRetrievalHit::getScore, bo.getScore());
                    lqw.eq(bo.getRerankScore() != null, KbRetrievalHit::getRerankScore, bo.getRerankScore());
                    lqw.eq(bo.getUsedInPrompt() != null, KbRetrievalHit::getUsedInPrompt, bo.getUsedInPrompt());
                    lqw.eq(StringUtils.isNotBlank(bo.getContentPreview()), KbRetrievalHit::getContentPreview, bo.getContentPreview());
                    lqw.eq(StringUtils.isNotBlank(bo.getStatus()), KbRetrievalHit::getStatus, bo.getStatus());
                    lqw.eq(StringUtils.isNotBlank(bo.getCreateBy()), KbRetrievalHit::getCreateBy, bo.getCreateBy());
                    lqw.eq(bo.getCreateTime() != null, KbRetrievalHit::getCreateTime, bo.getCreateTime());
                    lqw.eq(StringUtils.isNotBlank(bo.getUpdateBy()), KbRetrievalHit::getUpdateBy, bo.getUpdateBy());
                    lqw.eq(bo.getUpdateTime() != null, KbRetrievalHit::getUpdateTime, bo.getUpdateTime());
                    lqw.eq(StringUtils.isNotBlank(bo.getDelFlag()), KbRetrievalHit::getDelFlag, bo.getDelFlag());
        return lqw;
    }

    /**
     * 新增知识库检索命中明细表
     */
    @Override
    public Boolean insertByBo(KbRetrievalHitBo bo) {
        KbRetrievalHit add = MapstructUtils.convert(bo, KbRetrievalHit. class);
        validEntityBeforeSave(add);
        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            bo.setHitId(add.getHitId());
        }
        return flag;
    }

    /**
     * 修改知识库检索命中明细表
     */
    @Override
    public Boolean updateByBo(KbRetrievalHitBo bo) {
        KbRetrievalHit update = MapstructUtils.convert(bo, KbRetrievalHit. class);
        validEntityBeforeSave(update);
        return baseMapper.updateById(update) > 0;
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(KbRetrievalHit entity) {
        //TODO 做一些数据校验,如唯一约束
    }

    /**
     * 批量删除知识库检索命中明细表
     */
    @Override
    public Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid) {
        if (isValid) {
            //TODO 做一些业务上的校验,判断是否需要校验
        }
        return baseMapper.deleteBatchIds(ids) > 0;
    }
}
