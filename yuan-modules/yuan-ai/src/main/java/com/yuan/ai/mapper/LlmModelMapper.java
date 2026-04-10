package com.yuan.ai.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yuan.ai.domain.LlmModel;
import com.yuan.ai.domain.bo.LlmModelBo;
import com.yuan.ai.domain.vo.LlmModelVo;
import com.yuan.common.core.domain.model.SelectModel;
import com.yuan.core.mapper.BaseMapperPlus;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * llm_modelMapper接口
 *
 * @author yuan
 * @date Thu Feb 26 21:44:17 CST 2026
 */
@Mapper
public interface LlmModelMapper extends BaseMapperPlus<LlmModel, LlmModelVo> {

    List<SelectModel> selectModelByEndpoint(@Param("endpointId") Long endpointId);

    List<SelectModel> selectModel();

    Page<LlmModelVo> selectModelVoPage(@Param("bo") LlmModelBo bo, @Param("page") Page<LlmModel> pageQuery);
}
