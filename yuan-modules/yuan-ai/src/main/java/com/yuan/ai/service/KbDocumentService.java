package com.yuan.ai.service;

import com.yuan.ai.domain.vo.KbDocumentVo;
import com.yuan.ai.domain.bo.KbDocumentBo;
    import com.yuan.core.page.TableDataInfo;
    import com.yuan.core.page.PageQuery;

import java.util.Collection;
import java.util.List;

/**
 * 知识库文档表Service接口
 *
 * @author yuan
 * @date Fri May 01 16:08:29 CST 2026
 */
public interface KbDocumentService {

    /**
     * 查询知识库文档表
     */
        KbDocumentVo queryById(Long docId);

        /**
         * 查询知识库文档表列表
         */
        TableDataInfo<KbDocumentVo> queryPageList(KbDocumentBo bo, PageQuery pageQuery);

    /**
     * 查询知识库文档表列表
     */
    List<KbDocumentVo> queryList(KbDocumentBo bo);

    /**
     * 新增知识库文档表
     */
    Boolean insertByBo(KbDocumentBo bo);

    /**
     * 修改知识库文档表
     */
    Boolean updateByBo(KbDocumentBo bo);

    /**
     * 校验并批量删除知识库文档表信息
     */
    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);
}
