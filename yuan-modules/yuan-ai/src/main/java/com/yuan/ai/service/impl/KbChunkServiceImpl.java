package com.yuan.ai.service.impl;

import com.yuan.common.core.utils.MapstructUtils;
    import com.yuan.core.page.TableDataInfo;
    import com.yuan.core.page.PageQuery;
    import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.yuan.ai.domain.bo.KbChunkBo;
import com.yuan.ai.domain.vo.KbChunkVo;
import com.yuan.ai.domain.KbChunk;
import com.yuan.ai.mapper.KbChunkMapper;
import com.yuan.ai.service.KbChunkService;
import com.yuan.common.core.utils.StringUtils;

import java.util.List;
import java.util.Map;
import java.util.Collection;

/**
 * 知识库文档切片表Service业务层处理
 *
 * @author yuan
 * @date Fri May 01 16:08:37 CST 2026
 */
@RequiredArgsConstructor
@Service
public class KbChunkServiceImpl implements KbChunkService {

    private final KbChunkMapper baseMapper;

    /**
     * 查询知识库文档切片表
     */
    @Override
    public KbChunkVo queryById(Long chunkId) {
        return baseMapper.selectVoById(chunkId);
    }

        /**
         * 查询知识库文档切片表列表
         */
        @Override
        public TableDataInfo<KbChunkVo> queryPageList(KbChunkBo bo, PageQuery pageQuery) {
            LambdaQueryWrapper<KbChunk> lqw = buildQueryWrapper(bo);
            Page<KbChunkVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
            return TableDataInfo.build(result);
        }

    /**
     * 查询知识库文档切片表列表
     */
    @Override
    public List<KbChunkVo> queryList(KbChunkBo bo) {
        LambdaQueryWrapper<KbChunk> lqw = buildQueryWrapper(bo);
        return baseMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<KbChunk> buildQueryWrapper(KbChunkBo bo) {
        LambdaQueryWrapper<KbChunk> lqw = Wrappers.lambdaQuery();
                    lqw.eq(bo.getChunkId() != null, KbChunk::getChunkId, bo.getChunkId());
                    lqw.eq(StringUtils.isNotBlank(bo.getTenantId()), KbChunk::getTenantId, bo.getTenantId());
                    lqw.eq(bo.getKbId() != null, KbChunk::getKbId, bo.getKbId());
                    lqw.eq(bo.getDocId() != null, KbChunk::getDocId, bo.getDocId());
                    lqw.eq(bo.getChunkNo() != null, KbChunk::getChunkNo, bo.getChunkNo());
                    lqw.eq(StringUtils.isNotBlank(bo.getChunkTitle()), KbChunk::getChunkTitle, bo.getChunkTitle());
                    lqw.eq(StringUtils.isNotBlank(bo.getContent()), KbChunk::getContent, bo.getContent());
                    lqw.eq(StringUtils.isNotBlank(bo.getContentHash()), KbChunk::getContentHash, bo.getContentHash());
                    lqw.eq(bo.getTokenCount() != null, KbChunk::getTokenCount, bo.getTokenCount());
                    lqw.eq(bo.getCharCount() != null, KbChunk::getCharCount, bo.getCharCount());
                    lqw.eq(bo.getPageNo() != null, KbChunk::getPageNo, bo.getPageNo());
                    lqw.eq(StringUtils.isNotBlank(bo.getSectionTitle()), KbChunk::getSectionTitle, bo.getSectionTitle());
                    lqw.eq(bo.getStartOffset() != null, KbChunk::getStartOffset, bo.getStartOffset());
                    lqw.eq(bo.getEndOffset() != null, KbChunk::getEndOffset, bo.getEndOffset());
                    lqw.eq(StringUtils.isNotBlank(bo.getEmbeddingStatus()), KbChunk::getEmbeddingStatus, bo.getEmbeddingStatus());
                    lqw.eq(bo.getEmbeddingId() != null, KbChunk::getEmbeddingId, bo.getEmbeddingId());
                    lqw.eq(StringUtils.isNotBlank(bo.getStatus()), KbChunk::getStatus, bo.getStatus());
                    lqw.eq(StringUtils.isNotBlank(bo.getCreateBy()), KbChunk::getCreateBy, bo.getCreateBy());
                    lqw.eq(bo.getCreateTime() != null, KbChunk::getCreateTime, bo.getCreateTime());
                    lqw.eq(StringUtils.isNotBlank(bo.getUpdateBy()), KbChunk::getUpdateBy, bo.getUpdateBy());
                    lqw.eq(bo.getUpdateTime() != null, KbChunk::getUpdateTime, bo.getUpdateTime());
                    lqw.eq(StringUtils.isNotBlank(bo.getDelFlag()), KbChunk::getDelFlag, bo.getDelFlag());
        return lqw;
    }

    /**
     * 新增知识库文档切片表
     */
    @Override
    public Boolean insertByBo(KbChunkBo bo) {
        KbChunk add = MapstructUtils.convert(bo, KbChunk. class);
        validEntityBeforeSave(add);
        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            bo.setChunkId(add.getChunkId());
        }
        return flag;
    }

    /**
     * 修改知识库文档切片表
     */
    @Override
    public Boolean updateByBo(KbChunkBo bo) {
        KbChunk update = MapstructUtils.convert(bo, KbChunk. class);
        validEntityBeforeSave(update);
        return baseMapper.updateById(update) > 0;
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(KbChunk entity) {
        //TODO 做一些数据校验,如唯一约束
    }

    /**
     * 批量删除知识库文档切片表
     */
    @Override
    public Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid) {
        if (isValid) {
            //TODO 做一些业务上的校验,判断是否需要校验
        }
        return baseMapper.deleteBatchIds(ids) > 0;
    }
}
