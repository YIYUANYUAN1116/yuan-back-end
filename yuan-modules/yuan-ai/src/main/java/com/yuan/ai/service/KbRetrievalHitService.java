package com.yuan.ai.service;

import com.yuan.ai.domain.vo.KbRetrievalHitVo;
import com.yuan.ai.domain.bo.KbRetrievalHitBo;
    import com.yuan.core.page.TableDataInfo;
    import com.yuan.core.page.PageQuery;

import java.util.Collection;
import java.util.List;

/**
 * 知识库检索命中明细表Service接口
 *
 * @author yuan
 * @date Fri May 01 16:08:50 CST 2026
 */
public interface KbRetrievalHitService {

    /**
     * 查询知识库检索命中明细表
     */
        KbRetrievalHitVo queryById(Long hitId);

        /**
         * 查询知识库检索命中明细表列表
         */
        TableDataInfo<KbRetrievalHitVo> queryPageList(KbRetrievalHitBo bo, PageQuery pageQuery);

    /**
     * 查询知识库检索命中明细表列表
     */
    List<KbRetrievalHitVo> queryList(KbRetrievalHitBo bo);

    /**
     * 新增知识库检索命中明细表
     */
    Boolean insertByBo(KbRetrievalHitBo bo);

    /**
     * 修改知识库检索命中明细表
     */
    Boolean updateByBo(KbRetrievalHitBo bo);

    /**
     * 校验并批量删除知识库检索命中明细表信息
     */
    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);
}
