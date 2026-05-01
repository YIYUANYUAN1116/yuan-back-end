package com.yuan.ai.controller;

import java.util.List;

import lombok.RequiredArgsConstructor;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.*;
import cn.dev33.satoken.annotation.SaCheckPermission;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.annotation.Validated;
import com.yuan.common.idempotent.annotation.RepeatSubmit;
import com.yuan.common.log.annotation.Log;
import com.yuan.common.web.core.BaseController;
import com.yuan.core.page.PageQuery;
import com.yuan.common.core.domain.R;
import com.yuan.common.core.validate.AddGroup;
import com.yuan.common.core.validate.EditGroup;
import com.yuan.common.log.enums.BusinessType;
import com.yuan.common.excel.utils.ExcelUtil;
import com.yuan.common.doc.annotation.PathId;
import com.yuan.common.doc.annotation.PathIds;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import com.yuan.ai.domain.vo.KbDocumentTextVo;
import com.yuan.ai.domain.bo.KbDocumentTextBo;
import com.yuan.ai.service.KbDocumentTextService;
import com.yuan.core.page.TableDataInfo;

/**
 * 知识库文档解析文本表
 *
 * @author yuan
 * @date Fri May 01 16:08:34 CST 2026
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/ai/kbDocumentText")
@Tag(name = "KbDocumentTextController",description = "知识库文档解析文本表")
public class KbDocumentTextController extends BaseController {

    private final KbDocumentTextService kbDocumentTextService;

/**
 * 查询知识库文档解析文本表列表
 */
@SaCheckPermission("ai:kbDocumentText:list")
@GetMapping("/list")
@Operation(summary = "查询知识库文档解析文本表列表",operationId = "KbDocumentText_list")
    public TableDataInfo<KbDocumentTextVo> list(KbDocumentTextBo bo, PageQuery pageQuery) {
        return kbDocumentTextService.queryPageList(bo, pageQuery);
    }

    /**
     * 导出知识库文档解析文本表列表
     */
    @SaCheckPermission("ai:kbDocumentText:export")
    @Log(title = "知识库文档解析文本表", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @Operation(summary = "导出知识库文档解析文本表列表",operationId = "KbDocumentText_export")
    public void export(KbDocumentTextBo bo, HttpServletResponse response) {
        List<KbDocumentTextVo> list = kbDocumentTextService.queryList(bo);
        ExcelUtil.exportExcel(list, "知识库文档解析文本表", KbDocumentTextVo.class, response);
    }

    /**
     * 获取知识库文档解析文本表详细信息
     *
     * @param textId 主键
     */
    @SaCheckPermission("ai:kbDocumentText:query")
    @GetMapping("/{textId}")
    @Operation(summary = "获取知识库文档解析文本表详细信息",operationId = "KbDocumentText_getInfo")
    public R<KbDocumentTextVo> getInfo(@NotNull(message = "主键不能为空")
                                         @PathId @PathVariable Long textId) {
        return R.ok(kbDocumentTextService.queryById(textId));
    }

    /**
     * 新增知识库文档解析文本表
     */
    @SaCheckPermission("ai:kbDocumentText:add")
    @Log(title = "知识库文档解析文本表", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    @Operation(summary = "新增知识库文档解析文本表",operationId = "KbDocumentText_add")
    public R<Void> add(@Validated(AddGroup.class) @RequestBody KbDocumentTextBo bo) {
        return toAjax(kbDocumentTextService.insertByBo(bo));
    }

    /**
     * 修改知识库文档解析文本表
     */
    @SaCheckPermission("ai:kbDocumentText:edit")
    @Log(title = "知识库文档解析文本表", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    @Operation(summary = "修改知识库文档解析文本表",operationId = "KbDocumentText_edit")
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody KbDocumentTextBo bo) {
        return toAjax(kbDocumentTextService.updateByBo(bo));
    }

    /**
     * 删除知识库文档解析文本表
     *
     * @param textIds 主键串
     */
    @SaCheckPermission("ai:kbDocumentText:remove")
    @Log(title = "知识库文档解析文本表", businessType = BusinessType.DELETE)
    @DeleteMapping("/{textIds}")
    @Operation(summary = "删除知识库文档解析文本表",operationId = "KbDocumentText_remove")
    public R<Void> remove(@NotEmpty(message = "主键不能为空")
                          @PathVariable @PathIds Long[] textIds) {
        return toAjax(kbDocumentTextService.deleteWithValidByIds(List.of(textIds), true));
    }
}
