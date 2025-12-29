package com.yuan.workflow.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yuan.common.core.utils.MapstructUtils;
import com.yuan.common.core.utils.StringUtils;
import com.yuan.core.page.PageQuery;
import com.yuan.core.page.TableDataInfo;
import lombok.RequiredArgsConstructor;
import com.yuan.workflow.domain.WfDefinition;
import com.yuan.workflow.domain.bo.WfDefinitionBo;
import com.yuan.workflow.domain.vo.WfDefinitionVo;
import com.yuan.workflow.mapper.WfDefinitionMapper;
import com.yuan.workflow.service.WfDefinitionService;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

/**
 * wfdService业务层处理
 *
 
 * @date Sun Dec 28 11:26:30 CST 2025
 */
@RequiredArgsConstructor
@Service
public class WfDefinitionServiceImpl implements WfDefinitionService {

    private final WfDefinitionMapper baseMapper;

    /**
     * 查询wfd
     */
    @Override
    public WfDefinitionVo queryById(Long id) {
        return baseMapper.selectVoById(id);
    }

        /**
         * 查询wfd列表
         */
        @Override
        public TableDataInfo<WfDefinitionVo> queryPageList(WfDefinitionBo bo, PageQuery pageQuery) {
            LambdaQueryWrapper<WfDefinition> lqw = buildQueryWrapper(bo);
            Page<WfDefinitionVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
            return TableDataInfo.build(result);
        }

    /**
     * 查询wfd列表
     */
    @Override
    public List<WfDefinitionVo> queryList(WfDefinitionBo bo) {
        LambdaQueryWrapper<WfDefinition> lqw = buildQueryWrapper(bo);
        return baseMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<WfDefinition> buildQueryWrapper(WfDefinitionBo bo) {
        LambdaQueryWrapper<WfDefinition> lqw = Wrappers.lambdaQuery();
                    lqw.eq(bo.getId() != null, WfDefinition::getId, bo.getId());
                    lqw.eq(bo.getTenantId() != null, WfDefinition::getTenantId, bo.getTenantId());
                    lqw.eq(StringUtils.isNotBlank(bo.getdefinitionKey()), WfDefinition::getdefinitionKey, bo.getdefinitionKey());
                    lqw.eq(StringUtils.isNotBlank(bo.getProcessName()), WfDefinition::getProcessName, bo.getProcessName());
                    lqw.eq(bo.getVersion() != null, WfDefinition::getVersion, bo.getVersion());
                    lqw.eq(StringUtils.isNotBlank(bo.getStatus()), WfDefinition::getStatus, bo.getStatus());
                    lqw.eq(StringUtils.isNotBlank(bo.getFormSchema()), WfDefinition::getFormSchema, bo.getFormSchema());
                    lqw.eq(StringUtils.isNotBlank(bo.getFlowJson()), WfDefinition::getFlowJson, bo.getFlowJson());
                    lqw.eq(StringUtils.isNotBlank(bo.getRemark()), WfDefinition::getRemark, bo.getRemark());
                    lqw.eq(bo.getCreateBy() != null, WfDefinition::getCreateBy, bo.getCreateBy());
                    lqw.eq(bo.getCreateTime() != null, WfDefinition::getCreateTime, bo.getCreateTime());
                    lqw.eq(bo.getUpdateTime() != null, WfDefinition::getUpdateTime, bo.getUpdateTime());
        return lqw;
    }

    /**
     * 新增wfd
     */
    @Override
    public Boolean insertByBo(WfDefinitionBo bo) {
        WfDefinition add = MapstructUtils.convert(bo, WfDefinition. class);
        validEntityBeforeSave(add);
        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            bo.setId(add.getId());
        }
        return flag;
    }

    /**
     * 修改wfd
     */
    @Override
    public Boolean updateByBo(WfDefinitionBo bo) {
        WfDefinition update = MapstructUtils.convert(bo, WfDefinition. class);
        validEntityBeforeSave(update);
        return baseMapper.updateById(update) > 0;
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(WfDefinition entity) {
        //TODO 做一些数据校验,如唯一约束
    }

    /**
     * 批量删除wfd
     */
    @Override
    public Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid) {
        if (isValid) {
            //TODO 做一些业务上的校验,判断是否需要校验
        }
        return baseMapper.deleteBatchIds(ids) > 0;
    }
}
