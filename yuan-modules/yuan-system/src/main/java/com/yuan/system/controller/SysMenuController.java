package com.yuan.system.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.dev33.satoken.annotation.SaCheckRole;
import cn.dev33.satoken.annotation.SaMode;
import com.yuan.common.core.constant.TenantConstants;
import com.yuan.common.core.domain.R;
import com.yuan.common.core.validate.AddGroup;
import com.yuan.common.core.validate.EditGroup;
import com.yuan.common.doc.annotation.PathId;
import com.yuan.common.doc.annotation.PathIds;
import com.yuan.common.excel.utils.ExcelUtil;
import com.yuan.common.idempotent.annotation.RepeatSubmit;
import com.yuan.common.log.annotation.Log;
import com.yuan.common.log.enums.BusinessType;
import com.yuan.common.satoken.utils.LoginHelper;
import com.yuan.common.web.core.BaseController;
import com.yuan.core.page.PageQuery;
import com.yuan.core.page.TableDataInfo;
import com.yuan.system.domain.SysMenu;
import com.yuan.system.domain.bo.SysMenuBo;
import com.yuan.system.domain.vo.ReactRouterVo;
import com.yuan.system.domain.vo.SysMenuVo;
import com.yuan.system.domain.vo.TreeSelectVo;
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
 
 * @date Wed Dec 10 17:20:04 CST 2025
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/system/sysMenu")
@Tag(name = "SysMenuController", description = "菜单")
public class SysMenuController extends BaseController {

    private final SysMenuService menuService;

    /**
     * 查询菜单列表
     */
    @SaCheckPermission("system:menu:list")
    @GetMapping("/list")
    @Operation(summary = "查询菜单列表", operationId = "sysMenu_list")
    public TableDataInfo<SysMenuVo> list(SysMenuBo bo, PageQuery pageQuery) {
        return menuService.queryPageList(bo, pageQuery);
    }

    /**
     * 导出菜单列表
     */
    @SaCheckPermission("system:menu:export")
    @Log(title = "菜单", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @Operation(summary = "导出菜单列表", operationId = "sysMenu_export")
    public void export(SysMenuBo bo, HttpServletResponse response) {
        List<SysMenuVo> list = menuService.queryList(bo);
        ExcelUtil.exportExcel(list, "菜单", SysMenuVo.class, response);
    }

    /**
     * 获取菜单详细信息
     *
     * @param menuId 主键
     */
    @SaCheckPermission("system:menu:query")
    @GetMapping("/{menuId}")
    @Operation(summary = "获取菜单详细信息", operationId = "sysMenu_getInfo")
    public R<SysMenuVo> getInfo(@NotNull(message = "主键不能为空")
                                @PathVariable @PathId Long menuId) {
        return R.ok(menuService.queryById(menuId));
    }

    /**
     * 新增菜单
     */
    @SaCheckPermission("system:menu:add")
    @Log(title = "菜单", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    @Operation(summary = "新增菜单", operationId = "sysMenu_add")
    public R<Void> add(@Validated(AddGroup.class) @RequestBody SysMenuBo bo) {
        return toAjax(menuService.insertByBo(bo));
    }

    /**
     * 修改菜单
     */
    @SaCheckPermission("system:menu:edit")
    @Log(title = "菜单", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    @Operation(summary = "修改菜单", operationId = "sysMenu_edit")
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody SysMenuBo bo) {
        return toAjax(menuService.updateByBo(bo));
    }

    /**
     * 删除菜单
     *
     * @param menuIds 主键串
     */
    @SaCheckPermission("system:menu:remove")
    @Log(title = "菜单", businessType = BusinessType.DELETE)
    @DeleteMapping("/{menuIds}")
    @Operation(summary = "删除菜单", operationId = "sysMenu_remove")
    public R<Void> remove(@NotEmpty(message = "主键不能为空")
                          @PathVariable @PathIds Long[] menuIds) {
        return toAjax(menuService.deleteWithValidByIds(List.of(menuIds), true));
    }

    /**
     * 获取菜单下拉树列表
     */
    @SaCheckRole(value = {
            TenantConstants.SUPER_ADMIN_ROLE_KEY,
            TenantConstants.TENANT_ADMIN_ROLE_KEY
    }, mode = SaMode.OR)
    @SaCheckPermission("system:menu:list")
    @GetMapping("/treeselect")
    @Operation(summary = "获取菜单下拉树列表", operationId = "sysMenu_treeselect")
    public R<TreeSelectVo> treeselect(SysMenuBo bo, @PathId Long roleId) {
        List<SysMenuVo> menus = menuService.selectMenuList(bo, LoginHelper.getUserId());
        TreeSelectVo selectVo = new TreeSelectVo();
        selectVo.setTreeList(menuService.buildMenuTreeSelect(menus));
        selectVo.setCheckedKeys(menuService.selectMenuListByRoleId(roleId));
        return R.ok(selectVo);
    }

    @SaCheckRole(value = {
            TenantConstants.SUPER_ADMIN_ROLE_KEY,
            TenantConstants.TENANT_ADMIN_ROLE_KEY
    }, mode = SaMode.OR)
    @SaCheckPermission("system:menu:list")
    @GetMapping(value = "/roleMenuTreeselect/{roleId}")
    @Operation(summary = "获取角色菜单树", operationId = "sysMenu_roleMenuTreeselect")
    public R<TreeSelectVo> roleMenuTreeselect(@PathVariable("roleId") @PathId Long roleId) {
        List<SysMenuVo> menus = menuService.selectMenuList(LoginHelper.getUserId());
        TreeSelectVo selectVo = new TreeSelectVo();
        selectVo.setCheckedKeys(menuService.selectMenuListByRoleId(roleId));
        selectVo.setTreeList(menuService.buildMenuTreeSelect(menus));
        return R.ok(selectVo);
    }

    /**
     * 查询树型菜单列表
     */
    @SaCheckPermission("system:menu:list")
    @GetMapping("/listTree")
    @Operation(summary = "查询树型菜单列表", operationId = "sysMenu_listTree")
    public TableDataInfo<SysMenuVo> listTree(SysMenuBo bo) {
        List<SysMenuVo> sysMenuVos = menuService.listTree(bo);
        return TableDataInfo.build(sysMenuVos);
    }

    /**
     * 获取路由信息
     *
     * @return 路由信息
     */
    @GetMapping("/menuRouters")
    @Operation(summary = "获取菜单路由", operationId = "menuRouters")
    public R<List<ReactRouterVo>> menuRouters() {
        List<SysMenu> menus = menuService.selectMenuTreeByUserId(LoginHelper.getUserId());
        return R.ok(menuService.buildRouterVos(menus));
    }
}
