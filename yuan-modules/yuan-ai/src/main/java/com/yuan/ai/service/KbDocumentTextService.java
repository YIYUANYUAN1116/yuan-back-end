package com.yuan.ai.service;

import com.yuan.ai.domain.vo.KbDocumentTextVo;
import com.yuan.ai.domain.bo.KbDocumentTextBo;
    import com.yuan.core.page.TableDataInfo;
    import com.yuan.core.page.PageQuery;

import java.util.Collection;
import java.util.List;

/**
 * 知识库文档解析文本表Service接口
 *
 * @author yuan
 * @date Fri May 01 16:08:34 CST 2026
 */
public interface KbDocumentTextService {

    /**
     * 查询知识库文档解析文本表
     */
        KbDocumentTextVo queryById(Long textId);

        /**
         * 查询知识库文档解析文本表列表
         */
        TableDataInfo<KbDocumentTextVo> queryPageList(KbDocumentTextBo bo, PageQuery pageQuery);

    /**
     * 查询知识库文档解析文本表列表
     */
    List<KbDocumentTextVo> queryList(KbDocumentTextBo bo);

    /**
     * 新增知识库文档解析文本表
     */
    Boolean insertByBo(KbDocumentTextBo bo);

    /**
     * 修改知识库文档解析文本表
     */
    Boolean updateByBo(KbDocumentTextBo bo);

    /**
     * 校验并批量删除知识库文档解析文本表信息
     */
    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);
}
