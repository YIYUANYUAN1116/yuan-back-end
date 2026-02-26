package com.yuan.ai.service;

import com.yuan.ai.domain.vo.LlmInvocationVo;
import com.yuan.ai.domain.bo.LlmInvocationBo;
    import com.yuan.core.page.TableDataInfo;
    import com.yuan.core.page.PageQuery;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * llm_invocationService接口
 *
 * @author yuan
 * @date Thu Feb 26 21:44:25 CST 2026
 */
public interface LlmInvocationService {

    /**
     * 查询llm_invocation
     */
        LlmInvocationVo queryById(Long id);

        /**
         * 查询llm_invocation列表
         */
        TableDataInfo<LlmInvocationVo> queryPageList(LlmInvocationBo bo, PageQuery pageQuery);

    /**
     * 查询llm_invocation列表
     */
    List<LlmInvocationVo> queryList(LlmInvocationBo bo);

    /**
     * 新增llm_invocation
     */
    Boolean insertByBo(LlmInvocationBo bo);

    /**
     * 修改llm_invocation
     */
    Boolean updateByBo(LlmInvocationBo bo);

    /**
     * 校验并批量删除llm_invocation信息
     */
    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);

    long begin(String tenantId, String traceId, String endpointKey, String providerCode, String modelName,
               Long conversationId, Long assistantMsgId, Map<String, Object> reqJson);

    void success(long id, String responseText, int latencyMs);

    void fail(long id, String errorMsg, int latencyMs);
}
