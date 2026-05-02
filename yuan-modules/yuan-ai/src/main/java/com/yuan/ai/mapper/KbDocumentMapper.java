package com.yuan.ai.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yuan.ai.domain.KbDocument;
import com.yuan.ai.domain.bo.KbDocumentBo;
import com.yuan.ai.domain.vo.KbDocumentVo;
import com.yuan.common.core.domain.model.SelectModel;
import com.yuan.core.mapper.BaseMapperPlus;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 知识库文档表Mapper接口
 *
 * @author yuan
 * @date Fri May 01 16:08:29 CST 2026
 */
@Mapper
public interface KbDocumentMapper extends BaseMapperPlus<KbDocument, KbDocumentVo> {

    List<SelectModel> selectKbDocument(@Param("kbId") Long kbId);

    Page<KbDocumentVo> selectKbDocumentVoPage(@Param("bo") KbDocumentBo bo, @Param("page") Page<KbDocument> pageQuery);
}
