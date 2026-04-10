package com.yuan.ai.mapper;

import com.yuan.ai.domain.ChatMessageChunk;
import com.yuan.ai.domain.vo.ChatMessageChunkVo;
import com.yuan.core.mapper.BaseMapperPlus;
import org.apache.ibatis.annotations.Mapper;

/**
 * chat_message_chunkMapper接口
 *
 * @author yuan
 * @date Thu Feb 26 21:44:46 CST 2026
 */
@Mapper
public interface ChatMessageChunkMapper extends BaseMapperPlus<ChatMessageChunk, ChatMessageChunkVo> {

}
