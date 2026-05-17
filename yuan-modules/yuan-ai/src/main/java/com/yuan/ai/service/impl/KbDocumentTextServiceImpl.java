package com.yuan.ai.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yuan.ai.domain.KbDocumentText;
import com.yuan.ai.domain.bo.KbDocumentTextBo;
import com.yuan.ai.domain.vo.KbDocumentTextVo;
import com.yuan.ai.mapper.KbDocumentTextMapper;
import com.yuan.ai.service.KbDocumentTextService;
import com.yuan.common.core.utils.MapstructUtils;
import com.yuan.common.core.utils.StringUtils;
import com.yuan.core.page.PageQuery;
import com.yuan.core.page.TableDataInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

/**
 * 知识库文档解析文本表Service业务层处理
 *
 * @author yuan
 * @date Fri May 01 16:08:34 CST 2026
 */
@RequiredArgsConstructor
@Service
public class KbDocumentTextServiceImpl implements KbDocumentTextService {

    private final KbDocumentTextMapper baseMapper;

    /**
     * 查询知识库文档解析文本表
     */
    @Override
    public KbDocumentTextVo queryById(Long textId) {
        return baseMapper.selectVoById(textId);
    }

        /**
         * 查询知识库文档解析文本表列表
         */
        @Override
        public TableDataInfo<KbDocumentTextVo> queryPageList(KbDocumentTextBo bo, PageQuery pageQuery) {
            LambdaQueryWrapper<KbDocumentText> lqw = buildQueryWrapper(bo);
            Page<KbDocumentTextVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
            return TableDataInfo.build(result);
        }

    /**
     * 查询知识库文档解析文本表列表
     */
    @Override
    public List<KbDocumentTextVo> queryList(KbDocumentTextBo bo) {
        LambdaQueryWrapper<KbDocumentText> lqw = buildQueryWrapper(bo);
        return baseMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<KbDocumentText> buildQueryWrapper(KbDocumentTextBo bo) {
        LambdaQueryWrapper<KbDocumentText> lqw = Wrappers.lambdaQuery();
                    lqw.eq(bo.getTextId() != null, KbDocumentText::getTextId, bo.getTextId());
                    lqw.eq(StringUtils.isNotBlank(bo.getTenantId()), KbDocumentText::getTenantId, bo.getTenantId());
                    lqw.eq(bo.getKbId() != null, KbDocumentText::getKbId, bo.getKbId());
                    lqw.eq(bo.getDocId() != null, KbDocumentText::getDocId, bo.getDocId());
                    lqw.eq(StringUtils.isNotBlank(bo.getRawText()), KbDocumentText::getRawText, bo.getRawText());
                    lqw.eq(StringUtils.isNotBlank(bo.getCleanText()), KbDocumentText::getCleanText, bo.getCleanText());
                    lqw.eq(StringUtils.isNotBlank(bo.getParserType()), KbDocumentText::getParserType, bo.getParserType());
                    lqw.eq(StringUtils.isNotBlank(bo.getParserVersion()), KbDocumentText::getParserVersion, bo.getParserVersion());
                    lqw.eq(bo.getCharCount() != null, KbDocumentText::getCharCount, bo.getCharCount());
                    lqw.eq(bo.getTokenCount() != null, KbDocumentText::getTokenCount, bo.getTokenCount());
                    lqw.eq(StringUtils.isNotBlank(bo.getStatus()), KbDocumentText::getStatus, bo.getStatus());
                    lqw.eq(StringUtils.isNotBlank(bo.getCreateBy()), KbDocumentText::getCreateBy, bo.getCreateBy());
                    lqw.eq(bo.getCreateTime() != null, KbDocumentText::getCreateTime, bo.getCreateTime());
                    lqw.eq(StringUtils.isNotBlank(bo.getUpdateBy()), KbDocumentText::getUpdateBy, bo.getUpdateBy());
                    lqw.eq(bo.getUpdateTime() != null, KbDocumentText::getUpdateTime, bo.getUpdateTime());

        return lqw;
    }

    /**
     * 新增知识库文档解析文本表
     */
    @Override
    public Boolean insertByBo(KbDocumentTextBo bo) {
        KbDocumentText add = MapstructUtils.convert(bo, KbDocumentText. class);
        validEntityBeforeSave(add);
        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            bo.setTextId(add.getTextId());
        }
        return flag;
    }

    /**
     * 修改知识库文档解析文本表
     */
    @Override
    public Boolean updateByBo(KbDocumentTextBo bo) {
        KbDocumentText update = MapstructUtils.convert(bo, KbDocumentText. class);
        validEntityBeforeSave(update);
        return baseMapper.updateById(update) > 0;
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(KbDocumentText entity) {
        //TODO 做一些数据校验,如唯一约束
    }

    /**
     * 批量删除知识库文档解析文本表
     */
    @Override
    public Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid) {
        if (isValid) {
            //TODO 做一些业务上的校验,判断是否需要校验
        }
        return baseMapper.deleteBatchIds(ids) > 0;
    }
}
