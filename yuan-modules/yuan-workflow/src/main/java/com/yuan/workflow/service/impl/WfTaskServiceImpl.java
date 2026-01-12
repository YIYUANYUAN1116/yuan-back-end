package com.yuan.workflow.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yuan.common.core.utils.MapstructUtils;
import com.yuan.common.core.utils.StringUtils;
import com.yuan.core.page.PageQuery;
import com.yuan.core.page.TableDataInfo;
import com.yuan.workflow.api.enums.TaskStatus;
import com.yuan.workflow.domain.WfInstance;
import com.yuan.workflow.domain.WfNodeInstance;
import lombok.RequiredArgsConstructor;
import com.yuan.workflow.domain.WfTask;
import com.yuan.workflow.domain.bo.WfTaskBo;
import com.yuan.workflow.domain.vo.WfTaskVo;
import com.yuan.workflow.mapper.WfTaskMapper;
import com.yuan.workflow.service.WfTaskService;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
 * wftService业务层处理
 *
 
 * @date Sun Dec 28 11:26:41 CST 2025
 */
@RequiredArgsConstructor
@Service
public class WfTaskServiceImpl implements WfTaskService {

    private final WfTaskMapper baseMapper;

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
            task.setStatus(TaskStatus.TODO.getCode());
            baseMapper.insert(task);
        }
    }
}
