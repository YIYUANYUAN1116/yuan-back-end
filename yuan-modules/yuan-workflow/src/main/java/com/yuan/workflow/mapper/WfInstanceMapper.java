package com.yuan.workflow.mapper;

import com.yuan.core.mapper.BaseMapperPlus;
import org.apache.ibatis.annotations.Mapper;
import com.yuan.workflow.domain.WfInstance;
import com.yuan.workflow.domain.vo.WfInstanceVo;

/**
 * wfiMapper接口
 *
 
 * @date Sun Dec 28 11:26:34 CST 2025
 */
@Mapper
public interface WfInstanceMapper extends BaseMapperPlus<WfInstance, WfInstanceVo> {

}
