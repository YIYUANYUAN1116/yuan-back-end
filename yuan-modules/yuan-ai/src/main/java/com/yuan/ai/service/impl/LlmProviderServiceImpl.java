package com.yuan.ai.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yuan.ai.domain.LlmProvider;
import com.yuan.ai.domain.bo.LlmProviderBo;
import com.yuan.ai.domain.vo.LlmProviderVo;
import com.yuan.ai.mapper.LlmProviderMapper;
import com.yuan.ai.service.LlmProviderService;
import com.yuan.common.core.utils.MapstructUtils;
import com.yuan.common.core.utils.StringUtils;
import com.yuan.core.page.PageQuery;
import com.yuan.core.page.TableDataInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

/**
 * llm_providerService业务层处理
 *
 * @author yuan
 * @date Thu Feb 26 21:44:12 CST 2026
 */
@RequiredArgsConstructor
@Service
public class LlmProviderServiceImpl implements LlmProviderService {

    private final LlmProviderMapper baseMapper;

    /**
     * 查询llm_provider
     */
    @Override
    public LlmProviderVo queryById(Long id) {
        return baseMapper.selectVoById(id);
    }

        /**
         * 查询llm_provider列表
         */
        @Override
        public TableDataInfo<LlmProviderVo> queryPageList(LlmProviderBo bo, PageQuery pageQuery) {
            LambdaQueryWrapper<LlmProvider> lqw = buildQueryWrapper(bo);
            Page<LlmProviderVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
            return TableDataInfo.build(result);
        }

    /**
     * 查询llm_provider列表
     */
    @Override
    public List<LlmProviderVo> queryList(LlmProviderBo bo) {
        LambdaQueryWrapper<LlmProvider> lqw = buildQueryWrapper(bo);
        return baseMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<LlmProvider> buildQueryWrapper(LlmProviderBo bo) {
        LambdaQueryWrapper<LlmProvider> lqw = Wrappers.lambdaQuery();
                    lqw.eq(bo.getId() != null, LlmProvider::getId, bo.getId());
                    lqw.eq(StringUtils.isNotBlank(bo.getCode()), LlmProvider::getCode, bo.getCode());
                    lqw.eq(StringUtils.isNotBlank(bo.getName()), LlmProvider::getName, bo.getName());
                    lqw.eq(StringUtils.isNotBlank(bo.getProtocol()), LlmProvider::getProtocol, bo.getProtocol());
                    lqw.eq(bo.getCreateTime() != null, LlmProvider::getCreateTime, bo.getCreateTime());
        return lqw;
    }

    /**
     * 新增llm_provider
     */
    @Override
    public Boolean insertByBo(LlmProviderBo bo) {
        LlmProvider add = MapstructUtils.convert(bo, LlmProvider. class);
        validEntityBeforeSave(add);
        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            bo.setId(add.getId());
        }
        return flag;
    }

    /**
     * 修改llm_provider
     */
    @Override
    public Boolean updateByBo(LlmProviderBo bo) {
        LlmProvider update = MapstructUtils.convert(bo, LlmProvider. class);
        validEntityBeforeSave(update);
        return baseMapper.updateById(update) > 0;
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(LlmProvider entity) {
        //TODO 做一些数据校验,如唯一约束
    }

    /**
     * 批量删除llm_provider
     */
    @Override
    public Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid) {
        if (isValid) {
            //TODO 做一些业务上的校验,判断是否需要校验
        }
        return baseMapper.deleteBatchIds(ids) > 0;
    }

}
