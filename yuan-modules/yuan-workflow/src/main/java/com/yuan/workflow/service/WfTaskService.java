package com.yuan.workflow.service;

import com.yuan.core.page.PageQuery;
import com.yuan.core.page.TableDataInfo;
import com.yuan.workflow.domain.WfInstance;
import com.yuan.workflow.domain.WfNodeInstance;
import com.yuan.workflow.domain.bo.WfTaskBo;
import com.yuan.workflow.domain.vo.WfTaskVo;
import com.yuan.workflow.domain.vo.WorkItemRowVO;

import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
 * wftService接口
 *
 
 * @date Sun Dec 28 11:26:41 CST 2025
 */
public interface WfTaskService {

    /**
     * 查询wft
     */
        WfTaskVo queryById(Long id);

        /**
         * 查询wft列表
         */
        TableDataInfo<WfTaskVo> queryPageList(WfTaskBo bo, PageQuery pageQuery);

    /**
     * 查询wft列表
     */
    List<WfTaskVo> queryList(WfTaskBo bo);

    /**
     * 新增wft
     */
    Boolean insertByBo(WfTaskBo bo);

    /**
     * 修改wft
     */
    Boolean updateByBo(WfTaskBo bo);

    /**
     * 校验并批量删除wft信息
     */
    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);

    void createTasks(WfInstance instance, WfNodeInstance nodeInstance, Set<Long> userIds);

    TableDataInfo<WorkItemRowVO> myTask(WfTaskBo bo, PageQuery pageQuery);

    TableDataInfo<WorkItemRowVO> myApproval(WfTaskBo bo, PageQuery pageQuery);

    void deleteByInstanceIds(Collection<Long> ids);

    List<WfTaskVo> selectVoByNodeInstanceId(Long nodeInstanceId);

    WfTaskVo findCurrentUserTask(Long instanceId);
}
