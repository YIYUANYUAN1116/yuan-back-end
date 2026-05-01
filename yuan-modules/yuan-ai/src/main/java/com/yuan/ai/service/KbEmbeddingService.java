package com.yuan.ai.service;

import com.yuan.ai.domain.vo.KbEmbeddingVo;
import com.yuan.ai.domain.bo.KbEmbeddingBo;
    import com.yuan.core.page.TableDataInfo;
    import com.yuan.core.page.PageQuery;

import java.util.Collection;
import java.util.List;

/**
 * 知识库向量元数据表Service接口
 *
 * @author yuan
 * @date Fri May 01 16:08:40 CST 2026
 */
public interface KbEmbeddingService {

    /**
     * 查询知识库向量元数据表
     */
        KbEmbeddingVo queryById(Long embeddingId);

        /**
         * 查询知识库向量元数据表列表
         */
        TableDataInfo<KbEmbeddingVo> queryPageList(KbEmbeddingBo bo, PageQuery pageQuery);

    /**
     * 查询知识库向量元数据表列表
     */
    List<KbEmbeddingVo> queryList(KbEmbeddingBo bo);

    /**
     * 新增知识库向量元数据表
     */
    Boolean insertByBo(KbEmbeddingBo bo);

    /**
     * 修改知识库向量元数据表
     */
    Boolean updateByBo(KbEmbeddingBo bo);

    /**
     * 校验并批量删除知识库向量元数据表信息
     */
    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);
}
