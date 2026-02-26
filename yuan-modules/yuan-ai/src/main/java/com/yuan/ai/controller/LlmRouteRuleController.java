package com.yuan.ai.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.yuan.ai.domain.bo.LlmRouteRuleBo;
import com.yuan.ai.domain.vo.LlmRouteRuleVo;
import com.yuan.ai.service.LlmRouteRuleService;
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
 * llm_route_rule
 *
 * @author yuan
 * @date Thu Feb 26 21:44:32 CST 2026
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/ai/llmRouteRule")
@Tag(name = "LlmRouteRuleController",description = "llm_route_rule")
public class LlmRouteRuleController extends BaseController {

    private final LlmRouteRuleService llmRouteRuleService;

/**
 * 查询llm_route_rule列表
 */
@SaCheckPermission("ai:llmRouteRule:list")
@GetMapping("/list")
@Operation(summary = "查询llm_route_rule列表",operationId = "LlmRouteRule_list")
    public TableDataInfo<LlmRouteRuleVo> list(LlmRouteRuleBo bo, PageQuery pageQuery) {
        return llmRouteRuleService.queryPageList(bo, pageQuery);
    }

    /**
     * 导出llm_route_rule列表
     */
    @SaCheckPermission("ai:llmRouteRule:export")
    @Log(title = "llm_route_rule", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @Operation(summary = "导出llm_route_rule列表",operationId = "LlmRouteRule_export")
    public void export(LlmRouteRuleBo bo, HttpServletResponse response) {
        List<LlmRouteRuleVo> list = llmRouteRuleService.queryList(bo);
        ExcelUtil.exportExcel(list, "llm_route_rule", LlmRouteRuleVo.class, response);
    }

    /**
     * 获取llm_route_rule详细信息
     *
     * @param id 主键
     */
    @SaCheckPermission("ai:llmRouteRule:query")
    @GetMapping("/{id}")
    @Operation(summary = "获取llm_route_rule详细信息",operationId = "LlmRouteRule_getInfo")
    public R<LlmRouteRuleVo> getInfo(@NotNull(message = "主键不能为空")
                                         @PathId @PathVariable Long id) {
        return R.ok(llmRouteRuleService.queryById(id));
    }

    /**
     * 新增llm_route_rule
     */
    @SaCheckPermission("ai:llmRouteRule:add")
    @Log(title = "llm_route_rule", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    @Operation(summary = "新增llm_route_rule",operationId = "LlmRouteRule_add")
    public R<Void> add(@Validated(AddGroup.class) @RequestBody LlmRouteRuleBo bo) {
        return toAjax(llmRouteRuleService.insertByBo(bo));
    }

    /**
     * 修改llm_route_rule
     */
    @SaCheckPermission("ai:llmRouteRule:edit")
    @Log(title = "llm_route_rule", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    @Operation(summary = "修改llm_route_rule",operationId = "LlmRouteRule_edit")
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody LlmRouteRuleBo bo) {
        return toAjax(llmRouteRuleService.updateByBo(bo));
    }

    /**
     * 删除llm_route_rule
     *
     * @param ids 主键串
     */
    @SaCheckPermission("ai:llmRouteRule:remove")
    @Log(title = "llm_route_rule", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    @Operation(summary = "删除llm_route_rule",operationId = "LlmRouteRule_remove")
    public R<Void> remove(@NotEmpty(message = "主键不能为空")
                          @PathVariable @PathIds Long[] ids) {
        return toAjax(llmRouteRuleService.deleteWithValidByIds(List.of(ids), true));
    }
}
