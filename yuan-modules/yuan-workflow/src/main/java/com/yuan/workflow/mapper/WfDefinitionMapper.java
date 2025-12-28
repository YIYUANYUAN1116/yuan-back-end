package com.yuan.workflow.mapper;

import com.yuan.core.mapper.BaseMapperPlus;
import org.apache.ibatis.annotations.Mapper;
import com.yuan.workflow.domain.WfDefinition;
import com.yuan.workflow.domain.vo.WfDefinitionVo;
import org.apache.ibatis.annotations.Param;

/**
 * wfdMapper接口
 *
 
 * @date Sun Dec 28 11:26:30 CST 2025
 */
@Mapper
public interface WfDefinitionMapper extends BaseMapperPlus<WfDefinition, WfDefinitionVo> {

    WfDefinition selectLatestPublished(@Param("tenantId") String tenantId,@Param("processKey") String processKey);
}
