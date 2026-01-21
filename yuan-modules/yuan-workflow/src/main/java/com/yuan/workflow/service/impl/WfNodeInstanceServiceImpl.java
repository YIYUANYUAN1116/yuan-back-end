package com.yuan.workflow.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yuan.common.core.utils.MapstructUtils;
import com.yuan.common.core.utils.StringUtils;
import com.yuan.core.page.PageQuery;
import com.yuan.core.page.TableDataInfo;
import com.yuan.workflow.core.parser.FlowParser;
import com.yuan.workflow.domain.enums.NodeStatus;
import com.yuan.workflow.domain.enums.NodeType;
import com.yuan.workflow.mapper.WfDefinitionMapper;
import com.yuan.workflow.model.logicflow.LfNode;
import lombok.RequiredArgsConstructor;
import com.yuan.workflow.domain.WfNodeInstance;
import com.yuan.workflow.domain.bo.WfNodeInstanceBo;
import com.yuan.workflow.domain.vo.WfNodeInstanceVo;
import com.yuan.workflow.mapper.WfNodeInstanceMapper;
import com.yuan.workflow.service.WfNodeInstanceService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;
import java.util.Set;

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
    private final WfDefinitionMapper wfDefinitionMapper;
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
        LambdaQueryWrapper<WfNodeInstance> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(WfNodeInstance::getInstanceId, instanceId)
                .orderByAsc(WfNodeInstance::getOrderNo);
        return baseMapper.selectVoList(queryWrapper);
    }

    @Override
    public List<WfNodeInstanceVo> getTimelineByInstanceId(Long instanceId) {

        LambdaQueryWrapper<WfNodeInstance> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(WfNodeInstance::getInstanceId, instanceId)
                .orderByAsc(WfNodeInstance::getOrderNo);
//        flowParser.parse()
        return List.of();
    }
}
