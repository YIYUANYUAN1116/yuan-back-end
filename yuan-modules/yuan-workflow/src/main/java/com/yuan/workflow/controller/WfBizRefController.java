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
import com.yuan.workflow.domain.bo.WfBizRefBo;
import com.yuan.workflow.domain.vo.WfBizRefVo;
import com.yuan.workflow.service.WfBizRefService;
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
 * wfref
 *
 * @author yuan
 * @date Mon Dec 29 20:51:33 CST 2025
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/workflow/wfBizRef")
@Tag(name = "WfBizRefController",description = "wfref")
public class WfBizRefController extends BaseController {

    private final WfBizRefService wfBizRefService;

/**
 * 查询wfref列表
 */
@SaCheckPermission("workflow:wfBizRef:list")
@GetMapping("/list")
@Operation(summary = "查询wfref列表",operationId = "WfBizRef_list")
    public TableDataInfo<WfBizRefVo> list(WfBizRefBo bo, PageQuery pageQuery) {
        return wfBizRefService.queryPageList(bo, pageQuery);
    }

    /**
     * 导出wfref列表
     */
    @SaCheckPermission("workflow:wfBizRef:export")
    @Log(title = "流程业务关联", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @Operation(summary = "导出wfref列表",operationId = "WfBizRef_export")
    public void export(WfBizRefBo bo, HttpServletResponse response) {
        List<WfBizRefVo> list = wfBizRefService.queryList(bo);
        ExcelUtil.exportExcel(list, "wfref", WfBizRefVo.class, response);
    }

    /**
     * 获取wfref详细信息
     *
     * @param id 主键
     */
    @SaCheckPermission("workflow:wfBizRef:query")
    @GetMapping("/{id}")
    @Operation(summary = "获取wfref详细信息",operationId = "WfBizRef_getInfo")
    public R<WfBizRefVo> getInfo(@NotNull(message = "主键不能为空")
                                         @PathId @PathVariable Long id) {
        return R.ok(wfBizRefService.queryById(id));
    }

    /**
     * 新增wfref
     */
    @SaCheckPermission("workflow:wfBizRef:add")
    @Log(title = "流程业务关联", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    @Operation(summary = "新增wfref",operationId = "WfBizRef_add")
    public R<Void> add(@Validated(AddGroup.class) @RequestBody WfBizRefBo bo) {
        return toAjax(wfBizRefService.insertByBo(bo));
    }

    /**
     * 修改wfref
     */
    @SaCheckPermission("workflow:wfBizRef:edit")
    @Log(title = "流程业务关联", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    @Operation(summary = "修改wfref",operationId = "WfBizRef_edit")
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody WfBizRefBo bo) {
        return toAjax(wfBizRefService.updateByBo(bo));
    }

    /**
     * 删除wfref
     *
     * @param ids 主键串
     */
    @SaCheckPermission("workflow:wfBizRef:remove")
    @Log(title = "流程业务关联", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    @Operation(summary = "删除wfref",operationId = "WfBizRef_remove")
    public R<Void> remove(@NotEmpty(message = "主键不能为空")
                          @PathVariable @PathIds Long[] ids) {
        return toAjax(wfBizRefService.deleteWithValidByIds(List.of(ids), true));
    }
}
