package com.yuan.ai.mapper;

import com.yuan.ai.domain.ChatConversation;
import com.yuan.ai.domain.vo.ChatConversationVo;
import com.yuan.core.mapper.BaseMapperPlus;
import org.apache.ibatis.annotations.Mapper;

/**
 * chat_conversationMapper接口
 *
 * @author yuan
 * @date Thu Feb 26 21:44:35 CST 2026
 */
@Mapper
public interface ChatConversationMapper extends BaseMapperPlus<ChatConversation, ChatConversationVo> {

}
