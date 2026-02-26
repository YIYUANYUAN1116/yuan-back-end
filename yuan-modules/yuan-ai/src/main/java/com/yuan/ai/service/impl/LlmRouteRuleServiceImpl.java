package com.yuan.ai.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yuan.ai.domain.LlmRouteRule;
import com.yuan.ai.domain.bo.LlmRouteRuleBo;
import com.yuan.ai.domain.vo.LlmRouteRuleVo;
import com.yuan.ai.mapper.LlmRouteRuleMapper;
import com.yuan.ai.service.LlmRouteRuleService;
import com.yuan.common.core.utils.MapstructUtils;
import com.yuan.common.core.utils.StringUtils;
import com.yuan.core.page.PageQuery;
import com.yuan.core.page.TableDataInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

/**
 * llm_route_ruleService业务层处理
 *
 * @author yuan
 * @date Thu Feb 26 21:44:32 CST 2026
 */
@RequiredArgsConstructor
@Service
public class LlmRouteRuleServiceImpl implements LlmRouteRuleService {

    private final LlmRouteRuleMapper baseMapper;

    /**
     * 查询llm_route_rule
     */
    @Override
    public LlmRouteRuleVo queryById(Long id) {
        return baseMapper.selectVoById(id);
    }

        /**
         * 查询llm_route_rule列表
         */
        @Override
        public TableDataInfo<LlmRouteRuleVo> queryPageList(LlmRouteRuleBo bo, PageQuery pageQuery) {
            LambdaQueryWrapper<LlmRouteRule> lqw = buildQueryWrapper(bo);
            Page<LlmRouteRuleVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
            return TableDataInfo.build(result);
        }

    /**
     * 查询llm_route_rule列表
     */
    @Override
    public List<LlmRouteRuleVo> queryList(LlmRouteRuleBo bo) {
        LambdaQueryWrapper<LlmRouteRule> lqw = buildQueryWrapper(bo);
        return baseMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<LlmRouteRule> buildQueryWrapper(LlmRouteRuleBo bo) {
        LambdaQueryWrapper<LlmRouteRule> lqw = Wrappers.lambdaQuery();
                    lqw.eq(bo.getId() != null, LlmRouteRule::getId, bo.getId());
                    lqw.eq(StringUtils.isNotBlank(bo.getTenantId()), LlmRouteRule::getTenantId, bo.getTenantId());
                    lqw.eq(StringUtils.isNotBlank(bo.getRuleName()), LlmRouteRule::getRuleName, bo.getRuleName());
                    lqw.eq(StringUtils.isNotBlank(bo.getMatchJson()), LlmRouteRule::getMatchJson, bo.getMatchJson());
                    lqw.eq(StringUtils.isNotBlank(bo.getCandidateEndpoints()), LlmRouteRule::getCandidateEndpoints, bo.getCandidateEndpoints());
                    lqw.eq(StringUtils.isNotBlank(bo.getStrategy()), LlmRouteRule::getStrategy, bo.getStrategy());
                    lqw.eq(bo.getEnabled() != null, LlmRouteRule::getEnabled, bo.getEnabled());
                    lqw.eq(bo.getPriority() != null, LlmRouteRule::getPriority, bo.getPriority());
        return lqw;
    }

    /**
     * 新增llm_route_rule
     */
    @Override
    public Boolean insertByBo(LlmRouteRuleBo bo) {
        LlmRouteRule add = MapstructUtils.convert(bo, LlmRouteRule. class);
        validEntityBeforeSave(add);
        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            bo.setId(add.getId());
        }
        return flag;
    }

    /**
     * 修改llm_route_rule
     */
    @Override
    public Boolean updateByBo(LlmRouteRuleBo bo) {
        LlmRouteRule update = MapstructUtils.convert(bo, LlmRouteRule. class);
        validEntityBeforeSave(update);
        return baseMapper.updateById(update) > 0;
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(LlmRouteRule entity) {
        //TODO 做一些数据校验,如唯一约束
    }

    /**
     * 批量删除llm_route_rule
     */
    @Override
    public Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid) {
        if (isValid) {
            //TODO 做一些业务上的校验,判断是否需要校验
        }
        return baseMapper.deleteBatchIds(ids) > 0;
    }
}
