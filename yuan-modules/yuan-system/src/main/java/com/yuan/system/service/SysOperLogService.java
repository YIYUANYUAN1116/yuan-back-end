package com.yuan.system.service;

import com.yuan.core.page.PageQuery;
import com.yuan.core.page.TableDataInfo;
import com.yuan.system.domain.bo.SysOperLogBo;
import com.yuan.system.domain.vo.SysOperLogVo;

import java.util.Collection;
import java.util.List;

/**
 * oprelogService接口
 *
 * @author ageerle
 * @date Wed Dec 17 21:48:33 CST 2025
 */
public interface SysOperLogService {

    /**
     * 查询oprelog
     */
        SysOperLogVo queryById(Long operId);

        /**
         * 查询oprelog列表
         */
        TableDataInfo<SysOperLogVo> queryPageList(SysOperLogBo bo, PageQuery pageQuery);

    /**
     * 查询oprelog列表
     */
    List<SysOperLogVo> queryList(SysOperLogBo bo);

    /**
     * 新增oprelog
     */
    Boolean insertByBo(SysOperLogBo bo);

    /**
     * 修改oprelog
     */
    Boolean updateByBo(SysOperLogBo bo);

    /**
     * 校验并批量删除oprelog信息
     */
    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);
}
