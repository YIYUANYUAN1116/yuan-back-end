package com.yuan.oa.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yuan.common.core.bizno.BizNoGenerator;
import com.yuan.common.core.bizno.BizNoPrefixEnum;
import com.yuan.common.core.utils.MapstructUtils;
import com.yuan.common.core.utils.StringUtils;
import com.yuan.common.satoken.utils.LoginHelper;
import com.yuan.core.page.PageQuery;
import com.yuan.core.page.TableDataInfo;
import com.yuan.oa.domain.OaLeaveApply;
import com.yuan.oa.domain.bo.OaLeaveApplyBo;
import com.yuan.oa.domain.vo.OaLeaveApplyVo;
import com.yuan.oa.mapper.OaLeaveApplyMapper;
import com.yuan.oa.service.OaLeaveApplyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;

/**
 * oa_leaveService业务层处理
 *
 * @author yuan
 * @date Fri Jan 16 13:46:51 CST 2026
 */
@RequiredArgsConstructor
@Service
public class OaLeaveApplyServiceImpl implements OaLeaveApplyService {

    private final OaLeaveApplyMapper baseMapper;
    private final BizNoGenerator bizNoGenerator;

    /**
     * 查询oa_leave
     */
    @Override
    public OaLeaveApplyVo queryById(Long id) {
        return baseMapper.selectVoById(id);
    }

        /**
         * 查询oa_leave列表
         */
        @Override
        public TableDataInfo<OaLeaveApplyVo> queryPageList(OaLeaveApplyBo bo, PageQuery pageQuery) {
            LambdaQueryWrapper<OaLeaveApply> lqw = buildQueryWrapper(bo);
            Page<OaLeaveApplyVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
            return TableDataInfo.build(result);
        }

    /**
     * 查询oa_leave列表
     */
    @Override
    public List<OaLeaveApplyVo> queryList(OaLeaveApplyBo bo) {
        LambdaQueryWrapper<OaLeaveApply> lqw = buildQueryWrapper(bo);
        return baseMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<OaLeaveApply> buildQueryWrapper(OaLeaveApplyBo bo) {
        LambdaQueryWrapper<OaLeaveApply> lqw = Wrappers.lambdaQuery();
                    lqw.eq(bo.getId() != null, OaLeaveApply::getId, bo.getId());
                    lqw.eq(StringUtils.isNotBlank(bo.getTenantId()), OaLeaveApply::getTenantId, bo.getTenantId());
                    lqw.eq(StringUtils.isNotBlank(bo.getApplyNo()), OaLeaveApply::getApplyNo, bo.getApplyNo());
                    lqw.eq(bo.getApplicantId() != null, OaLeaveApply::getApplicantId, bo.getApplicantId());
                    lqw.eq(StringUtils.isNotBlank(bo.getApplicantName()), OaLeaveApply::getApplicantName, bo.getApplicantName());
                    lqw.eq(bo.getApplicantDept() != null, OaLeaveApply::getApplicantDept, bo.getApplicantDept());
                    lqw.eq(StringUtils.isNotBlank(bo.getLeaveType()), OaLeaveApply::getLeaveType, bo.getLeaveType());
                    lqw.eq(StringUtils.isNotBlank(bo.getReason()), OaLeaveApply::getReason, bo.getReason());
                    lqw.eq(bo.getStartTime() != null, OaLeaveApply::getStartTime, bo.getStartTime());
                    lqw.eq(bo.getEndTime() != null, OaLeaveApply::getEndTime, bo.getEndTime());
                    lqw.eq(bo.getLeaveDays() != null, OaLeaveApply::getLeaveDays, bo.getLeaveDays());
                    lqw.eq(bo.getLeaveHours() != null, OaLeaveApply::getLeaveHours, bo.getLeaveHours());
                    lqw.eq(StringUtils.isNotBlank(bo.getStatus()), OaLeaveApply::getStatus, bo.getStatus());
                    lqw.eq(bo.getCreateDept() != null, OaLeaveApply::getCreateDept, bo.getCreateDept());
                    lqw.eq(bo.getCreateBy() != null, OaLeaveApply::getCreateBy, bo.getCreateBy());
                    lqw.eq(bo.getCreateTime() != null, OaLeaveApply::getCreateTime, bo.getCreateTime());
                    lqw.eq(bo.getUpdateBy() != null, OaLeaveApply::getUpdateBy, bo.getUpdateBy());
                    lqw.eq(bo.getUpdateTime() != null, OaLeaveApply::getUpdateTime, bo.getUpdateTime());
        return lqw;
    }

    /**
     * 新增oa_leave
     */
    @Override
    @Transactional
    public Boolean insertByBo(OaLeaveApplyBo bo) {
        fillApplyBo(bo);
        OaLeaveApply add = MapstructUtils.convert(bo, OaLeaveApply. class);
        validEntityBeforeSave(add);
        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            bo.setId(add.getId());
        }
        return flag;
    }

    /**
     * 修改oa_leave
     */
    @Override
    @Transactional
    public Boolean updateByBo(OaLeaveApplyBo bo) {
        OaLeaveApply update = MapstructUtils.convert(bo, OaLeaveApply. class);
        validEntityBeforeSave(update);
        return baseMapper.updateById(update) > 0;
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(OaLeaveApply entity) {
        //TODO 做一些数据校验,如唯一约束
    }

    /**
     * 批量删除oa_leave
     */
    @Override
    @Transactional
    public Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid) {
        if (isValid) {
            //TODO 做一些业务上的校验,判断是否需要校验
        }
        return baseMapper.deleteBatchIds(ids) > 0;
    }

    @Override
    public OaLeaveApplyVo queryByBizNo(String bizNo) {
        return baseMapper.selectVoOne(Wrappers.<OaLeaveApply>lambdaQuery().eq(OaLeaveApply::getApplyNo, bizNo));
    }

    private void fillApplyBo(OaLeaveApplyBo bo) {
        String bizNo = bizNoGenerator.generate(BizNoPrefixEnum.OA);
        bo.setApplyNo(bizNo);
        bo.setApplicantId(LoginHelper.getUserId());
        bo.setApplicantDept(LoginHelper.getDeptId());
        bo.setApplicantName(LoginHelper.getUsername());
        bo.setApplicantDeptName(LoginHelper.getDeptName());
    }
}
