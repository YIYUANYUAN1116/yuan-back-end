package com.yuan.system.service;

import com.yuan.core.page.PageQuery;
import com.yuan.core.page.TableDataInfo;
import com.yuan.system.domain.bo.SysRolePostBo;
import com.yuan.system.domain.vo.SysRolePostVo;

import java.util.Collection;
import java.util.List;

/**
 * sys_role_postService接口
 *
 * @author yuan
 * @date Mon Jan 05 20:10:39 CST 2026
 */
public interface SysRolePostService {

    /**
     * 查询sys_role_post
     */
        SysRolePostVo queryById(Long roleId);

        /**
         * 查询sys_role_post列表
         */
        TableDataInfo<SysRolePostVo> queryPageList(SysRolePostBo bo, PageQuery pageQuery);

    /**
     * 查询sys_role_post列表
     */
    List<SysRolePostVo> queryList(SysRolePostBo bo);

    /**
     * 新增sys_role_post
     */
    Boolean insertByBo(SysRolePostBo bo);

    /**
     * 修改sys_role_post
     */
    Boolean updateByBo(SysRolePostBo bo);

    /**
     * 校验并批量删除sys_role_post信息
     */
    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);
}
