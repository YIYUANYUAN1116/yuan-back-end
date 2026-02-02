package com.yuan.system.service.api;

import com.yuan.common.core.utils.StreamUtils;
import com.yuan.core.page.PageQuery;
import com.yuan.system.api.UserQueryApi;
import com.yuan.system.domain.SysDept;
import com.yuan.system.domain.SysUser;
import com.yuan.system.dto.SysUserDTO;
import com.yuan.system.mapper.SysDeptMapper;
import com.yuan.system.mapper.SysUserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserQueryApiImpl implements UserQueryApi {

    private final SysUserMapper userMapper;
    private final SysDeptMapper deptMapper;

    @Override
    public Set<Long> findUserIdsByRoleIds(Set<Long> roleId) {
        return userMapper.selectUserIdsByRoleIds(roleId);
    }

    @Override
    public Set<Long> findUserIdsByDeptIds(Set<Long> deptId) {
        return userMapper.selectUserIdsByDeptIds(deptId);
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

    @Override
    public Set<Long> findUserIdsByPostIds(Set<Long> postId) {
        return userMapper.selectUserIdsByPostIds(postId);
    }

    @Override
    public Map<Long, String> getUserNameMap(Set<Long> userIds) {
        List<SysUser> sysUsers = userMapper.selectByIds(userIds);
        return StreamUtils.toMap(sysUsers,SysUser::getUserId,SysUser::getNickName);
    }

    @Override
    public List<SysUserDTO> queryPageList(SysUserDTO userDTO, Integer page, Integer pageSize) {
        if (page == null || page < 1) page = 1;
        if (pageSize == null || pageSize < 1) pageSize = 10;
        PageQuery pageQuery = new PageQuery(pageSize,page);

        return userMapper.selectPageDto(pageQuery.build(),userDTO);
    }
}

