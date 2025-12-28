package com.yuan.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yuan.common.core.utils.MapstructUtils;
import com.yuan.core.page.PageQuery;
import com.yuan.core.page.TableDataInfo;
import com.yuan.system.domain.SysRoleDept;
import com.yuan.system.domain.bo.SysRoleDeptBo;
import com.yuan.system.domain.vo.SysRoleDeptVo;
import com.yuan.system.mapper.SysRoleDeptMapper;
import com.yuan.system.service.SysRoleDeptService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

/**
 * 部门角色Service业务层处理
 *
 
 * @date Wed Dec 10 17:21:37 CST 2025
 */
@RequiredArgsConstructor
@Service
public class SysRoleDeptServiceImpl implements SysRoleDeptService {

    private final SysRoleDeptMapper baseMapper;

    /**
     * 查询部门角色
     */
    @Override
    public SysRoleDeptVo queryById(Long deptId) {
        return baseMapper.selectVoById(deptId);
    }

        /**
         * 查询部门角色列表
         */
        @Override
        public TableDataInfo<SysRoleDeptVo> queryPageList(SysRoleDeptBo bo, PageQuery pageQuery) {
            LambdaQueryWrapper<SysRoleDept> lqw = buildQueryWrapper(bo);
            Page<SysRoleDeptVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
            return TableDataInfo.build(result);
        }

    /**
     * 查询部门角色列表
     */
    @Override
    public List<SysRoleDeptVo> queryList(SysRoleDeptBo bo) {
        LambdaQueryWrapper<SysRoleDept> lqw = buildQueryWrapper(bo);
        return baseMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<SysRoleDept> buildQueryWrapper(SysRoleDeptBo bo) {
        LambdaQueryWrapper<SysRoleDept> lqw = Wrappers.lambdaQuery();
                    lqw.eq(bo.getRoleId() != null, SysRoleDept::getRoleId, bo.getRoleId());
                    lqw.eq(bo.getDeptId() != null, SysRoleDept::getDeptId, bo.getDeptId());
        return lqw;
    }

    /**
     * 新增部门角色
     */
    @Override
    public Boolean insertByBo(SysRoleDeptBo bo) {
        SysRoleDept add = MapstructUtils.convert(bo, SysRoleDept. class);
        validEntityBeforeSave(add);
        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            bo.setDeptId(add.getDeptId());
        }
        return flag;
    }

    /**
     * 修改部门角色
     */
    @Override
    public Boolean updateByBo(SysRoleDeptBo bo) {
        SysRoleDept update = MapstructUtils.convert(bo, SysRoleDept. class);
        validEntityBeforeSave(update);
        return baseMapper.updateById(update) > 0;
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(SysRoleDept entity) {
        //TODO 做一些数据校验,如唯一约束
    }

    /**
     * 批量删除部门角色
     */
    @Override
    public Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid) {
        if (isValid) {
            //TODO 做一些业务上的校验,判断是否需要校验
        }
        return baseMapper.deleteBatchIds(ids) > 0;
    }
}
