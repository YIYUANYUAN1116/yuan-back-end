package com.yuan.system.service;

import cn.dev33.satoken.secure.BCrypt;
import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.yuan.common.core.constant.SystemConstants;
import com.yuan.common.core.constant.TenantConstants;
import com.yuan.common.core.enums.BooleanEnum;
import com.yuan.system.domain.*;
import com.yuan.system.mapper.*;
import com.yuan.system.utils.UserNameGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.yuan.common.core.constant.Constants.DEFAULT_ORDER_NUM;

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
        boolean exists = userPostMapper.exists(Wrappers.<SysUserPost>lambdaQuery()
                .eq(SysUserPost::getUserId, userId)
                .eq(SysUserPost::getPostId, postId)
                .eq(SysUserPost::getIsPrimary, BooleanEnum.TURE.getValue()));
        if (exists) {
            return;
        }
        //清除原有主岗位
        userPostMapper.update(Wrappers.<SysUserPost>lambdaUpdate()
                .eq(SysUserPost::getUserId, userId)
                .set(SysUserPost::getIsPrimary, BooleanEnum.FALSE.getValue()));

        SysUserPost up = new SysUserPost();
        up.setUserId(userId);
        up.setPostId(postId);
        up.setIsPrimary(true);
        userPostMapper.insert(up);
    }


    private SysDept createRootDept(String tenantId) {

        SysDept sysDept = deptMapper.selectOne(Wrappers.<SysDept>lambdaQuery()
                .eq(SysDept::getTenantId, tenantId)
                .eq(SysDept::getParentId, SystemConstants.ROOT_DEPT_ANCESTORS));
        if (sysDept!=null)return sysDept;

        SysDept dept = new SysDept();
        dept.setTenantId(tenantId);
        dept.setDeptName("总部");
        dept.setParentId(SystemConstants.ROOT_DEPT_ANCESTORS);
        dept.setOrderNum(DEFAULT_ORDER_NUM);
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
        role.setRoleSort(DEFAULT_ORDER_NUM);
        roleMapper.insert(role);
        return role;
    }

    private SysPost createAdminPost(String tenantId,Long deptId) {
        SysPost sysPost = postMapper.selectOne(Wrappers.<SysPost>lambdaQuery()
                .eq(SysPost::getTenantId, tenantId)
                .eq(SysPost::getDeptId, deptId)
                .eq(SysPost::getPostCode, TenantConstants.TENANT_ADMIN_ROLE_KEY));
        if (sysPost != null) return sysPost;

        SysPost post = new SysPost();
        post.setTenantId(tenantId);
        post.setPostName("租户管理员");
        post.setPostCode(TenantConstants.TENANT_ADMIN_ROLE_KEY);
        post.setPostSort(DEFAULT_ORDER_NUM);
        post.setStatus(SystemConstants.NORMAL);
        post.setDeptId(deptId);
        postMapper.insert(post);
        return post;
    }

    private SysUser createAdminUser(SysTenant tenant,Long postId) {
        //todo 生成激活链接给用户，用户激活设置密码
        SysUser user = new SysUser();
        user.setTenantId(tenant.getTenantId());
        user.setUserName(UserNameGenerator.generateUserName(IdUtil.getSnowflakeNextId()));
        user.setPassword("123456");
        user.setPassword(BCrypt.hashpw(user.getPassword()));
        user.setPhonenumber(tenant.getContactPhone());
        user.setStatus(SystemConstants.NORMAL);
        user.setNickName(tenant.getContactUserName());
        user.setPrimaryPostId(postId);
        userMapper.insert(user);
        return user;
    }


    private void bindPostRole(Long postId, Long roleId) {
        boolean exists = rolePostMapper.exists(Wrappers.<SysRolePost>lambdaQuery()
                .eq(SysRolePost::getRoleId, roleId)
                .eq(SysRolePost::getPostId, postId));
        if (exists) {
            return;
        }
        SysRolePost up = new SysRolePost();
        up.setRoleId(roleId);
        up.setPostId(postId);
        rolePostMapper.insert(up);
    }
}
