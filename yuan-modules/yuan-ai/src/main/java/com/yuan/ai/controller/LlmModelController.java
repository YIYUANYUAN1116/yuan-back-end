package com.yuan.ai.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.yuan.ai.domain.bo.LlmModelBo;
import com.yuan.ai.domain.vo.LlmModelVo;
import com.yuan.ai.service.LlmModelService;
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
 * llm_model
 *
 * @author yuan
 * @date Thu Feb 26 21:44:17 CST 2026
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/ai/llmModel")
@Tag(name = "LlmModelController",description = "llm_model")
public class LlmModelController extends BaseController {

    private final LlmModelService llmModelService;

/**
 * 查询llm_model列表
 */
@SaCheckPermission("ai:llmModel:list")
@GetMapping("/list")
@Operation(summary = "查询llm_model列表",operationId = "LlmModel_list")
    public TableDataInfo<LlmModelVo> list(LlmModelBo bo, PageQuery pageQuery) {
        return llmModelService.queryPageList(bo, pageQuery);
    }

    /**
     * 导出llm_model列表
     */
    @SaCheckPermission("ai:llmModel:export")
    @Log(title = "llm_model", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @Operation(summary = "导出llm_model列表",operationId = "LlmModel_export")
    public void export(LlmModelBo bo, HttpServletResponse response) {
        List<LlmModelVo> list = llmModelService.queryList(bo);
        ExcelUtil.exportExcel(list, "llm_model", LlmModelVo.class, response);
    }

    /**
     * 获取llm_model详细信息
     *
     * @param id 主键
     */
    @SaCheckPermission("ai:llmModel:query")
    @GetMapping("/{id}")
    @Operation(summary = "获取llm_model详细信息",operationId = "LlmModel_getInfo")
    public R<LlmModelVo> getInfo(@NotNull(message = "主键不能为空")
                                         @PathId @PathVariable Long id) {
        return R.ok(llmModelService.queryById(id));
    }

    /**
     * 新增llm_model
     */
    @SaCheckPermission("ai:llmModel:add")
    @Log(title = "llm_model", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    @Operation(summary = "新增llm_model",operationId = "LlmModel_add")
    public R<Void> add(@Validated(AddGroup.class) @RequestBody LlmModelBo bo) {
        return toAjax(llmModelService.insertByBo(bo));
    }

    /**
     * 修改llm_model
     */
    @SaCheckPermission("ai:llmModel:edit")
    @Log(title = "llm_model", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    @Operation(summary = "修改llm_model",operationId = "LlmModel_edit")
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody LlmModelBo bo) {
        return toAjax(llmModelService.updateByBo(bo));
    }

    /**
     * 删除llm_model
     *
     * @param ids 主键串
     */
    @SaCheckPermission("ai:llmModel:remove")
    @Log(title = "llm_model", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    @Operation(summary = "删除llm_model",operationId = "LlmModel_remove")
    public R<Void> remove(@NotEmpty(message = "主键不能为空")
                          @PathVariable @PathIds Long[] ids) {
        return toAjax(llmModelService.deleteWithValidByIds(List.of(ids), true));
    }
}
