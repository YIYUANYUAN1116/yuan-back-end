package com.yuan.workflow.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yuan.common.core.utils.MapstructUtils;
import com.yuan.common.core.utils.StringUtils;
import com.yuan.common.json.utils.JsonUtils;
import com.yuan.core.page.PageQuery;
import com.yuan.core.page.TableDataInfo;
import com.yuan.workflow.api.cmd.StartProcessCmd;
import com.yuan.workflow.api.enums.InstanceStatus;
import com.yuan.workflow.domain.WfDefinition;
import lombok.RequiredArgsConstructor;
import com.yuan.workflow.domain.WfInstance;
import com.yuan.workflow.domain.bo.WfInstanceBo;
import com.yuan.workflow.domain.vo.WfInstanceVo;
import com.yuan.workflow.mapper.WfInstanceMapper;
import com.yuan.workflow.service.WfInstanceService;
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
            LambdaQueryWrapper<WfInstance> lqw = buildQueryWrapper(bo);
            Page<WfInstanceVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
            return TableDataInfo.build(result);
        }

    /**
     * 查询wfi列表
     */
    @Override
    public List<WfInstanceVo> queryList(WfInstanceBo bo) {
        LambdaQueryWrapper<WfInstance> lqw = buildQueryWrapper(bo);
        return baseMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<WfInstance> buildQueryWrapper(WfInstanceBo bo) {
        LambdaQueryWrapper<WfInstance> lqw = Wrappers.lambdaQuery();
                    lqw.eq(bo.getId() != null, WfInstance::getId, bo.getId());
                    lqw.eq(bo.getTenantId() != null, WfInstance::getTenantId, bo.getTenantId());
                    lqw.eq(bo.getDefinitionId() != null, WfInstance::getDefinitionId, bo.getDefinitionId());
                    lqw.eq(StringUtils.isNotBlank(bo.getDefinitionKey()), WfInstance::getDefinitionKey, bo.getDefinitionKey());
                    lqw.eq(bo.getVersion() != null, WfInstance::getVersion, bo.getVersion());
                    lqw.eq(StringUtils.isNotBlank(bo.getStatus()), WfInstance::getStatus, bo.getStatus());
                    lqw.eq(bo.getStartUserId() != null, WfInstance::getStartUserId, bo.getStartUserId());
                    lqw.eq(bo.getStartTime() != null, WfInstance::getStartTime, bo.getStartTime());
                    lqw.eq(bo.getEndTime() != null, WfInstance::getEndTime, bo.getEndTime());
        return lqw;
    }

    /**
     * 新增wfi
     */
    @Override
    public Boolean insertByBo(WfInstanceBo bo) {
        WfInstance add = MapstructUtils.convert(bo, WfInstance. class);
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
        WfInstance update = MapstructUtils.convert(bo, WfInstance. class);
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
        instance.setVersion(def.getVersion());
        instance.setStatus(InstanceStatus.RUNNING.getCode());
        instance.setStartUserId(cmd.getStarterUserId());
        instance.setVariables(JsonUtils.toJsonString(cmd.getVariables()));
        baseMapper.insert(instance);
        return instance;
    }
}
