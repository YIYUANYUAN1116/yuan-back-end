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
import com.yuan.system.domain.bo.SysRolePostBo;
import com.yuan.system.domain.vo.SysRolePostVo;
import com.yuan.system.service.SysRolePostService;
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
 * sys_role_post
 *
 * @author yuan
 * @date Mon Jan 05 20:10:39 CST 2026
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/system/sysRolePost")
@Tag(name = "SysRolePostController",description = "sys_role_post")
public class SysRolePostController extends BaseController {

    private final SysRolePostService sysRolePostService;

/**
 * 查询sys_role_post列表
 */
@SaCheckPermission("system:sysRolePost:list")
@GetMapping("/list")
@Operation(summary = "查询sys_role_post列表",operationId = "SysRolePost_list")
    public TableDataInfo<SysRolePostVo> list(SysRolePostBo bo, PageQuery pageQuery) {
        return sysRolePostService.queryPageList(bo, pageQuery);
    }

    /**
     * 导出sys_role_post列表
     */
    @SaCheckPermission("system:sysRolePost:export")
    @Log(title = "sys_role_post", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @Operation(summary = "导出sys_role_post列表",operationId = "SysRolePost_export")
    public void export(SysRolePostBo bo, HttpServletResponse response) {
        List<SysRolePostVo> list = sysRolePostService.queryList(bo);
        ExcelUtil.exportExcel(list, "sys_role_post", SysRolePostVo.class, response);
    }

    /**
     * 获取sys_role_post详细信息
     *
     * @param roleId 主键
     */
    @SaCheckPermission("system:sysRolePost:query")
    @GetMapping("/{roleId}")
    @Operation(summary = "获取sys_role_post详细信息",operationId = "SysRolePost_getInfo")
    public R<SysRolePostVo> getInfo(@NotNull(message = "主键不能为空")
                                         @PathId @PathVariable Long roleId) {
        return R.ok(sysRolePostService.queryById(roleId));
    }

    /**
     * 新增sys_role_post
     */
    @SaCheckPermission("system:sysRolePost:add")
    @Log(title = "sys_role_post", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    @Operation(summary = "新增sys_role_post",operationId = "SysRolePost_add")
    public R<Void> add(@Validated(AddGroup.class) @RequestBody SysRolePostBo bo) {
        return toAjax(sysRolePostService.insertByBo(bo));
    }

    /**
     * 修改sys_role_post
     */
    @SaCheckPermission("system:sysRolePost:edit")
    @Log(title = "sys_role_post", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    @Operation(summary = "修改sys_role_post",operationId = "SysRolePost_edit")
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody SysRolePostBo bo) {
        return toAjax(sysRolePostService.updateByBo(bo));
    }

    /**
     * 删除sys_role_post
     *
     * @param roleIds 主键串
     */
    @SaCheckPermission("system:sysRolePost:remove")
    @Log(title = "sys_role_post", businessType = BusinessType.DELETE)
    @DeleteMapping("/{roleIds}")
    @Operation(summary = "删除sys_role_post",operationId = "SysRolePost_remove")
    public R<Void> remove(@NotEmpty(message = "主键不能为空")
                          @PathVariable @PathIds Long[] roleIds) {
        return toAjax(sysRolePostService.deleteWithValidByIds(List.of(roleIds), true));
    }
}
