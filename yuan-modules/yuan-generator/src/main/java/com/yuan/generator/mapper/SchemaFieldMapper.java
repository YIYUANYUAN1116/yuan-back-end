package com.yuan.generator.mapper;


import org.apache.ibatis.annotations.Mapper;
import com.yuan.core.mapper.BaseMapperPlus;
import com.yuan.generator.domain.SchemaField;
import com.yuan.generator.domain.vo.SchemaFieldVo;

/**
 * 数据模型字段Mapper接口
 *
 * @author ruoyi
 * @date 2024-01-01
 */
@Mapper
public interface SchemaFieldMapper extends BaseMapperPlus<SchemaField, SchemaFieldVo> {

}