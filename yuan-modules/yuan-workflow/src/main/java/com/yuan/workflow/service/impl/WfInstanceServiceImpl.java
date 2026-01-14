package com.yuan.workflow.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yuan.common.core.utils.MapstructUtils;
import com.yuan.common.core.utils.StringUtils;
import com.yuan.common.json.utils.JsonUtils;
import com.yuan.core.page.PageQuery;
import com.yuan.core.page.TableDataInfo;
import com.yuan.workflow.api.cmd.StartProcessCmd;
import com.yuan.workflow.domain.WfDefinition;
import com.yuan.workflow.domain.WfInstance;
import com.yuan.workflow.domain.bo.WfInstanceBo;
import com.yuan.workflow.domain.enums.InstanceStatus;
import com.yuan.workflow.domain.vo.WfInstanceVo;
import com.yuan.workflow.domain.vo.WorkItemRowVO;
import com.yuan.workflow.mapper.WfInstanceMapper;
import com.yuan.workflow.service.WfInstanceService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

/**
 * wfiService业务层处理
 *
 * @date Sun Dec 28 11:26:34 CST 2025
 */
@RequiredArgsConstructor
@Service
public class WfInstanceServiceImpl implements WfInstanceService {

    private final WfInstanceMapper baseMapper;

    /**
     * 查询wfi
     */
    @Override
    public WfInstanceVo queryById(Long id) {
        return baseMapper.selectVoById(id);
    }

    /**
     * 查询wfi列表
     */
    @Override
    public TableDataInfo<WfInstanceVo> queryPageList(WfInstanceBo bo, PageQuery pageQuery) {
        QueryWrapper<WfInstance> lqw = buildQueryWrapper(bo);
        Page<WfInstanceVo> result = baseMapper.selectWfInstanceVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }

    /**
     * 查询wfi列表
     */
    @Override
    public List<WfInstanceVo> queryList(WfInstanceBo bo) {
        QueryWrapper<WfInstance> lqw = buildQueryWrapper(bo);
        return baseMapper.selectVoList(lqw);
    }

    private QueryWrapper<WfInstance> buildQueryWrapper(WfInstanceBo bo) {
        QueryWrapper<WfInstance> lqw = Wrappers.query();
                lqw.eq(bo.getId() != null, "wi.id", bo.getId());
                lqw.eq(bo.getTenantId() != null, "wi.tenant_id", bo.getTenantId());
                lqw.eq(bo.getDefinitionId() != null, "wi.definition_id", bo.getDefinitionId());
                lqw.like(StringUtils.isNotBlank(bo.getDefinitionKey()), "wi.definition_key", bo.getDefinitionKey());
                lqw.eq(bo.getDefinitionVersion() != null, "wi.version", bo.getDefinitionVersion());
                lqw.eq(StringUtils.isNotBlank(bo.getStatus()), "wi.status", bo.getStatus());
                lqw.eq(bo.getStartId() != null, "wi.start_id", bo.getStartId());
                lqw.like(StringUtils.isNotBlank(bo.getStartName()), "wi.start_name", bo.getStartName());
                lqw.like(StringUtils.isNotBlank(bo.getStartDeptName()), "wi.start_dept_name", bo.getStartDeptName());
                lqw.eq(bo.getStartTime() != null, "wi.start_time", bo.getStartTime());
                lqw.eq(bo.getEndTime() != null, "wi.end_time", bo.getEndTime());
                lqw.like(StringUtils.isNotBlank(bo.getBizNo()), "wr.biz_no", bo.getBizNo());
                lqw.eq(StringUtils.isNotBlank(bo.getBizType()), "wr.biz_type", bo.getBizType());
                lqw.like(StringUtils.isNotBlank(bo.getDefinitionName()), "wi.definition_name", bo.getDefinitionName());
        return lqw;
    }

    /**
     * 新增wfi
     */
    @Override
    public Boolean insertByBo(WfInstanceBo bo) {
        WfInstance add = MapstructUtils.convert(bo, WfInstance.class);
        validEntityBeforeSave(add);
        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            bo.setId(add.getId());
        }
        return flag;
    }

    /**
     * 修改wfi
     */
    @Override
    public Boolean updateByBo(WfInstanceBo bo) {
        WfInstance update = MapstructUtils.convert(bo, WfInstance.class);
        validEntityBeforeSave(update);
        return baseMapper.updateById(update) > 0;
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(WfInstance entity) {
        //TODO 做一些数据校验,如唯一约束
    }

    /**
     * 批量删除wfi
     */
    @Override
    public Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid) {
        if (isValid) {
            //TODO 做一些业务上的校验,判断是否需要校验
        }
        return baseMapper.deleteBatchIds(ids) > 0;
    }

    @Override
    public WfInstance createInstance(StartProcessCmd cmd, WfDefinition def) {
        WfInstance instance = new WfInstance();
        instance.setTenantId(cmd.getTenantId());
        instance.setDefinitionId(def.getId());
        instance.setDefinitionKey(def.getDefinitionKey());
        instance.setDefinitionVersion(def.getVersion());
        instance.setStatus(InstanceStatus.RUNNING);
        instance.setStartId(cmd.getStartId());
        instance.setStartName(cmd.getStartName());
        instance.setLastOperatorId(cmd.getOperatorId());
        instance.setLastOperatorName(cmd.getOperatorName());
        instance.setStartDeptId(cmd.getStartDeptId());
        instance.setStartDeptName(cmd.getStartDeptName());
        instance.setDefinitionName(def.getDefinitionName());
        instance.setVariables(JsonUtils.toJsonString(cmd.getVariables()));
        baseMapper.insert(instance);
        return instance;
    }

    @Override
    public TableDataInfo<WorkItemRowVO> myApply(WfInstanceBo bo, PageQuery pageQuery) {
//        QueryWrapper<WfInstance> lqw = buildQueryWrapper(bo);
//        lqw.eq("wi.start_user_id", LoginHelper.getUserId());
//        Page<WfInstanceVo> result = baseMapper.selectWfInstanceVoPage(pageQuery.build(), lqw);
//        return TableDataInfo.build(result);
        return null;
    }
}
