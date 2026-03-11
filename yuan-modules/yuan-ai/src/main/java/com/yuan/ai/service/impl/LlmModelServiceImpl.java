package com.yuan.ai.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yuan.ai.domain.LlmModel;
import com.yuan.ai.domain.bo.LlmModelBo;
import com.yuan.ai.domain.vo.LlmModelVo;
import com.yuan.ai.mapper.LlmModelMapper;
import com.yuan.ai.service.LlmModelService;
import com.yuan.common.core.utils.MapstructUtils;
import com.yuan.common.core.utils.StringUtils;
import com.yuan.core.page.PageQuery;
import com.yuan.core.page.TableDataInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

/**
 * llm_modelService业务层处理
 *
 * @author yuan
 * @date Thu Feb 26 21:44:17 CST 2026
 */
@RequiredArgsConstructor
@Service
public class LlmModelServiceImpl implements LlmModelService {

    private final LlmModelMapper baseMapper;

    /**
     * 查询llm_model
     */
    @Override
    public LlmModelVo queryById(Long id) {
        return baseMapper.selectVoById(id);
    }

        /**
         * 查询llm_model列表
         */
        @Override
        public TableDataInfo<LlmModelVo> queryPageList(LlmModelBo bo, PageQuery pageQuery) {
            LambdaQueryWrapper<LlmModel> lqw = buildQueryWrapper(bo);
            Page<LlmModelVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
            return TableDataInfo.build(result);
        }

    /**
     * 查询llm_model列表
     */
    @Override
    public List<LlmModelVo> queryList(LlmModelBo bo) {
        LambdaQueryWrapper<LlmModel> lqw = buildQueryWrapper(bo);
        return baseMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<LlmModel> buildQueryWrapper(LlmModelBo bo) {
        LambdaQueryWrapper<LlmModel> lqw = Wrappers.lambdaQuery();
                    lqw.eq(bo.getId() != null, LlmModel::getId, bo.getId());
                    lqw.eq(StringUtils.isNotBlank(bo.getProviderCode()), LlmModel::getProviderCode, bo.getProviderCode());
                    lqw.eq(StringUtils.isNotBlank(bo.getModelName()), LlmModel::getModelName, bo.getModelName());
                    lqw.eq(StringUtils.isNotBlank(bo.getDisplayName()), LlmModel::getDisplayName, bo.getDisplayName());
                    lqw.eq(StringUtils.isNotBlank(bo.getCapabilityJson()), LlmModel::getCapabilityJson, bo.getCapabilityJson());
                    lqw.eq(bo.getContextWindow() != null, LlmModel::getContextWindow, bo.getContextWindow());
                    lqw.eq(bo.getStatus() != null, LlmModel::getStatus, bo.getStatus());
        return lqw;
    }

    /**
     * 新增llm_model
     */
    @Override
    public Boolean insertByBo(LlmModelBo bo) {
        LlmModel add = MapstructUtils.convert(bo, LlmModel. class);
        validEntityBeforeSave(add);
        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            bo.setId(add.getId());
        }
        return flag;
    }

    /**
     * 修改llm_model
     */
    @Override
    public Boolean updateByBo(LlmModelBo bo) {
        LlmModel update = MapstructUtils.convert(bo, LlmModel. class);
        validEntityBeforeSave(update);
        return baseMapper.updateById(update) > 0;
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(LlmModel entity) {
        //TODO 做一些数据校验,如唯一约束
    }

    /**
     * 批量删除llm_model
     */
    @Override
    public Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid) {
        if (isValid) {
            //TODO 做一些业务上的校验,判断是否需要校验
        }
        return baseMapper.deleteBatchIds(ids) > 0;
    }

    @Override
    public LlmModel getById(Long id) { return baseMapper.selectById(id); }
}
