package com.yuan.workflow.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yuan.common.core.utils.MapstructUtils;
import com.yuan.common.core.utils.StreamUtils;
import com.yuan.common.core.utils.StringUtils;
import com.yuan.common.satoken.utils.LoginHelper;
import com.yuan.core.page.PageQuery;
import com.yuan.core.page.TableDataInfo;
import com.yuan.system.api.UserQueryApi;
import com.yuan.workflow.domain.*;
import com.yuan.workflow.domain.bo.WfTaskBo;
import com.yuan.workflow.domain.enums.TaskStatus;
import com.yuan.workflow.domain.vo.WfTaskVo;
import com.yuan.workflow.domain.vo.WorkItemRowVO;
import com.yuan.workflow.mapper.WfInstanceMapper;
import com.yuan.workflow.mapper.WfNodeInstanceMapper;
import com.yuan.workflow.mapper.WfTaskLogMapper;
import com.yuan.workflow.mapper.WfTaskMapper;
import com.yuan.workflow.service.WfBizRefService;
import com.yuan.workflow.service.WfTaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * wftService业务层处理
 *
 
 * @date Sun Dec 28 11:26:41 CST 2025
 */
@RequiredArgsConstructor
@Service
public class WfTaskServiceImpl implements WfTaskService {

    private final WfTaskMapper baseMapper;
    private final WfInstanceMapper instanceMapper;
    private final WfNodeInstanceMapper nodeInstanceMapper;
    private final WfBizRefService wfBizRefService;
    private final UserQueryApi userQueryApi;
    private final WfTaskLogMapper wfTaskLogMapper;

    /**
     * 查询wft
     */
    @Override
    public WfTaskVo queryById(Long id) {
        return baseMapper.selectVoById(id);
    }

        /**
         * 查询wft列表
         */
        @Override
        public TableDataInfo<WfTaskVo> queryPageList(WfTaskBo bo, PageQuery pageQuery) {
            LambdaQueryWrapper<WfTask> lqw = buildQueryWrapper(bo);
            Page<WfTaskVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
            return TableDataInfo.build(result);
        }

    /**
     * 查询wft列表
     */
    @Override
    public List<WfTaskVo> queryList(WfTaskBo bo) {
        LambdaQueryWrapper<WfTask> lqw = buildQueryWrapper(bo);
        return baseMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<WfTask> buildQueryWrapper(WfTaskBo bo) {
        LambdaQueryWrapper<WfTask> lqw = Wrappers.lambdaQuery();
                    lqw.eq(bo.getId() != null, WfTask::getId, bo.getId());
                    lqw.eq(bo.getTenantId() != null, WfTask::getTenantId, bo.getTenantId());
                    lqw.eq(bo.getInstanceId() != null, WfTask::getInstanceId, bo.getInstanceId());
                    lqw.eq(bo.getNodeInstanceId() != null, WfTask::getNodeInstanceId, bo.getNodeInstanceId());
                    lqw.eq(bo.getAssigneeId() != null, WfTask::getAssigneeId, bo.getAssigneeId());
                    lqw.eq(StringUtils.isNotBlank(bo.getStatus()), WfTask::getStatus, bo.getStatus());
                    lqw.eq(StringUtils.isNotBlank(bo.getAction()), WfTask::getAction, bo.getAction());
                    lqw.eq(StringUtils.isNotBlank(bo.getComment()), WfTask::getComment, bo.getComment());
                    lqw.eq(bo.getCreateTime() != null, WfTask::getCreateTime, bo.getCreateTime());
                    lqw.eq(bo.getFinishTime() != null, WfTask::getFinishTime, bo.getFinishTime());
        return lqw;
    }

    /**
     * 新增wft
     */
    @Override
    public Boolean insertByBo(WfTaskBo bo) {
        WfTask add = MapstructUtils.convert(bo, WfTask. class);
        validEntityBeforeSave(add);
        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            bo.setId(add.getId());
        }
        return flag;
    }

    /**
     * 修改wft
     */
    @Override
    public Boolean updateByBo(WfTaskBo bo) {
        WfTask update = MapstructUtils.convert(bo, WfTask. class);
        validEntityBeforeSave(update);
        return baseMapper.updateById(update) > 0;
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(WfTask entity) {
        //TODO 做一些数据校验,如唯一约束
    }

    /**
     * 批量删除wft
     */
    @Override
    public Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid) {
        if (isValid) {
            //TODO 做一些业务上的校验,判断是否需要校验
        }
        return baseMapper.deleteBatchIds(ids) > 0;
    }

    @Override
    public void createTasks(WfInstance instance, WfNodeInstance nodeInstance, Set<Long> userIds) {
        for (Long uid : userIds) {
            WfTask task = new WfTask();
            task.setTenantId(instance.getTenantId());
            task.setInstanceId(instance.getId());
            task.setNodeInstanceId(nodeInstance.getId());
            task.setAssigneeId(uid);
            task.setStatus(TaskStatus.TODO);
            baseMapper.insert(task);
        }
    }

    @Override
    public TableDataInfo<WorkItemRowVO> myTask(WfTaskBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<WfTask> lqw = buildQueryWrapper(bo);
        if (!LoginHelper.isSuperAdmin()){
            lqw.eq(WfTask::getAssigneeId, LoginHelper.getUserId());
        }
        lqw.eq(WfTask::getStatus,TaskStatus.TODO);
        Page<WfTask> wfTaskPage = baseMapper.selectPage(pageQuery.build(), lqw);
        return TableDataInfo.build(enrichFromTaskPage(wfTaskPage));
    }

    @Override
    public TableDataInfo<WorkItemRowVO> myApproval(WfTaskBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<WfTask> lqw = buildQueryWrapper(bo);
        if (!LoginHelper.isSuperAdmin()){
            lqw.eq(WfTask::getOperatorId, LoginHelper.getUserId());
        }
        lqw.eq(WfTask::getStatus,TaskStatus.DONE);
        Page<WfTask> wfTaskPage = baseMapper.selectPage(pageQuery.build(), lqw);
        return TableDataInfo.build(enrichFromTaskPage(wfTaskPage));
    }

    @Override
    @Transactional
    public void deleteByInstanceIds(Collection<Long> ids) {
        baseMapper.delete(Wrappers.<WfTask>lambdaQuery()
                .in(WfTask::getInstanceId, ids));
        wfTaskLogMapper.delete(Wrappers.<WfTaskLog>lambdaQuery()
        .in(WfTaskLog::getInstanceId, ids));
    }

    private Page<WorkItemRowVO> enrichFromTaskPage( Page<WfTask> taskPage){
        List<WfTask> tasks = taskPage.getRecords();
        if (tasks.isEmpty()) {
            return new Page<>(taskPage.getCurrent(), taskPage.getSize(), taskPage.getTotal());
        }

        Set<Long> instanceIds = tasks.stream().map(WfTask::getInstanceId).collect(Collectors.toSet());
        Set<Long> nodeInsIds  = tasks.stream().map(WfTask::getNodeInstanceId).collect(Collectors.toSet());
        Set<Long> userIds     = tasks.stream().map(WfTask::getAssigneeId).collect(Collectors.toSet());

        List<WfInstance> instances = instanceMapper.selectByIds(instanceIds);
        Map<Long, WfInstance> instanceMap = StreamUtils.toMap(instances, WfInstance::getId);

        List<WfNodeInstance> nodeInsList = nodeInstanceMapper.selectByIds(nodeInsIds);
        Map<Long, WfNodeInstance> nodeMap = StreamUtils.toMap(nodeInsList, WfNodeInstance::getId);

        List<WfBizRef> bizRefs = wfBizRefService.listByInstanceIds(instanceIds);
        Map<Long, WfBizRef> bizMap = StreamUtils.toMap(bizRefs, WfBizRef::getInstanceId);

        // starterId 需要从 instance 取
        instances.forEach(i -> userIds.add(i.getStarterId()));

        Map<Long, String> userNameMap = userQueryApi.getUserNameMap(userIds);

        List<WorkItemRowVO> rows = tasks.stream().map(t -> {
            WorkItemRowVO vo = new WorkItemRowVO();
            vo.setTaskId(t.getId());
            vo.setInstanceId(t.getInstanceId());
            vo.setNodeInstanceId(t.getNodeInstanceId());

            vo.setTaskStatus(t.getStatus());
            vo.setTaskAction(t.getAction());
            vo.setTaskComment(t.getComment());
            vo.setTaskCreateTime(t.getCreateTime());
            vo.setTaskFinishTime(t.getFinishTime());

            WfInstance ins = instanceMap.get(t.getInstanceId());
            if (ins != null) {
                vo.setInstanceStatus(ins.getStatus());
                vo.setInstanceStartTime(ins.getStartTime());
                vo.setInstanceEndTime(ins.getEndTime());
                vo.setInstanceEndReason(ins.getEndReason());
                vo.setInstanceEndComment(ins.getEndComment());
                vo.setStarterId(ins.getStarterId());
                vo.setStarterName(userNameMap.get(ins.getStarterId()));
            }

            WfNodeInstance ni = nodeMap.get(t.getNodeInstanceId());
            if (ni != null) {
                vo.setNodeKey(ni.getNodeKey());
                // 如果你有 nodeNameSnapshot，就取它；没有就先用 nodeKey
                vo.setNodeName(ni.getNodeName());
            }

            WfBizRef br = bizMap.get(t.getInstanceId());
            if (br != null) {
                vo.setBizType(br.getBizType());
                vo.setBizId(br.getBizId());
                vo.setBizNo(br.getBizNo());
            }

            vo.setAssigneeId(t.getAssigneeId());
            vo.setAssigneeName(userNameMap.get(t.getAssigneeId()));
            return vo;
        }).toList();

        Page<WorkItemRowVO> voPage = new Page<>(taskPage.getCurrent(), taskPage.getSize(), taskPage.getTotal());
        voPage.setRecords(rows);
        return voPage;
    }
}
