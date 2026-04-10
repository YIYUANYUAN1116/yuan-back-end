package com.yuan.ai.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yuan.ai.domain.LlmPolicy;
import com.yuan.ai.domain.bo.LlmPolicyBo;
import com.yuan.ai.domain.vo.LlmPolicyVo;
import com.yuan.ai.mapper.LlmPolicyMapper;
import com.yuan.ai.service.LlmPolicyService;
import com.yuan.common.core.utils.MapstructUtils;
import com.yuan.common.core.utils.StringUtils;
import com.yuan.core.page.PageQuery;
import com.yuan.core.page.TableDataInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

/**
 * llm_policyService业务层处理
 *
 * @author yuan
 * @date Thu Feb 26 21:44:28 CST 2026
 */
@RequiredArgsConstructor
@Service
public class LlmPolicyServiceImpl implements LlmPolicyService {

    private final LlmPolicyMapper baseMapper;

    /**
     * 查询llm_policy
     */
    @Override
    public LlmPolicyVo queryById(Long id) {
        return baseMapper.selectVoById(id);
    }

        /**
         * 查询llm_policy列表
         */
        @Override
        public TableDataInfo<LlmPolicyVo> queryPageList(LlmPolicyBo bo, PageQuery pageQuery) {
            LambdaQueryWrapper<LlmPolicy> lqw = buildQueryWrapper(bo);
            Page<LlmPolicyVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
            return TableDataInfo.build(result);
        }

    /**
     * 查询llm_policy列表
     */
    @Override
    public List<LlmPolicyVo> queryList(LlmPolicyBo bo) {
        LambdaQueryWrapper<LlmPolicy> lqw = buildQueryWrapper(bo);
        return baseMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<LlmPolicy> buildQueryWrapper(LlmPolicyBo bo) {
        LambdaQueryWrapper<LlmPolicy> lqw = Wrappers.lambdaQuery();
                    lqw.eq(bo.getId() != null, LlmPolicy::getId, bo.getId());
                    lqw.eq(StringUtils.isNotBlank(bo.getTenantId()), LlmPolicy::getTenantId, bo.getTenantId());
                    lqw.eq(StringUtils.isNotBlank(bo.getScopeType()), LlmPolicy::getScopeType, bo.getScopeType());
                    lqw.eq(StringUtils.isNotBlank(bo.getScopeId()), LlmPolicy::getScopeId, bo.getScopeId());
                    lqw.eq(bo.getDailyCalls() != null, LlmPolicy::getDailyCalls, bo.getDailyCalls());
                    lqw.eq(bo.getDailyTokens() != null, LlmPolicy::getDailyTokens, bo.getDailyTokens());
                    lqw.eq(bo.getConcurrency() != null, LlmPolicy::getConcurrency, bo.getConcurrency());
                    lqw.eq(StringUtils.isNotBlank(bo.getAllowEndpoints()), LlmPolicy::getAllowEndpoints, bo.getAllowEndpoints());
                    lqw.eq(bo.getEnabled() != null, LlmPolicy::getEnabled, bo.getEnabled());
        return lqw;
    }

    /**
     * 新增llm_policy
     */
    @Override
    public Boolean insertByBo(LlmPolicyBo bo) {
        LlmPolicy add = MapstructUtils.convert(bo, LlmPolicy. class);
        validEntityBeforeSave(add);
        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            bo.setId(add.getId());
        }
        return flag;
    }

    /**
     * 修改llm_policy
     */
    @Override
    public Boolean updateByBo(LlmPolicyBo bo) {
        LlmPolicy update = MapstructUtils.convert(bo, LlmPolicy. class);
        validEntityBeforeSave(update);
        return baseMapper.updateById(update) > 0;
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(LlmPolicy entity) {
        //TODO 做一些数据校验,如唯一约束
    }

    /**
     * 批量删除llm_policy
     */
    @Override
    public Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid) {
        if (isValid) {
            //TODO 做一些业务上的校验,判断是否需要校验
        }
        return baseMapper.deleteBatchIds(ids) > 0;
    }
}
