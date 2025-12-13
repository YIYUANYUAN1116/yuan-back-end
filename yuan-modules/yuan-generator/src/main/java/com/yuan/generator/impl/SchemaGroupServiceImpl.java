package com.yuan.generator.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.RequiredArgsConstructor;
import com.yuan.common.core.utils.MapstructUtils;
import com.yuan.common.core.utils.StringUtils;
import com.yuan.core.page.PageQuery;
import com.yuan.core.page.TableDataInfo;
import com.yuan.generator.domain.SchemaGroup;
import com.yuan.generator.domain.bo.SchemaGroupBo;
import com.yuan.generator.domain.vo.SchemaGroupVo;
import com.yuan.generator.mapper.SchemaGroupMapper;
import com.yuan.generator.service.SchemaGroupService;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

/**
 * 数据模型分组Service业务层处理
 *
 * @author ruoyi
 */
@RequiredArgsConstructor
@Service
public class SchemaGroupServiceImpl implements SchemaGroupService {

    private final SchemaGroupMapper baseMapper;

    /**
     * 查询数据模型分组
     */
    @Override
    public SchemaGroupVo queryById(Long id) {
        return baseMapper.selectVoById(id);
    }

    /**
     * 查询数据模型分组列表
     */
    @Override
    public TableDataInfo<SchemaGroupVo> queryPageList(SchemaGroupBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<SchemaGroup> lqw = buildQueryWrapper(bo);
        Page<SchemaGroupVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }

    /**
     * 查询数据模型分组列表
     */
    @Override
    public List<SchemaGroupVo> queryList(SchemaGroupBo bo) {
        LambdaQueryWrapper<SchemaGroup> lqw = buildQueryWrapper(bo);
        return baseMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<SchemaGroup> buildQueryWrapper(SchemaGroupBo bo) {
        LambdaQueryWrapper<SchemaGroup> lqw = Wrappers.lambdaQuery();
        lqw.like(StringUtils.isNotBlank(bo.getName()), SchemaGroup::getName, bo.getName());
        lqw.eq(StringUtils.isNotBlank(bo.getCode()), SchemaGroup::getCode, bo.getCode());
        return lqw;
    }

    /**
     * 新增数据模型分组
     */
    @Override
    public Boolean insertByBo(SchemaGroupBo bo) {
        SchemaGroup add = MapstructUtils.convert(bo, SchemaGroup.class);
        validEntityBeforeSave(add);
        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            bo.setId(add.getId());
        }
        return flag;
    }

    /**
     * 修改数据模型分组
     */
    @Override
    public Boolean updateByBo(SchemaGroupBo bo) {
        SchemaGroup update = MapstructUtils.convert(bo, SchemaGroup.class);
        validEntityBeforeSave(update);
        return baseMapper.updateById(update) > 0;
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(SchemaGroup entity) {
        //TODO 做一些数据校验,如唯一约束
    }

    /**
     * 批量删除数据模型分组
     */
    @Override
    public Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid) {
        return baseMapper.deleteByIds(ids) > 0;
    }
}