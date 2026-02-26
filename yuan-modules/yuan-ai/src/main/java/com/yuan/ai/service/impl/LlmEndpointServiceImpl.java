package com.yuan.ai.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yuan.ai.domain.LlmEndpoint;
import com.yuan.ai.domain.bo.LlmEndpointBo;
import com.yuan.ai.domain.vo.LlmEndpointVo;
import com.yuan.ai.mapper.LlmEndpointMapper;
import com.yuan.ai.service.LlmEndpointService;
import com.yuan.common.core.utils.MapstructUtils;
import com.yuan.common.core.utils.StringUtils;
import com.yuan.core.page.PageQuery;
import com.yuan.core.page.TableDataInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

/**
 * llm_endpointService业务层处理
 *
 * @author yuan
 * @date Thu Feb 26 21:44:21 CST 2026
 */
@RequiredArgsConstructor
@Service
public class LlmEndpointServiceImpl implements LlmEndpointService {

    private final LlmEndpointMapper baseMapper;

    /**
     * 查询llm_endpoint
     */
    @Override
    public LlmEndpointVo queryById(Long id) {
        return baseMapper.selectVoById(id);
    }

        /**
         * 查询llm_endpoint列表
         */
        @Override
        public TableDataInfo<LlmEndpointVo> queryPageList(LlmEndpointBo bo, PageQuery pageQuery) {
            LambdaQueryWrapper<LlmEndpoint> lqw = buildQueryWrapper(bo);
            Page<LlmEndpointVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
            return TableDataInfo.build(result);
        }

    /**
     * 查询llm_endpoint列表
     */
    @Override
    public List<LlmEndpointVo> queryList(LlmEndpointBo bo) {
        LambdaQueryWrapper<LlmEndpoint> lqw = buildQueryWrapper(bo);
        return baseMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<LlmEndpoint> buildQueryWrapper(LlmEndpointBo bo) {
        LambdaQueryWrapper<LlmEndpoint> lqw = Wrappers.lambdaQuery();
                    lqw.eq(bo.getId() != null, LlmEndpoint::getId, bo.getId());
                    lqw.eq(StringUtils.isNotBlank(bo.getTenantId()), LlmEndpoint::getTenantId, bo.getTenantId());
                    lqw.eq(StringUtils.isNotBlank(bo.getEndpointKey()), LlmEndpoint::getEndpointKey, bo.getEndpointKey());
                    lqw.eq(StringUtils.isNotBlank(bo.getProviderCode()), LlmEndpoint::getProviderCode, bo.getProviderCode());
                    lqw.eq(StringUtils.isNotBlank(bo.getBaseUrl()), LlmEndpoint::getBaseUrl, bo.getBaseUrl());
                    lqw.eq(StringUtils.isNotBlank(bo.getApiKey()), LlmEndpoint::getApiKey, bo.getApiKey());
                    lqw.eq(bo.getDefaultModelId() != null, LlmEndpoint::getDefaultModelId, bo.getDefaultModelId());
                    lqw.eq(bo.getEnabled() != null, LlmEndpoint::getEnabled, bo.getEnabled());
                    lqw.eq(bo.getPriority() != null, LlmEndpoint::getPriority, bo.getPriority());
                    lqw.eq(StringUtils.isNotBlank(bo.getTagsJson()), LlmEndpoint::getTagsJson, bo.getTagsJson());
                    lqw.eq(bo.getCreateTime() != null, LlmEndpoint::getCreateTime, bo.getCreateTime());
                    lqw.eq(bo.getUpdateTime() != null, LlmEndpoint::getUpdateTime, bo.getUpdateTime());
        return lqw;
    }

    /**
     * 新增llm_endpoint
     */
    @Override
    public Boolean insertByBo(LlmEndpointBo bo) {
        LlmEndpoint add = MapstructUtils.convert(bo, LlmEndpoint. class);
        validEntityBeforeSave(add);
        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            bo.setId(add.getId());
        }
        return flag;
    }

    /**
     * 修改llm_endpoint
     */
    @Override
    public Boolean updateByBo(LlmEndpointBo bo) {
        LlmEndpoint update = MapstructUtils.convert(bo, LlmEndpoint. class);
        validEntityBeforeSave(update);
        return baseMapper.updateById(update) > 0;
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(LlmEndpoint entity) {
        //TODO 做一些数据校验,如唯一约束
    }

    /**
     * 批量删除llm_endpoint
     */
    @Override
    public Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid) {
        if (isValid) {
            //TODO 做一些业务上的校验,判断是否需要校验
        }
        return baseMapper.deleteBatchIds(ids) > 0;
    }

    @Override
    public LlmEndpoint getEnabledByKey(String tenantId, String endpointKey) {
        return baseMapper.selectOne(new LambdaQueryWrapper<LlmEndpoint>()
                .eq(LlmEndpoint::getTenantId, tenantId)
                .eq(LlmEndpoint::getEndpointKey, endpointKey)
                .eq(LlmEndpoint::getEnabled, 1)
                .last("limit 1"));
    }

    @Override
    public LlmEndpoint pickHighestPriority(String tenantId) {
        return baseMapper.selectOne(new LambdaQueryWrapper<LlmEndpoint>()
                .eq(LlmEndpoint::getTenantId, tenantId)
                .eq(LlmEndpoint::getEnabled, 1)
                .orderByDesc(LlmEndpoint::getPriority)
                .last("limit 1"));
    }
}
