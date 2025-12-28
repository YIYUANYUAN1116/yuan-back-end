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
import com.yuan.system.domain.bo.SysRoleDeptBo;
import com.yuan.system.domain.vo.SysRoleDeptVo;
import com.yuan.system.service.SysRoleDeptService;
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
 * 部门角色
 *
 
 * @date Wed Dec 10 17:21:37 CST 2025
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/system/sysRoleDept")
@Tag(name = "sysRoleDeptService",description = "部门角色")
public class SysRoleDeptController extends BaseController {

    private final SysRoleDeptService sysRoleDeptService;

/**
 * 查询部门角色列表
 */
@SaCheckPermission("system:sysRoleDept:list")
@GetMapping("/list")
@Operation(summary = "查询部门角色列表",operationId = "sysRoleDept_list")
    public TableDataInfo<SysRoleDeptVo> list(SysRoleDeptBo bo, PageQuery pageQuery) {
        return sysRoleDeptService.queryPageList(bo, pageQuery);
    }

    /**
     * 导出部门角色列表
     */
    @SaCheckPermission("system:sysRoleDept:export")
    @Log(title = "部门角色", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @Operation(summary = "导出部门角色列表",operationId = "sysRoleDept_export")
    public void export(SysRoleDeptBo bo, HttpServletResponse response) {
        List<SysRoleDeptVo> list = sysRoleDeptService.queryList(bo);
        ExcelUtil.exportExcel(list, "部门角色", SysRoleDeptVo.class, response);
    }

    /**
     * 获取部门角色详细信息
     *
     * @param deptId 主键
     */
    @SaCheckPermission("system:sysRoleDept:query")
    @GetMapping("/{deptId}")
    @Operation(summary = "获取部门角色详细信息",operationId = "sysRoleDept_getInfo")
    public R<SysRoleDeptVo> getInfo(@NotNull(message = "主键不能为空")
                                     @PathVariable @PathId Long deptId) {
        return R.ok(sysRoleDeptService.queryById(deptId));
    }

    /**
     * 新增部门角色
     */
    @SaCheckPermission("system:sysRoleDept:add")
    @Log(title = "部门角色", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    @Operation(summary = "新增部门角色",operationId = "sysRoleDept_add")
    public R<Void> add(@Validated(AddGroup.class) @RequestBody SysRoleDeptBo bo) {
        return toAjax(sysRoleDeptService.insertByBo(bo));
    }

    /**
     * 修改部门角色
     */
    @SaCheckPermission("system:sysRoleDept:edit")
    @Log(title = "部门角色", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    @Operation(summary = "修改部门角色",operationId = "sysRoleDept_edit")
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody SysRoleDeptBo bo) {
        return toAjax(sysRoleDeptService.updateByBo(bo));
    }

    /**
     * 删除部门角色
     *
     * @param deptIds 主键串
     */
    @SaCheckPermission("system:sysRoleDept:remove")
    @Log(title = "部门角色", businessType = BusinessType.DELETE)
    @DeleteMapping("/{deptIds}")
    @Operation(summary = "删除部门角色",operationId = "sysRoleDept_remove")
    public R<Void> remove(@NotEmpty(message = "主键不能为空")
                          @PathVariable @PathIds Long[] deptIds) {
        return toAjax(sysRoleDeptService.deleteWithValidByIds(List.of(deptIds), true));
    }
}
