package com.yuan.workflow.mapper;

import com.yuan.core.mapper.BaseMapperPlus;
import org.apache.ibatis.annotations.Mapper;
import com.yuan.workflow.domain.WfNodeInstance;
import com.yuan.workflow.domain.vo.WfNodeInstanceVo;
import org.apache.ibatis.annotations.Param;

/**
 * wfnMapper接口
 *

 * @date Sun Dec 28 11:26:37 CST 2025
 */
@Mapper
public interface WfNodeInstanceMapper extends BaseMapperPlus<WfNodeInstance, WfNodeInstanceVo> {

    void finishAll(@Param("instanceId") Long instanceId,@Param("status") String status);

    int nextOrderNo(@Param("instanceId") Long instanceId);
}
