package com.yuan.workflow.mapper;

import com.yuan.core.mapper.BaseMapperPlus;
import org.apache.ibatis.annotations.Mapper;
import com.yuan.workflow.domain.WfTaskLog;
import com.yuan.workflow.domain.vo.WfTaskLogVo;

/**
 * wftlMapper接口
 *
 
 * @date Sun Dec 28 11:26:44 CST 2025
 */
@Mapper
public interface WfTaskLogMapper extends BaseMapperPlus<WfTaskLog, WfTaskLogVo> {

}
