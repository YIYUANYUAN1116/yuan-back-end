package com.yuan.ai.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.yuan.ai.domain.bo.LlmPolicyBo;
import com.yuan.ai.domain.vo.LlmPolicyVo;
import com.yuan.ai.service.LlmPolicyService;
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
 * llm_policy
 *
 * @author yuan
 * @date Thu Feb 26 21:44:28 CST 2026
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/ai/llmPolicy")
@Tag(name = "LlmPolicyController",description = "llm_policy")
public class LlmPolicyController extends BaseController {

    private final LlmPolicyService llmPolicyService;

/**
 * 查询llm_policy列表
 */
@SaCheckPermission("ai:llmPolicy:list")
@GetMapping("/list")
@Operation(summary = "查询llm_policy列表",operationId = "LlmPolicy_list")
    public TableDataInfo<LlmPolicyVo> list(LlmPolicyBo bo, PageQuery pageQuery) {
        return llmPolicyService.queryPageList(bo, pageQuery);
    }

    /**
     * 导出llm_policy列表
     */
    @SaCheckPermission("ai:llmPolicy:export")
    @Log(title = "llm_policy", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @Operation(summary = "导出llm_policy列表",operationId = "LlmPolicy_export")
    public void export(LlmPolicyBo bo, HttpServletResponse response) {
        List<LlmPolicyVo> list = llmPolicyService.queryList(bo);
        ExcelUtil.exportExcel(list, "llm_policy", LlmPolicyVo.class, response);
    }

    /**
     * 获取llm_policy详细信息
     *
     * @param id 主键
     */
    @SaCheckPermission("ai:llmPolicy:query")
    @GetMapping("/{id}")
    @Operation(summary = "获取llm_policy详细信息",operationId = "LlmPolicy_getInfo")
    public R<LlmPolicyVo> getInfo(@NotNull(message = "主键不能为空")
                                         @PathId @PathVariable Long id) {
        return R.ok(llmPolicyService.queryById(id));
    }

    /**
     * 新增llm_policy
     */
    @SaCheckPermission("ai:llmPolicy:add")
    @Log(title = "llm_policy", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    @Operation(summary = "新增llm_policy",operationId = "LlmPolicy_add")
    public R<Void> add(@Validated(AddGroup.class) @RequestBody LlmPolicyBo bo) {
        return toAjax(llmPolicyService.insertByBo(bo));
    }

    /**
     * 修改llm_policy
     */
    @SaCheckPermission("ai:llmPolicy:edit")
    @Log(title = "llm_policy", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    @Operation(summary = "修改llm_policy",operationId = "LlmPolicy_edit")
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody LlmPolicyBo bo) {
        return toAjax(llmPolicyService.updateByBo(bo));
    }

    /**
     * 删除llm_policy
     *
     * @param ids 主键串
     */
    @SaCheckPermission("ai:llmPolicy:remove")
    @Log(title = "llm_policy", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    @Operation(summary = "删除llm_policy",operationId = "LlmPolicy_remove")
    public R<Void> remove(@NotEmpty(message = "主键不能为空")
                          @PathVariable @PathIds Long[] ids) {
        return toAjax(llmPolicyService.deleteWithValidByIds(List.of(ids), true));
    }
}
