package com.yuan.workflow.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yuan.core.mapper.BaseMapperPlus;
import com.yuan.workflow.domain.WfInstance;
import com.yuan.workflow.domain.vo.WfInstanceVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * wfiMapper接口
 *
 
 * @date Sun Dec 28 11:26:34 CST 2025
 */
@Mapper
public interface WfInstanceMapper extends BaseMapperPlus<WfInstance, WfInstanceVo> {

    Page<WfInstanceVo> selectWfInstanceVoPage(@Param("page") Page<WfInstance> build,@Param(Constants.WRAPPER) Wrapper<WfInstance> lqw);
}
