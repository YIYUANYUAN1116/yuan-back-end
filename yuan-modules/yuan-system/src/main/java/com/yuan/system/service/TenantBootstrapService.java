package com.yuan.system.service;

import cn.dev33.satoken.secure.BCrypt;
import com.yuan.common.core.constant.SystemConstants;
import com.yuan.common.core.constant.TenantConstants;
import com.yuan.system.domain.*;
import com.yuan.system.mapper.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TenantBootstrapService {
    private final SysUserMapper userMapper;
    private final SysRoleMapper roleMapper;
    private final SysPostMapper postMapper;
    private final SysDeptMapper deptMapper;
    private final SysUserPostMapper userPostMapper;
    private final SysRolePostMapper rolePostMapper;

    /**
     * 初始化租户数据（部门 + 角色 + 职位 + 管理员）
     */
    public void initTenant(SysTenant tenant) {

        String tenantId = tenant.getTenantId();

        // 1. 创建默认部门
        SysDept dept = createRootDept(tenantId);

        // 2. 创建租户管理员角色
        SysRole adminRole = createTenantAdminRole(tenantId);

        // 3. 创建管理员岗位
        SysPost adminPost = createAdminPost(tenantId,dept.getDeptId());

        // 4. 创建管理员用户
        SysUser adminUser = createAdminUser(tenant,adminPost.getPostId());

        // 5. 绑定关系
        bindPostRole(adminPost.getPostId(), adminRole.getRoleId());
        bindPostUser(adminPost.getPostId(), adminUser.getUserId());
    }

    private void bindPostUser(Long postId, Long userId) {
        SysUserPost up = new SysUserPost();
        up.setUserId(userId);
        up.setPostId(postId);
        up.setIsPrimary(true);
        userPostMapper.insert(up);
    }


    private SysDept createRootDept(String tenantId) {
        SysDept dept = new SysDept();
        dept.setTenantId(tenantId);
        dept.setDeptName("总部");
        dept.setParentId(0L);
        dept.setOrderNum(0);
        dept.setStatus(SystemConstants.NORMAL);
        deptMapper.insert(dept);
        return dept;
    }

    private SysRole createTenantAdminRole(String tenantId) {
        SysRole sysRole =  roleMapper.selectRolesByRoleKey(TenantConstants.TENANT_ADMIN_ROLE_KEY,tenantId);
        if (sysRole != null) return sysRole;
        SysRole role = new SysRole();
        role.setTenantId(tenantId);
        role.setRoleName("租户管理员");
        role.setRoleKey(TenantConstants.TENANT_ADMIN_ROLE_KEY);
        role.setStatus(SystemConstants.NORMAL);
        role.setRemark("租户管理员 "+tenantId);
        role.setRoleSort(0);
        roleMapper.insert(role);
        return role;
    }

    private SysPost createAdminPost(String tenantId,Long deptId) {
        SysPost post = new SysPost();
        post.setTenantId(tenantId);
        post.setPostName("租户管理员");
        post.setPostCode(TenantConstants.TENANT_ADMIN_ROLE_KEY);
        post.setPostSort(0);
        post.setStatus(SystemConstants.NORMAL);
        post.setDeptId(deptId);
        postMapper.insert(post);
        return post;
    }

    private SysUser createAdminUser(SysTenant tenant,Long postId) {
        //todo 生成激活链接给用户，用户激活设置密码
        SysUser user = new SysUser();
        user.setTenantId(tenant.getTenantId());
        user.setUserName(tenant.getContactPhone());
        user.setPassword("123456");
        user.setPassword(BCrypt.hashpw(user.getPassword()));
        user.setPhonenumber(tenant.getContactPhone());
        user.setStatus(SystemConstants.NORMAL);
        user.setNickName(tenant.getCompanyName()+":租户管理员");
        user.setPrimaryPostId(postId);
        userMapper.insert(user);
        return user;
    }


    private void bindPostRole(Long postId, Long roleId) {
        SysRolePost up = new SysRolePost();
        up.setRoleId(roleId);
        up.setPostId(postId);
        rolePostMapper.insert(up);
    }
}
