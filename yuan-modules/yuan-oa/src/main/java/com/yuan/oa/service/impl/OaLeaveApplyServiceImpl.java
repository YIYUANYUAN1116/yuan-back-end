package com.yuan.oa.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yuan.common.core.bizno.BizNoGenerator;
import com.yuan.common.core.bizno.BizNoPrefixEnum;
import com.yuan.common.core.constant.ApplyConstants;
import com.yuan.common.core.enums.apply.ApplyStatus;
import com.yuan.common.core.exception.base.BaseException;
import com.yuan.common.core.exception.oa.OaErrorCode;
import com.yuan.common.core.exception.oa.OaException;
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
import com.yuan.workflow.api.WorkflowService;
import com.yuan.workflow.cmd.StartCmd;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    private final WorkflowService workflowService;

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
        if (entity.getId() != null) {
            OaLeaveApply oaLeaveApply = baseMapper.selectById(entity.getId());
            if (!oaLeaveApply.getStatus().equals(ApplyStatus.DRAFT)){
                throw new OaException(OaErrorCode.CANNOT_EDIT_NON_DRAFT);
            }
        }


    }

    /**
     * 批量删除oa_leave
     */
    @Override
    @Transactional
    public Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid) {
        if (isValid) {
            //TODO 做一些业务上的校验,判断是否需要校验
            baseMapper.selectByIds(ids).forEach(bo -> {
                if (!bo.getStatus().equals(ApplyStatus.DRAFT)){
                    throw new OaException(OaErrorCode.CANNOT_DELETE_NON_DRAFT);
                }
            });
        }
        return baseMapper.deleteByIds(ids) > 0;
    }

    @Override
    public OaLeaveApplyVo queryByBizNo(String bizNo) {
        return baseMapper.selectVoOne(Wrappers.<OaLeaveApply>lambdaQuery().eq(OaLeaveApply::getApplyNo, bizNo));
    }

    @Override
    @Transactional
    public Boolean submit(String bizNo) {

        // 1) 查单据
        OaLeaveApply apply = baseMapper.selectOne(Wrappers.<OaLeaveApply>lambdaQuery().eq(OaLeaveApply::getApplyNo, bizNo));
        if (apply == null) {
            throw new BaseException("申请不存在");
        }

        // 2) 状态校验：只能草稿提交
        if (!ApplyStatus.DRAFT.equals(apply.getStatus())) {
            throw new BaseException("只能提交草稿状态的申请");
        }

        // 3) 组装发起流程命令
        StartCmd cmd = new StartCmd();
        cmd.setBizType(ApplyConstants.OA_LEAVE);
        cmd.setBizId(apply.getId());
        cmd.setBizNo(apply.getApplyNo());
        cmd.setStarterId(LoginHelper.getUserId());
        cmd.setStarterDeptId(LoginHelper.getDeptId());
        cmd.setTitle(apply.getApplicantName() + " 请假 " + (apply.getLeaveDays() == null ? "" : apply.getLeaveDays()) + " 天");
        cmd.setDefinitionKey(ApplyConstants.OA_LEAVE);
        cmd.setComment(apply.getReason());
        // 网关/指派需要的变量
        Map<String, Object> vars = new HashMap<>();
        vars.put("days", apply.getLeaveDays());
        cmd.setVariables(vars);

        // 4) 发起流程
        Long instanceId = workflowService.startProcess(cmd);

        // 5) 回写：状态=审批中 + instanceId + submitTime
        OaLeaveApply upd = new OaLeaveApply();
        upd.setId(apply.getId());
        upd.setStatus(ApplyStatus.APPROVING);
        upd.setStartTime(LocalDateTime.now());
        return baseMapper.updateById(upd)>0;
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
