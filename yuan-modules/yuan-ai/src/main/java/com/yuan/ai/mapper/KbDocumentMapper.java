package com.yuan.ai.mapper;

import com.yuan.ai.domain.KbDocument;
import com.yuan.ai.domain.vo.KbDocumentVo;
import com.yuan.core.mapper.BaseMapperPlus;
import org.apache.ibatis.annotations.Mapper;

/**
 * 知识库文档表Mapper接口
 *
 * @author yuan
 * @date Fri May 01 16:08:29 CST 2026
 */
@Mapper
public interface KbDocumentMapper extends BaseMapperPlus<KbDocument, KbDocumentVo> {

}
