package com.yuan.system.service;

import com.yuan.core.page.PageQuery;
import com.yuan.core.page.TableDataInfo;
import com.yuan.system.domain.bo.SysTenantBo;
import com.yuan.system.domain.vo.SysTenantVo;

import java.util.Collection;
import java.util.List;

/**
 * 多租户Service接口
 *
 * @author ageerle
 * @date Wed Dec 10 17:18:08 CST 2025
 */
public interface SysTenantService {

    /**
     * 查询多租户
     */
        SysTenantVo queryById(Long id);

        /**
         * 查询多租户列表
         */
        TableDataInfo<SysTenantVo> queryPageList(SysTenantBo bo, PageQuery pageQuery);

    /**
     * 查询多租户列表
     */
    List<SysTenantVo> queryList(SysTenantBo bo);

    /**
     * 新增多租户
     */
    Boolean insertByBo(SysTenantBo bo);

    /**
     * 修改多租户
     */
    Boolean updateByBo(SysTenantBo bo);

    /**
     * 校验并批量删除多租户信息
     */
    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);
}
