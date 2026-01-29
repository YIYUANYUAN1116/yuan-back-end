package com.yuan.workflow.service;

import com.yuan.core.page.PageQuery;
import com.yuan.core.page.TableDataInfo;
import com.yuan.workflow.cmd.RecordTransitionCmd;
import com.yuan.workflow.domain.WfTransitionLog;
import com.yuan.workflow.domain.bo.WfTransitionLogBo;
import com.yuan.workflow.domain.vo.WfTransitionLogVo;

import java.util.Collection;
import java.util.List;

/**
 * wf_transition_logService接口
 *
 * @author yuan
 * @date Wed Jan 28 21:54:22 CST 2026
 */
public interface WfTransitionLogService {

    /**
     * 查询wf_transition_log
     */
        WfTransitionLogVo queryById(Long id);

        /**
         * 查询wf_transition_log列表
         */
        TableDataInfo<WfTransitionLogVo> queryPageList(WfTransitionLogBo bo, PageQuery pageQuery);

    /**
     * 查询wf_transition_log列表
     */
    List<WfTransitionLogVo> queryList(WfTransitionLogBo bo);

    /**
     * 新增wf_transition_log
     */
    Boolean insertByBo(WfTransitionLogBo bo);

    /**
     * 修改wf_transition_log
     */
    Boolean updateByBo(WfTransitionLogBo bo);

    /**
     * 校验并批量删除wf_transition_log信息
     */
    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);

    void recordSuccess(RecordTransitionCmd cmd);

    void recordFail(RecordTransitionCmd cmd, Exception ex);

    List<WfTransitionLog> selectVoListByInstanceId(Long instanceId);
}
