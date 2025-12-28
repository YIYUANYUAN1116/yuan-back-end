package com.yuan.system.service;

import com.yuan.common.log.event.LogininforEvent;
import com.yuan.core.page.PageQuery;
import com.yuan.core.page.TableDataInfo;
import com.yuan.system.domain.bo.SysLogininforBo;
import com.yuan.system.domain.vo.SysLogininforVo;

import java.util.Collection;
import java.util.List;

/**
 * loginlogService接口
 *
 
 * @date Wed Dec 17 21:48:44 CST 2025
 */
public interface SysLogininforService {

    /**
     * 查询loginlog
     */
        SysLogininforVo queryById(Long infoId);

        /**
         * 查询loginlog列表
         */
        TableDataInfo<SysLogininforVo> queryPageList(SysLogininforBo bo, PageQuery pageQuery);

    /**
     * 查询loginlog列表
     */
    List<SysLogininforVo> queryList(SysLogininforBo bo);

    /**
     * 新增loginlog
     */
    Boolean insertByBo(SysLogininforBo bo);

    /**
     * 修改loginlog
     */
    Boolean updateByBo(SysLogininforBo bo);

    /**
     * 校验并批量删除loginlog信息
     */
    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);

    void recordLogininfor(LogininforEvent logininforEvent);
}
