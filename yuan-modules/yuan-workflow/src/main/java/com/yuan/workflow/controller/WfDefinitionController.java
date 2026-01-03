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
import com.yuan.workflow.api.enums.DefinitionAction;
import com.yuan.workflow.domain.bo.WfDefinitionBo;
import com.yuan.workflow.domain.bo.WfDefinitionDto;
import com.yuan.workflow.domain.vo.WfDefinitionVo;
import com.yuan.workflow.service.WfDefinitionService;
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
 * wfd
 *
 * @author ageerle
 * @date Sun Dec 28 11:26:30 CST 2025
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/workflow/wfDefinition")
@Tag(name = "WfDefinitionController",description = "wfd")
public class WfDefinitionController extends BaseController {

    private final WfDefinitionService wfDefinitionService;

/**
 * 查询wfd列表
 */
@SaCheckPermission("system:wfDefinition:list")
@GetMapping("/list")
@Operation(summary = "查询wfd列表",operationId = "WfDefinition_list")
    public TableDataInfo<WfDefinitionVo> list(WfDefinitionBo bo, PageQuery pageQuery) {
        return wfDefinitionService.queryPageList(bo, pageQuery);
    }

    /**
     * 导出wfd列表
     */
    @SaCheckPermission("system:wfDefinition:export")
    @Log(title = "流程定义", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @Operation(summary = "导出wfd列表",operationId = "WfDefinition_export")
    public void export(WfDefinitionBo bo, HttpServletResponse response) {
        List<WfDefinitionVo> list = wfDefinitionService.queryList(bo);
        ExcelUtil.exportExcel(list, "wfd", WfDefinitionVo.class, response);
    }

    /**
     * 获取wfd详细信息
     *
     * @param id 主键
     */
    @SaCheckPermission("system:wfDefinition:query")
    @GetMapping("/{id}")
    @Operation(summary = "获取wfd详细信息",operationId = "WfDefinition_getInfo")
    public R<WfDefinitionVo> getInfo(@NotNull(message = "主键不能为空")
                                         @PathId @PathVariable Long id) {
        return R.ok(wfDefinitionService.queryById(id));
    }

    /**
     * 新增wfd
     */
    @SaCheckPermission("system:wfDefinition:add")
    @Log(title = "流程定义", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    @Operation(summary = "新增wfd",operationId = "WfDefinition_add")
    public R<Void> add(@Validated(AddGroup.class) @RequestBody WfDefinitionBo bo) {
        return toAjax(wfDefinitionService.insertByBo(bo));
    }

    /**
     * 修改wfd
     */
    @SaCheckPermission("system:wfDefinition:edit")
    @Log(title = "流程定义", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    @Operation(summary = "修改wfd",operationId = "WfDefinition_edit")
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody WfDefinitionBo bo) {
        return toAjax(wfDefinitionService.updateByBo(bo));
    }

    /**
     * 删除wfd
     *
     * @param ids 主键串
     */
    @SaCheckPermission("system:wfDefinition:remove")
    @Log(title = "流程定义", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    @Operation(summary = "删除wfd",operationId = "WfDefinition_remove")
    public R<Void> remove(@NotEmpty(message = "主键不能为空")
                          @PathVariable @PathIds Long[] ids) {
        return toAjax(wfDefinitionService.deleteWithValidByIds(List.of(ids), true));
    }


    /**
     * 修改wfd
     */
    @SaCheckPermission("system:wfDefinition:edit")
    @Log(title = "流程定义", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping("/dto")
    @Operation(summary = "修改wfd_dto",operationId = "WfDefinition_edit_dto")
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody WfDefinitionDto dto) {
        return toAjax(wfDefinitionService.updateByDto(dto));
    }

    /**
     * 修改wfd
     */
    @SaCheckPermission("system:wfDefinition:edit")
    @Log(title = "流程定义", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PostMapping("/{id}/{action}")
    @Operation(summary = "修改流程定义状态",operationId = "WfDefinition_change_status")
    public R<Void> changeStatus(
            @PathVariable @PathId Long id,
            @PathVariable String action
    ) {
        DefinitionAction definitionAction = DefinitionAction.fromValue(action);
        wfDefinitionService.changeStatus(id, definitionAction);
        return R.ok();
    }

}
