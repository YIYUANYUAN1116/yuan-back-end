package com.yuan.workflow.service;

import com.yuan.core.page.PageQuery;
import com.yuan.core.page.TableDataInfo;
import com.yuan.workflow.domain.WfNodeInstance;
import com.yuan.workflow.domain.bo.WfNodeInstanceBo;
import com.yuan.workflow.domain.enums.NodeStatus;
import com.yuan.workflow.domain.vo.WfNodeInstanceVo;
import com.yuan.workflow.domain.vo.detail.WfTimelineEventVo;
import com.yuan.workflow.model.logicflow.LfGraph;
import com.yuan.workflow.model.logicflow.LfNode;

import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
 * wfnService接口
 *
 
 * @date Sun Dec 28 11:26:37 CST 2025
 */
public interface WfNodeInstanceService {

    /**
     * 查询wfn
     */
        WfNodeInstanceVo queryById(Long id);

        /**
         * 查询wfn列表
         */
        TableDataInfo<WfNodeInstanceVo> queryPageList(WfNodeInstanceBo bo, PageQuery pageQuery);

    /**
     * 查询wfn列表
     */
    List<WfNodeInstanceVo> queryList(WfNodeInstanceBo bo);

    /**
     * 新增wfn
     */
    Boolean insertByBo(WfNodeInstanceBo bo);

    /**
     * 修改wfn
     */
    Boolean updateByBo(WfNodeInstanceBo bo);

    /**
     * 校验并批量删除wfn信息
     */
    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);

    WfNodeInstance createNodeInstance(Long instanceId, LfNode lfNode, NodeStatus nodeStatus, int orderNo);

    int nextOrderNo(Long instanceId);

    List<WfNodeInstance> listWaitNodesByInstanceIds(Set<Long> instanceIds);

    void deleteByInstanceIds(Collection<Long> ids);

    List<WfNodeInstanceVo> selectVoByInstanceId(Long instanceId);

    List<WfTimelineEventVo> selectTimelineByInstanceId(Long instanceId);

    List<WfNodeInstanceVo> selectStepNodeVoByInstanceId(Long instanceId);

    List<WfTimelineEventVo> selectTimelineByInstanceIdAndLfGraph(Long instanceId, LfGraph lfGraph);

    List<WfNodeInstanceVo> selectStepNodeVoByInstanceIdAndLfGraph(Long instanceId, LfGraph lfGraph);
}
