package com.yuan.system.service;

import com.yuan.core.page.PageQuery;
import com.yuan.core.page.TableDataInfo;
import com.yuan.system.domain.bo.SysRoleMenuBo;
import com.yuan.system.domain.vo.SysRoleMenuVo;

import java.util.Collection;
import java.util.List;

/**
 * 角色菜单Service接口
 *
 * @author ageerle
 * @date Wed Dec 10 17:21:43 CST 2025
 */
public interface SysRoleMenuService {

    /**
     * 查询角色菜单
     */
        SysRoleMenuVo queryById(Long menuId);

        /**
         * 查询角色菜单列表
         */
        TableDataInfo<SysRoleMenuVo> queryPageList(SysRoleMenuBo bo, PageQuery pageQuery);

    /**
     * 查询角色菜单列表
     */
    List<SysRoleMenuVo> queryList(SysRoleMenuBo bo);

    /**
     * 新增角色菜单
     */
    Boolean insertByBo(SysRoleMenuBo bo);

    /**
     * 修改角色菜单
     */
    Boolean updateByBo(SysRoleMenuBo bo);

    /**
     * 校验并批量删除角色菜单信息
     */
    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);
}
