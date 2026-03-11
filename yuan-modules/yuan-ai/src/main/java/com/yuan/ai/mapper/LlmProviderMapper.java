package com.yuan.ai.mapper;

import com.yuan.ai.domain.LlmProvider;
import com.yuan.ai.domain.vo.LlmProviderVo;
import com.yuan.common.core.domain.model.StrSelectModel;
import com.yuan.core.mapper.BaseMapperPlus;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * llm_providerMapper接口
 *
 * @author yuan
 * @date Thu Feb 26 21:44:12 CST 2026
 */
@Mapper
public interface LlmProviderMapper extends BaseMapperPlus<LlmProvider, LlmProviderVo> {

    List<StrSelectModel> selectProvider();
}
