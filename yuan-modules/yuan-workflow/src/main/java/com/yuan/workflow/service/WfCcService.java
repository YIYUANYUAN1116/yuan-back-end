package com.yuan.workflow.service;

import com.yuan.core.page.PageQuery;
import com.yuan.core.page.TableDataInfo;
import com.yuan.workflow.domain.bo.WfCcBo;
import com.yuan.workflow.domain.vo.WfCcVo;

import java.util.Collection;
import java.util.List;

/**
 * wfccService接口
 *
 
 * @date Sun Dec 28 11:26:25 CST 2025
 */
public interface WfCcService {

    /**
     * 查询wfcc
     */
        WfCcVo queryById(Long id);

        /**
         * 查询wfcc列表
         */
        TableDataInfo<WfCcVo> queryPageList(WfCcBo bo, PageQuery pageQuery);

    /**
     * 查询wfcc列表
     */
    List<WfCcVo> queryList(WfCcBo bo);

    /**
     * 新增wfcc
     */
    Boolean insertByBo(WfCcBo bo);

    /**
     * 修改wfcc
     */
    Boolean updateByBo(WfCcBo bo);

    /**
     * 校验并批量删除wfcc信息
     */
    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);
}
