package com.yuan.workflow.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yuan.common.core.utils.MapstructUtils;
import com.yuan.common.core.utils.StringUtils;
import com.yuan.core.page.PageQuery;
import com.yuan.core.page.TableDataInfo;
import com.yuan.workflow.api.cmd.StartProcessCmd;
import com.yuan.workflow.api.enums.InstanceStatus;
import com.yuan.workflow.domain.WfBizRef;
import com.yuan.workflow.domain.bo.WfBizRefBo;
import com.yuan.workflow.domain.vo.WfBizRefVo;
import com.yuan.workflow.mapper.WfBizRefMapper;
import com.yuan.workflow.service.WfBizRefService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * wfrefService业务层处理
 *
 * @author yuan
 * @date Mon Dec 29 20:51:33 CST 2025
 */
@RequiredArgsConstructor
@Service
public class WfBizRefServiceImpl implements WfBizRefService {

    private final WfBizRefMapper baseMapper;

    /**
     * 查询wfref
     */
    @Override
    public WfBizRefVo queryById(Long id) {
        return baseMapper.selectVoById(id);
    }

        /**
         * 查询wfref列表
         */
        @Override
        public TableDataInfo<WfBizRefVo> queryPageList(WfBizRefBo bo, PageQuery pageQuery) {
            LambdaQueryWrapper<WfBizRef> lqw = buildQueryWrapper(bo);
            Page<WfBizRefVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
            return TableDataInfo.build(result);
        }

    /**
     * 查询wfref列表
     */
    @Override
    public List<WfBizRefVo> queryList(WfBizRefBo bo) {
        LambdaQueryWrapper<WfBizRef> lqw = buildQueryWrapper(bo);
        return baseMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<WfBizRef> buildQueryWrapper(WfBizRefBo bo) {
        LambdaQueryWrapper<WfBizRef> lqw = Wrappers.lambdaQuery();
                    lqw.eq(bo.getId() != null, WfBizRef::getId, bo.getId());
                    lqw.eq(StringUtils.isNotBlank(bo.getBizType()), WfBizRef::getBizType, bo.getBizType());
                    lqw.eq(StringUtils.isNotBlank(bo.getBizId()), WfBizRef::getBizId, bo.getBizId());
                    lqw.eq(bo.getInstanceId() != null, WfBizRef::getInstanceId, bo.getInstanceId());
                    lqw.eq(StringUtils.isNotBlank(bo.getStatus()), WfBizRef::getStatus, bo.getStatus());
                    lqw.eq(bo.getCreateBy() != null, WfBizRef::getCreateBy, bo.getCreateBy());
                    lqw.eq(bo.getCreateTime() != null, WfBizRef::getCreateTime, bo.getCreateTime());
                    lqw.eq(bo.getUpdateTime() != null, WfBizRef::getUpdateTime, bo.getUpdateTime());
        return lqw;
    }

    /**
     * 新增wfref
     */
    @Override
    public Boolean insertByBo(WfBizRefBo bo) {
        WfBizRef add = MapstructUtils.convert(bo, WfBizRef. class);
        validEntityBeforeSave(add);
        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            bo.setId(add.getId());
        }
        return flag;
    }

    /**
     * 修改wfref
     */
    @Override
    public Boolean updateByBo(WfBizRefBo bo) {
        WfBizRef update = MapstructUtils.convert(bo, WfBizRef. class);
        validEntityBeforeSave(update);
        return baseMapper.updateById(update) > 0;
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(WfBizRef entity) {
        //TODO 做一些数据校验,如唯一约束
    }

    /**
     * 批量删除wfref
     */
    @Override
    public Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid) {
        if (isValid) {
            //TODO 做一些业务上的校验,判断是否需要校验
        }
        return baseMapper.deleteBatchIds(ids) > 0;
    }

    @Override
    public void bindWfBizRef(StartProcessCmd cmd, Long instanceId) {
        WfBizRef ref = new WfBizRef();
        ref.setBizType(cmd.getBizType());
        ref.setBizId(cmd.getBizId());

        ref.setInstanceId(instanceId);
        ref.setStatus(InstanceStatus.RUNNING.getCode());
        ref.setCreateBy(cmd.getStarterUserId());
        ref.setCreateTime(new Date());
        ref.setUpdateTime(new Date());
        baseMapper.insert(ref);
    }
}
