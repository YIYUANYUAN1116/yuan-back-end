package com.yuan.ai.service;

import com.yuan.ai.domain.LlmModel;
import com.yuan.ai.domain.vo.LlmModelVo;
import com.yuan.ai.domain.bo.LlmModelBo;
    import com.yuan.core.page.TableDataInfo;
    import com.yuan.core.page.PageQuery;

import java.util.Collection;
import java.util.List;

/**
 * llm_modelService接口
 *
 * @author yuan
 * @date Thu Feb 26 21:44:17 CST 2026
 */
public interface LlmModelService {

    /**
     * 查询llm_model
     */
        LlmModelVo queryById(Long id);

        /**
         * 查询llm_model列表
         */
        TableDataInfo<LlmModelVo> queryPageList(LlmModelBo bo, PageQuery pageQuery);

    /**
     * 查询llm_model列表
     */
    List<LlmModelVo> queryList(LlmModelBo bo);

    /**
     * 新增llm_model
     */
    Boolean insertByBo(LlmModelBo bo);

    /**
     * 修改llm_model
     */
    Boolean updateByBo(LlmModelBo bo);

    /**
     * 校验并批量删除llm_model信息
     */
    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);

    LlmModel getById(Long id);
}
