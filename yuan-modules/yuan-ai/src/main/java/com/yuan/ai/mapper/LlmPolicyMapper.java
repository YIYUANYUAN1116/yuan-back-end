package com.yuan.ai.mapper;

import com.yuan.ai.domain.LlmPolicy;
import com.yuan.ai.domain.vo.LlmPolicyVo;
import com.yuan.core.mapper.BaseMapperPlus;
import org.apache.ibatis.annotations.Mapper;

/**
 * llm_policyMapper接口
 *
 * @author yuan
 * @date Thu Feb 26 21:44:28 CST 2026
 */
@Mapper
public interface LlmPolicyMapper extends BaseMapperPlus<LlmPolicy, LlmPolicyVo> {

}
