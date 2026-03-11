package com.yuan.ai.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.yuan.ai.domain.bo.LlmProviderBo;
import com.yuan.ai.domain.vo.LlmProviderVo;
import com.yuan.ai.mapper.LlmProviderMapper;
import com.yuan.ai.service.LlmProviderService;
import com.yuan.common.core.domain.R;
import com.yuan.common.core.domain.model.StrSelectModel;
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
 * llm_provider
 *
 * @author yuan
 * @date Thu Feb 26 21:44:12 CST 2026
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/ai/llmProvider")
@Tag(name = "LlmProviderController",description = "llm_provider")
public class LlmProviderController extends BaseController {

    private final LlmProviderService llmProviderService;
    private final LlmProviderMapper llmProviderMapper;

/**
 * 查询llm_provider列表
 */
@SaCheckPermission("ai:llmProvider:list")
@GetMapping("/list")
@Operation(summary = "查询llm_provider列表",operationId = "LlmProvider_list")
    public TableDataInfo<LlmProviderVo> list(LlmProviderBo bo, PageQuery pageQuery) {
        return llmProviderService.queryPageList(bo, pageQuery);
    }

    /**
     * 导出llm_provider列表
     */
    @SaCheckPermission("ai:llmProvider:export")
    @Log(title = "llm_provider", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @Operation(summary = "导出llm_provider列表",operationId = "LlmProvider_export")
    public void export(LlmProviderBo bo, HttpServletResponse response) {
        List<LlmProviderVo> list = llmProviderService.queryList(bo);
        ExcelUtil.exportExcel(list, "llm_provider", LlmProviderVo.class, response);
    }

    /**
     * 获取llm_provider详细信息
     *
     * @param id 主键
     */
    @SaCheckPermission("ai:llmProvider:query")
    @GetMapping("/{id}")
    @Operation(summary = "获取llm_provider详细信息",operationId = "LlmProvider_getInfo")
    public R<LlmProviderVo> getInfo(@NotNull(message = "主键不能为空")
                                         @PathId @PathVariable Long id) {
        return R.ok(llmProviderService.queryById(id));
    }

    /**
     * 新增llm_provider
     */
    @SaCheckPermission("ai:llmProvider:add")
    @Log(title = "llm_provider", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    @Operation(summary = "新增llm_provider",operationId = "LlmProvider_add")
    public R<Void> add(@Validated(AddGroup.class) @RequestBody LlmProviderBo bo) {
        return toAjax(llmProviderService.insertByBo(bo));
    }

    /**
     * 修改llm_provider
     */
    @SaCheckPermission("ai:llmProvider:edit")
    @Log(title = "llm_provider", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    @Operation(summary = "修改llm_provider",operationId = "LlmProvider_edit")
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody LlmProviderBo bo) {
        return toAjax(llmProviderService.updateByBo(bo));
    }

    /**
     * 删除llm_provider
     *
     * @param ids 主键串
     */
    @SaCheckPermission("ai:llmProvider:remove")
    @Log(title = "llm_provider", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    @Operation(summary = "删除llm_provider",operationId = "LlmProvider_remove")
    public R<Void> remove(@NotEmpty(message = "主键不能为空")
                          @PathVariable @PathIds Long[] ids) {
        return toAjax(llmProviderService.deleteWithValidByIds(List.of(ids), true));
    }


    @SaCheckPermission("ai:llmProvider:query")
    @GetMapping("/selectProvider")
    @Operation(summary = "获取供应商选择框列表",operationId = "selectProvider")
    public R<List<StrSelectModel>> selectProvider() {
        return R.ok(llmProviderMapper.selectProvider());
    }

}
