package com.yuan.ai.mapper;

import com.yuan.ai.domain.ChatModel;
import com.yuan.ai.domain.vo.ChatModelVo;
import com.yuan.core.mapper.BaseMapperPlus;
import org.apache.ibatis.annotations.Mapper;

/**
 * chat_modelMapper接口
 *
 * @author ageerle
 * @date Mon Feb 16 14:59:14 CST 2026
 */
@Mapper
public interface ChatModelMapper extends BaseMapperPlus<ChatModel, ChatModelVo> {

}
