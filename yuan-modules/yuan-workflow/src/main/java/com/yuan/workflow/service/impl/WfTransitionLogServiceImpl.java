package com.yuan.workflow.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yuan.common.core.utils.MapstructUtils;
import com.yuan.common.core.utils.StringUtils;
import com.yuan.core.page.PageQuery;
import com.yuan.core.page.TableDataInfo;
import com.yuan.workflow.cmd.RecordTransitionCmd;
import com.yuan.workflow.domain.WfTransitionLog;
import com.yuan.workflow.domain.bo.WfTransitionLogBo;
import com.yuan.workflow.domain.vo.WfTransitionLogVo;
import com.yuan.workflow.enums.TransitionResult;
import com.yuan.workflow.mapper.WfTransitionLogMapper;
import com.yuan.workflow.service.WfTransitionLogService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * wf_transition_logService业务层处理
 *
 * @author yuan
 * @date Wed Jan 28 21:54:22 CST 2026
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class WfTransitionLogServiceImpl implements WfTransitionLogService {

    private final WfTransitionLogMapper baseMapper;
    private final ObjectMapper objectMapper;

    /**
     * 查询wf_transition_log
     */
    @Override
    public WfTransitionLogVo queryById(Long id) {
        return baseMapper.selectVoById(id);
    }

        /**
         * 查询wf_transition_log列表
         */
        @Override
        public TableDataInfo<WfTransitionLogVo> queryPageList(WfTransitionLogBo bo, PageQuery pageQuery) {
            LambdaQueryWrapper<WfTransitionLog> lqw = buildQueryWrapper(bo);
            Page<WfTransitionLogVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
            return TableDataInfo.build(result);
        }

    /**
     * 查询wf_transition_log列表
     */
    @Override
    public List<WfTransitionLogVo> queryList(WfTransitionLogBo bo) {
        LambdaQueryWrapper<WfTransitionLog> lqw = buildQueryWrapper(bo);
        return baseMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<WfTransitionLog> buildQueryWrapper(WfTransitionLogBo bo) {
        LambdaQueryWrapper<WfTransitionLog> lqw = Wrappers.lambdaQuery();
                    lqw.eq(bo.getId() != null, WfTransitionLog::getId, bo.getId());
                    lqw.eq(StringUtils.isNotBlank(bo.getTenantId()), WfTransitionLog::getTenantId, bo.getTenantId());
                    lqw.eq(bo.getDefId() != null, WfTransitionLog::getDefId, bo.getDefId());
                    lqw.eq(bo.getDefVersion() != null, WfTransitionLog::getDefVersion, bo.getDefVersion());
                    lqw.eq(bo.getInstanceId() != null, WfTransitionLog::getInstanceId, bo.getInstanceId());
                    lqw.eq(bo.getNodeInstanceId() != null, WfTransitionLog::getNodeInstanceId, bo.getNodeInstanceId());
                    lqw.eq(bo.getTaskId() != null, WfTransitionLog::getTaskId, bo.getTaskId());
                    lqw.eq(StringUtils.isNotBlank(bo.getFromNodeKey()), WfTransitionLog::getFromNodeKey, bo.getFromNodeKey());
                    lqw.eq(StringUtils.isNotBlank(bo.getToNodeKey()), WfTransitionLog::getToNodeKey, bo.getToNodeKey());
                    lqw.eq(StringUtils.isNotBlank(bo.getAction()), WfTransitionLog::getAction, bo.getAction());
                    lqw.eq(StringUtils.isNotBlank(bo.getOperatorType()), WfTransitionLog::getOperatorType, bo.getOperatorType());
                    lqw.eq(bo.getOperatorId() != null, WfTransitionLog::getOperatorId, bo.getOperatorId());
                    lqw.eq(StringUtils.isNotBlank(bo.getComment()), WfTransitionLog::getComment, bo.getComment());
                    lqw.eq(StringUtils.isNotBlank(bo.getConditionExpr()), WfTransitionLog::getConditionExpr, bo.getConditionExpr());
                    lqw.eq(StringUtils.isNotBlank(bo.getVariablesSnapshot()), WfTransitionLog::getVariablesSnapshot, bo.getVariablesSnapshot());
                    lqw.eq(StringUtils.isNotBlank(bo.getResult()), WfTransitionLog::getResult, bo.getResult());
                    lqw.eq(bo.getCreateTime() != null, WfTransitionLog::getCreateTime, bo.getCreateTime());
        return lqw;
    }

    /**
     * 新增wf_transition_log
     */
    @Override
    public Boolean insertByBo(WfTransitionLogBo bo) {
        WfTransitionLog add = MapstructUtils.convert(bo, WfTransitionLog. class);
        validEntityBeforeSave(add);
        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            bo.setId(add.getId());
        }
        return flag;
    }

    /**
     * 修改wf_transition_log
     */
    @Override
    public Boolean updateByBo(WfTransitionLogBo bo) {
        WfTransitionLog update = MapstructUtils.convert(bo, WfTransitionLog. class);
        validEntityBeforeSave(update);
        return baseMapper.updateById(update) > 0;
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(WfTransitionLog entity) {
        //TODO 做一些数据校验,如唯一约束
    }

    /**
     * 批量删除wf_transition_log
     */
    @Override
    public Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid) {
        if (isValid) {
            //TODO 做一些业务上的校验,判断是否需要校验
        }
        return baseMapper.deleteBatchIds(ids) > 0;
    }

    @Override
    @Transactional
    public void recordSuccess(RecordTransitionCmd cmd) {
        insert(cmd, TransitionResult.SUCCESS, null);
    }

    @Override
    @Transactional
    public void recordFail(RecordTransitionCmd cmd, Exception ex) {
        insert(cmd, TransitionResult.FAIL, ex);
    }

    @Override
    public List<WfTransitionLog> selectVoListByInstanceId(Long instanceId) {
        LambdaQueryWrapper<WfTransitionLog> wrapper = Wrappers.<WfTransitionLog>lambdaQuery()
                .eq(WfTransitionLog::getInstanceId, instanceId)
                .orderByAsc(WfTransitionLog::getCreateTime);
        return baseMapper.selectList(wrapper);
    }

    private void insert(RecordTransitionCmd cmd, TransitionResult result, Exception ex) {
        WfTransitionLog log = new WfTransitionLog();
        log.setTenantId(cmd.getTenantId());
        log.setDefId(cmd.getDefId());
        log.setDefVersion(cmd.getDefVersion());
        log.setInstanceId(cmd.getInstanceId());

        log.setNodeInstanceId(cmd.getNodeInstanceId());
        log.setTaskId(cmd.getTaskId());

        log.setFromNodeKey(cmd.getFromNodeKey());
        log.setToNodeKey(cmd.getToNodeKey());

        log.setAction(cmd.getAction());

        log.setOperatorType(cmd.getOperatorType());
        log.setOperatorId(cmd.getOperatorId());

        // comment：失败时也可以追加异常提示
        String c = cmd.getComment();
        if (result == TransitionResult.FAIL && ex != null) {
            String msg = ex.getMessage();
            if (msg != null && msg.length() > 200) msg = msg.substring(0, 200);
            c = (c == null ? "" : c + " | ") + "FAIL: " + msg;
        }
        log.setComment(c);

        log.setConditionExpr(cmd.getConditionExpr());

        log.setVariablesSnapshot(toJsonSafe(cmd.getVariablesSnapshot()));

        log.setResult(result);
        log.setCreateTime(LocalDateTime.now());

        baseMapper.insert(log);
    }

    private String toJsonSafe(Map<String, Object> m) {
        if (m == null || m.isEmpty()) return null;
        try {
            return objectMapper.writeValueAsString(m);
        } catch (Exception e) {
            // 万一序列化失败，不影响主流程
            log.warn("[WfTransitionLogService][toJsonSafe] toJsonSafe error", e);
            return "{\"_err\":\"json\"}";
        }
    }
}
