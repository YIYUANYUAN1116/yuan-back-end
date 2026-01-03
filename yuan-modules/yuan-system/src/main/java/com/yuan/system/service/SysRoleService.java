package com.yuan.system.service;

import com.yuan.core.page.PageQuery;
import com.yuan.core.page.TableDataInfo;
import com.yuan.system.domain.bo.SysRoleBo;
import com.yuan.system.domain.bo.SysUserBo;
import com.yuan.system.domain.vo.SelectRolesVo;
import com.yuan.system.domain.vo.SysRoleVo;
import com.yuan.system.domain.vo.SysUserVo;

import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
 * 角色Service接口
 *
 
 * @date Sun Dec 07 17:25:44 CST 2025
 */
public interface SysRoleService {

    /**
     * 查询角色
     */
        SysRoleVo queryById(Long roleId);

        /**
         * 查询角色列表
         */
        TableDataInfo<SysRoleVo> queryPageList(SysRoleBo bo, PageQuery pageQuery);

    /**
     * 查询角色列表
     */
    List<SysRoleVo> queryList(SysRoleBo bo);

    /**
     * 新增角色
     */
    Boolean insertByBo(SysRoleBo bo);

    /**
     * 修改角色
     */
    Boolean updateByBo(SysRoleBo bo);

    /**
     * 校验并批量删除角色信息
     */
    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);

    List<SysRoleVo> selectRoleAll();

    boolean checkRoleNameUnique(SysRoleBo role);

    boolean checkRoleKeyUnique(SysRoleBo role);

    List<SysRoleVo> selectRolesByUserId(Long userId);

    SelectRolesVo selectSelectRolesVo(Long userId);

    TableDataInfo<SysUserVo> selectAllocatedUserList(SysUserBo user, PageQuery pageQuery);

    TableDataInfo<SysUserVo> selectUnallocatedUserList(SysUserBo user, PageQuery pageQuery);

    int deleteUsers(Long roleId, Long[] userIds);

    void checkRoleDataScope(Long roleId);

    int insertUsers(Long roleId, Long[] userIds);

    Set<String> selectRolePermissionByUserId(Long userId);

    List<SysRoleVo> selectRoleList(SysRoleBo sysRoleBo);
}
