package com.yuan.ai.service;

import com.yuan.ai.domain.vo.ChatMessageChunkVo;
import com.yuan.ai.domain.bo.ChatMessageChunkBo;
    import com.yuan.core.page.TableDataInfo;
    import com.yuan.core.page.PageQuery;

import java.util.Collection;
import java.util.List;

/**
 * chat_message_chunkService接口
 *
 * @author yuan
 * @date Thu Feb 26 21:44:46 CST 2026
 */
public interface ChatMessageChunkService {

    /**
     * 查询chat_message_chunk
     */
        ChatMessageChunkVo queryById(Long id);

        /**
         * 查询chat_message_chunk列表
         */
        TableDataInfo<ChatMessageChunkVo> queryPageList(ChatMessageChunkBo bo, PageQuery pageQuery);

    /**
     * 查询chat_message_chunk列表
     */
    List<ChatMessageChunkVo> queryList(ChatMessageChunkBo bo);

    /**
     * 新增chat_message_chunk
     */
    Boolean insertByBo(ChatMessageChunkBo bo);

    /**
     * 修改chat_message_chunk
     */
    Boolean updateByBo(ChatMessageChunkBo bo);

    /**
     * 校验并批量删除chat_message_chunk信息
     */
    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);
}
