package com.yuan.system.mapper;

import com.yuan.core.mapper.BaseMapperPlus;
import com.yuan.system.domain.SysLogininfor;
import com.yuan.system.domain.vo.SysLogininforVo;
import org.apache.ibatis.annotations.Mapper;


/**
 * 系统访问日志情况信息 数据层
 *
 * @author Lion Li
 */
@Mapper
public interface SysLogininforMapper extends BaseMapperPlus<SysLogininfor, SysLogininforVo> {

}
