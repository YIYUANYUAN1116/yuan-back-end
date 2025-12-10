package com.yuan.system.mapper;

import com.yuan.core.mapper.BaseMapperPlus;
import com.yuan.system.domain.SysDept;
import com.yuan.system.domain.vo.SysDeptVo;
import org.apache.ibatis.annotations.Mapper;


/**
 * 部门Mapper接口
 *
 * @author ageerle
 * @date Wed Dec 10 17:08:31 CST 2025
 */
@Mapper
public interface SysDeptMapper extends BaseMapperPlus<SysDept, SysDeptVo> {

}
