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
import com.yuan.system.domain.bo.SysOperLogBo;
import com.yuan.system.domain.vo.SysOperLogVo;
import com.yuan.system.service.SysOperLogService;
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
 * oprelog
 *

 * @date Wed Dec 17 21:48:33 CST 2025
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/system/sysOperLog")
@Tag(name = "SysOperLogController",description = "oprelog")
public class SysOperLogController extends BaseController {

    private final SysOperLogService sysOperLogService;

/**
 * 查询oprelog列表
 */
@SaCheckPermission("system:operlog:list")
@GetMapping("/list")
@Operation(summary = "查询oprelog列表",operationId = "SysOperLog_list")
    public TableDataInfo<SysOperLogVo> list(SysOperLogBo bo, PageQuery pageQuery) {
        return sysOperLogService.queryPageList(bo, pageQuery);
    }

    /**
     * 导出oprelog列表
     */
    @SaCheckPermission("system:operlog:export")
    @Log(title = "操作日志", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @Operation(summary = "导出oprelog列表",operationId = "SysOperLog_export")
    public void export(SysOperLogBo bo, HttpServletResponse response) {
        List<SysOperLogVo> list = sysOperLogService.queryList(bo);
        ExcelUtil.exportExcel(list, "oprelog", SysOperLogVo.class, response);
    }

    /**
     * 获取oprelog详细信息
     *
     * @param operId 主键
     */
    @SaCheckPermission("system:operlog:query")
    @GetMapping("/{operId}")
    @Operation(summary = "获取oprelog详细信息",operationId = "SysOperLog_getInfo")
    public R<SysOperLogVo> getInfo(@NotNull(message = "主键不能为空")
                                     @PathVariable @PathId Long operId) {
        return R.ok(sysOperLogService.queryById(operId));
    }

    /**
     * 新增oprelog
     */
    @SaCheckPermission("system:operlog:add")
    @Log(title = "操作日志", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    @Operation(summary = "新增oprelog",operationId = "SysOperLog_add")
    public R<Void> add(@Validated(AddGroup.class) @RequestBody SysOperLogBo bo) {
        return toAjax(sysOperLogService.insertByBo(bo));
    }

    /**
     * 修改oprelog
     */
    @SaCheckPermission("system:operlog:edit")
    @Log(title = "操作日志", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    @Operation(summary = "修改oprelog",operationId = "SysOperLog_edit")
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody SysOperLogBo bo) {
        return toAjax(sysOperLogService.updateByBo(bo));
    }

    /**
     * 删除oprelog
     *
     * @param operIds 主键串
     */
    @SaCheckPermission("system:operlog:remove")
    @Log(title = "操作日志", businessType = BusinessType.DELETE)
    @DeleteMapping("/{operIds}")
    @Operation(summary = "删除oprelog",operationId = "SysOperLog_remove")
    public R<Void> remove(@NotEmpty(message = "主键不能为空")
                          @PathVariable @PathIds Long[] operIds) {
        return toAjax(sysOperLogService.deleteWithValidByIds(List.of(operIds), true));
    }
}
