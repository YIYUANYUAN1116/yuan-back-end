package com.yuan.system.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.dev33.satoken.secure.BCrypt;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.ObjectUtil;
import com.yuan.common.core.domain.R;
import com.yuan.common.core.domain.model.LoginUser;
import com.yuan.common.core.utils.StreamUtils;
import com.yuan.common.core.utils.StringUtils;
import com.yuan.common.core.validate.AddGroup;
import com.yuan.common.core.validate.EditGroup;
import com.yuan.common.excel.utils.ExcelUtil;
import com.yuan.common.idempotent.annotation.RepeatSubmit;
import com.yuan.common.log.annotation.Log;
import com.yuan.common.log.enums.BusinessType;
import com.yuan.common.satoken.utils.LoginHelper;
import com.yuan.common.web.core.BaseController;
import com.yuan.core.page.PageQuery;
import com.yuan.core.page.TableDataInfo;
import com.yuan.system.domain.bo.SysUserBo;
import com.yuan.system.domain.vo.SysRoleVo;
import com.yuan.system.domain.vo.SysUserInfoVo;
import com.yuan.system.domain.vo.SysUserVo;
import com.yuan.system.domain.vo.UserInfoVo;
import com.yuan.system.service.SysRoleService;
import com.yuan.system.service.SysUserService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 用户
 *
 * @author ageerle
 * @date Sun Dec 07 17:25:38 CST 2025
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/system/sysUser")
public class SysUserController extends BaseController {

    private final SysUserService sysUserService;
    private final SysRoleService sysRoleService;

    /**
     * 查询用户列表
     */
    @SaCheckPermission("system:sysUser:list")
    @GetMapping("/list")
    public TableDataInfo<SysUserVo> list(SysUserBo bo, PageQuery pageQuery) {
        return sysUserService.queryPageList(bo, pageQuery);
    }

    /**
     * 导出用户列表
     */
    @SaCheckPermission("system:sysUser:export")
    @Log(title = "用户", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(SysUserBo bo, HttpServletResponse response) {
        List<SysUserVo> list = sysUserService.queryList(bo);
        ExcelUtil.exportExcel(list, "用户", SysUserVo.class, response);
    }

    /**
     * 获取用户详细信息
     *
     * @param userId 主键
     */
    @SaCheckPermission("system:sysUser:query")
    @GetMapping("/{userId}")
    public R<SysUserInfoVo> getInfo(@NotNull(message = "主键不能为空")
                                     @PathVariable Long userId) {

        SysUserInfoVo userInfoVo = new SysUserInfoVo();
        List<SysRoleVo> roles = sysRoleService.selectRoleAll();
        userInfoVo.setRoles(LoginHelper.isSuperAdmin(userId) ? roles : StreamUtils.filter(roles, r -> !r.isSuperAdmin()));
        if (ObjectUtil.isNotNull(userId)) {
            SysUserVo sysUser = sysUserService.queryById(userId);
            userInfoVo.setUser(sysUser);
            userInfoVo.setRoleIds(StreamUtils.toList(sysUser.getRoles(), SysRoleVo::getRoleId));
        }
        return R.ok(userInfoVo);
    }

    /**
     * 新增用户
     */
    @SaCheckPermission("system:sysUser:add")
    @Log(title = "用户", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public R<Void> add(@Validated(AddGroup.class) @RequestBody SysUserBo user) {
        if (!sysUserService.checkUserNameUnique(user)) {
            return R.fail("新增用户'" + user.getUserName() + "'失败，登录账号已存在");
        } else if (StringUtils.isNotEmpty(user.getPhonenumber()) && !sysUserService.checkPhoneUnique(user)) {
            return R.fail("新增用户'" + user.getUserName() + "'失败，手机号码已存在");
        } else if (StringUtils.isNotEmpty(user.getEmail()) && !sysUserService.checkEmailUnique(user)) {
            return R.fail("新增用户'" + user.getUserName() + "'失败，邮箱账号已存在");
        }
//        if (TenantHelper.isEnable()) {
//            if (!tenantService.checkAccountBalance(TenantHelper.getTenantId())) {
//                return R.fail("当前租户下用户名额不足，请联系管理员");
//            }
//        }
        if(StringUtils.isEmpty(user.getPassword())){
            user.setPassword("123456");
        }
        if(StringUtils.isEmpty(user.getNickName())){
            user.setNickName(user.getUserName());
        }
        user.setDeptId(103L);
        user.setPassword(BCrypt.hashpw(user.getPassword()));
        return toAjax(sysUserService.insertByBo(user));
    }

    /**
     * 修改用户
     */
    @SaCheckPermission("system:sysUser:edit")
    @Log(title = "用户", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody SysUserBo user) {
        if (!sysUserService.checkUserNameUnique(user)) {
            return R.fail("修改用户'" + user.getUserName() + "'失败，登录账号已存在");
        } else if (StringUtils.isNotEmpty(user.getPhonenumber()) && !sysUserService.checkPhoneUnique(user)) {
            return R.fail("修改用户'" + user.getUserName() + "'失败，手机号码已存在");
        } else if (StringUtils.isNotEmpty(user.getEmail()) && !sysUserService.checkEmailUnique(user)) {
            return R.fail("修改用户'" + user.getUserName() + "'失败，邮箱账号已存在");
        }
        return toAjax(sysUserService.updateByBo(user));
    }

    /**
     * 删除用户
     *
     * @param userIds 主键串
     */
    @SaCheckPermission("system:sysUser:remove")
    @Log(title = "用户", businessType = BusinessType.DELETE)
    @DeleteMapping("/{userIds}")
    public R<Void> remove(@NotEmpty(message = "主键不能为空")
                          @PathVariable Long[] userIds) {
        if (ArrayUtil.contains(userIds, LoginHelper.getUserId())) {
            return R.fail("当前用户不能删除");
        }
        return toAjax(sysUserService.deleteWithValidByIds(List.of(userIds), true));
    }

    /**
     * 用户授权角色
     *
     * @param userId  用户Id
     * @param roleIds 角色ID串
     */
    @SaCheckPermission("system:user:edit")
    @Log(title = "用户管理", businessType = BusinessType.GRANT)
    @PutMapping("/authRole")
    public R<Void> insertAuthRole(Long userId, Long[] roleIds) {
        sysUserService.insertUserAuth(userId, roleIds);
        return R.ok();
    }

    /**
     * 获取用户详细信息
     *
     */
    @GetMapping("/getInfo")
    public R<UserInfoVo> getCurrentInfo() {
        UserInfoVo userInfoVo = new UserInfoVo();
        LoginUser loginUser = LoginHelper.getLoginUser();
        SysUserVo sysUserVo = sysUserService.queryById(loginUser.getUserId());
        userInfoVo.setUser(sysUserVo);
        userInfoVo.setPermissions(loginUser.getMenuPermission());
        userInfoVo.setRoles(loginUser.getRolePermission());
        return R.ok(userInfoVo);
    }
}
