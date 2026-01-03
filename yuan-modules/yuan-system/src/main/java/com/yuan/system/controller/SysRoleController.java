package com.yuan.system.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.yuan.common.core.domain.R;
import com.yuan.common.core.validate.AddGroup;
import com.yuan.common.core.validate.EditGroup;
import com.yuan.common.doc.annotation.PathId;
import com.yuan.common.doc.annotation.PathIds;
import com.yuan.common.excel.utils.ExcelUtil;
import com.yuan.common.idempotent.annotation.RepeatSubmit;
import com.yuan.common.log.annotation.Log;
import com.yuan.common.log.enums.BusinessType;
import com.yuan.common.web.core.BaseController;
import com.yuan.core.page.PageQuery;
import com.yuan.core.page.TableDataInfo;
import com.yuan.system.domain.bo.SysRoleBo;
import com.yuan.system.domain.bo.SysUserBo;
import com.yuan.system.domain.vo.SelectRolesVo;
import com.yuan.system.domain.vo.SysRoleVo;
import com.yuan.system.domain.vo.SysUserVo;
import com.yuan.system.service.SysRoleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 角色
 *
 
 * @date Sun Dec 07 17:25:44 CST 2025
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/system/sysRole")
@Tag(name = "SysRoleController",description = "角色")
public class SysRoleController extends BaseController {

    private final SysRoleService sysRoleService;

    /**
     * 查询角色列表
     */
    @SaCheckPermission("system:sysRole:list")
    @GetMapping("/list")
    @Operation(summary = "查询角色列表",operationId = "sysRole_list")
    public TableDataInfo<SysRoleVo> list(SysRoleBo bo, PageQuery pageQuery) {
        return sysRoleService.queryPageList(bo, pageQuery);
    }

    /**
     * 导出角色列表
     */
    @SaCheckPermission("system:sysRole:export")
    @Log(title = "角色", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @Operation(summary = "导出角色列表",operationId = "sysRole_export")
    public void export(SysRoleBo bo, HttpServletResponse response) {
        List<SysRoleVo> list = sysRoleService.queryList(bo);
        ExcelUtil.exportExcel(list, "角色", SysRoleVo.class, response);
    }

    /**
     * 获取角色详细信息
     *
     * @param roleId 主键
     */
    @SaCheckPermission("system:sysRole:query")
    @GetMapping("/{roleId}")
    @Operation(summary = "获取角色详细信息",operationId = "sysRole_getInfo")
    public R<SysRoleVo> getInfo(@NotNull(message = "主键不能为空")
                                @PathVariable @PathId Long roleId) {
        return R.ok(sysRoleService.queryById(roleId));
    }

    /**
     * 新增角色
     */
    @SaCheckPermission("system:sysRole:add")
    @Log(title = "角色", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    @Operation(summary = "新增角色",operationId = "sysRole_add")
    public R<Void> add(@Validated(AddGroup.class) @RequestBody SysRoleBo bo) {
        if (!sysRoleService.checkRoleNameUnique(bo)) {
            return R.fail("新增角色'" + bo.getRoleName() + "'失败，角色名称已存在");
        } else if (!sysRoleService.checkRoleKeyUnique(bo)) {
            return R.fail("新增角色'" + bo.getRoleName() + "'失败，角色权限已存在");
        }
        return toAjax(sysRoleService.insertByBo(bo));
    }

    /**
     * 修改角色
     */
    @SaCheckPermission("system:sysRole:edit")
    @Log(title = "角色", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    @Operation(summary = "修改角色",operationId = "sysRole_edit")
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody SysRoleBo bo) {
        if (!sysRoleService.checkRoleNameUnique(bo)) {
            return R.fail("修改角色'" + bo.getRoleName() + "'失败，角色名称已存在");
        } else if (!sysRoleService.checkRoleKeyUnique(bo)) {
            return R.fail("修改角色'" + bo.getRoleName() + "'失败，角色权限已存在");
        }
        return toAjax(sysRoleService.updateByBo(bo));
    }

    /**
     * 删除角色
     *
     * @param roleIds 主键串
     */
    @SaCheckPermission("system:sysRole:remove")
    @Log(title = "角色", businessType = BusinessType.DELETE)
    @DeleteMapping("/{roleIds}")
    @Operation(summary = "删除角色",operationId = "sysRole_remove")
    public R<Void> remove(@NotEmpty(message = "主键不能为空")
                          @PathVariable @PathIds Long[] roleIds) {
        return toAjax(sysRoleService.deleteWithValidByIds(List.of(roleIds), true));
    }

    /**
     * 获取角色选择框列表
     */
    @SaCheckPermission("system:role:query")
    @GetMapping("/optionselect")
    @Operation(summary = "获取角色选择框列表",operationId = "sysRoleOptionselect")
    public R<SelectRolesVo> optionSelect(@RequestParam @PathId Long userId) {
        return R.ok(sysRoleService.selectSelectRolesVo(userId));
    }

    /**
     * 查询已分配用户角色列表
     */
    @SaCheckPermission("system:role:list")
    @GetMapping("/allocatedList")
    @Operation(summary = "获取角色已分配用户列表",operationId = "roleAllocatedUserList")
    public TableDataInfo<SysUserVo> allocatedUserList(SysUserBo bo, PageQuery pageQuery) {
        return sysRoleService.selectAllocatedUserList(bo, pageQuery);
    }

    /**
     * 查询未分配用户角色列表
     */
    @SaCheckPermission("system:role:list")
    @GetMapping("/unallocatedList")
    @Operation(summary = "获取角色未分配用户列表",operationId = "roleUnallocatedUserList")
    public TableDataInfo<SysUserVo> unallocatedUserList(SysUserBo bo, PageQuery pageQuery) {
        return sysRoleService.selectUnallocatedUserList(bo, pageQuery);
    }

    /**
     * 批量取消授权用户
     *
     * @param roleId  角色ID
     * @param userIds 用户ID串
     */
    @SaCheckPermission("system:role:edit")
    @Log(title = "角色管理", businessType = BusinessType.GRANT)
    @PutMapping("/cancelAll")
    @Operation(summary = "批量取消授权用户",operationId = "roleCancelUserAll")
    public R<Void> cancelUserAll(@PathId Long roleId, @PathIds Long[] userIds) {
        return toAjax(sysRoleService.deleteUsers(roleId, userIds));
    }

    /**
     * 批量选择用户授权
     *
     * @param roleId  角色ID
     * @param userIds 用户ID串
     */
    @SaCheckPermission("system:role:edit")
    @Log(title = "角色管理", businessType = BusinessType.GRANT)
    @PutMapping("/selectAll")
    @Operation(summary = "批量选择用户授权",operationId = "roleSelectUserAll")
    public R<Void> selectUserAll(@PathId Long roleId, @PathIds Long[] userIds) {
        sysRoleService.checkRoleDataScope(roleId);
        return toAjax(sysRoleService.insertUsers(roleId, userIds));
    }

}
