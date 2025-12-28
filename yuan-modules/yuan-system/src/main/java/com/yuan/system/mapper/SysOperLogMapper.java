package com.yuan.system.mapper;

import com.yuan.core.mapper.BaseMapperPlus;
import com.yuan.system.domain.SysOperLog;
import com.yuan.system.domain.vo.SysOperLogVo;
import org.apache.ibatis.annotations.Mapper;


/**
 * oprelogMapper接口
 *
 
 * @date Wed Dec 17 21:48:33 CST 2025
 */
@Mapper
public interface SysOperLogMapper extends BaseMapperPlus<SysOperLog, SysOperLogVo> {

}
