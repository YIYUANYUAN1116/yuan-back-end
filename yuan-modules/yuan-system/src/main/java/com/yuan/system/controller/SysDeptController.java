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
import com.yuan.system.domain.bo.SysDeptBo;
import com.yuan.system.domain.vo.SysDeptVo;
import com.yuan.system.service.SysDeptService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 部门
 *
 * @author ageerle
 * @date Wed Dec 10 17:08:31 CST 2025
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/system/sysDept")
@Tag(name = "SysDeptController",description = "部门")
public class SysDeptController extends BaseController {

    private final SysDeptService sysDeptService;

    /**
     * 查询部门列表
     */
    @SaCheckPermission("system:sysDept:list")
    @GetMapping("/list")
    @Operation(summary = "查询部门列表",operationId = "sysDept_list")
    public TableDataInfo<SysDeptVo> list(SysDeptBo bo, PageQuery pageQuery) {
        return sysDeptService.queryPageList(bo, pageQuery);
    }

    /**
     * 导出部门列表
     */
    @SaCheckPermission("system:sysDept:export")
    @Log(title = "部门管理", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @Operation(summary = "导出部门列表",operationId = "sysDept_export")
    public void export(SysDeptBo bo, HttpServletResponse response) {
        List<SysDeptVo> list = sysDeptService.queryList(bo);
        ExcelUtil.exportExcel(list, "部门", SysDeptVo.class, response);
    }

    /**
     * 获取部门详细信息
     *
     * @param deptId 主键
     */
    @Operation(summary = "导出部门列表",operationId = "sysDept_getInfo")
    @SaCheckPermission("system:sysDept:query")
    @GetMapping("/{deptId}")
    public R<SysDeptVo> getInfo(@NotNull(message = "主键不能为空")
                                @PathVariable Long deptId) {
        return R.ok(sysDeptService.queryById(deptId));
    }

    /**
     * 新增部门
     */
    @SaCheckPermission("system:sysDept:add")
    @Log(title = "部门管理", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    @Operation(summary = "新增部门",operationId = "sysDept_add")
    public R<Void> add(@Validated(AddGroup.class) @RequestBody SysDeptBo bo) {
        return toAjax(sysDeptService.insertByBo(bo));
    }

    /**
     * 修改部门
     */
    @Operation(summary = "修改部门",operationId = "sysDept_edit")
    @SaCheckPermission("system:sysDept:edit")
    @Log(title = "部门管理", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody SysDeptBo bo) {
        return toAjax(sysDeptService.updateByBo(bo));
    }

    /**
     * 删除部门
     *
     * @param deptIds 主键串
     */
    @Operation(summary = "删除部门",operationId = "sysDept_remove")
    @SaCheckPermission("system:sysDept:remove")
    @Log(title = "部门管理", businessType = BusinessType.DELETE)
    @DeleteMapping("/{deptIds}")
    public R<Void> remove(@NotEmpty(message = "主键不能为空")
                          @PathVariable Long[] deptIds) {
        return toAjax(sysDeptService.deleteWithValidByIds(List.of(deptIds), true));
    }
}
