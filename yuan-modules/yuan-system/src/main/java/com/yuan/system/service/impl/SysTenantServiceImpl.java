package com.yuan.system.service.impl;

import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yuan.common.core.utils.MapstructUtils;
import com.yuan.common.core.utils.StringUtils;
import com.yuan.core.page.PageQuery;
import com.yuan.core.page.TableDataInfo;
import com.yuan.system.domain.SysTenant;
import com.yuan.system.domain.bo.SysTenantBo;
import com.yuan.system.domain.vo.SysTenantVo;
import com.yuan.system.mapper.SysTenantMapper;
import com.yuan.system.service.SysTenantService;
import com.yuan.system.service.TenantBootstrapService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;

/**
 * 多租户Service业务层处理
 *
 
 * @date Wed Dec 10 17:18:08 CST 2025
 */
@RequiredArgsConstructor
@Service
public class SysTenantServiceImpl implements SysTenantService {

    private final SysTenantMapper baseMapper;
    private final TenantBootstrapService tenantBootstrapService;

    /**
     * 查询多租户
     */
    @Override
    public SysTenantVo queryById(Long id) {
        return baseMapper.selectVoById(id);
    }

        /**
         * 查询多租户列表
         */
        @Override
        public TableDataInfo<SysTenantVo> queryPageList(SysTenantBo bo, PageQuery pageQuery) {
            LambdaQueryWrapper<SysTenant> lqw = buildQueryWrapper(bo);
            Page<SysTenantVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
            return TableDataInfo.build(result);
        }

    /**
     * 查询多租户列表
     */
    @Override
    public List<SysTenantVo> queryList(SysTenantBo bo) {
        LambdaQueryWrapper<SysTenant> lqw = buildQueryWrapper(bo);
        return baseMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<SysTenant> buildQueryWrapper(SysTenantBo bo) {
        LambdaQueryWrapper<SysTenant> lqw = Wrappers.lambdaQuery();
                    lqw.eq(bo.getId() != null, SysTenant::getId, bo.getId());
                    lqw.eq(StringUtils.isNotBlank(bo.getTenantId()), SysTenant::getTenantId, bo.getTenantId());
                    lqw.eq(StringUtils.isNotBlank(bo.getContactUserName()), SysTenant::getContactUserName, bo.getContactUserName());
                    lqw.eq(StringUtils.isNotBlank(bo.getContactPhone()), SysTenant::getContactPhone, bo.getContactPhone());
                    lqw.eq(StringUtils.isNotBlank(bo.getCompanyName()), SysTenant::getCompanyName, bo.getCompanyName());
                    lqw.eq(StringUtils.isNotBlank(bo.getLicenseNumber()), SysTenant::getLicenseNumber, bo.getLicenseNumber());
                    lqw.eq(StringUtils.isNotBlank(bo.getAddress()), SysTenant::getAddress, bo.getAddress());
                    lqw.eq(StringUtils.isNotBlank(bo.getIntro()), SysTenant::getIntro, bo.getIntro());
                    lqw.eq(StringUtils.isNotBlank(bo.getDomain()), SysTenant::getDomain, bo.getDomain());
                    lqw.eq(StringUtils.isNotBlank(bo.getRemark()), SysTenant::getRemark, bo.getRemark());
                    lqw.eq(bo.getPackageId() != null, SysTenant::getPackageId, bo.getPackageId());
                    lqw.eq(bo.getExpireTime() != null, SysTenant::getExpireTime, bo.getExpireTime());
                    lqw.eq(bo.getAccountCount() != null, SysTenant::getAccountCount, bo.getAccountCount());
                    lqw.eq(StringUtils.isNotBlank(bo.getStatus()), SysTenant::getStatus, bo.getStatus());
                    lqw.eq(StringUtils.isNotBlank(bo.getDelFlag()), SysTenant::getDelFlag, bo.getDelFlag());
                    lqw.eq(bo.getCreateDept() != null, SysTenant::getCreateDept, bo.getCreateDept());
                    lqw.eq(bo.getCreateBy() != null, SysTenant::getCreateBy, bo.getCreateBy());
                    lqw.eq(bo.getCreateTime() != null, SysTenant::getCreateTime, bo.getCreateTime());
                    lqw.eq(bo.getUpdateBy() != null, SysTenant::getUpdateBy, bo.getUpdateBy());
                    lqw.eq(bo.getUpdateTime() != null, SysTenant::getUpdateTime, bo.getUpdateTime());
        return lqw;
    }

    /**
     * 新增多租户
     */
    @Override
    @Transactional
    public Boolean insertByBo(SysTenantBo bo) {
        bo.setTenantId(IdUtil.getSnowflakeNextIdStr());
        SysTenant add = MapstructUtils.convert(bo, SysTenant. class);
        validEntityBeforeSave(add);
        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            tenantBootstrapService.initTenant(add);
            bo.setId(add.getId());
        }
        return flag;
    }

    /**
     * 修改多租户
     */
    @Override
    public Boolean updateByBo(SysTenantBo bo) {
        SysTenant update = MapstructUtils.convert(bo, SysTenant. class);
        validEntityBeforeSave(update);
        return baseMapper.updateById(update) > 0;
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(SysTenant entity) {
        //TODO 做一些数据校验,如唯一约束
    }

    /**
     * 批量删除多租户
     */
    @Override
    public Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid) {
        if (isValid) {
            //TODO 做一些业务上的校验,判断是否需要校验
        }
        return baseMapper.deleteBatchIds(ids) > 0;
    }
}
