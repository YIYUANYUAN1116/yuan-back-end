package com.yuan.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yuan.common.core.utils.MapstructUtils;
import com.yuan.common.core.utils.StringUtils;
import com.yuan.common.core.utils.ip.AddressUtils;
import com.yuan.common.log.event.OperLogEvent;
import com.yuan.core.page.PageQuery;
import com.yuan.core.page.TableDataInfo;
import com.yuan.system.domain.SysOperLog;
import com.yuan.system.domain.bo.SysOperLogBo;
import com.yuan.system.domain.vo.SysOperLogVo;
import com.yuan.system.mapper.SysOperLogMapper;
import com.yuan.system.service.SysOperLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

/**
 * oprelogService业务层处理
 *

 * @date Wed Dec 17 21:48:33 CST 2025
 */
@RequiredArgsConstructor
@Service
public class SysOperLogServiceImpl implements SysOperLogService {

    private final SysOperLogMapper baseMapper;


    /**
     * 操作日志记录
     *
     * @param operLogEvent 操作日志事件
     */
    @Async
    @EventListener
    @Override
    public void recordOper(OperLogEvent operLogEvent) {
        SysOperLogBo operLog = MapstructUtils.convert(operLogEvent, SysOperLogBo.class);
        // 远程查询操作地点
        operLog.setOperLocation(AddressUtils.getRealAddressByIP(operLog.getOperIp()));
        operLog.setOperTime(LocalDateTime.now());
        insertByBo(operLog);
    }

    /**
     * 查询oprelog
     */
    @Override
    public SysOperLogVo queryById(Long operId) {
        return baseMapper.selectVoById(operId);
    }

        /**
         * 查询oprelog列表
         */
        @Override
        public TableDataInfo<SysOperLogVo> queryPageList(SysOperLogBo bo, PageQuery pageQuery) {
            LambdaQueryWrapper<SysOperLog> lqw = buildQueryWrapper(bo);
            Page<SysOperLogVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
            return TableDataInfo.build(result);
        }

    /**
     * 查询oprelog列表
     */
    @Override
    public List<SysOperLogVo> queryList(SysOperLogBo bo) {
        LambdaQueryWrapper<SysOperLog> lqw = buildQueryWrapper(bo);
        return baseMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<SysOperLog> buildQueryWrapper(SysOperLogBo bo) {
        LambdaQueryWrapper<SysOperLog> lqw = Wrappers.lambdaQuery();
                    lqw.eq(bo.getOperId() != null, SysOperLog::getOperId, bo.getOperId());
                    lqw.eq(StringUtils.isNotBlank(bo.getTenantId()), SysOperLog::getTenantId, bo.getTenantId());
                    lqw.eq(StringUtils.isNotBlank(bo.getTitle()), SysOperLog::getTitle, bo.getTitle());
                    lqw.eq(bo.getBusinessType() != null, SysOperLog::getBusinessType, bo.getBusinessType());
                    lqw.eq(StringUtils.isNotBlank(bo.getMethod()), SysOperLog::getMethod, bo.getMethod());
                    lqw.eq(StringUtils.isNotBlank(bo.getRequestMethod()), SysOperLog::getRequestMethod, bo.getRequestMethod());
                    lqw.eq(bo.getOperatorType() != null, SysOperLog::getOperatorType, bo.getOperatorType());
                    lqw.eq(StringUtils.isNotBlank(bo.getOperName()), SysOperLog::getOperName, bo.getOperName());
                    lqw.eq(StringUtils.isNotBlank(bo.getDeptName()), SysOperLog::getDeptName, bo.getDeptName());
                    lqw.eq(StringUtils.isNotBlank(bo.getOperUrl()), SysOperLog::getOperUrl, bo.getOperUrl());
                    lqw.eq(StringUtils.isNotBlank(bo.getOperIp()), SysOperLog::getOperIp, bo.getOperIp());
                    lqw.eq(StringUtils.isNotBlank(bo.getOperLocation()), SysOperLog::getOperLocation, bo.getOperLocation());
                    lqw.eq(StringUtils.isNotBlank(bo.getOperParam()), SysOperLog::getOperParam, bo.getOperParam());
                    lqw.eq(StringUtils.isNotBlank(bo.getJsonResult()), SysOperLog::getJsonResult, bo.getJsonResult());
                    lqw.eq(bo.getStatus() != null, SysOperLog::getStatus, bo.getStatus());
                    lqw.eq(StringUtils.isNotBlank(bo.getErrorMsg()), SysOperLog::getErrorMsg, bo.getErrorMsg());
                    lqw.eq(bo.getOperTime() != null, SysOperLog::getOperTime, bo.getOperTime());
                    lqw.eq(bo.getCostTime() != null, SysOperLog::getCostTime, bo.getCostTime());
        return lqw;
    }

    /**
     * 新增oprelog
     */
    @Override
    public Boolean insertByBo(SysOperLogBo bo) {
        SysOperLog add = MapstructUtils.convert(bo, SysOperLog. class);
        validEntityBeforeSave(add);
        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            bo.setOperId(add.getOperId());
        }
        return flag;
    }

    /**
     * 修改oprelog
     */
    @Override
    public Boolean updateByBo(SysOperLogBo bo) {
        SysOperLog update = MapstructUtils.convert(bo, SysOperLog. class);
        validEntityBeforeSave(update);
        return baseMapper.updateById(update) > 0;
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(SysOperLog entity) {
        //TODO 做一些数据校验,如唯一约束
    }

    /**
     * 批量删除oprelog
     */
    @Override
    public Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid) {
        if (isValid) {
            //TODO 做一些业务上的校验,判断是否需要校验
        }
        return baseMapper.deleteBatchIds(ids) > 0;
    }
}
