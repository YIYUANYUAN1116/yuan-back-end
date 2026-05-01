package com.yuan.ai.mapper;

import com.yuan.ai.domain.KbRetrievalHit;
import com.yuan.ai.domain.vo.KbRetrievalHitVo;
import com.yuan.core.mapper.BaseMapperPlus;
import org.apache.ibatis.annotations.Mapper;

/**
 * 知识库检索命中明细表Mapper接口
 *
 * @author yuan
 * @date Fri May 01 16:08:50 CST 2026
 */
@Mapper
public interface KbRetrievalHitMapper extends BaseMapperPlus<KbRetrievalHit, KbRetrievalHitVo> {

}
