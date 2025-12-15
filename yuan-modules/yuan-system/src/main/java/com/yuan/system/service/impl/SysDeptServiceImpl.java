package com.yuan.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yuan.common.core.utils.MapstructUtils;
import com.yuan.common.core.utils.StringUtils;
import com.yuan.core.page.PageQuery;
import com.yuan.core.page.TableDataInfo;
import com.yuan.system.domain.SysDept;
import com.yuan.system.domain.bo.SysDeptBo;
import com.yuan.system.domain.vo.SysDeptVo;
import com.yuan.system.mapper.SysDeptMapper;
import com.yuan.system.service.SysDeptService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

/**
 * 部门Service业务层处理
 *
 * @author ageerle
 * @date Wed Dec 10 17:08:31 CST 2025
 */
@RequiredArgsConstructor
@Service
public class SysDeptServiceImpl implements SysDeptService {

    private final SysDeptMapper baseMapper;

    /**
     * 查询部门
     */
    @Override
    public SysDeptVo queryById(Long deptId) {
        return baseMapper.selectVoById(deptId);
    }

        /**
         * 查询部门列表
         */
        @Override
        public TableDataInfo<SysDeptVo> queryPageList(SysDeptBo bo, PageQuery pageQuery) {
            LambdaQueryWrapper<SysDept> lqw = buildQueryWrapper(bo);
            Page<SysDeptVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
            return TableDataInfo.build(result);
        }

    /**
     * 查询部门列表
     */
    @Override
    public List<SysDeptVo> queryList(SysDeptBo bo) {
        LambdaQueryWrapper<SysDept> lqw = buildQueryWrapper(bo);
        return baseMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<SysDept> buildQueryWrapper(SysDeptBo bo) {
        LambdaQueryWrapper<SysDept> lqw = Wrappers.lambdaQuery();
                    lqw.eq(bo.getDeptId() != null, SysDept::getDeptId, bo.getDeptId());
                    lqw.eq(StringUtils.isNotBlank(bo.getTenantId()), SysDept::getTenantId, bo.getTenantId());
                    lqw.eq(bo.getParentId() != null, SysDept::getParentId, bo.getParentId());
                    lqw.eq(StringUtils.isNotBlank(bo.getAncestors()), SysDept::getAncestors, bo.getAncestors());
                    lqw.eq(StringUtils.isNotBlank(bo.getDeptName()), SysDept::getDeptName, bo.getDeptName());
                    lqw.eq(bo.getOrderNum() != null, SysDept::getOrderNum, bo.getOrderNum());
                    lqw.eq(StringUtils.isNotBlank(bo.getLeader()), SysDept::getLeader, bo.getLeader());
                    lqw.eq(StringUtils.isNotBlank(bo.getPhone()), SysDept::getPhone, bo.getPhone());
                    lqw.eq(StringUtils.isNotBlank(bo.getEmail()), SysDept::getEmail, bo.getEmail());
                    lqw.eq(StringUtils.isNotBlank(bo.getStatus()), SysDept::getStatus, bo.getStatus());
                    lqw.eq(StringUtils.isNotBlank(bo.getDelFlag()), SysDept::getDelFlag, bo.getDelFlag());
                    lqw.eq(bo.getCreateDept() != null, SysDept::getCreateDept, bo.getCreateDept());
                    lqw.eq(bo.getCreateBy() != null, SysDept::getCreateBy, bo.getCreateBy());
                    lqw.eq(bo.getCreateTime() != null, SysDept::getCreateTime, bo.getCreateTime());
                    lqw.eq(bo.getUpdateBy() != null, SysDept::getUpdateBy, bo.getUpdateBy());
                    lqw.eq(bo.getUpdateTime() != null, SysDept::getUpdateTime, bo.getUpdateTime());
        return lqw;
    }

    /**
     * 新增部门
     */
    @Override
    public Boolean insertByBo(SysDeptBo bo) {
        SysDept add = MapstructUtils.convert(bo, SysDept. class);
        validEntityBeforeSave(add);
        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            bo.setDeptId(add.getDeptId());
        }
        return flag;
    }

    /**
     * 修改部门
     */
    @Override
    public Boolean updateByBo(SysDeptBo bo) {
        SysDept update = MapstructUtils.convert(bo, SysDept. class);
        validEntityBeforeSave(update);
        return baseMapper.updateById(update) > 0;
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(SysDept entity) {
        //TODO 做一些数据校验,如唯一约束
    }

    /**
     * 批量删除部门
     */
    @Override
    public Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid) {
        if (isValid) {
            //TODO 做一些业务上的校验,判断是否需要校验
        }
        return baseMapper.deleteBatchIds(ids) > 0;
    }
}
