package com.yuan.ai.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.yuan.ai.domain.bo.LlmInvocationBo;
import com.yuan.ai.domain.vo.LlmInvocationVo;
import com.yuan.ai.service.LlmInvocationService;
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
 * llm_invocation
 *
 * @author yuan
 * @date Thu Feb 26 21:44:25 CST 2026
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/ai/llmInvocation")
@Tag(name = "LlmInvocationController",description = "llm_invocation")
public class LlmInvocationController extends BaseController {

    private final LlmInvocationService llmInvocationService;

/**
 * 查询llm_invocation列表
 */
@SaCheckPermission("ai:llmInvocation:list")
@GetMapping("/list")
@Operation(summary = "查询llm_invocation列表",operationId = "LlmInvocation_list")
    public TableDataInfo<LlmInvocationVo> list(LlmInvocationBo bo, PageQuery pageQuery) {
        return llmInvocationService.queryPageList(bo, pageQuery);
    }

    /**
     * 导出llm_invocation列表
     */
    @SaCheckPermission("ai:llmInvocation:export")
    @Log(title = "llm_invocation", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @Operation(summary = "导出llm_invocation列表",operationId = "LlmInvocation_export")
    public void export(LlmInvocationBo bo, HttpServletResponse response) {
        List<LlmInvocationVo> list = llmInvocationService.queryList(bo);
        ExcelUtil.exportExcel(list, "llm_invocation", LlmInvocationVo.class, response);
    }

    /**
     * 获取llm_invocation详细信息
     *
     * @param id 主键
     */
    @SaCheckPermission("ai:llmInvocation:query")
    @GetMapping("/{id}")
    @Operation(summary = "获取llm_invocation详细信息",operationId = "LlmInvocation_getInfo")
    public R<LlmInvocationVo> getInfo(@NotNull(message = "主键不能为空")
                                         @PathId @PathVariable Long id) {
        return R.ok(llmInvocationService.queryById(id));
    }

    /**
     * 新增llm_invocation
     */
    @SaCheckPermission("ai:llmInvocation:add")
    @Log(title = "llm_invocation", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    @Operation(summary = "新增llm_invocation",operationId = "LlmInvocation_add")
    public R<Void> add(@Validated(AddGroup.class) @RequestBody LlmInvocationBo bo) {
        return toAjax(llmInvocationService.insertByBo(bo));
    }

    /**
     * 修改llm_invocation
     */
    @SaCheckPermission("ai:llmInvocation:edit")
    @Log(title = "llm_invocation", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    @Operation(summary = "修改llm_invocation",operationId = "LlmInvocation_edit")
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody LlmInvocationBo bo) {
        return toAjax(llmInvocationService.updateByBo(bo));
    }

    /**
     * 删除llm_invocation
     *
     * @param ids 主键串
     */
    @SaCheckPermission("ai:llmInvocation:remove")
    @Log(title = "llm_invocation", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    @Operation(summary = "删除llm_invocation",operationId = "LlmInvocation_remove")
    public R<Void> remove(@NotEmpty(message = "主键不能为空")
                          @PathVariable @PathIds Long[] ids) {
        return toAjax(llmInvocationService.deleteWithValidByIds(List.of(ids), true));
    }
}
