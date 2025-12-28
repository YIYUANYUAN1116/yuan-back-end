package com.yuan.system.mapper;

import org.apache.ibatis.annotations.Mapper;
import com.yuan.core.mapper.BaseMapperPlus;
import com.yuan.system.domain.SysTenant;
import com.yuan.system.domain.vo.SysTenantVo;

/**
 * 多租户Mapper接口
 *
 
 * @date Wed Dec 10 17:18:08 CST 2025
 */
@Mapper
public interface SysTenantMapper extends BaseMapperPlus<SysTenant, SysTenantVo> {

}
