package com.yuan.workflow.mapper;

import com.yuan.core.mapper.BaseMapperPlus;
import org.apache.ibatis.annotations.Mapper;
import com.yuan.workflow.domain.WfTask;
import com.yuan.workflow.domain.vo.WfTaskVo;
import org.apache.ibatis.annotations.Param;

/**
 * wftMapper接口
 *
 
 * @date Sun Dec 28 11:26:41 CST 2025
 */
@Mapper
public interface WfTaskMapper extends BaseMapperPlus<WfTask, WfTaskVo> {

    void finishAll(@Param("instanceId") Long instanceId,@Param("status") String status);
}
