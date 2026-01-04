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
import com.yuan.workflow.domain.bo.WfNodeInstanceBo;
import com.yuan.workflow.domain.vo.WfNodeInstanceVo;
import com.yuan.workflow.service.WfNodeInstanceService;
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
 * wfn
 *
 * @author ageerle
 * @date Sun Dec 28 11:26:37 CST 2025
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/workflow/wfNodeInstance")
@Tag(name = "WfNodeInstanceController",description = "wfn")
public class WfNodeInstanceController extends BaseController {

    private final WfNodeInstanceService wfNodeInstanceService;

/**
 * 查询wfn列表
 */
@SaCheckPermission("system:wfNodeInstance:list")
@GetMapping("/list")
@Operation(summary = "查询wfn列表",operationId = "WfNodeInstance_list")
    public TableDataInfo<WfNodeInstanceVo> list(WfNodeInstanceBo bo, PageQuery pageQuery) {
        return wfNodeInstanceService.queryPageList(bo, pageQuery);
    }

    /**
     * 导出wfn列表
     */
    @SaCheckPermission("system:wfNodeInstance:export")
    @Log(title = "流程结点", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @Operation(summary = "导出wfn列表",operationId = "WfNodeInstance_export")
    public void export(WfNodeInstanceBo bo, HttpServletResponse response) {
        List<WfNodeInstanceVo> list = wfNodeInstanceService.queryList(bo);
        ExcelUtil.exportExcel(list, "wfn", WfNodeInstanceVo.class, response);
    }

    /**
     * 获取wfn详细信息
     *
     * @param id 主键
     */
    @SaCheckPermission("system:wfNodeInstance:query")
    @GetMapping("/{id}")
    @Operation(summary = "获取wfn详细信息",operationId = "WfNodeInstance_getInfo")
    public R<WfNodeInstanceVo> getInfo(@NotNull(message = "主键不能为空")
                                         @PathId @PathVariable Long id) {
        return R.ok(wfNodeInstanceService.queryById(id));
    }

    /**
     * 新增wfn
     */
    @SaCheckPermission("system:wfNodeInstance:add")
    @Log(title = "流程结点", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    @Operation(summary = "新增wfn",operationId = "WfNodeInstance_add")
    public R<Void> add(@Validated(AddGroup.class) @RequestBody WfNodeInstanceBo bo) {
        return toAjax(wfNodeInstanceService.insertByBo(bo));
    }

    /**
     * 修改wfn
     */
    @SaCheckPermission("system:wfNodeInstance:edit")
    @Log(title = "流程结点", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    @Operation(summary = "修改wfn",operationId = "WfNodeInstance_edit")
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody WfNodeInstanceBo bo) {
        return toAjax(wfNodeInstanceService.updateByBo(bo));
    }

    /**
     * 删除wfn
     *
     * @param ids 主键串
     */
    @SaCheckPermission("system:wfNodeInstance:remove")
    @Log(title = "流程结点", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    @Operation(summary = "删除wfn",operationId = "WfNodeInstance_remove")
    public R<Void> remove(@NotEmpty(message = "主键不能为空")
                          @PathVariable @PathIds Long[] ids) {
        return toAjax(wfNodeInstanceService.deleteWithValidByIds(List.of(ids), true));
    }
}
