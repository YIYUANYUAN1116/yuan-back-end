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
import com.yuan.system.domain.bo.SysLogininforBo;
import com.yuan.system.domain.vo.SysLogininforVo;
import com.yuan.system.service.SysLogininforService;
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
 * loginlog
 *
 
 * @date Wed Dec 17 21:48:44 CST 2025
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/system/sysLogininfor")
@Tag(name = "SysLogininforController",description = "loginlog")
public class SysLogininforController extends BaseController {

    private final SysLogininforService sysLogininforService;

/**
 * 查询loginlog列表
 */
@SaCheckPermission("system:sysLogininfor:list")
@GetMapping("/list")
@Operation(summary = "查询loginlog列表",operationId = "SysLogininfor_list")
    public TableDataInfo<SysLogininforVo> list(SysLogininforBo bo, PageQuery pageQuery) {
        return sysLogininforService.queryPageList(bo, pageQuery);
    }

    /**
     * 导出loginlog列表
     */
    @SaCheckPermission("system:sysLogininfor:export")
    @Log(title = "登录日志", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @Operation(summary = "导出loginlog列表",operationId = "SysLogininfor_export")
    public void export(SysLogininforBo bo, HttpServletResponse response) {
        List<SysLogininforVo> list = sysLogininforService.queryList(bo);
        ExcelUtil.exportExcel(list, "loginlog", SysLogininforVo.class, response);
    }

    /**
     * 获取loginlog详细信息
     *
     * @param infoId 主键
     */
    @SaCheckPermission("system:sysLogininfor:query")
    @GetMapping("/{infoId}")
    @Operation(summary = "获取loginlog详细信息",operationId = "SysLogininfor_getInfo")
    public R<SysLogininforVo> getInfo(@NotNull(message = "主键不能为空")
                                     @PathVariable @PathId Long infoId) {
        return R.ok(sysLogininforService.queryById(infoId));
    }

    /**
     * 新增loginlog
     */
    @SaCheckPermission("system:sysLogininfor:add")
    @Log(title = "登录日志", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    @Operation(summary = "新增loginlog",operationId = "SysLogininfor_add")
    public R<Void> add(@Validated(AddGroup.class) @RequestBody SysLogininforBo bo) {
        return toAjax(sysLogininforService.insertByBo(bo));
    }

    /**
     * 修改loginlog
     */
    @SaCheckPermission("system:sysLogininfor:edit")
    @Log(title = "登录日志", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    @Operation(summary = "修改loginlog",operationId = "SysLogininfor_edit")
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody SysLogininforBo bo) {
        return toAjax(sysLogininforService.updateByBo(bo));
    }

    /**
     * 删除loginlog
     *
     * @param infoIds 主键串
     */
    @SaCheckPermission("system:sysLogininfor:remove")
    @Log(title = "登录日志", businessType = BusinessType.DELETE)
    @DeleteMapping("/{infoIds}")
    @Operation(summary = "删除loginlog",operationId = "SysLogininfor_remove")
    public R<Void> remove(@NotEmpty(message = "主键不能为空")
                          @PathVariable @PathIds Long[] infoIds) {
        return toAjax(sysLogininforService.deleteWithValidByIds(List.of(infoIds), true));
    }
}
