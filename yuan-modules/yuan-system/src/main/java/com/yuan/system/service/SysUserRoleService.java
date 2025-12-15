package com.yuan.system.service;

import com.yuan.core.page.PageQuery;
import com.yuan.core.page.TableDataInfo;
import com.yuan.system.domain.bo.SysUserRoleBo;
import com.yuan.system.domain.vo.SysUserRoleVo;

import java.util.Collection;
import java.util.List;

/**
 * 用户角色Service接口
 *
 * @author ageerle
 * @date Sun Dec 07 17:25:51 CST 2025
 */
public interface SysUserRoleService {

    /**
     * 查询用户角色
     */
        SysUserRoleVo queryById(Long roleId);

        /**
         * 查询用户角色列表
         */
        TableDataInfo<SysUserRoleVo> queryPageList(SysUserRoleBo bo, PageQuery pageQuery);

    /**
     * 查询用户角色列表
     */
    List<SysUserRoleVo> queryList(SysUserRoleBo bo);

    /**
     * 新增用户角色
     */
    Boolean insertByBo(SysUserRoleBo bo);

    /**
     * 修改用户角色
     */
    Boolean updateByBo(SysUserRoleBo bo);

    /**
     * 校验并批量删除用户角色信息
     */
    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);
}
