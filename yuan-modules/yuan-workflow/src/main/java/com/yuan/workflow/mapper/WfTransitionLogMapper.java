package com.yuan.workflow.mapper;

import com.yuan.core.mapper.BaseMapperPlus;
import com.yuan.workflow.domain.WfTransitionLog;
import com.yuan.workflow.domain.vo.WfTransitionLogVo;
import org.apache.ibatis.annotations.Mapper;

/**
 * wf_transition_logMapper接口
 *
 * @author yuan
 * @date Wed Jan 28 21:54:22 CST 2026
 */
@Mapper
public interface WfTransitionLogMapper extends BaseMapperPlus<WfTransitionLog, WfTransitionLogVo> {

}
