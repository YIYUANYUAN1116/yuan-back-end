package com.yuan.ai.mapper;

import com.yuan.ai.domain.LlmRouteRule;
import com.yuan.ai.domain.vo.LlmRouteRuleVo;
import com.yuan.core.mapper.BaseMapperPlus;
import org.apache.ibatis.annotations.Mapper;

/**
 * llm_route_ruleMapper接口
 *
 * @author yuan
 * @date Thu Feb 26 21:44:32 CST 2026
 */
@Mapper
public interface LlmRouteRuleMapper extends BaseMapperPlus<LlmRouteRule, LlmRouteRuleVo> {

}
