package com.yuan.ai.mapper;

import com.yuan.ai.domain.KbEmbedding;
import com.yuan.ai.domain.vo.KbEmbeddingVo;
import com.yuan.core.mapper.BaseMapperPlus;
import org.apache.ibatis.annotations.Mapper;

/**
 * 知识库向量元数据表Mapper接口
 *
 * @author yuan
 * @date Fri May 01 16:08:40 CST 2026
 */
@Mapper
public interface KbEmbeddingMapper extends BaseMapperPlus<KbEmbedding, KbEmbeddingVo> {

}
