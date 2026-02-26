package com.yuan.ai.mapper;

import com.yuan.ai.domain.LlmModel;
import com.yuan.ai.domain.vo.LlmModelVo;
import com.yuan.core.mapper.BaseMapperPlus;
import org.apache.ibatis.annotations.Mapper;

/**
 * llm_modelMapper接口
 *
 * @author yuan
 * @date Thu Feb 26 21:44:17 CST 2026
 */
@Mapper
public interface LlmModelMapper extends BaseMapperPlus<LlmModel, LlmModelVo> {

}
