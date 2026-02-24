package com.yuan.ai.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yuan.ai.domain.ChatModel;
import com.yuan.ai.domain.bo.ChatModelBo;
import com.yuan.ai.domain.vo.ChatModelVo;
import com.yuan.ai.mapper.ChatModelMapper;
import com.yuan.ai.service.ChatModelService;
import com.yuan.common.core.utils.MapstructUtils;
import com.yuan.common.core.utils.StringUtils;
import com.yuan.core.page.PageQuery;
import com.yuan.core.page.TableDataInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

/**
 * chat_modelService业务层处理
 *
 * @author ageerle
 * @date Mon Feb 16 14:59:14 CST 2026
 */
@RequiredArgsConstructor
@Service
public class ChatModelServiceImpl implements ChatModelService {

    private final ChatModelMapper baseMapper;

    /**
     * 查询chat_model
     */
    @Override
    public ChatModelVo queryById(Long id) {
        return baseMapper.selectVoById(id);
    }

        /**
         * 查询chat_model列表
         */
        @Override
        public TableDataInfo<ChatModelVo> queryPageList(ChatModelBo bo, PageQuery pageQuery) {
            LambdaQueryWrapper<ChatModel> lqw = buildQueryWrapper(bo);
            Page<ChatModelVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
            return TableDataInfo.build(result);
        }

    /**
     * 查询chat_model列表
     */
    @Override
    public List<ChatModelVo> queryList(ChatModelBo bo) {
        LambdaQueryWrapper<ChatModel> lqw = buildQueryWrapper(bo);
        return baseMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<ChatModel> buildQueryWrapper(ChatModelBo bo) {
        LambdaQueryWrapper<ChatModel> lqw = Wrappers.lambdaQuery();
                    lqw.eq(bo.getId() != null, ChatModel::getId, bo.getId());
                    lqw.eq(StringUtils.isNotBlank(bo.getTenantId()), ChatModel::getTenantId, bo.getTenantId());
                    lqw.eq(StringUtils.isNotBlank(bo.getCategory()), ChatModel::getCategory, bo.getCategory());
                    lqw.eq(StringUtils.isNotBlank(bo.getModelName()), ChatModel::getModelName, bo.getModelName());
                    lqw.eq(StringUtils.isNotBlank(bo.getProviderName()), ChatModel::getProviderName, bo.getProviderName());
                    lqw.eq(StringUtils.isNotBlank(bo.getModelDescribe()), ChatModel::getModelDescribe, bo.getModelDescribe());
                    lqw.eq(bo.getModelPrice() != null, ChatModel::getModelPrice, bo.getModelPrice());
                    lqw.eq(StringUtils.isNotBlank(bo.getModelType()), ChatModel::getModelType, bo.getModelType());
                    lqw.eq(StringUtils.isNotBlank(bo.getModelShow()), ChatModel::getModelShow, bo.getModelShow());
                    lqw.eq(StringUtils.isNotBlank(bo.getSystemPrompt()), ChatModel::getSystemPrompt, bo.getSystemPrompt());
                    lqw.eq(StringUtils.isNotBlank(bo.getApiHost()), ChatModel::getApiHost, bo.getApiHost());
                    lqw.eq(StringUtils.isNotBlank(bo.getApiKey()), ChatModel::getApiKey, bo.getApiKey());
                    lqw.eq(StringUtils.isNotBlank(bo.getApiUrl()), ChatModel::getApiUrl, bo.getApiUrl());
                    lqw.eq(bo.getCreateDept() != null, ChatModel::getCreateDept, bo.getCreateDept());
                    lqw.eq(bo.getCreateBy() != null, ChatModel::getCreateBy, bo.getCreateBy());
                    lqw.eq(bo.getCreateTime() != null, ChatModel::getCreateTime, bo.getCreateTime());
                    lqw.eq(bo.getUpdateBy() != null, ChatModel::getUpdateBy, bo.getUpdateBy());
                    lqw.eq(bo.getUpdateTime() != null, ChatModel::getUpdateTime, bo.getUpdateTime());
                    lqw.eq(StringUtils.isNotBlank(bo.getRemark()), ChatModel::getRemark, bo.getRemark());
                    lqw.eq(bo.getPriority() != null, ChatModel::getPriority, bo.getPriority());
        return lqw;
    }

    /**
     * 新增chat_model
     */
    @Override
    public Boolean insertByBo(ChatModelBo bo) {
        ChatModel add = MapstructUtils.convert(bo, ChatModel. class);
        validEntityBeforeSave(add);
        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            bo.setId(add.getId());
        }
        return flag;
    }

    /**
     * 修改chat_model
     */
    @Override
    public Boolean updateByBo(ChatModelBo bo) {
        ChatModel update = MapstructUtils.convert(bo, ChatModel. class);
        validEntityBeforeSave(update);
        return baseMapper.updateById(update) > 0;
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(ChatModel entity) {
        //TODO 做一些数据校验,如唯一约束
    }

    /**
     * 批量删除chat_model
     */
    @Override
    public Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid) {
        if (isValid) {
            //TODO 做一些业务上的校验,判断是否需要校验
        }
        return baseMapper.deleteBatchIds(ids) > 0;
    }
}
