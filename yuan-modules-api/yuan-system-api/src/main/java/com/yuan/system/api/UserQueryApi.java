package com.yuan.system.api;

import com.yuan.system.dto.SysUserDTO;

import java.util.List;

public interface UserQueryApi {
    /**
     * 根据角色查询用户ID
     */
    List<Long> findUserIdsByRole(String roleKey, String tenantId);

    /**
     * 根据部门查询用户ID
     */
    List<Long> findUserIdsByDept(Long deptId, String tenantId);

    /**
     * 查询用户
     */
    SysUserDTO findUserById(Long userId);

    /**
     * 查询发起人上级
     */
    Long findLeaderUserId(Long userId);
}
