package com.yuan.ai.mapper;

import com.yuan.ai.domain.LlmEndpoint;
import com.yuan.ai.domain.vo.LlmEndpointVo;
import com.yuan.common.core.domain.model.StrSelectModel;
import com.yuan.core.mapper.BaseMapperPlus;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * llm_endpointMapper接口
 *
 * @author yuan
 * @date Thu Feb 26 21:44:21 CST 2026
 */
@Mapper
public interface LlmEndpointMapper extends BaseMapperPlus<LlmEndpoint, LlmEndpointVo> {

    List<StrSelectModel> selectEndpoint(@Param("providerCode") String providerCode);
}
