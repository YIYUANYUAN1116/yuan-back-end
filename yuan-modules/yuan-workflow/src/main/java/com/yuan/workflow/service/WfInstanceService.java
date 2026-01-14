package com.yuan.workflow.service;

import com.yuan.core.page.PageQuery;
import com.yuan.core.page.TableDataInfo;
import com.yuan.workflow.api.cmd.StartProcessCmd;
import com.yuan.workflow.domain.WfDefinition;
import com.yuan.workflow.domain.WfInstance;
import com.yuan.workflow.domain.bo.WfInstanceBo;
import com.yuan.workflow.domain.vo.WfInstanceVo;
import com.yuan.workflow.domain.vo.WorkItemRowVO;

import java.util.Collection;
import java.util.List;

/**
 * wfiService接口
 *
 
 * @date Sun Dec 28 11:26:34 CST 2025
 */
public interface WfInstanceService {

    /**
     * 查询wfi
     */
        WfInstanceVo queryById(Long id);

        /**
         * 查询wfi列表
         */
        TableDataInfo<WfInstanceVo> queryPageList(WfInstanceBo bo, PageQuery pageQuery);

    /**
     * 查询wfi列表
     */
    List<WfInstanceVo> queryList(WfInstanceBo bo);

    /**
     * 新增wfi
     */
    Boolean insertByBo(WfInstanceBo bo);

    /**
     * 修改wfi
     */
    Boolean updateByBo(WfInstanceBo bo);

    /**
     * 校验并批量删除wfi信息
     */
    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);

    WfInstance createInstance(StartProcessCmd cmd, WfDefinition def);

    TableDataInfo<WorkItemRowVO> myApply(WfInstanceBo bo, PageQuery pageQuery);
}
