package com.yuan.ai.service.impl;

import com.yuan.common.core.utils.MapstructUtils;
    import com.yuan.core.page.TableDataInfo;
    import com.yuan.core.page.PageQuery;
    import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.yuan.ai.domain.bo.KbBaseAuthBo;
import com.yuan.ai.domain.vo.KbBaseAuthVo;
import com.yuan.ai.domain.KbBaseAuth;
import com.yuan.ai.mapper.KbBaseAuthMapper;
import com.yuan.ai.service.KbBaseAuthService;
import com.yuan.common.core.utils.StringUtils;

import java.util.List;
import java.util.Map;
import java.util.Collection;

/**
 * 知识库授权表Service业务层处理
 *
 * @author yuan
 * @date Fri May 01 16:08:26 CST 2026
 */
@RequiredArgsConstructor
@Service
public class KbBaseAuthServiceImpl implements KbBaseAuthService {

    private final KbBaseAuthMapper baseMapper;

    /**
     * 查询知识库授权表
     */
    @Override
    public KbBaseAuthVo queryById(Long authId) {
        return baseMapper.selectVoById(authId);
    }

        /**
         * 查询知识库授权表列表
         */
        @Override
        public TableDataInfo<KbBaseAuthVo> queryPageList(KbBaseAuthBo bo, PageQuery pageQuery) {
            LambdaQueryWrapper<KbBaseAuth> lqw = buildQueryWrapper(bo);
            Page<KbBaseAuthVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
            return TableDataInfo.build(result);
        }

    /**
     * 查询知识库授权表列表
     */
    @Override
    public List<KbBaseAuthVo> queryList(KbBaseAuthBo bo) {
        LambdaQueryWrapper<KbBaseAuth> lqw = buildQueryWrapper(bo);
        return baseMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<KbBaseAuth> buildQueryWrapper(KbBaseAuthBo bo) {
        LambdaQueryWrapper<KbBaseAuth> lqw = Wrappers.lambdaQuery();
                    lqw.eq(bo.getAuthId() != null, KbBaseAuth::getAuthId, bo.getAuthId());
                    lqw.eq(StringUtils.isNotBlank(bo.getTenantId()), KbBaseAuth::getTenantId, bo.getTenantId());
                    lqw.eq(bo.getKbId() != null, KbBaseAuth::getKbId, bo.getKbId());
                    lqw.eq(StringUtils.isNotBlank(bo.getSubjectType()), KbBaseAuth::getSubjectType, bo.getSubjectType());
                    lqw.eq(StringUtils.isNotBlank(bo.getSubjectId()), KbBaseAuth::getSubjectId, bo.getSubjectId());
                    lqw.eq(StringUtils.isNotBlank(bo.getPermission()), KbBaseAuth::getPermission, bo.getPermission());
                    lqw.eq(StringUtils.isNotBlank(bo.getStatus()), KbBaseAuth::getStatus, bo.getStatus());
                    lqw.eq(StringUtils.isNotBlank(bo.getCreateBy()), KbBaseAuth::getCreateBy, bo.getCreateBy());
                    lqw.eq(bo.getCreateTime() != null, KbBaseAuth::getCreateTime, bo.getCreateTime());
                    lqw.eq(StringUtils.isNotBlank(bo.getUpdateBy()), KbBaseAuth::getUpdateBy, bo.getUpdateBy());
                    lqw.eq(bo.getUpdateTime() != null, KbBaseAuth::getUpdateTime, bo.getUpdateTime());
                    lqw.eq(StringUtils.isNotBlank(bo.getDelFlag()), KbBaseAuth::getDelFlag, bo.getDelFlag());
        return lqw;
    }

    /**
     * 新增知识库授权表
     */
    @Override
    public Boolean insertByBo(KbBaseAuthBo bo) {
        KbBaseAuth add = MapstructUtils.convert(bo, KbBaseAuth. class);
        validEntityBeforeSave(add);
        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            bo.setAuthId(add.getAuthId());
        }
        return flag;
    }

    /**
     * 修改知识库授权表
     */
    @Override
    public Boolean updateByBo(KbBaseAuthBo bo) {
        KbBaseAuth update = MapstructUtils.convert(bo, KbBaseAuth. class);
        validEntityBeforeSave(update);
        return baseMapper.updateById(update) > 0;
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(KbBaseAuth entity) {
        //TODO 做一些数据校验,如唯一约束
    }

    /**
     * 批量删除知识库授权表
     */
    @Override
    public Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid) {
        if (isValid) {
            //TODO 做一些业务上的校验,判断是否需要校验
        }
        return baseMapper.deleteBatchIds(ids) > 0;
    }
}
