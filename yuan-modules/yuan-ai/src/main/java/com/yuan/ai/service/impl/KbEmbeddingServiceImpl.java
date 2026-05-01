package com.yuan.ai.service.impl;

import com.yuan.common.core.utils.MapstructUtils;
    import com.yuan.core.page.TableDataInfo;
    import com.yuan.core.page.PageQuery;
    import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.yuan.ai.domain.bo.KbEmbeddingBo;
import com.yuan.ai.domain.vo.KbEmbeddingVo;
import com.yuan.ai.domain.KbEmbedding;
import com.yuan.ai.mapper.KbEmbeddingMapper;
import com.yuan.ai.service.KbEmbeddingService;
import com.yuan.common.core.utils.StringUtils;

import java.util.List;
import java.util.Map;
import java.util.Collection;

/**
 * 知识库向量元数据表Service业务层处理
 *
 * @author yuan
 * @date Fri May 01 16:08:40 CST 2026
 */
@RequiredArgsConstructor
@Service
public class KbEmbeddingServiceImpl implements KbEmbeddingService {

    private final KbEmbeddingMapper baseMapper;

    /**
     * 查询知识库向量元数据表
     */
    @Override
    public KbEmbeddingVo queryById(Long embeddingId) {
        return baseMapper.selectVoById(embeddingId);
    }

        /**
         * 查询知识库向量元数据表列表
         */
        @Override
        public TableDataInfo<KbEmbeddingVo> queryPageList(KbEmbeddingBo bo, PageQuery pageQuery) {
            LambdaQueryWrapper<KbEmbedding> lqw = buildQueryWrapper(bo);
            Page<KbEmbeddingVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
            return TableDataInfo.build(result);
        }

    /**
     * 查询知识库向量元数据表列表
     */
    @Override
    public List<KbEmbeddingVo> queryList(KbEmbeddingBo bo) {
        LambdaQueryWrapper<KbEmbedding> lqw = buildQueryWrapper(bo);
        return baseMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<KbEmbedding> buildQueryWrapper(KbEmbeddingBo bo) {
        LambdaQueryWrapper<KbEmbedding> lqw = Wrappers.lambdaQuery();
                    lqw.eq(bo.getEmbeddingId() != null, KbEmbedding::getEmbeddingId, bo.getEmbeddingId());
                    lqw.eq(StringUtils.isNotBlank(bo.getTenantId()), KbEmbedding::getTenantId, bo.getTenantId());
                    lqw.eq(bo.getKbId() != null, KbEmbedding::getKbId, bo.getKbId());
                    lqw.eq(bo.getDocId() != null, KbEmbedding::getDocId, bo.getDocId());
                    lqw.eq(bo.getChunkId() != null, KbEmbedding::getChunkId, bo.getChunkId());
                    lqw.eq(bo.getModelId() != null, KbEmbedding::getModelId, bo.getModelId());
                    lqw.eq(StringUtils.isNotBlank(bo.getModelCode()), KbEmbedding::getModelCode, bo.getModelCode());
                    lqw.eq(StringUtils.isNotBlank(bo.getVectorStore()), KbEmbedding::getVectorStore, bo.getVectorStore());
                    lqw.eq(StringUtils.isNotBlank(bo.getCollectionName()), KbEmbedding::getCollectionName, bo.getCollectionName());
                    lqw.eq(StringUtils.isNotBlank(bo.getVectorId()), KbEmbedding::getVectorId, bo.getVectorId());
                    lqw.eq(bo.getVectorDim() != null, KbEmbedding::getVectorDim, bo.getVectorDim());
                    lqw.eq(StringUtils.isNotBlank(bo.getContentHash()), KbEmbedding::getContentHash, bo.getContentHash());
                    lqw.eq(StringUtils.isNotBlank(bo.getEmbedVersion()), KbEmbedding::getEmbedVersion, bo.getEmbedVersion());
                    lqw.eq(StringUtils.isNotBlank(bo.getStatus()), KbEmbedding::getStatus, bo.getStatus());
                    lqw.eq(StringUtils.isNotBlank(bo.getErrorMessage()), KbEmbedding::getErrorMessage, bo.getErrorMessage());
                    lqw.eq(StringUtils.isNotBlank(bo.getCreateBy()), KbEmbedding::getCreateBy, bo.getCreateBy());
                    lqw.eq(bo.getCreateTime() != null, KbEmbedding::getCreateTime, bo.getCreateTime());
                    lqw.eq(StringUtils.isNotBlank(bo.getUpdateBy()), KbEmbedding::getUpdateBy, bo.getUpdateBy());
                    lqw.eq(bo.getUpdateTime() != null, KbEmbedding::getUpdateTime, bo.getUpdateTime());
                    lqw.eq(StringUtils.isNotBlank(bo.getDelFlag()), KbEmbedding::getDelFlag, bo.getDelFlag());
        return lqw;
    }

    /**
     * 新增知识库向量元数据表
     */
    @Override
    public Boolean insertByBo(KbEmbeddingBo bo) {
        KbEmbedding add = MapstructUtils.convert(bo, KbEmbedding. class);
        validEntityBeforeSave(add);
        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            bo.setEmbeddingId(add.getEmbeddingId());
        }
        return flag;
    }

    /**
     * 修改知识库向量元数据表
     */
    @Override
    public Boolean updateByBo(KbEmbeddingBo bo) {
        KbEmbedding update = MapstructUtils.convert(bo, KbEmbedding. class);
        validEntityBeforeSave(update);
        return baseMapper.updateById(update) > 0;
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(KbEmbedding entity) {
        //TODO 做一些数据校验,如唯一约束
    }

    /**
     * 批量删除知识库向量元数据表
     */
    @Override
    public Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid) {
        if (isValid) {
            //TODO 做一些业务上的校验,判断是否需要校验
        }
        return baseMapper.deleteBatchIds(ids) > 0;
    }
}
