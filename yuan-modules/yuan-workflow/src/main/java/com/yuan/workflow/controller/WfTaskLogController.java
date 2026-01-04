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
import com.yuan.workflow.domain.bo.WfTaskLogBo;
import com.yuan.workflow.domain.vo.WfTaskLogVo;
import com.yuan.workflow.service.WfTaskLogService;
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
 * wftl
 *
 * @author ageerle
 * @date Sun Dec 28 11:26:44 CST 2025
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/workflow/wfTaskLog")
@Tag(name = "WfTaskLogController",description = "wftl")
public class WfTaskLogController extends BaseController {

    private final WfTaskLogService wfTaskLogService;

/**
 * 查询wftl列表
 */
@SaCheckPermission("system:wfTaskLog:list")
@GetMapping("/list")
@Operation(summary = "查询wftl列表",operationId = "WfTaskLog_list")
    public TableDataInfo<WfTaskLogVo> list(WfTaskLogBo bo, PageQuery pageQuery) {
        return wfTaskLogService.queryPageList(bo, pageQuery);
    }

    /**
     * 导出wftl列表
     */
    @SaCheckPermission("system:wfTaskLog:export")
    @Log(title = "流程任务日志", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @Operation(summary = "导出wftl列表",operationId = "WfTaskLog_export")
    public void export(WfTaskLogBo bo, HttpServletResponse response) {
        List<WfTaskLogVo> list = wfTaskLogService.queryList(bo);
        ExcelUtil.exportExcel(list, "wftl", WfTaskLogVo.class, response);
    }

    /**
     * 获取wftl详细信息
     *
     * @param id 主键
     */
    @SaCheckPermission("system:wfTaskLog:query")
    @GetMapping("/{id}")
    @Operation(summary = "获取wftl详细信息",operationId = "WfTaskLog_getInfo")
    public R<WfTaskLogVo> getInfo(@NotNull(message = "主键不能为空")
                                         @PathId @PathVariable Long id) {
        return R.ok(wfTaskLogService.queryById(id));
    }

    /**
     * 新增wftl
     */
    @SaCheckPermission("system:wfTaskLog:add")
    @Log(title = "流程任务日志", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    @Operation(summary = "新增wftl",operationId = "WfTaskLog_add")
    public R<Void> add(@Validated(AddGroup.class) @RequestBody WfTaskLogBo bo) {
        return toAjax(wfTaskLogService.insertByBo(bo));
    }

    /**
     * 修改wftl
     */
    @SaCheckPermission("system:wfTaskLog:edit")
    @Log(title = "流程任务日志", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    @Operation(summary = "修改wftl",operationId = "WfTaskLog_edit")
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody WfTaskLogBo bo) {
        return toAjax(wfTaskLogService.updateByBo(bo));
    }

    /**
     * 删除wftl
     *
     * @param ids 主键串
     */
    @SaCheckPermission("system:wfTaskLog:remove")
    @Log(title = "流程任务日志", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    @Operation(summary = "删除wftl",operationId = "WfTaskLog_remove")
    public R<Void> remove(@NotEmpty(message = "主键不能为空")
                          @PathVariable @PathIds Long[] ids) {
        return toAjax(wfTaskLogService.deleteWithValidByIds(List.of(ids), true));
    }
}
