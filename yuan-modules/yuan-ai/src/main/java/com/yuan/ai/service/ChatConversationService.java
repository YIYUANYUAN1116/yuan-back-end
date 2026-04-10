package com.yuan.ai.service;

import com.yuan.ai.domain.ChatConversation;
import com.yuan.ai.domain.vo.ChatConversationVo;
import com.yuan.ai.domain.bo.ChatConversationBo;
    import com.yuan.core.page.TableDataInfo;
    import com.yuan.core.page.PageQuery;

import java.util.Collection;
import java.util.List;

/**
 * chat_conversationService接口
 *
 * @author yuan
 * @date Thu Feb 26 21:44:35 CST 2026
 */
public interface ChatConversationService {

    /**
     * 查询chat_conversation
     */
        ChatConversationVo queryById(Long id);

        /**
         * 查询chat_conversation列表
         */
        TableDataInfo<ChatConversationVo> queryPageList(ChatConversationBo bo, PageQuery pageQuery);

    /**
     * 查询chat_conversation列表
     */
    List<ChatConversationVo> queryList(ChatConversationBo bo);

    /**
     * 新增chat_conversation
     */
    Boolean insertByBo(ChatConversationBo bo);

    /**
     * 修改chat_conversation
     */
    Boolean updateByBo(ChatConversationBo bo);

    /**
     * 校验并批量删除chat_conversation信息
     */
    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);

    ChatConversation getOrCreate(String tenantId, Long conversationId, Long userId, Long appId, Long modelId,String title);

    void touch(Long conversationId);
}
