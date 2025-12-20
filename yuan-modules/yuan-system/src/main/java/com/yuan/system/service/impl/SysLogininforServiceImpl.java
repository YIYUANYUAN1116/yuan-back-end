package com.yuan.system.service.impl;

import cn.hutool.http.useragent.UserAgent;
import cn.hutool.http.useragent.UserAgentUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yuan.common.core.constant.Constants;
import com.yuan.common.core.utils.MapstructUtils;
import com.yuan.common.core.utils.ServletUtils;
import com.yuan.common.core.utils.StringUtils;
import com.yuan.common.core.utils.ip.AddressUtils;
import com.yuan.common.log.event.LogininforEvent;
import com.yuan.core.page.PageQuery;
import com.yuan.core.page.TableDataInfo;
import com.yuan.system.domain.SysLogininfor;
import com.yuan.system.domain.bo.SysLogininforBo;
import com.yuan.system.domain.vo.SysLogininforVo;
import com.yuan.system.mapper.SysLogininforMapper;
import com.yuan.system.service.SysLogininforService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

/**
 * loginlogService业务层处理
 *
 * @author ageerle
 * @date Wed Dec 17 21:48:44 CST 2025
 */
@RequiredArgsConstructor
@Service
@Slf4j
public class SysLogininforServiceImpl implements SysLogininforService {

    private final SysLogininforMapper baseMapper;

    /**
     * 查询loginlog
     */
    @Override
    public SysLogininforVo queryById(Long infoId) {
        return baseMapper.selectVoById(infoId);
    }

        /**
         * 查询loginlog列表
         */
        @Override
        public TableDataInfo<SysLogininforVo> queryPageList(SysLogininforBo bo, PageQuery pageQuery) {
            LambdaQueryWrapper<SysLogininfor> lqw = buildQueryWrapper(bo);
            Page<SysLogininforVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
            return TableDataInfo.build(result);
        }

    /**
     * 查询loginlog列表
     */
    @Override
    public List<SysLogininforVo> queryList(SysLogininforBo bo) {
        LambdaQueryWrapper<SysLogininfor> lqw = buildQueryWrapper(bo);
        return baseMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<SysLogininfor> buildQueryWrapper(SysLogininforBo bo) {
        LambdaQueryWrapper<SysLogininfor> lqw = Wrappers.lambdaQuery();
                    lqw.eq(bo.getInfoId() != null, SysLogininfor::getInfoId, bo.getInfoId());
                    lqw.eq(StringUtils.isNotBlank(bo.getTenantId()), SysLogininfor::getTenantId, bo.getTenantId());
                    lqw.eq(StringUtils.isNotBlank(bo.getUserName()), SysLogininfor::getUserName, bo.getUserName());
                    lqw.eq(StringUtils.isNotBlank(bo.getIpaddr()), SysLogininfor::getIpaddr, bo.getIpaddr());
                    lqw.eq(StringUtils.isNotBlank(bo.getLoginLocation()), SysLogininfor::getLoginLocation, bo.getLoginLocation());
                    lqw.eq(StringUtils.isNotBlank(bo.getBrowser()), SysLogininfor::getBrowser, bo.getBrowser());
                    lqw.eq(StringUtils.isNotBlank(bo.getOs()), SysLogininfor::getOs, bo.getOs());
                    lqw.eq(StringUtils.isNotBlank(bo.getStatus()), SysLogininfor::getStatus, bo.getStatus());
                    lqw.eq(StringUtils.isNotBlank(bo.getMsg()), SysLogininfor::getMsg, bo.getMsg());
                    lqw.eq(bo.getLoginTime() != null, SysLogininfor::getLoginTime, bo.getLoginTime());
        return lqw;
    }

    /**
     * 新增loginlog
     */
    @Override
    public Boolean insertByBo(SysLogininforBo bo) {
        SysLogininfor add = MapstructUtils.convert(bo, SysLogininfor. class);
        validEntityBeforeSave(add);
        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            bo.setInfoId(add.getInfoId());
        }
        return flag;
    }

    /**
     * 修改loginlog
     */
    @Override
    public Boolean updateByBo(SysLogininforBo bo) {
        SysLogininfor update = MapstructUtils.convert(bo, SysLogininfor. class);
        validEntityBeforeSave(update);
        return baseMapper.updateById(update) > 0;
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(SysLogininfor entity) {
        //TODO 做一些数据校验,如唯一约束
    }

    /**
     * 批量删除loginlog
     */
    @Override
    public Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid) {
        if (isValid) {
            //TODO 做一些业务上的校验,判断是否需要校验
        }
        return baseMapper.deleteBatchIds(ids) > 0;
    }

    /**
     * 记录登录信息
     *
     * @param logininforEvent 登录事件
     */
    @Async
    @EventListener
    @Override
    public void recordLogininfor(LogininforEvent logininforEvent) {
        HttpServletRequest request = logininforEvent.getRequest();
        final UserAgent userAgent = UserAgentUtil.parse(request.getHeader("User-Agent"));
        final String ip = ServletUtils.getClientIP(request);

        String address = AddressUtils.getRealAddressByIP(ip);
        StringBuilder s = new StringBuilder();
        s.append(getBlock(ip));
        s.append(address);
        s.append(getBlock(logininforEvent.getUsername()));
        s.append(getBlock(logininforEvent.getStatus()));
        s.append(getBlock(logininforEvent.getMessage()));
        // 打印信息到日志
        log.info(s.toString(), logininforEvent.getArgs());
        // 获取客户端操作系统
        String os = userAgent.getOs().getName();
        // 获取客户端浏览器
        String browser = userAgent.getBrowser().getName();
        // 封装对象
        SysLogininforBo logininfor = new SysLogininforBo();
        logininfor.setTenantId(logininforEvent.getTenantId());
        logininfor.setUserName(logininforEvent.getUsername());
        logininfor.setIpaddr(ip);
        logininfor.setLoginLocation(address);
        logininfor.setBrowser(browser);
        logininfor.setOs(os);
        logininfor.setMsg(logininforEvent.getMessage());
        // 日志状态
        if (StringUtils.equalsAny(logininforEvent.getStatus(), Constants.LOGIN_SUCCESS, Constants.LOGOUT, Constants.REGISTER)) {
            logininfor.setStatus(Constants.SUCCESS);
        } else if (Constants.LOGIN_FAIL.equals(logininforEvent.getStatus())) {
            logininfor.setStatus(Constants.FAIL);
        }
        logininfor.setLoginTime(LocalDateTime.now());
        // 插入数据
        insertByBo(logininfor);
    }

    private String getBlock(Object msg) {
        if (msg == null) {
            msg = "";
        }
        return "[" + msg.toString() + "]";
    }
}
