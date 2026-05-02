package com.yuan.ai.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yuan.ai.domain.KbDocument;
import com.yuan.ai.domain.bo.KbDocumentBo;
import com.yuan.ai.domain.vo.KbDocumentVo;
import com.yuan.ai.mapper.KbDocumentMapper;
import com.yuan.ai.service.KbDocumentService;
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
 * 知识库文档表Service业务层处理
 *
 * @author yuan
 * @date Fri May 01 16:08:29 CST 2026
 */
@RequiredArgsConstructor
@Service
public class KbDocumentServiceImpl implements KbDocumentService {

    private final KbDocumentMapper baseMapper;


    /**
     * 查询知识库文档表
     */
    @Override
    public KbDocumentVo queryById(Long docId) {
        return baseMapper.selectVoById(docId);
    }

        /**
         * 查询知识库文档表列表
         */
        @Override
        public TableDataInfo<KbDocumentVo> queryPageList(KbDocumentBo bo, PageQuery pageQuery) {
            Page<KbDocumentVo> result = baseMapper.selectKbDocumentVoPage(bo, pageQuery.build());
            return TableDataInfo.build(result);
        }

    /**
     * 查询知识库文档表列表
     */
    @Override
    public List<KbDocumentVo> queryList(KbDocumentBo bo) {
        LambdaQueryWrapper<KbDocument> lqw = buildQueryWrapper(bo);
        return baseMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<KbDocument> buildQueryWrapper(KbDocumentBo bo) {
        LambdaQueryWrapper<KbDocument> lqw = Wrappers.lambdaQuery();
                    lqw.eq(bo.getDocId() != null, KbDocument::getDocId, bo.getDocId());
                    lqw.eq(StringUtils.isNotBlank(bo.getTenantId()), KbDocument::getTenantId, bo.getTenantId());
                    lqw.eq(bo.getKbId() != null, KbDocument::getKbId, bo.getKbId());
                    lqw.eq(bo.getFileId() != null, KbDocument::getFileId, bo.getFileId());
                    lqw.eq(StringUtils.isNotBlank(bo.getFileName()), KbDocument::getFileName, bo.getFileName());
                    lqw.eq(StringUtils.isNotBlank(bo.getFileType()), KbDocument::getFileType, bo.getFileType());
                    lqw.eq(bo.getFileSize() != null, KbDocument::getFileSize, bo.getFileSize());
                    lqw.eq(StringUtils.isNotBlank(bo.getFileUrl()), KbDocument::getFileUrl, bo.getFileUrl());
                    lqw.eq(StringUtils.isNotBlank(bo.getObjectKey()), KbDocument::getObjectKey, bo.getObjectKey());
                    lqw.eq(StringUtils.isNotBlank(bo.getTitle()), KbDocument::getTitle, bo.getTitle());
                    lqw.eq(StringUtils.isNotBlank(bo.getSourceType()), KbDocument::getSourceType, bo.getSourceType());
                    lqw.eq(StringUtils.isNotBlank(bo.getSourceUrl()), KbDocument::getSourceUrl, bo.getSourceUrl());
                    lqw.eq(StringUtils.isNotBlank(bo.getParseStatus()), KbDocument::getParseStatus, bo.getParseStatus());
                    lqw.eq(StringUtils.isNotBlank(bo.getEmbedStatus()), KbDocument::getEmbedStatus, bo.getEmbedStatus());
                    lqw.eq(bo.getChunkCount() != null, KbDocument::getChunkCount, bo.getChunkCount());
                    lqw.eq(bo.getTokenCount() != null, KbDocument::getTokenCount, bo.getTokenCount());
                    lqw.eq(bo.getCharCount() != null, KbDocument::getCharCount, bo.getCharCount());
                    lqw.eq(StringUtils.isNotBlank(bo.getContentHash()), KbDocument::getContentHash, bo.getContentHash());
                    lqw.eq(StringUtils.isNotBlank(bo.getErrorMessage()), KbDocument::getErrorMessage, bo.getErrorMessage());
                    lqw.eq(StringUtils.isNotBlank(bo.getStatus()), KbDocument::getStatus, bo.getStatus());
                    lqw.eq(StringUtils.isNotBlank(bo.getCreateBy()), KbDocument::getCreateBy, bo.getCreateBy());
                    lqw.eq(bo.getCreateTime() != null, KbDocument::getCreateTime, bo.getCreateTime());
                    lqw.eq(StringUtils.isNotBlank(bo.getUpdateBy()), KbDocument::getUpdateBy, bo.getUpdateBy());
                    lqw.eq(bo.getUpdateTime() != null, KbDocument::getUpdateTime, bo.getUpdateTime());
                    lqw.eq(StringUtils.isNotBlank(bo.getDelFlag()), KbDocument::getDelFlag, bo.getDelFlag());
        return lqw;
    }

    /**
     * 新增知识库文档表
     */
    @Override
    public Boolean insertByBo(KbDocumentBo bo) {
        KbDocument add = MapstructUtils.convert(bo, KbDocument. class);
        validEntityBeforeSave(add);
        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            bo.setDocId(add.getDocId());
        }
        return flag;
    }

    /**
     * 修改知识库文档表
     */
    @Override
    public Boolean updateByBo(KbDocumentBo bo) {
        KbDocument update = MapstructUtils.convert(bo, KbDocument. class);
        validEntityBeforeSave(update);
        return baseMapper.updateById(update) > 0;
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(KbDocument entity) {
        //TODO 做一些数据校验,如唯一约束
    }

    /**
     * 批量删除知识库文档表
     */
    @Override
    public Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid) {
        if (isValid) {
            //TODO 做一些业务上的校验,判断是否需要校验
        }
        return baseMapper.deleteBatchIds(ids) > 0;
    }

    @Override
    public List<SelectModel> selectKbDocument(Long kbId) {
        return baseMapper.selectKbDocument(kbId);
    }
}
