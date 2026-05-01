package com.yuan.ai.mapper;

import com.yuan.ai.domain.KbBase;
import com.yuan.ai.domain.vo.KbBaseVo;
import com.yuan.core.mapper.BaseMapperPlus;
import org.apache.ibatis.annotations.Mapper;

/**
 * 知识库主表Mapper接口
 *
 * @author yuan
 * @date Fri May 01 16:08:21 CST 2026
 */
@Mapper
public interface KbBaseMapper extends BaseMapperPlus<KbBase, KbBaseVo> {

}
