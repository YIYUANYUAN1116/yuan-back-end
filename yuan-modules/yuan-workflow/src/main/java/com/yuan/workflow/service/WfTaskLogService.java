package com.yuan.workflow.service;

import com.yuan.core.page.PageQuery;
import com.yuan.core.page.TableDataInfo;
import com.yuan.workflow.domain.bo.WfTaskLogBo;
import com.yuan.workflow.domain.vo.WfTaskLogVo;

import java.util.Collection;
import java.util.List;

/**
 * wftlService接口
 *
 
 * @date Sun Dec 28 11:26:44 CST 2025
 */
public interface WfTaskLogService {

    /**
     * 查询wftl
     */
        WfTaskLogVo queryById(Long id);

        /**
         * 查询wftl列表
         */
        TableDataInfo<WfTaskLogVo> queryPageList(WfTaskLogBo bo, PageQuery pageQuery);

    /**
     * 查询wftl列表
     */
    List<WfTaskLogVo> queryList(WfTaskLogBo bo);

    /**
     * 新增wftl
     */
    Boolean insertByBo(WfTaskLogBo bo);

    /**
     * 修改wftl
     */
    Boolean updateByBo(WfTaskLogBo bo);

    /**
     * 校验并批量删除wftl信息
     */
    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);
}
