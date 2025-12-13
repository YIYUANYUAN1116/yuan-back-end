package com.yuan.generator.mapper;


import org.apache.ibatis.annotations.Mapper;
import com.yuan.core.mapper.BaseMapperPlus;
import com.yuan.generator.domain.Schema;
import com.yuan.generator.domain.vo.SchemaVo;

/**
 * 数据模型Mapper接口
 *
 * @author ruoyi
 * @date 2024-01-01
 */
@Mapper
public interface SchemaMapper extends BaseMapperPlus<Schema, SchemaVo> {

}