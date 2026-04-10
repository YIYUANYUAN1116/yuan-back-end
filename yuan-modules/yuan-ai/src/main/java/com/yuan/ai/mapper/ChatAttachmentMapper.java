package com.yuan.ai.mapper;

import com.yuan.ai.domain.ChatAttachment;
import com.yuan.ai.domain.vo.ChatAttachmentVo;
import com.yuan.core.mapper.BaseMapperPlus;
import org.apache.ibatis.annotations.Mapper;

/**
 * chat_attachmentMapper接口
 *
 * @author yuan
 * @date Thu Feb 26 21:44:43 CST 2026
 */
@Mapper
public interface ChatAttachmentMapper extends BaseMapperPlus<ChatAttachment, ChatAttachmentVo> {

}
