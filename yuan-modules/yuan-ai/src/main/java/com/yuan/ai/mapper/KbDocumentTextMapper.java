package com.yuan.ai.mapper;

import com.yuan.ai.domain.KbDocumentText;
import com.yuan.ai.domain.vo.KbDocumentTextVo;
import com.yuan.core.mapper.BaseMapperPlus;
import org.apache.ibatis.annotations.Mapper;

/**
 * 知识库文档解析文本表Mapper接口
 *
 * @author yuan
 * @date Fri May 01 16:08:34 CST 2026
 */
@Mapper
public interface KbDocumentTextMapper extends BaseMapperPlus<KbDocumentText, KbDocumentTextVo> {

}
