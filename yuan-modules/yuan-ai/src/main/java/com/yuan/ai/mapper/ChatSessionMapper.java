package com.yuan.ai.mapper;

import com.yuan.ai.domain.ChatSession;
import com.yuan.ai.domain.vo.ChatSessionVo;
import com.yuan.core.mapper.BaseMapperPlus;
import org.apache.ibatis.annotations.Mapper;

/**
 * chat-sessionMapper接口
 *
 * @author ageerle
 * @date Mon Feb 16 14:59:22 CST 2026
 */
@Mapper
public interface ChatSessionMapper extends BaseMapperPlus<ChatSession, ChatSessionVo> {

}
