package com.yuan.system.service.impl;

import com.yuan.system.api.UserQueryApi;
import com.yuan.system.domain.SysDept;
import com.yuan.system.domain.SysUser;
import com.yuan.system.dto.SysUserDTO;
import com.yuan.system.mapper.SysDeptMapper;
import com.yuan.system.mapper.SysUserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserQueryApiImpl implements UserQueryApi {

    private final SysUserMapper userMapper;
    private final SysDeptMapper deptMapper;

    @Override
    public List<Long> findUserIdsByRole(String roleKey, String tenantId) {
        return userMapper.selectUserIdsByRole(roleKey, tenantId);
    }

    @Override
    public List<Long> findUserIdsByDept(Long deptId, String tenantId) {
        return userMapper.selectUserIdsByDept(deptId, tenantId);
    }

    @Override
    public SysUserDTO findUserById(Long userId) {
        return userMapper.selectDtoById(userId);
    }

    @Override
    public Long findLeaderUserId(Long userId) {
        SysUser user = userMapper.selectById(userId);
        if (user == null) return null;
        SysDept dept = deptMapper.selectByUserId(user.getUserId());
        return dept != null ? dept.getLeaderId() : null;
    }
}

