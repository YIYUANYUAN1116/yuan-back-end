package com.yuan.ai.service;

import com.yuan.ai.domain.vo.KbRetrievalLogVo;
import com.yuan.ai.domain.bo.KbRetrievalLogBo;
    import com.yuan.core.page.TableDataInfo;
    import com.yuan.core.page.PageQuery;

import java.util.Collection;
import java.util.List;

/**
 * 知识库检索日志表Service接口
 *
 * @author yuan
 * @date Fri May 01 16:08:45 CST 2026
 */
public interface KbRetrievalLogService {

    /**
     * 查询知识库检索日志表
     */
        KbRetrievalLogVo queryById(Long logId);

        /**
         * 查询知识库检索日志表列表
         */
        TableDataInfo<KbRetrievalLogVo> queryPageList(KbRetrievalLogBo bo, PageQuery pageQuery);

    /**
     * 查询知识库检索日志表列表
     */
    List<KbRetrievalLogVo> queryList(KbRetrievalLogBo bo);

    /**
     * 新增知识库检索日志表
     */
    Boolean insertByBo(KbRetrievalLogBo bo);

    /**
     * 修改知识库检索日志表
     */
    Boolean updateByBo(KbRetrievalLogBo bo);

    /**
     * 校验并批量删除知识库检索日志表信息
     */
    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);
}
