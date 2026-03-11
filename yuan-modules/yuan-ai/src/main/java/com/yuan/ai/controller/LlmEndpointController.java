package com.yuan.ai.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.yuan.ai.domain.bo.LlmEndpointBo;
import com.yuan.ai.domain.vo.LlmEndpointVo;
import com.yuan.ai.mapper.LlmEndpointMapper;
import com.yuan.ai.service.LlmEndpointService;
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
 * llm_endpoint
 *
 * @author yuan
 * @date Thu Feb 26 21:44:21 CST 2026
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/ai/llmEndpoint")
@Tag(name = "LlmEndpointController",description = "llm_endpoint")
public class LlmEndpointController extends BaseController {

    private final LlmEndpointService llmEndpointService;
    private final LlmEndpointMapper llmEndpointMapper;

/**
 * 查询llm_endpoint列表
 */
@SaCheckPermission("ai:llmEndpoint:list")
@GetMapping("/list")
@Operation(summary = "查询llm_endpoint列表",operationId = "LlmEndpoint_list")
    public TableDataInfo<LlmEndpointVo> list(LlmEndpointBo bo, PageQuery pageQuery) {
        return llmEndpointService.queryPageList(bo, pageQuery);
    }

    /**
     * 导出llm_endpoint列表
     */
    @SaCheckPermission("ai:llmEndpoint:export")
    @Log(title = "llm_endpoint", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @Operation(summary = "导出llm_endpoint列表",operationId = "LlmEndpoint_export")
    public void export(LlmEndpointBo bo, HttpServletResponse response) {
        List<LlmEndpointVo> list = llmEndpointService.queryList(bo);
        ExcelUtil.exportExcel(list, "llm_endpoint", LlmEndpointVo.class, response);
    }

    /**
     * 获取llm_endpoint详细信息
     *
     * @param id 主键
     */
    @SaCheckPermission("ai:llmEndpoint:query")
    @GetMapping("/{id}")
    @Operation(summary = "获取llm_endpoint详细信息",operationId = "LlmEndpoint_getInfo")
    public R<LlmEndpointVo> getInfo(@NotNull(message = "主键不能为空")
                                         @PathId @PathVariable Long id) {
        return R.ok(llmEndpointService.queryById(id));
    }

    /**
     * 新增llm_endpoint
     */
    @SaCheckPermission("ai:llmEndpoint:add")
    @Log(title = "llm_endpoint", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    @Operation(summary = "新增llm_endpoint",operationId = "LlmEndpoint_add")
    public R<Void> add(@Validated(AddGroup.class) @RequestBody LlmEndpointBo bo) {
        return toAjax(llmEndpointService.insertByBo(bo));
    }

    /**
     * 修改llm_endpoint
     */
    @SaCheckPermission("ai:llmEndpoint:edit")
    @Log(title = "llm_endpoint", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    @Operation(summary = "修改llm_endpoint",operationId = "LlmEndpoint_edit")
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody LlmEndpointBo bo) {
        return toAjax(llmEndpointService.updateByBo(bo));
    }

    /**
     * 删除llm_endpoint
     *
     * @param ids 主键串
     */
    @SaCheckPermission("ai:llmEndpoint:remove")
    @Log(title = "llm_endpoint", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    @Operation(summary = "删除llm_endpoint",operationId = "LlmEndpoint_remove")
    public R<Void> remove(@NotEmpty(message = "主键不能为空")
                          @PathVariable @PathIds Long[] ids) {
        return toAjax(llmEndpointService.deleteWithValidByIds(List.of(ids), true));
    }


    @SaCheckPermission("ai:llmEndpoint:query")
    @GetMapping("/selectEndpoint")
    @Operation(summary = "获取供应商选择框列表",operationId = "selectEndpoint")
    public R<List<StrSelectModel>> selectEndpoint(@RequestParam String providerCode) {
        return R.ok(llmEndpointMapper.selectEndpoint(providerCode));
    }
}
