package com.yuan.generator.mapper;

import org.apache.ibatis.annotations.Mapper;
import com.yuan.core.mapper.BaseMapperPlus;
import com.yuan.generator.domain.SchemaGroup;
import com.yuan.generator.domain.vo.SchemaGroupVo;

/**
 * 数据模型分组Mapper接口
 *
 * @author ruoyi
 * @date 2024-01-01
 */
@Mapper
public interface SchemaGroupMapper extends BaseMapperPlus<SchemaGroup, SchemaGroupVo> {

}