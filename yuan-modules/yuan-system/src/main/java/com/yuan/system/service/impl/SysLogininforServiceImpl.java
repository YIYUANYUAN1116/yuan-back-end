package com.yuan.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yuan.common.core.utils.MapstructUtils;
import com.yuan.common.core.utils.StringUtils;
import com.yuan.core.page.PageQuery;
import com.yuan.core.page.TableDataInfo;
import com.yuan.system.domain.SysLogininfor;
import com.yuan.system.domain.bo.SysLogininforBo;
import com.yuan.system.domain.vo.SysLogininforVo;
import com.yuan.system.mapper.SysLogininforMapper;
import com.yuan.system.service.SysLogininforService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
}
