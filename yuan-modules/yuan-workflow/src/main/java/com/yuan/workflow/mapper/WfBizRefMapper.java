package com.yuan.workflow.mapper;

import com.yuan.core.mapper.BaseMapperPlus;
import com.yuan.workflow.domain.WfBizRef;
import com.yuan.workflow.domain.vo.WfBizRefVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * wfrefMapper接口
 *
 * @author yuan
 * @date Mon Dec 29 20:51:33 CST 2025
 */
@Mapper
public interface WfBizRefMapper extends BaseMapperPlus<WfBizRef, WfBizRefVo> {

    WfBizRef selectByInstanceId(@Param("instanceId") Long instanceId);
}
