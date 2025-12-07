package com.yuan.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yuan.common.core.utils.MapstructUtils;
import com.yuan.common.core.utils.StringUtils;
import com.yuan.core.page.PageQuery;
import com.yuan.core.page.TableDataInfo;
import com.yuan.system.domain.SysUser;
import com.yuan.system.domain.bo.SysUserBo;
import com.yuan.system.domain.vo.SysUserVo;
import com.yuan.system.mapper.SysUserMapper;
import com.yuan.system.service.SysUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

/**
 * 用户Service业务层处理
 *
 * @author ageerle
 * @date Sun Dec 07 17:25:38 CST 2025
 */
@RequiredArgsConstructor
@Service
public class SysUserServiceImpl implements SysUserService {

    private final SysUserMapper baseMapper;

    /**
     * 查询用户
     */
    @Override
    public SysUserVo queryById(Long userId) {
        return baseMapper.selectVoById(userId);
    }

        /**
         * 查询用户列表
         */
        @Override
        public TableDataInfo<SysUserVo> queryPageList(SysUserBo bo, PageQuery pageQuery) {
            LambdaQueryWrapper<SysUser> lqw = buildQueryWrapper(bo);
            Page<SysUserVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
            return TableDataInfo.build(result);
        }

    /**
     * 查询用户列表
     */
    @Override
    public List<SysUserVo> queryList(SysUserBo bo) {
        LambdaQueryWrapper<SysUser> lqw = buildQueryWrapper(bo);
        return baseMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<SysUser> buildQueryWrapper(SysUserBo bo) {
        LambdaQueryWrapper<SysUser> lqw = Wrappers.lambdaQuery();
                    lqw.eq(bo.getUserId() != null, SysUser::getUserId, bo.getUserId());
                    lqw.eq(StringUtils.isNotBlank(bo.getOpenId()), SysUser::getOpenId, bo.getOpenId());
                    lqw.eq(StringUtils.isNotBlank(bo.getUserGrade()), SysUser::getUserGrade, bo.getUserGrade());
                    lqw.eq(bo.getUserBalance() != null, SysUser::getUserBalance, bo.getUserBalance());
                    lqw.eq(StringUtils.isNotBlank(bo.getTenantId()), SysUser::getTenantId, bo.getTenantId());
                    lqw.eq(bo.getDeptId() != null, SysUser::getDeptId, bo.getDeptId());
                    lqw.eq(StringUtils.isNotBlank(bo.getUserName()), SysUser::getUserName, bo.getUserName());
                    lqw.eq(StringUtils.isNotBlank(bo.getNickName()), SysUser::getNickName, bo.getNickName());
                    lqw.eq(StringUtils.isNotBlank(bo.getUserType()), SysUser::getUserType, bo.getUserType());
                    lqw.eq(StringUtils.isNotBlank(bo.getUserPlan()), SysUser::getUserPlan, bo.getUserPlan());
                    lqw.eq(StringUtils.isNotBlank(bo.getEmail()), SysUser::getEmail, bo.getEmail());
                    lqw.eq(StringUtils.isNotBlank(bo.getPhonenumber()), SysUser::getPhonenumber, bo.getPhonenumber());
                    lqw.eq(StringUtils.isNotBlank(bo.getSex()), SysUser::getSex, bo.getSex());
                    lqw.eq(StringUtils.isNotBlank(bo.getAvatar()), SysUser::getAvatar, bo.getAvatar());
                    lqw.eq(StringUtils.isNotBlank(bo.getWxAvatar()), SysUser::getWxAvatar, bo.getWxAvatar());
                    lqw.eq(StringUtils.isNotBlank(bo.getPassword()), SysUser::getPassword, bo.getPassword());
                    lqw.eq(StringUtils.isNotBlank(bo.getStatus()), SysUser::getStatus, bo.getStatus());
                    lqw.eq(StringUtils.isNotBlank(bo.getDelFlag()), SysUser::getDelFlag, bo.getDelFlag());
                    lqw.eq(StringUtils.isNotBlank(bo.getLoginIp()), SysUser::getLoginIp, bo.getLoginIp());
                    lqw.eq(bo.getLoginDate() != null, SysUser::getLoginDate, bo.getLoginDate());
                    lqw.eq(StringUtils.isNotBlank(bo.getDomainName()), SysUser::getDomainName, bo.getDomainName());
                    lqw.eq(bo.getCreateDept() != null, SysUser::getCreateDept, bo.getCreateDept());
                    lqw.eq(bo.getCreateBy() != null, SysUser::getCreateBy, bo.getCreateBy());
                    lqw.eq(bo.getCreateTime() != null, SysUser::getCreateTime, bo.getCreateTime());
                    lqw.eq(bo.getUpdateBy() != null, SysUser::getUpdateBy, bo.getUpdateBy());
                    lqw.eq(bo.getUpdateTime() != null, SysUser::getUpdateTime, bo.getUpdateTime());
                    lqw.eq(StringUtils.isNotBlank(bo.getRemark()), SysUser::getRemark, bo.getRemark());
                    lqw.eq(StringUtils.isNotBlank(bo.getKroleGroupType()), SysUser::getKroleGroupType, bo.getKroleGroupType());
                    lqw.eq(StringUtils.isNotBlank(bo.getKroleGroupIds()), SysUser::getKroleGroupIds, bo.getKroleGroupIds());
        return lqw;
    }

    /**
     * 新增用户
     */
    @Override
    public Boolean insertByBo(SysUserBo bo) {
        SysUser add = MapstructUtils.convert(bo, SysUser. class);
        validEntityBeforeSave(add);
        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            bo.setUserId(add.getUserId());
        }
        return flag;
    }

    /**
     * 修改用户
     */
    @Override
    public Boolean updateByBo(SysUserBo bo) {
        SysUser update = MapstructUtils.convert(bo, SysUser. class);
        validEntityBeforeSave(update);
        return baseMapper.updateById(update) > 0;
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(SysUser entity) {
        //TODO 做一些数据校验,如唯一约束
    }

    /**
     * 批量删除用户
     */
    @Override
    public Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid) {
        if (isValid) {
            //TODO 做一些业务上的校验,判断是否需要校验
        }
        return baseMapper.deleteBatchIds(ids) > 0;
    }
}
