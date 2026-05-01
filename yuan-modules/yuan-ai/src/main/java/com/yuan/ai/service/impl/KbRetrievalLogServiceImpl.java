package com.yuan.ai.service.impl;

import com.yuan.common.core.utils.MapstructUtils;
    import com.yuan.core.page.TableDataInfo;
    import com.yuan.core.page.PageQuery;
    import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.yuan.ai.domain.bo.KbRetrievalLogBo;
import com.yuan.ai.domain.vo.KbRetrievalLogVo;
import com.yuan.ai.domain.KbRetrievalLog;
import com.yuan.ai.mapper.KbRetrievalLogMapper;
import com.yuan.ai.service.KbRetrievalLogService;
import com.yuan.common.core.utils.StringUtils;

import java.util.List;
import java.util.Map;
import java.util.Collection;

/**
 * 知识库检索日志表Service业务层处理
 *
 * @author yuan
 * @date Fri May 01 16:08:45 CST 2026
 */
@RequiredArgsConstructor
@Service
public class KbRetrievalLogServiceImpl implements KbRetrievalLogService {

    private final KbRetrievalLogMapper baseMapper;

    /**
     * 查询知识库检索日志表
     */
    @Override
    public KbRetrievalLogVo queryById(Long logId) {
        return baseMapper.selectVoById(logId);
    }

        /**
         * 查询知识库检索日志表列表
         */
        @Override
        public TableDataInfo<KbRetrievalLogVo> queryPageList(KbRetrievalLogBo bo, PageQuery pageQuery) {
            LambdaQueryWrapper<KbRetrievalLog> lqw = buildQueryWrapper(bo);
            Page<KbRetrievalLogVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
            return TableDataInfo.build(result);
        }

    /**
     * 查询知识库检索日志表列表
     */
    @Override
    public List<KbRetrievalLogVo> queryList(KbRetrievalLogBo bo) {
        LambdaQueryWrapper<KbRetrievalLog> lqw = buildQueryWrapper(bo);
        return baseMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<KbRetrievalLog> buildQueryWrapper(KbRetrievalLogBo bo) {
        LambdaQueryWrapper<KbRetrievalLog> lqw = Wrappers.lambdaQuery();
                    lqw.eq(bo.getLogId() != null, KbRetrievalLog::getLogId, bo.getLogId());
                    lqw.eq(StringUtils.isNotBlank(bo.getTenantId()), KbRetrievalLog::getTenantId, bo.getTenantId());
                    lqw.eq(bo.getKbId() != null, KbRetrievalLog::getKbId, bo.getKbId());
                    lqw.eq(StringUtils.isNotBlank(bo.getKbIds()), KbRetrievalLog::getKbIds, bo.getKbIds());
                    lqw.eq(bo.getSessionId() != null, KbRetrievalLog::getSessionId, bo.getSessionId());
                    lqw.eq(bo.getConversationId() != null, KbRetrievalLog::getConversationId, bo.getConversationId());
                    lqw.eq(bo.getMessageId() != null, KbRetrievalLog::getMessageId, bo.getMessageId());
                    lqw.eq(bo.getInvocationId() != null, KbRetrievalLog::getInvocationId, bo.getInvocationId());
                    lqw.eq(StringUtils.isNotBlank(bo.getQueryText()), KbRetrievalLog::getQueryText, bo.getQueryText());
                    lqw.eq(StringUtils.isNotBlank(bo.getRewriteQuery()), KbRetrievalLog::getRewriteQuery, bo.getRewriteQuery());
                    lqw.eq(bo.getEmbeddingModelId() != null, KbRetrievalLog::getEmbeddingModelId, bo.getEmbeddingModelId());
                    lqw.eq(bo.getTopK() != null, KbRetrievalLog::getTopK, bo.getTopK());
                    lqw.eq(bo.getMinScore() != null, KbRetrievalLog::getMinScore, bo.getMinScore());
                    lqw.eq(bo.getHitCount() != null, KbRetrievalLog::getHitCount, bo.getHitCount());
                    lqw.eq(bo.getUsedCount() != null, KbRetrievalLog::getUsedCount, bo.getUsedCount());
                    lqw.eq(bo.getLatencyMs() != null, KbRetrievalLog::getLatencyMs, bo.getLatencyMs());
                    lqw.eq(StringUtils.isNotBlank(bo.getStatus()), KbRetrievalLog::getStatus, bo.getStatus());
                    lqw.eq(StringUtils.isNotBlank(bo.getErrorMessage()), KbRetrievalLog::getErrorMessage, bo.getErrorMessage());
                    lqw.eq(StringUtils.isNotBlank(bo.getCreateBy()), KbRetrievalLog::getCreateBy, bo.getCreateBy());
                    lqw.eq(bo.getCreateTime() != null, KbRetrievalLog::getCreateTime, bo.getCreateTime());
                    lqw.eq(StringUtils.isNotBlank(bo.getUpdateBy()), KbRetrievalLog::getUpdateBy, bo.getUpdateBy());
                    lqw.eq(bo.getUpdateTime() != null, KbRetrievalLog::getUpdateTime, bo.getUpdateTime());
                    lqw.eq(StringUtils.isNotBlank(bo.getDelFlag()), KbRetrievalLog::getDelFlag, bo.getDelFlag());
        return lqw;
    }

    /**
     * 新增知识库检索日志表
     */
    @Override
    public Boolean insertByBo(KbRetrievalLogBo bo) {
        KbRetrievalLog add = MapstructUtils.convert(bo, KbRetrievalLog. class);
        validEntityBeforeSave(add);
        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            bo.setLogId(add.getLogId());
        }
        return flag;
    }

    /**
     * 修改知识库检索日志表
     */
    @Override
    public Boolean updateByBo(KbRetrievalLogBo bo) {
        KbRetrievalLog update = MapstructUtils.convert(bo, KbRetrievalLog. class);
        validEntityBeforeSave(update);
        return baseMapper.updateById(update) > 0;
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(KbRetrievalLog entity) {
        //TODO 做一些数据校验,如唯一约束
    }

    /**
     * 批量删除知识库检索日志表
     */
    @Override
    public Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid) {
        if (isValid) {
            //TODO 做一些业务上的校验,判断是否需要校验
        }
        return baseMapper.deleteBatchIds(ids) > 0;
    }
}
