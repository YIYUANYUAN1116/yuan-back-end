package com.yuan.system.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.lang.tree.Tree;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yuan.common.core.utils.CommonTreeUtils;
import com.yuan.common.core.utils.MapstructUtils;
import com.yuan.common.core.utils.StringUtils;
import com.yuan.common.core.utils.TreeBuildUtils;
import com.yuan.core.page.PageQuery;
import com.yuan.core.page.TableDataInfo;
import com.yuan.system.domain.SysDept;
import com.yuan.system.domain.SysUser;
import com.yuan.system.domain.bo.SysDeptBo;
import com.yuan.system.domain.bo.SysUserBo;
import com.yuan.system.domain.vo.SysDeptVo;
import com.yuan.system.domain.vo.SysUserVo;
import com.yuan.system.mapper.SysDeptMapper;
import com.yuan.system.mapper.SysUserMapper;
import com.yuan.system.service.SysDeptService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * deptService业务层处理
 *
 * @date Mon Dec 22 15:20:54 CST 2025
 */
@RequiredArgsConstructor
@Service
public class SysDeptServiceImpl implements SysDeptService {

    private final SysDeptMapper baseMapper;
    private final SysUserMapper userMapper;

    /**
     * 查询dept
     */
    @Override
    public SysDeptVo queryById(Long deptId) {
        return baseMapper.selectVoById(deptId);
    }

    /**
     * 查询dept列表
     */
    @Override
    public TableDataInfo<SysDeptVo> queryPageList(SysDeptBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<SysDept> lqw = buildQueryWrapper(bo);
        Page<SysDeptVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }

    /**
     * 查询dept列表
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
        lqw.like(StringUtils.isNotBlank(bo.getDeptName()), SysDept::getDeptName, bo.getDeptName());
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
        lqw.orderByAsc(SysDept::getOrderNum);
        return lqw;
    }

    /**
     * 新增dept
     */
    @Override
    public Boolean insertByBo(SysDeptBo bo) {
        SysDept add = MapstructUtils.convert(bo, SysDept.class);
        validEntityBeforeSave(add);
        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            bo.setDeptId(add.getDeptId());
        }
        return flag;
    }

    /**
     * 修改dept
     */
    @Override
    public Boolean updateByBo(SysDeptBo bo) {
        SysDept update = MapstructUtils.convert(bo, SysDept.class);
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
     * 批量删除dept
     */
    @Override
    public Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid) {
        if (isValid) {
            //TODO 做一些业务上的校验,判断是否需要校验
        }
        return baseMapper.deleteBatchIds(ids) > 0;
    }

    @Override
    public List<SysDeptVo> listTree(SysDeptBo bo) {
        // 1. 查询匹配的菜单（根据menuName）
        LambdaQueryWrapper<SysDept> lqw = buildQueryWrapper(bo);
        List<SysDeptVo> matchedDepts = baseMapper.selectVoList(lqw);

        if (matchedDepts.isEmpty()) {
            return Collections.emptyList();
        }

        // 2. 查询所有菜单
        List<SysDeptVo> allDepts = baseMapper.selectVoList(new LambdaQueryWrapper<>());


        List<SysDeptVo> resultDept = null;
        if (!Objects.equals(allDepts.size(), matchedDepts.size())) {
            // 3. 构建子菜单集合（只保留匹配的菜单及其子菜单）
            Set<Long> includedIds = new HashSet<>();
            for (SysDeptVo sysDept : matchedDepts) {
                collectChildren(sysDept.getDeptId(), allDepts, includedIds);
            }

            resultDept = allDepts.stream()
                    .filter(m -> includedIds.contains(m.getDeptId()))
                    .collect(Collectors.toList());
        }

        resultDept = resultDept == null ? matchedDepts : resultDept;
        // 构建树
        return CommonTreeUtils.buildTree(
                resultDept,
                SysDeptVo::getDeptId,      // id 获取器
                SysDeptVo::getParentId,    // parentId 获取器
                SysDeptVo::setChildren,    // children 设置器
                0L                       // 根节点 parentId = 0
        );
    }

    @Override
    public List<SysDeptVo> selectDeptList(SysDeptBo bo, Long userId) {
        // 1. 查询匹配的菜单（根据menuName）
        LambdaQueryWrapper<SysDept> lqw = buildQueryWrapper(bo);
        return baseMapper.selectVoList(lqw);
    }

    @Override
    public List<Tree<Long>> buildDeptTreeSelect(List<SysDeptVo> deptVos) {
        if (CollUtil.isEmpty(deptVos)) {
            return CollUtil.newArrayList();
        }
        return TreeBuildUtils.build(deptVos, (deptVo, tree) ->
                tree.setId(deptVo.getDeptId())
                        .setParentId(deptVo.getParentId())
                        .setName(deptVo.getDeptName())
                        .setWeight(deptVo.getOrderNum()));
    }

    @Override
    public TableDataInfo<SysUserVo> selectAllocatedUserList(SysUserBo user, PageQuery pageQuery) {
        QueryWrapper<SysUser> wrapper = new QueryWrapper<>();
        wrapper.eq(user.getDeptId() != null, "u.dept_id", user.getDeptId())
                .like(StringUtils.isNotBlank(user.getUserName()), "u.user_name", user.getUserName())
                .like(StringUtils.isNotBlank(user.getNickName()), "u.nick_name", user.getNickName())
                .eq(StringUtils.isNotBlank(user.getStatus()), "u.status", user.getStatus())
                .like(StringUtils.isNotBlank(user.getPhonenumber()), "u.phonenumber", user.getPhonenumber());
        Page<SysUserVo> page = userMapper.selectPageUserList(pageQuery.build(), wrapper);
        return TableDataInfo.build(page);
    }

    @Override
    public TableDataInfo<SysUserVo> selectUnallocatedUserList(SysUserBo user, PageQuery pageQuery) {
        List<Long> userIds = userMapper.selectUserIdsBydeptId(user.getDeptId());
        QueryWrapper<SysUser> wrapper = new QueryWrapper<>();
        wrapper.and(w -> w.ne("u.dept_id", user.getDeptId())
                        .or()
                        .isNull("u.dept_id"))
                .notIn(CollUtil.isNotEmpty(userIds), "u.user_id", userIds)
                .like(StringUtils.isNotBlank(user.getUserName()), "u.user_name", user.getUserName())
                .like(StringUtils.isNotBlank(user.getNickName()), "u.nick_name", user.getNickName())
                .eq(StringUtils.isNotBlank(user.getStatus()), "u.status", user.getStatus())
                .like(StringUtils.isNotBlank(user.getPhonenumber()), "u.phonenumber", user.getPhonenumber());
        Page<SysUserVo> page = userMapper.selectPageUserList(pageQuery.build(), wrapper);
        return TableDataInfo.build(page);
    }

    @Override
    public Boolean cancelUserAll(Long deptId, Long[] userIds) {
        int rows = userMapper.update(new LambdaUpdateWrapper<SysUser>()
                .eq(SysUser::getDeptId, deptId)
                .in(SysUser::getUserId, Arrays.asList(userIds))
                .set(SysUser::getDeptId, null));
//        if (rows > 0) {
//            cleanOnlineUserByRole(roleId);
//        }
        return rows > 0;
    }

    @Override
    public Boolean selectUserAll(Long deptId, Long[] userIds) {
        int rows = userMapper.update(new LambdaUpdateWrapper<SysUser>()
                .in(SysUser::getUserId, Arrays.asList(userIds))
                .set(SysUser::getDeptId, deptId));
//        if (rows > 0) {
//            cleanOnlineUserByRole(roleId);
//        }
        return rows > 0;
    }

    @Override
    public Boolean checkDeptNameUnique(SysDeptBo bo) {
        boolean exist = baseMapper.exists(new LambdaQueryWrapper<SysDept>()
                .eq(SysDept::getDeptName, bo.getDeptName())
                .ne(ObjectUtil.isNotNull(bo.getDeptId()), SysDept::getDeptId, bo.getDeptId()));
        return !exist;
    }

    // 递归收集子菜单
    private void collectChildren(Long parentId, List<SysDeptVo> allDepts, Set<Long> includedIds) {
        includedIds.add(parentId);
        for (SysDeptVo sysDeptVo : allDepts) {
            if (parentId.equals(sysDeptVo.getParentId()) && !includedIds.contains(sysDeptVo.getDeptId())) {
                collectChildren(sysDeptVo.getDeptId(), allDepts, includedIds);
            }
        }
    }
}
