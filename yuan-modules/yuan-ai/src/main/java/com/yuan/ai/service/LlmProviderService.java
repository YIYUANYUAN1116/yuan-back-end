package com.yuan.ai.service;

import com.yuan.ai.domain.vo.LlmProviderVo;
import com.yuan.ai.domain.bo.LlmProviderBo;
    import com.yuan.core.page.TableDataInfo;
    import com.yuan.core.page.PageQuery;

import java.util.Collection;
import java.util.List;

/**
 * llm_providerService接口
 *
 * @author yuan
 * @date Thu Feb 26 21:44:12 CST 2026
 */
public interface LlmProviderService {

    /**
     * 查询llm_provider
     */
        LlmProviderVo queryById(Long id);

        /**
         * 查询llm_provider列表
         */
        TableDataInfo<LlmProviderVo> queryPageList(LlmProviderBo bo, PageQuery pageQuery);

    /**
     * 查询llm_provider列表
     */
    List<LlmProviderVo> queryList(LlmProviderBo bo);

    /**
     * 新增llm_provider
     */
    Boolean insertByBo(LlmProviderBo bo);

    /**
     * 修改llm_provider
     */
    Boolean updateByBo(LlmProviderBo bo);

    /**
     * 校验并批量删除llm_provider信息
     */
    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);
}
