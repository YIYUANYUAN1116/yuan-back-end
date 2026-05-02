package com.yuan.ai.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yuan.ai.domain.KbChunk;
import com.yuan.ai.domain.bo.KbChunkBo;
import com.yuan.ai.domain.vo.KbChunkVo;
import com.yuan.core.mapper.BaseMapperPlus;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 知识库文档切片表Mapper接口
 *
 * @author yuan
 * @date Fri May 01 16:08:37 CST 2026
 */
@Mapper
public interface KbChunkMapper extends BaseMapperPlus<KbChunk, KbChunkVo> {

    Page<KbChunkVo> selectKbChunkVoPage(@Param("bo") KbChunkBo bo, @Param("page") Page<KbChunk> pageQuery);
}
