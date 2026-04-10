package com.yuan.ai.service;

import com.yuan.ai.domain.LlmEndpoint;
import com.yuan.ai.domain.bo.LlmEndpointBo;
import com.yuan.ai.domain.vo.LlmEndpointVo;
import com.yuan.core.page.PageQuery;
import com.yuan.core.page.TableDataInfo;

import java.util.Collection;
import java.util.List;

/**
 * llm_endpointService接口
 *
 * @author yuan
 * @date Thu Feb 26 21:44:21 CST 2026
 */
public interface LlmEndpointService {

    /**
     * 查询llm_endpoint
     */
        LlmEndpointVo queryById(Long id);

        /**
         * 查询llm_endpoint列表
         */
        TableDataInfo<LlmEndpointVo> queryPageList(LlmEndpointBo bo, PageQuery pageQuery);

    /**
     * 查询llm_endpoint列表
     */
    List<LlmEndpointVo> queryList(LlmEndpointBo bo);

    /**
     * 新增llm_endpoint
     */
    Boolean insertByBo(LlmEndpointBo bo);

    /**
     * 修改llm_endpoint
     */
    Boolean updateByBo(LlmEndpointBo bo);

    /**
     * 校验并批量删除llm_endpoint信息
     */
    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);

    LlmEndpoint getEnabledByKey(String tenantId, String endpointKey);

    LlmEndpoint pickHighestPriority(String tenantId);
}
