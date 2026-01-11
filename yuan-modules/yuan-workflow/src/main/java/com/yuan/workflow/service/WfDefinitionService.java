package com.yuan.workflow.service;

import com.yuan.core.page.PageQuery;
import com.yuan.core.page.TableDataInfo;
import com.yuan.workflow.api.enums.DefinitionAction;
import com.yuan.workflow.domain.bo.WfDefinitionBo;
import com.yuan.workflow.domain.dto.WfDefinitionDto;
import com.yuan.workflow.domain.vo.WfDefinitionVo;

import java.util.Collection;
import java.util.List;

/**
 * wfdService接口
 *
 
 * @date Sun Dec 28 11:26:30 CST 2025
 */
public interface WfDefinitionService {

    /**
     * 查询wfd
     */
        WfDefinitionVo queryById(Long id);

        /**
         * 查询wfd列表
         */
        TableDataInfo<WfDefinitionVo> queryPageList(WfDefinitionBo bo, PageQuery pageQuery);

    /**
     * 查询wfd列表
     */
    List<WfDefinitionVo> queryList(WfDefinitionBo bo);

    /**
     * 新增wfd
     */
    Boolean insertByBo(WfDefinitionBo bo);

    /**
     * 修改wfd
     */
    Boolean updateByBo(WfDefinitionBo bo);

    /**
     * 校验并批量删除wfd信息
     */
    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);

    Boolean updateByDto(WfDefinitionDto dto);

    Boolean changeStatus(Long id, DefinitionAction action);
}
