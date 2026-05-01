package com.yuan.ai.service;

import com.yuan.ai.domain.vo.KbChunkVo;
import com.yuan.ai.domain.bo.KbChunkBo;
    import com.yuan.core.page.TableDataInfo;
    import com.yuan.core.page.PageQuery;

import java.util.Collection;
import java.util.List;

/**
 * 知识库文档切片表Service接口
 *
 * @author yuan
 * @date Fri May 01 16:08:37 CST 2026
 */
public interface KbChunkService {

    /**
     * 查询知识库文档切片表
     */
        KbChunkVo queryById(Long chunkId);

        /**
         * 查询知识库文档切片表列表
         */
        TableDataInfo<KbChunkVo> queryPageList(KbChunkBo bo, PageQuery pageQuery);

    /**
     * 查询知识库文档切片表列表
     */
    List<KbChunkVo> queryList(KbChunkBo bo);

    /**
     * 新增知识库文档切片表
     */
    Boolean insertByBo(KbChunkBo bo);

    /**
     * 修改知识库文档切片表
     */
    Boolean updateByBo(KbChunkBo bo);

    /**
     * 校验并批量删除知识库文档切片表信息
     */
    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);
}
