package com.yuan.system.api;

import com.yuan.system.dto.SysUserDTO;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface UserQueryApi {
    /**
     * 根据角色查询用户ID
     */
    Set<Long> findUserIdsByRoleIds(Set<Long> roleId);

    /**
     * 根据部门查询用户ID
     */
    Set<Long> findUserIdsByDeptIds(Set<Long> deptId);

    /**
     * 查询用户
     */
    SysUserDTO findUserById(Long userId);

    /**
     * 查询发起人上级
     */
    Long findLeaderUserId(Long userId);

    /**
     * 根据岗位查询
     */
    Set<Long> findUserIdsByPostIds(Set<Long> postId);

    Map<Long, String> getUserNameMap(Set<Long> userIds);

    List<SysUserDTO>  queryPageList(SysUserDTO userDTO,Integer page,Integer pageSize);
}
