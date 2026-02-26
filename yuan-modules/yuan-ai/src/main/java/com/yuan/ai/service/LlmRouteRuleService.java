package com.yuan.ai.service;

import com.yuan.ai.domain.vo.LlmRouteRuleVo;
import com.yuan.ai.domain.bo.LlmRouteRuleBo;
    import com.yuan.core.page.TableDataInfo;
    import com.yuan.core.page.PageQuery;

import java.util.Collection;
import java.util.List;

/**
 * llm_route_ruleService接口
 *
 * @author yuan
 * @date Thu Feb 26 21:44:32 CST 2026
 */
public interface LlmRouteRuleService {

    /**
     * 查询llm_route_rule
     */
        LlmRouteRuleVo queryById(Long id);

        /**
         * 查询llm_route_rule列表
         */
        TableDataInfo<LlmRouteRuleVo> queryPageList(LlmRouteRuleBo bo, PageQuery pageQuery);

    /**
     * 查询llm_route_rule列表
     */
    List<LlmRouteRuleVo> queryList(LlmRouteRuleBo bo);

    /**
     * 新增llm_route_rule
     */
    Boolean insertByBo(LlmRouteRuleBo bo);

    /**
     * 修改llm_route_rule
     */
    Boolean updateByBo(LlmRouteRuleBo bo);

    /**
     * 校验并批量删除llm_route_rule信息
     */
    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);
}
