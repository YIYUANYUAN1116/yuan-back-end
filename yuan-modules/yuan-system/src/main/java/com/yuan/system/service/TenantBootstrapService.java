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
    private final SysUserRoleMapper userRoleMapper;
    private final SysUserPostMapper userPostMapper;

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
        SysPost adminPost = createAdminPost(tenantId);

        // 4. 创建管理员用户
        SysUser adminUser = createAdminUser(tenant,dept.getDeptId());

        // 5. 绑定关系
        bindUserRole(adminUser.getUserId(), adminRole.getRoleId());
        bindUserPost(adminUser.getUserId(), adminPost.getPostId());
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

    private SysPost createAdminPost(String tenantId) {
        SysPost post = new SysPost();
        post.setTenantId(tenantId);
        post.setPostName("系统管理员");
        post.setPostCode(TenantConstants.TENANT_ADMIN_ROLE_KEY);
        post.setPostSort(0);
        post.setStatus(SystemConstants.NORMAL);
        postMapper.insert(post);
        return post;
    }

    private SysUser createAdminUser(SysTenant tenant,Long deptId) {
        //todo 生成激活链接给用户，用户激活设置密码
        SysUser user = new SysUser();
        user.setTenantId(tenant.getTenantId());
        user.setUserName(tenant.getContactPhone());
        user.setPassword("123456");
        user.setPassword(BCrypt.hashpw(user.getPassword()));
        user.setPhonenumber(tenant.getContactPhone());
        user.setStatus(SystemConstants.NORMAL);
        user.setNickName(tenant.getCompanyName()+":租户管理员");

        userMapper.insert(user);
        return user;
    }

    private void bindUserRole(Long userId, Long roleId) {
        SysUserRole ur = new SysUserRole();
        ur.setUserId(userId);
        ur.setRoleId(roleId);
        userRoleMapper.insert(ur);
    }

    private void bindUserPost(Long userId, Long postId) {
        SysUserPost up = new SysUserPost();
        up.setUserId(userId);
        up.setPostId(postId);
        userPostMapper.insert(up);
    }
}
