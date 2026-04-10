package com.yuan.ai.service;

import com.yuan.ai.domain.vo.LlmPolicyVo;
import com.yuan.ai.domain.bo.LlmPolicyBo;
    import com.yuan.core.page.TableDataInfo;
    import com.yuan.core.page.PageQuery;

import java.util.Collection;
import java.util.List;

/**
 * llm_policyService接口
 *
 * @author yuan
 * @date Thu Feb 26 21:44:28 CST 2026
 */
public interface LlmPolicyService {

    /**
     * 查询llm_policy
     */
        LlmPolicyVo queryById(Long id);

        /**
         * 查询llm_policy列表
         */
        TableDataInfo<LlmPolicyVo> queryPageList(LlmPolicyBo bo, PageQuery pageQuery);

    /**
     * 查询llm_policy列表
     */
    List<LlmPolicyVo> queryList(LlmPolicyBo bo);

    /**
     * 新增llm_policy
     */
    Boolean insertByBo(LlmPolicyBo bo);

    /**
     * 修改llm_policy
     */
    Boolean updateByBo(LlmPolicyBo bo);

    /**
     * 校验并批量删除llm_policy信息
     */
    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);
}
