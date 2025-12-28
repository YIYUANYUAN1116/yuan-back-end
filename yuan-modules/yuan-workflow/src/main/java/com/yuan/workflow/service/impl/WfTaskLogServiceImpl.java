package com.yuan.workflow.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yuan.common.core.utils.MapstructUtils;
import com.yuan.common.core.utils.StringUtils;
import com.yuan.core.page.PageQuery;
import com.yuan.core.page.TableDataInfo;
import lombok.RequiredArgsConstructor;
import com.yuan.workflow.domain.WfTaskLog;
import com.yuan.workflow.domain.bo.WfTaskLogBo;
import com.yuan.workflow.domain.vo.WfTaskLogVo;
import com.yuan.workflow.mapper.WfTaskLogMapper;
import com.yuan.workflow.service.WfTaskLogService;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

/**
 * wftlService业务层处理
 *
 
 * @date Sun Dec 28 11:26:44 CST 2025
 */
@RequiredArgsConstructor
@Service
public class WfTaskLogServiceImpl implements WfTaskLogService {

    private final WfTaskLogMapper baseMapper;

    /**
     * 查询wftl
     */
    @Override
    public WfTaskLogVo queryById(Long id) {
        return baseMapper.selectVoById(id);
    }

        /**
         * 查询wftl列表
         */
        @Override
        public TableDataInfo<WfTaskLogVo> queryPageList(WfTaskLogBo bo, PageQuery pageQuery) {
            LambdaQueryWrapper<WfTaskLog> lqw = buildQueryWrapper(bo);
            Page<WfTaskLogVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
            return TableDataInfo.build(result);
        }

    /**
     * 查询wftl列表
     */
    @Override
    public List<WfTaskLogVo> queryList(WfTaskLogBo bo) {
        LambdaQueryWrapper<WfTaskLog> lqw = buildQueryWrapper(bo);
        return baseMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<WfTaskLog> buildQueryWrapper(WfTaskLogBo bo) {
        LambdaQueryWrapper<WfTaskLog> lqw = Wrappers.lambdaQuery();
                    lqw.eq(bo.getId() != null, WfTaskLog::getId, bo.getId());
                    lqw.eq(bo.getTaskId() != null, WfTaskLog::getTaskId, bo.getTaskId());
                    lqw.eq(bo.getInstanceId() != null, WfTaskLog::getInstanceId, bo.getInstanceId());
                    lqw.eq(StringUtils.isNotBlank(bo.getAction()), WfTaskLog::getAction, bo.getAction());
                    lqw.eq(bo.getOperatorId() != null, WfTaskLog::getOperatorId, bo.getOperatorId());
                    lqw.eq(StringUtils.isNotBlank(bo.getComment()), WfTaskLog::getComment, bo.getComment());
                    lqw.eq(bo.getOperateTime() != null, WfTaskLog::getOperateTime, bo.getOperateTime());
        return lqw;
    }

    /**
     * 新增wftl
     */
    @Override
    public Boolean insertByBo(WfTaskLogBo bo) {
        WfTaskLog add = MapstructUtils.convert(bo, WfTaskLog. class);
        validEntityBeforeSave(add);
        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            bo.setId(add.getId());
        }
        return flag;
    }

    /**
     * 修改wftl
     */
    @Override
    public Boolean updateByBo(WfTaskLogBo bo) {
        WfTaskLog update = MapstructUtils.convert(bo, WfTaskLog. class);
        validEntityBeforeSave(update);
        return baseMapper.updateById(update) > 0;
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(WfTaskLog entity) {
        //TODO 做一些数据校验,如唯一约束
    }

    /**
     * 批量删除wftl
     */
    @Override
    public Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid) {
        if (isValid) {
            //TODO 做一些业务上的校验,判断是否需要校验
        }
        return baseMapper.deleteBatchIds(ids) > 0;
    }
}
