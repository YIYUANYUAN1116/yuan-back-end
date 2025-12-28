package com.yuan.system.mapper;

import org.apache.ibatis.annotations.Mapper;
import com.yuan.core.mapper.BaseMapperPlus;
import com.yuan.system.domain.SysRoleDept;
import com.yuan.system.domain.vo.SysRoleDeptVo;

/**
 * 部门角色Mapper接口
 *
 
 * @date Wed Dec 10 17:21:37 CST 2025
 */
@Mapper
public interface SysRoleDeptMapper extends BaseMapperPlus<SysRoleDept, SysRoleDeptVo> {

}
