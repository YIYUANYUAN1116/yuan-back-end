package com.yuan.workflow.controller;

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
import com.yuan.workflow.domain.bo.WfCcBo;
import com.yuan.workflow.domain.vo.WfCcVo;
import com.yuan.workflow.service.WfCcService;
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
 * wfcc
 *
 * @author ageerle
 * @date Sun Dec 28 11:26:25 CST 2025
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/workflow/wfCc")
@Tag(name = "WfCcController",description = "wfcc")
public class WfCcController extends BaseController {

    private final WfCcService wfCcService;

/**
 * 查询wfcc列表
 */
@SaCheckPermission("system:wfCc:list")
@GetMapping("/list")
@Operation(summary = "查询wfcc列表",operationId = "WfCc_list")
    public TableDataInfo<WfCcVo> list(WfCcBo bo, PageQuery pageQuery) {
        return wfCcService.queryPageList(bo, pageQuery);
    }

    /**
     * 导出wfcc列表
     */
    @SaCheckPermission("system:wfCc:export")
    @Log(title = "wfcc", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @Operation(summary = "导出wfcc列表",operationId = "WfCc_export")
    public void export(WfCcBo bo, HttpServletResponse response) {
        List<WfCcVo> list = wfCcService.queryList(bo);
        ExcelUtil.exportExcel(list, "wfcc", WfCcVo.class, response);
    }

    /**
     * 获取wfcc详细信息
     *
     * @param id 主键
     */
    @SaCheckPermission("system:wfCc:query")
    @GetMapping("/{id}")
    @Operation(summary = "获取wfcc详细信息",operationId = "WfCc_getInfo")
    public R<WfCcVo> getInfo(@NotNull(message = "主键不能为空")
                                         @PathId @PathVariable Long id) {
        return R.ok(wfCcService.queryById(id));
    }

    /**
     * 新增wfcc
     */
    @SaCheckPermission("system:wfCc:add")
    @Log(title = "wfcc", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    @Operation(summary = "新增wfcc",operationId = "WfCc_add")
    public R<Void> add(@Validated(AddGroup.class) @RequestBody WfCcBo bo) {
        return toAjax(wfCcService.insertByBo(bo));
    }

    /**
     * 修改wfcc
     */
    @SaCheckPermission("system:wfCc:edit")
    @Log(title = "wfcc", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    @Operation(summary = "修改wfcc",operationId = "WfCc_edit")
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody WfCcBo bo) {
        return toAjax(wfCcService.updateByBo(bo));
    }

    /**
     * 删除wfcc
     *
     * @param ids 主键串
     */
    @SaCheckPermission("system:wfCc:remove")
    @Log(title = "wfcc", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    @Operation(summary = "删除wfcc",operationId = "WfCc_remove")
    public R<Void> remove(@NotEmpty(message = "主键不能为空")
                          @PathVariable @PathIds Long[] ids) {
        return toAjax(wfCcService.deleteWithValidByIds(List.of(ids), true));
    }
}
