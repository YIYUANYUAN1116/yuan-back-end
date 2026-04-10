package com.yuan.ai.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yuan.ai.domain.LlmEndpoint;
import com.yuan.ai.domain.bo.LlmEndpointBo;
import com.yuan.ai.domain.vo.LlmEndpointVo;
import com.yuan.common.core.domain.model.SelectModel;
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

    List<SelectModel> selectEndpointByProvider(@Param("providerId") Long providerId);

    Page<LlmEndpointVo> selectEndpointVoPage(@Param("page") Page<LlmEndpoint> page, @Param("bo") LlmEndpointBo bo);
}
