package com.yuan.ai.service;

import com.yuan.ai.domain.vo.ChatMessageVo;
import com.yuan.ai.domain.bo.ChatMessageBo;
    import com.yuan.core.page.TableDataInfo;
    import com.yuan.core.page.PageQuery;

import java.util.Collection;
import java.util.List;

/**
 * chat_messageService接口
 *
 * @author yuan
 * @date Thu Feb 26 21:44:39 CST 2026
 */
public interface ChatMessageService {

    /**
     * 查询chat_message
     */
        ChatMessageVo queryById(Long id);

        /**
         * 查询chat_message列表
         */
        TableDataInfo<ChatMessageVo> queryPageList(ChatMessageBo bo, PageQuery pageQuery);

    /**
     * 查询chat_message列表
     */
    List<ChatMessageVo> queryList(ChatMessageBo bo);

    /**
     * 新增chat_message
     */
    Boolean insertByBo(ChatMessageBo bo);

    /**
     * 修改chat_message
     */
    Boolean updateByBo(ChatMessageBo bo);

    /**
     * 校验并批量删除chat_message信息
     */
    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);

    long insertUserMsg(String tenantId, long conversationId, Long userId, Long modelId, String content);

    long insertAssistantPlaceholder(String tenantId, long conversationId, Long userId, Long modelId);

    void bindInvocation(long messageId, long invocationId);

    void finishAssistant(long messageId, String content);

    void failAssistant(long messageId, String partial, String errorMsg);
}
