package com.yuan.ai.mapper;

import com.yuan.ai.domain.KbBaseAuth;
import com.yuan.ai.domain.vo.KbBaseAuthVo;
import com.yuan.core.mapper.BaseMapperPlus;
import org.apache.ibatis.annotations.Mapper;

/**
 * 知识库授权表Mapper接口
 *
 * @author yuan
 * @date Fri May 01 16:08:26 CST 2026
 */
@Mapper
public interface KbBaseAuthMapper extends BaseMapperPlus<KbBaseAuth, KbBaseAuthVo> {

}
