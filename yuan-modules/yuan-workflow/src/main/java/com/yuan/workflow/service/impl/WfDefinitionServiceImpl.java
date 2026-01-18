package com.yuan.workflow.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yuan.common.core.exception.base.BaseException;
import com.yuan.common.core.utils.MapstructUtils;
import com.yuan.common.core.utils.StringUtils;
import com.yuan.core.page.PageQuery;
import com.yuan.core.page.TableDataInfo;
import com.yuan.workflow.domain.enums.DefinitionAction;
import com.yuan.workflow.domain.enums.DefinitionStatus;
import com.yuan.workflow.domain.WfDefinition;
import com.yuan.workflow.domain.bo.WfDefinitionBo;
import com.yuan.workflow.domain.dto.WfDefinitionDto;
import com.yuan.workflow.domain.vo.WfDefinitionVo;
import com.yuan.workflow.mapper.WfDefinitionMapper;
import com.yuan.workflow.service.WfDefinitionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
                    lqw.eq(StringUtils.isNotBlank(bo.getDefinitionKey()), WfDefinition::getDefinitionKey, bo.getDefinitionKey());
                    lqw.eq(StringUtils.isNotBlank(bo.getDefinitionName()), WfDefinition::getDefinitionName, bo.getDefinitionName());
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
        bo.setStatus(DefinitionStatus.DRAFT.getCode());
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

    @Override
    public Boolean updateByDto(WfDefinitionDto dto) {
        WfDefinition definition = baseMapper.selectById(dto.getId());
        if (definition == null) {
            throw new BaseException("流程定义不存在");
        }
        definition.setFlowJson(dto.getFlowJson());
        definition.setFormSchema(dto.getFormSchema());
        return baseMapper.updateById(definition) > 0;
    }

    @Transactional
    public Boolean changeStatus(Long id, DefinitionAction action) {
        WfDefinition definition = baseMapper.selectById(id);
        if (definition == null) {
            throw new BaseException("流程定义不存在");
        }
        DefinitionStatus current = definition.getStatus();
        switch (action) {
            case PUBLISH:
                publish(definition, current);
                break;
            case DISABLE:
                disable(definition, current);
                break;
            default:
                throw new BaseException("不支持的操作: " + action);
        }

       return baseMapper.updateById(definition)>0;
    }

    @Override
    public boolean checkDefNameUnique(WfDefinitionBo bo) {
        boolean exist = baseMapper.exists(new LambdaQueryWrapper<WfDefinition>()
                .eq(WfDefinition::getDefinitionName, bo.getDefinitionName())
                .ne(ObjectUtil.isNotNull(bo.getId()), WfDefinition::getId, bo.getId()));
        return !exist;
    }

    /**
     * 停用流程
     * @param definition
     * @param current
     */
    private void disable(WfDefinition definition, DefinitionStatus current) {
//        if (current != DefinitionStatus.PUBLISHED) {
//            throw new BaseException("只有已发布流程才能停用");
//        }
        // 校验流程合法性
        disableHandler(definition);

        definition.setStatus(DefinitionStatus.DISABLED);
    }

    /**
     * 发布流程
     * @param definition
     * @param current
     */
    private void publish(WfDefinition definition, DefinitionStatus current) {
//        if (current != DefinitionStatus.DRAFT) {
//            throw new BaseException("只有草稿状态才能发布");
//        }

        // 校验流程合法性
        publishedHandler(definition);

        definition.setStatus(DefinitionStatus.PUBLISHED);
    }

    private void disableHandler(WfDefinition definition) {

    }

    private void publishedHandler(WfDefinition definition) {

    }


}
