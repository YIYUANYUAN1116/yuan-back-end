package com.yuan.system.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.dev33.satoken.annotation.SaCheckRole;
import cn.dev33.satoken.annotation.SaMode;
import com.yuan.common.core.constant.TenantConstants;
import com.yuan.common.core.domain.R;
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
import com.yuan.system.domain.bo.SysMenuBo;
import com.yuan.system.domain.vo.MenuTreeSelectVo;
import com.yuan.system.domain.vo.SysMenuVo;
import com.yuan.system.service.SysMenuService;
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
 * 菜单
 *
 * @author ageerle
 * @date Wed Dec 10 17:20:04 CST 2025
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/system/sysMenu")
@Tag(name = "SysMenuController", description = "菜单")
public class SysMenuController extends BaseController {

    private final SysMenuService sysMenuService;

    /**
     * 查询菜单列表
     */
    @SaCheckPermission("system:sysMenu:list")
    @GetMapping("/list")
    @Operation(summary = "查询菜单列表", operationId = "sysMenu_list")
    public TableDataInfo<SysMenuVo> list(SysMenuBo bo, PageQuery pageQuery) {
        return sysMenuService.queryPageList(bo, pageQuery);
    }

    /**
     * 导出菜单列表
     */
    @SaCheckPermission("system:sysMenu:export")
    @Log(title = "菜单", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @Operation(summary = "导出菜单列表", operationId = "sysMenu_export")
    public void export(SysMenuBo bo, HttpServletResponse response) {
        List<SysMenuVo> list = sysMenuService.queryList(bo);
        ExcelUtil.exportExcel(list, "菜单", SysMenuVo.class, response);
    }

    /**
     * 获取菜单详细信息
     *
     * @param menuId 主键
     */
    @SaCheckPermission("system:sysMenu:query")
    @GetMapping("/{menuId}")
    @Operation(summary = "获取菜单详细信息", operationId = "sysMenu_getInfo")
    public R<SysMenuVo> getInfo(@NotNull(message = "主键不能为空")
                                @PathVariable Long menuId) {
        return R.ok(sysMenuService.queryById(menuId));
    }

    /**
     * 新增菜单
     */
    @SaCheckPermission("system:sysMenu:add")
    @Log(title = "菜单", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    @Operation(summary = "新增菜单", operationId = "sysMenu_add")
    public R<Void> add(@Validated(AddGroup.class) @RequestBody SysMenuBo bo) {
        return toAjax(sysMenuService.insertByBo(bo));
    }

    /**
     * 修改菜单
     */
    @SaCheckPermission("system:sysMenu:edit")
    @Log(title = "菜单", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    @Operation(summary = "修改菜单", operationId = "sysMenu_edit")
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody SysMenuBo bo) {
        return toAjax(sysMenuService.updateByBo(bo));
    }

    /**
     * 删除菜单
     *
     * @param menuIds 主键串
     */
    @SaCheckPermission("system:sysMenu:remove")
    @Log(title = "菜单", businessType = BusinessType.DELETE)
    @DeleteMapping("/{menuIds}")
    @Operation(summary = "删除菜单", operationId = "sysMenu_remove")
    public R<Void> remove(@NotEmpty(message = "主键不能为空")
                          @PathVariable Long[] menuIds) {
        return toAjax(sysMenuService.deleteWithValidByIds(List.of(menuIds), true));
    }

    /**
     * 获取菜单下拉树列表
     */
    @SaCheckRole(value = {
            TenantConstants.SUPER_ADMIN_ROLE_KEY,
            TenantConstants.TENANT_ADMIN_ROLE_KEY
    }, mode = SaMode.OR)
    @SaCheckPermission("system:menu:query")
    @GetMapping("/treeselect")
    @Operation(summary = "获取菜单下拉树列表", operationId = "sysMenu_treeselect")
    public R<MenuTreeSelectVo> treeselect(SysMenuBo bo, Long roleId) {
        List<SysMenuVo> menus = sysMenuService.selectMenuList(bo, LoginHelper.getUserId());
        MenuTreeSelectVo selectVo = new MenuTreeSelectVo();
        selectVo.setMenus(sysMenuService.buildMenuTreeSelect(menus));
        selectVo.setCheckedKeys(sysMenuService.selectMenuListByRoleId(roleId));
        return R.ok(selectVo);
    }

    @SaCheckRole(value = {
            TenantConstants.SUPER_ADMIN_ROLE_KEY,
            TenantConstants.TENANT_ADMIN_ROLE_KEY
    }, mode = SaMode.OR)
    @SaCheckPermission("system:menu:query")
    @GetMapping(value = "/roleMenuTreeselect/{roleId}")
    @Operation(summary = "获取角色菜单树", operationId = "sysMenu_roleMenuTreeselect")
    public R<MenuTreeSelectVo> roleMenuTreeselect(@PathVariable("roleId") Long roleId) {
        List<SysMenuVo> menus = sysMenuService.selectMenuList(LoginHelper.getUserId());
        MenuTreeSelectVo selectVo = new MenuTreeSelectVo();
        selectVo.setCheckedKeys(sysMenuService.selectMenuListByRoleId(roleId));
        selectVo.setMenus(sysMenuService.buildMenuTreeSelect(menus));
        return R.ok(selectVo);
    }

    /**
     * 查询树型菜单列表
     */
    @SaCheckPermission("system:sysMenu:list")
    @GetMapping("/listTree")
    @Operation(summary = "查询树型菜单列表", operationId = "sysMenu_listTree")
    public R<List<SysMenuVo>> listTree(SysMenuBo bo) {
        List<SysMenuVo> sysMenuVos = sysMenuService.listTree(bo);
        return R.ok(sysMenuVos);
    }
}
