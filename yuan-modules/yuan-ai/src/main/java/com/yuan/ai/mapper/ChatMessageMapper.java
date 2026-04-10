package com.yuan.ai.mapper;

import com.yuan.ai.domain.ChatMessage;
import com.yuan.ai.domain.vo.ChatMessageVo;
import com.yuan.core.mapper.BaseMapperPlus;
import org.apache.ibatis.annotations.Mapper;

/**
 * chat_messageMapper接口
 *
 * @author yuan
 * @date Thu Feb 26 21:44:39 CST 2026
 */
@Mapper
public interface ChatMessageMapper extends BaseMapperPlus<ChatMessage, ChatMessageVo> {

}
