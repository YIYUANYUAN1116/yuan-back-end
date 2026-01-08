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
    @SaCheckPermission("system:role:list")
    @GetMapping("/list")
    @Operation(summary = "查询角色列表",operationId = "sysRole_list")
    public TableDataInfo<SysRoleVo> list(SysRoleBo bo, PageQuery pageQuery) {
        return sysRoleService.queryPageList(bo, pageQuery);
    }

    /**
     * 导出角色列表
     */
    @SaCheckPermission("system:role:export")
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
    @SaCheckPermission("system:role:query")
    @GetMapping("/{roleId}")
    @Operation(summary = "获取角色详细信息",operationId = "sysRole_getInfo")
    public R<SysRoleVo> getInfo(@NotNull(message = "主键不能为空")
                                @PathVariable @PathId Long roleId) {
        return R.ok(sysRoleService.queryById(roleId));
    }

    /**
     * 新增角色
     */
    @SaCheckPermission("system:role:add")
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
    @SaCheckPermission("system:role:edit")
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
    @SaCheckPermission("system:role:remove")
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
    @GetMapping("/postRole")
    @Operation(summary = "获取角色选择框列表",operationId = "sysRolePostSelect")
    public R<SelectRolesVo> rolePostSelect(@RequestParam @PathId Long postId) {
        return R.ok(sysRoleService.selectPostRolesVo(postId));
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

}
