package com.yuan.ai.mapper;

import com.yuan.ai.domain.KbChunk;
import com.yuan.ai.domain.vo.KbChunkVo;
import com.yuan.core.mapper.BaseMapperPlus;
import org.apache.ibatis.annotations.Mapper;

/**
 * 知识库文档切片表Mapper接口
 *
 * @author yuan
 * @date Fri May 01 16:08:37 CST 2026
 */
@Mapper
public interface KbChunkMapper extends BaseMapperPlus<KbChunk, KbChunkVo> {

}
