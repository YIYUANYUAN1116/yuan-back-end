package com.yuan.ai.mapper;

import com.yuan.ai.domain.LlmInvocation;
import com.yuan.ai.domain.vo.LlmInvocationVo;
import com.yuan.core.mapper.BaseMapperPlus;
import org.apache.ibatis.annotations.Mapper;

/**
 * llm_invocationMapper接口
 *
 * @author yuan
 * @date Thu Feb 26 21:44:25 CST 2026
 */
@Mapper
public interface LlmInvocationMapper extends BaseMapperPlus<LlmInvocation, LlmInvocationVo> {

}
