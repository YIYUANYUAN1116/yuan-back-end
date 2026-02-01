package com.yuan.workflow.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yuan.common.core.utils.MapstructUtils;
import com.yuan.common.core.utils.StringUtils;
import com.yuan.core.page.PageQuery;
import com.yuan.core.page.TableDataInfo;
import com.yuan.system.api.UserQueryApi;
import com.yuan.workflow.core.parser.FlowParser;
import com.yuan.workflow.domain.WfDefinition;
import com.yuan.workflow.domain.WfNodeInstance;
import com.yuan.workflow.domain.WfTransitionLog;
import com.yuan.workflow.domain.bo.WfNodeInstanceBo;
import com.yuan.workflow.domain.enums.NodeStatus;
import com.yuan.workflow.domain.enums.NodeType;
import com.yuan.workflow.domain.vo.WfNodeInstanceVo;
import com.yuan.workflow.domain.vo.detail.WfTimelineEventVo;
import com.yuan.workflow.enums.OperatorType;
import com.yuan.workflow.mapper.WfDefinitionMapper;
import com.yuan.workflow.mapper.WfNodeInstanceMapper;
import com.yuan.workflow.model.logicflow.LfGraph;
import com.yuan.workflow.model.logicflow.LfNode;
import com.yuan.workflow.service.WfNodeInstanceService;
import com.yuan.workflow.service.WfTransitionLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

/**
 * wfnService业务层处理
 *
 
 * @date Sun Dec 28 11:26:37 CST 2025
 */
@RequiredArgsConstructor
@Service
public class WfNodeInstanceServiceImpl implements WfNodeInstanceService {

    private final WfNodeInstanceMapper baseMapper;
    private final FlowParser flowParser;
    private final WfDefinitionMapper definitionMapper;
    private final WfTransitionLogService transitionLogService;
    private final UserQueryApi userQueryApi;


    /**
     * 查询wfn
     */
    @Override
    public WfNodeInstanceVo queryById(Long id) {
        return baseMapper.selectVoById(id);
    }

        /**
         * 查询wfn列表
         */
        @Override
        public TableDataInfo<WfNodeInstanceVo> queryPageList(WfNodeInstanceBo bo, PageQuery pageQuery) {
            LambdaQueryWrapper<WfNodeInstance> lqw = buildQueryWrapper(bo);
            Page<WfNodeInstanceVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
            return TableDataInfo.build(result);
        }

    /**
     * 查询wfn列表
     */
    @Override
    public List<WfNodeInstanceVo> queryList(WfNodeInstanceBo bo) {
        LambdaQueryWrapper<WfNodeInstance> lqw = buildQueryWrapper(bo);
        return baseMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<WfNodeInstance> buildQueryWrapper(WfNodeInstanceBo bo) {
        LambdaQueryWrapper<WfNodeInstance> lqw = Wrappers.lambdaQuery();
                    lqw.eq(bo.getId() != null, WfNodeInstance::getId, bo.getId());
                    lqw.eq(bo.getInstanceId() != null, WfNodeInstance::getInstanceId, bo.getInstanceId());
                    lqw.eq(StringUtils.isNotBlank(bo.getNodeKey()), WfNodeInstance::getNodeKey, bo.getNodeKey());
                    lqw.eq(StringUtils.isNotBlank(bo.getNodeType()), WfNodeInstance::getNodeType, bo.getNodeType());
                    lqw.eq(StringUtils.isNotBlank(bo.getAssigneeType()), WfNodeInstance::getAssigneeType, bo.getAssigneeType());
                    lqw.eq(StringUtils.isNotBlank(bo.getAssigneeValue()), WfNodeInstance::getAssigneeValue, bo.getAssigneeValue());
                    lqw.eq(StringUtils.isNotBlank(bo.getStatus()), WfNodeInstance::getStatus, bo.getStatus());
                    lqw.eq(bo.getOrderNo() != null, WfNodeInstance::getOrderNo, bo.getOrderNo());
                    lqw.eq(bo.getCreateTime() != null, WfNodeInstance::getCreateTime, bo.getCreateTime());
        return lqw;
    }

    /**
     * 新增wfn
     */
    @Override
    public Boolean insertByBo(WfNodeInstanceBo bo) {
        WfNodeInstance add = MapstructUtils.convert(bo, WfNodeInstance. class);
        validEntityBeforeSave(add);
        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            bo.setId(add.getId());
        }
        return flag;
    }

    /**
     * 修改wfn
     */
    @Override
    public Boolean updateByBo(WfNodeInstanceBo bo) {
        WfNodeInstance update = MapstructUtils.convert(bo, WfNodeInstance. class);
        validEntityBeforeSave(update);
        return baseMapper.updateById(update) > 0;
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(WfNodeInstance entity) {
        //TODO 做一些数据校验,如唯一约束
    }

    /**
     * 批量删除wfn
     */
    @Override
    public Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid) {
        if (isValid) {
            //TODO 做一些业务上的校验,判断是否需要校验
        }
        return baseMapper.deleteBatchIds(ids) > 0;
    }

    @Override
    public WfNodeInstance createNodeInstance(Long instanceId, LfNode lfNode, NodeStatus nodeStatus, int orderNo) {
        WfNodeInstance ni = new WfNodeInstance();
        ni.setInstanceId(instanceId);
        ni.setNodeKey(lfNode.getId());
        ni.setNodeType(NodeType.of(lfNode.getProperties().getWfType()));
        ni.setNodeName(lfNode.getText().getValue());
        ni.setStatus(nodeStatus);
        ni.setOrderNo(orderNo);
        baseMapper.insert(ni);
        return ni;
    }

    /**
     * 获取nextOrderNo
     * @param instanceId
     * @return
     */
    @Override
    public int nextOrderNo(Long instanceId) {
        return baseMapper.nextOrderNo(instanceId);
    }

    @Override
    public List<WfNodeInstance> listWaitNodesByInstanceIds(Set<Long> instanceIds) {
        LambdaQueryWrapper<WfNodeInstance> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.in(WfNodeInstance::getInstanceId, instanceIds)
                .eq(WfNodeInstance::getStatus, NodeStatus.WAIT);
        return baseMapper.selectList(queryWrapper);
    }

    @Override
    @Transactional
    public void deleteByInstanceIds(Collection<Long> ids) {
        baseMapper.delete(Wrappers.<WfNodeInstance>lambdaQuery()
                .in(WfNodeInstance::getInstanceId, ids));
    }

    @Override
    public List<WfNodeInstanceVo> selectVoByInstanceId(Long instanceId) {
        return baseMapper.selectVoByInstanceId(instanceId);
    }

    @Override
    public List<WfTimelineEventVo> selectTimelineByInstanceId(Long instanceId) {

        WfDefinition def = definitionMapper.selectByInstanceId(instanceId);
        if (def == null) {
            return Collections.emptyList();
        }

        LfGraph graph = flowParser.parseNode(def);

        return selectTimelineByInstanceIdAndLfGraph(instanceId, graph);
    }

    @Override
    public List<WfNodeInstanceVo> selectStepNodeVoByInstanceId(Long instanceId) {
        WfDefinition wfDefinition =  definitionMapper.selectByInstanceId(instanceId);
        LfGraph graph = flowParser.parseNode(wfDefinition);
        return selectStepNodeVoByInstanceIdAndLfGraph(instanceId, graph);
    }

    @Override
    public List<WfTimelineEventVo> selectTimelineByInstanceIdAndLfGraph(Long instanceId, LfGraph graph) {
        if (graph == null || instanceId == null) return List.of();

        Map<String, String> nodeNameMap = graph.getNodes().stream()
                .collect(Collectors.toMap(
                        LfNode::getId,
                        item->item.getText().getValue(),
                        (a, b) -> a
                ));


        List<WfTransitionLog> logs = transitionLogService.selectVoListByInstanceId(instanceId);

        if (logs.isEmpty()) {
            return Collections.emptyList();
        }

        Set<Long> userIds = logs.stream()
                .filter(l -> OperatorType.USER.equals(l.getOperatorType()))
                .map(WfTransitionLog::getOperatorId)
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());

        Map<Long, String> userNameMap = userIds.isEmpty()
                ? Collections.emptyMap()
                : userQueryApi.getUserNameMap(userIds);

        List<WfTimelineEventVo> result = new ArrayList<>();

        for (WfTransitionLog l : logs) {

            WfTimelineEventVo vo = new WfTimelineEventVo();
            vo.setId(l.getId());
            vo.setTime(l.getCreateTime());

            vo.setAction(l.getAction());
            vo.setOperatorType(l.getOperatorType());
            vo.setOperatorId(l.getOperatorId());
            vo.setOperatorName(userNameMap.get(l.getOperatorId()));

            vo.setFromNodeKey(l.getFromNodeKey());
            vo.setFromNodeName(nodeNameMap.get(l.getFromNodeKey()));

            vo.setToNodeKey(l.getToNodeKey());
            vo.setToNodeName(nodeNameMap.get(l.getToNodeKey()));

            vo.setComment(l.getComment());
            vo.setConditionExpr(l.getConditionExpr());
            vo.setResult(l.getResult());

            result.add(vo);
        }

        return result;
    }

    @Override
    public List<WfNodeInstanceVo> selectStepNodeVoByInstanceIdAndLfGraph(Long instanceId, LfGraph graph) {
        if (graph == null || instanceId == null) return List.of();

        List<WfNodeInstanceVo> defNodeVoList =  flowParser.parseNode(graph);

        List<WfNodeInstanceVo> instanceVoList = baseMapper.selectVoByInstanceId(instanceId);
        Map<String, WfNodeInstanceVo> runtimeMap = instanceVoList.stream().collect(Collectors.toMap(
                WfNodeInstanceVo::getNodeKey,
                item -> item,
                (a, b) -> a
        ));

        mergeRuntime(defNodeVoList, runtimeMap);
        return defNodeVoList;
    }

    private void mergeRuntime(List<WfNodeInstanceVo> definitions,Map<String,WfNodeInstanceVo> runtimeMap) {
        for (WfNodeInstanceVo defNode : definitions) {
            WfNodeInstanceVo node = runtimeMap.get(defNode.getNodeKey());
            if (node == null) continue;
            defNode.setId(node.getId());
            defNode.setOperatorId(node.getOperatorId());
            defNode.setOperatorName(node.getOperatorName());
            defNode.setCreateTime(node.getCreateTime());
            defNode.setOrderNo(node.getOrderNo());
            defNode.setFinishedTime(node.getFinishedTime());
            defNode.setInstanceId(node.getInstanceId());
            defNode.setStatus(node.getStatus());
        }
    }
}
