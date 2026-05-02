package com.yuan.ai.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yuan.ai.domain.KbBase;
import com.yuan.ai.domain.bo.KbBaseBo;
import com.yuan.ai.domain.vo.KbBaseVo;
import com.yuan.common.core.domain.model.SelectModel;
import com.yuan.core.mapper.BaseMapperPlus;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 知识库主表Mapper接口
 *
 * @author yuan
 * @date Fri May 01 16:08:21 CST 2026
 */
@Mapper
public interface KbBaseMapper extends BaseMapperPlus<KbBase, KbBaseVo> {

    List<SelectModel> selectKb();

    Page<KbBaseVo> selectKbBaseVoPage(@Param("bo") KbBaseBo bo, @Param("page") Page<KbBase> pageQuery);
}
