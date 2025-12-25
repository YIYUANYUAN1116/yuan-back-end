package com.yuan.system.mapper;

import com.yuan.core.mapper.BaseMapperPlus;
import com.yuan.system.domain.SysDictType;
import com.yuan.system.domain.vo.SysDictTypeVo;
import org.apache.ibatis.annotations.Mapper;

/**
 * 字典表 数据层
 *
 * 
 */
@Mapper
public interface SysDictTypeMapper extends BaseMapperPlus<SysDictType, SysDictTypeVo> {

}

