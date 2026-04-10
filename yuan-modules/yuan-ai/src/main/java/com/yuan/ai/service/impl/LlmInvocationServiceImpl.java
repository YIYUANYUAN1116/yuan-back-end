package com.yuan.ai.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yuan.ai.domain.LlmInvocation;
import com.yuan.ai.domain.bo.LlmInvocationBo;
import com.yuan.ai.domain.vo.LlmInvocationVo;
import com.yuan.ai.mapper.LlmInvocationMapper;
import com.yuan.ai.service.LlmInvocationService;
import com.yuan.common.core.utils.MapstructUtils;
import com.yuan.common.core.utils.StringUtils;
import com.yuan.core.page.PageQuery;
import com.yuan.core.page.TableDataInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * llm_invocationService业务层处理
 *
 * @author yuan
 * @date Thu Feb 26 21:44:25 CST 2026
 */
@RequiredArgsConstructor
@Service
public class LlmInvocationServiceImpl implements LlmInvocationService {

    private final LlmInvocationMapper baseMapper;
    private final ObjectMapper om;
    /**
     * 查询llm_invocation
     */
    @Override
    public LlmInvocationVo queryById(Long id) {
        return baseMapper.selectVoById(id);
    }

        /**
         * 查询llm_invocation列表
         */
        @Override
        public TableDataInfo<LlmInvocationVo> queryPageList(LlmInvocationBo bo, PageQuery pageQuery) {
            LambdaQueryWrapper<LlmInvocation> lqw = buildQueryWrapper(bo);
            Page<LlmInvocationVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
            return TableDataInfo.build(result);
        }

    /**
     * 查询llm_invocation列表
     */
    @Override
    public List<LlmInvocationVo> queryList(LlmInvocationBo bo) {
        LambdaQueryWrapper<LlmInvocation> lqw = buildQueryWrapper(bo);
        return baseMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<LlmInvocation> buildQueryWrapper(LlmInvocationBo bo) {
        LambdaQueryWrapper<LlmInvocation> lqw = Wrappers.lambdaQuery();
                    lqw.eq(bo.getId() != null, LlmInvocation::getId, bo.getId());
                    lqw.eq(StringUtils.isNotBlank(bo.getTenantId()), LlmInvocation::getTenantId, bo.getTenantId());
                    lqw.eq(StringUtils.isNotBlank(bo.getTraceId()), LlmInvocation::getTraceId, bo.getTraceId());
                    lqw.eq(StringUtils.isNotBlank(bo.getModelName()), LlmInvocation::getModelName, bo.getModelName());
                    lqw.eq(bo.getConversationId() != null, LlmInvocation::getConversationId, bo.getConversationId());
                    lqw.eq(bo.getMessageId() != null, LlmInvocation::getMessageId, bo.getMessageId());
                    lqw.eq(StringUtils.isNotBlank(bo.getRequestJson()), LlmInvocation::getRequestJson, bo.getRequestJson());
                    lqw.eq(StringUtils.isNotBlank(bo.getResponseText()), LlmInvocation::getResponseText, bo.getResponseText());
                    lqw.eq(StringUtils.isNotBlank(bo.getResponseJson()), LlmInvocation::getResponseJson, bo.getResponseJson());
                    lqw.eq(bo.getTokenIn() != null, LlmInvocation::getTokenIn, bo.getTokenIn());
                    lqw.eq(bo.getTokenOut() != null, LlmInvocation::getTokenOut, bo.getTokenOut());
                    lqw.eq(bo.getCostAmount() != null, LlmInvocation::getCostAmount, bo.getCostAmount());
                    lqw.eq(bo.getLatencyMs() != null, LlmInvocation::getLatencyMs, bo.getLatencyMs());
                    lqw.eq(StringUtils.isNotBlank(bo.getStatus()), LlmInvocation::getStatus, bo.getStatus());
                    lqw.eq(StringUtils.isNotBlank(bo.getErrorMsg()), LlmInvocation::getErrorMsg, bo.getErrorMsg());
                    lqw.eq(bo.getCreateTime() != null, LlmInvocation::getCreateTime, bo.getCreateTime());
        return lqw;
    }

    /**
     * 新增llm_invocation
     */
    @Override
    public Boolean insertByBo(LlmInvocationBo bo) {
        LlmInvocation add = MapstructUtils.convert(bo, LlmInvocation. class);
        validEntityBeforeSave(add);
        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            bo.setId(add.getId());
        }
        return flag;
    }

    /**
     * 修改llm_invocation
     */
    @Override
    public Boolean updateByBo(LlmInvocationBo bo) {
        LlmInvocation update = MapstructUtils.convert(bo, LlmInvocation. class);
        validEntityBeforeSave(update);
        return baseMapper.updateById(update) > 0;
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(LlmInvocation entity) {
        //TODO 做一些数据校验,如唯一约束
    }

    /**
     * 批量删除llm_invocation
     */
    @Override
    public Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid) {
        if (isValid) {
            //TODO 做一些业务上的校验,判断是否需要校验
        }
        return baseMapper.deleteBatchIds(ids) > 0;
    }


    @Override
    public long begin(String tenantId, String traceId, Long endpointId, Long providerId, String modelName,
                      Long conversationId, Long assistantMsgId, Map<String, Object> reqJson) {
        try {
            LlmInvocation inv = new LlmInvocation();
            inv.setTenantId(tenantId);
            inv.setTraceId(traceId);
            inv.setEndpointId(endpointId);
            inv.setProviderId(providerId);
            inv.setModelName(modelName);
            inv.setConversationId(conversationId);
            inv.setMessageId(assistantMsgId);
            inv.setRequestJson(om.writeValueAsString(reqJson));
            inv.setStatus("FAILED"); // 先置失败，成功后更新
            inv.setCreateTime(LocalDateTime.now());
            baseMapper.insert(inv);
            return inv.getId();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    public void success(long id, String responseText, int latencyMs) {
        LlmInvocation upd = new LlmInvocation();
        upd.setId(id);
        upd.setResponseText(responseText);
        upd.setLatencyMs(latencyMs);
        upd.setStatus("SUCCESS");
        baseMapper.updateById(upd);
    }
    @Override
    public void fail(long id, String errorMsg, int latencyMs) {
        LlmInvocation upd = new LlmInvocation();
        upd.setId(id);
        upd.setErrorMsg(errorMsg);
        upd.setLatencyMs(latencyMs);
        upd.setStatus("FAILED");
        baseMapper.updateById(upd);
    }
}
