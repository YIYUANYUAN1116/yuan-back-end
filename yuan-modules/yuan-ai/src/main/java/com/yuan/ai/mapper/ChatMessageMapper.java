package com.yuan.ai.mapper;

import com.yuan.ai.domain.ChatMessage;
import com.yuan.ai.domain.vo.ChatMessageVo;
import com.yuan.core.mapper.BaseMapperPlus;
import org.apache.ibatis.annotations.Mapper;

/**
 * chat-messageMapper接口
 *
 * @author ageerle
 * @date Mon Feb 16 14:59:03 CST 2026
 */
@Mapper
public interface ChatMessageMapper extends BaseMapperPlus<ChatMessage, ChatMessageVo> {

}
