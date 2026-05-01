package com.yuan.ai.mapper;

import com.yuan.ai.domain.KbRetrievalLog;
import com.yuan.ai.domain.vo.KbRetrievalLogVo;
import com.yuan.core.mapper.BaseMapperPlus;
import org.apache.ibatis.annotations.Mapper;

/**
 * 知识库检索日志表Mapper接口
 *
 * @author yuan
 * @date Fri May 01 16:08:45 CST 2026
 */
@Mapper
public interface KbRetrievalLogMapper extends BaseMapperPlus<KbRetrievalLog, KbRetrievalLogVo> {

}
