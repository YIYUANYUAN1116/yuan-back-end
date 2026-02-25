package com.yuan.ai.service;

import com.yuan.ai.domain.model.ChatRequest;
import com.yuan.ai.domain.vo.ChatMessageVo;
import com.yuan.ai.domain.bo.ChatMessageBo;
    import com.yuan.core.page.TableDataInfo;
    import com.yuan.core.page.PageQuery;

import java.util.Collection;
import java.util.List;

/**
 * chat-messageService接口
 *
 * @author ageerle
 * @date Mon Feb 16 14:59:03 CST 2026
 */
public interface ChatMessageService {

    /**
     * 查询chat-message
     */
        ChatMessageVo queryById(Long id);

        /**
         * 查询chat-message列表
         */
        TableDataInfo<ChatMessageVo> queryPageList(ChatMessageBo bo, PageQuery pageQuery);

    /**
     * 查询chat-message列表
     */
    List<ChatMessageVo> queryList(ChatMessageBo bo);

    /**
     * 新增chat-message
     */
    Boolean insertByBo(ChatMessageBo bo);

    /**
     * 修改chat-message
     */
    Boolean updateByBo(ChatMessageBo bo);

    /**
     * 校验并批量删除chat-message信息
     */
    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);

    long insertUserMessage(ChatRequest req, String userContent);

    long insertAssistantPlaceholder(ChatRequest req, String modelNameToSave);

    void updateAssistantContent(long messageId, String content);
}
