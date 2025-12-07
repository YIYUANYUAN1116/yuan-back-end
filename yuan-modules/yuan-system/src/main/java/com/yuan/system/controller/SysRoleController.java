package com.yuan.system.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.yuan.common.core.domain.R;
import com.yuan.common.core.validate.AddGroup;
import com.yuan.common.core.validate.EditGroup;
import com.yuan.common.excel.utils.ExcelUtil;
import com.yuan.common.idempotent.annotation.RepeatSubmit;
import com.yuan.common.log.annotation.Log;
import com.yuan.common.log.enums.BusinessType;
import com.yuan.common.web.core.BaseController;
import com.yuan.core.page.PageQuery;
import com.yuan.core.page.TableDataInfo;
import com.yuan.system.domain.bo.SysRoleBo;
import com.yuan.system.domain.vo.SysRoleVo;
import com.yuan.system.service.SysRoleService;
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
 * @author ageerle
 * @date Sun Dec 07 17:25:44 CST 2025
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/system/sysRole")
public class SysRoleController extends BaseController {

    private final SysRoleService sysRoleService;

    /**
     * 查询角色列表
     */
    @SaCheckPermission("system:sysRole:list")
    @GetMapping("/list")
    public TableDataInfo<SysRoleVo> list(SysRoleBo bo, PageQuery pageQuery) {
        return sysRoleService.queryPageList(bo, pageQuery);
    }

    /**
     * 导出角色列表
     */
    @SaCheckPermission("system:sysRole:export")
    @Log(title = "角色", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
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
    public R<SysRoleVo> getInfo(@NotNull(message = "主键不能为空")
                                @PathVariable Long roleId) {
        return R.ok(sysRoleService.queryById(roleId));
    }

    /**
     * 新增角色
     */
    @SaCheckPermission("system:sysRole:add")
    @Log(title = "角色", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public R<Void> add(@Validated(AddGroup.class) @RequestBody SysRoleBo role) {
        if (!sysRoleService.checkRoleNameUnique(role)) {
            return R.fail("新增角色'" + role.getRoleName() + "'失败，角色名称已存在");
        } else if (!sysRoleService.checkRoleKeyUnique(role)) {
            return R.fail("新增角色'" + role.getRoleName() + "'失败，角色权限已存在");
        }
        return toAjax(sysRoleService.insertByBo(role));
    }

    /**
     * 修改角色
     */
    @SaCheckPermission("system:sysRole:edit")
    @Log(title = "角色", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody SysRoleBo role) {
        if (!sysRoleService.checkRoleNameUnique(role)) {
            return R.fail("新增角色'" + role.getRoleName() + "'失败，角色名称已存在");
        } else if (!sysRoleService.checkRoleKeyUnique(role)) {
            return R.fail("新增角色'" + role.getRoleName() + "'失败，角色权限已存在");
        }
        return toAjax(sysRoleService.updateByBo(role));
    }

    /**
     * 删除角色
     *
     * @param roleIds 主键串
     */
    @SaCheckPermission("system:sysRole:remove")
    @Log(title = "角色", businessType = BusinessType.DELETE)
    @DeleteMapping("/{roleIds}")
    public R<Void> remove(@NotEmpty(message = "主键不能为空")
                          @PathVariable Long[] roleIds) {
        return toAjax(sysRoleService.deleteWithValidByIds(List.of(roleIds), true));
    }
}
