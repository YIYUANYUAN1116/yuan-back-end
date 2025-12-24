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
import com.yuan.system.domain.bo.SysRoleMenuBo;
import com.yuan.system.domain.vo.SysRoleMenuVo;
import com.yuan.system.service.SysRoleMenuService;
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
 * 角色菜单
 *
 * @author ageerle
 * @date Wed Dec 10 17:21:43 CST 2025
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/system/sysRoleMenu")
@Tag(name = "sysRoleMenuService",description = "角色菜单")
public class SysRoleMenuController extends BaseController {

    private final SysRoleMenuService sysRoleMenuService;

/**
 * 查询角色菜单列表
 */
@SaCheckPermission("system:sysRoleMenu:list")
@GetMapping("/list")
@Operation(summary = "查询角色菜单列表",operationId = "sysRoleMenu_list")
    public TableDataInfo<SysRoleMenuVo> list(SysRoleMenuBo bo, PageQuery pageQuery) {
        return sysRoleMenuService.queryPageList(bo, pageQuery);
    }

    /**
     * 导出角色菜单列表
     */
    @SaCheckPermission("system:sysRoleMenu:export")
    @Log(title = "角色菜单", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @Operation(summary = "导出角色菜单列表",operationId = "sysRoleMenu_export")
    public void export(SysRoleMenuBo bo, HttpServletResponse response) {
        List<SysRoleMenuVo> list = sysRoleMenuService.queryList(bo);
        ExcelUtil.exportExcel(list, "角色菜单", SysRoleMenuVo.class, response);
    }

    /**
     * 获取角色菜单详细信息
     *
     * @param menuId 主键
     */
    @SaCheckPermission("system:sysRoleMenu:query")
    @GetMapping("/{menuId}")
    @Operation(summary = "获取角色菜单详细信息",operationId = "sysRoleMenu_getInfo")
    public R<SysRoleMenuVo> getInfo(@NotNull(message = "主键不能为空")
                                     @PathVariable @PathId Long menuId) {
        return R.ok(sysRoleMenuService.queryById(menuId));
    }

    /**
     * 新增角色菜单
     */
    @SaCheckPermission("system:sysRoleMenu:add")
    @Log(title = "角色菜单", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    @Operation(summary = "新增角色菜单",operationId = "sysRoleMenu_add")
    public R<Void> add(@Validated(AddGroup.class) @RequestBody SysRoleMenuBo bo) {
        return toAjax(sysRoleMenuService.insertByBo(bo));
    }

    /**
     * 修改角色菜单
     */
    @SaCheckPermission("system:sysRoleMenu:edit")
    @Log(title = "角色菜单", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    @Operation(summary = "修改角色菜单",operationId = "sysRoleMenu_edit")
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody SysRoleMenuBo bo) {
        return toAjax(sysRoleMenuService.updateByBo(bo));
    }

    /**
     * 删除角色菜单
     *
     * @param menuIds 主键串
     */
    @SaCheckPermission("system:sysRoleMenu:remove")
    @Log(title = "角色菜单", businessType = BusinessType.DELETE)
    @DeleteMapping("/{menuIds}")
    @Operation(summary = "删除角色菜单",operationId = "sysRoleMenu_remove")
    public R<Void> remove(@NotEmpty(message = "主键不能为空")
                          @PathVariable @PathIds Long[] menuIds) {
        return toAjax(sysRoleMenuService.deleteWithValidByIds(List.of(menuIds), true));
    }
}
