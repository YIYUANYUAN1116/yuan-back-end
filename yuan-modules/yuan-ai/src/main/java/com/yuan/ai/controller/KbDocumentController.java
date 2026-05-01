package com.yuan.ai.controller;

import java.util.List;

import lombok.RequiredArgsConstructor;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.*;
import cn.dev33.satoken.annotation.SaCheckPermission;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.multipart.MultipartFile;
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
import com.yuan.ai.domain.vo.KbDocumentVo;
import com.yuan.ai.domain.bo.KbDocumentBo;
import com.yuan.ai.service.KbDocumentService;
import com.yuan.ai.service.KbPipelineService;
import com.yuan.core.page.TableDataInfo;

/**
 * 知识库文档表
 *
 * @author yuan
 * @date Fri May 01 16:08:29 CST 2026
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/ai/kbDocument")
@Tag(name = "KbDocumentController",description = "知识库文档表")
public class KbDocumentController extends BaseController {

    private final KbDocumentService kbDocumentService;
    private final KbPipelineService kbPipelineService;

/**
 * 查询知识库文档表列表
 */
@SaCheckPermission("ai:kbDocument:list")
@GetMapping("/list")
@Operation(summary = "查询知识库文档表列表",operationId = "KbDocument_list")
    public TableDataInfo<KbDocumentVo> list(KbDocumentBo bo, PageQuery pageQuery) {
        return kbDocumentService.queryPageList(bo, pageQuery);
    }

    /**
     * 导出知识库文档表列表
     */
    @SaCheckPermission("ai:kbDocument:export")
    @Log(title = "知识库文档表", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @Operation(summary = "导出知识库文档表列表",operationId = "KbDocument_export")
    public void export(KbDocumentBo bo, HttpServletResponse response) {
        List<KbDocumentVo> list = kbDocumentService.queryList(bo);
        ExcelUtil.exportExcel(list, "知识库文档表", KbDocumentVo.class, response);
    }

    /**
     * 获取知识库文档表详细信息
     *
     * @param docId 主键
     */
    @SaCheckPermission("ai:kbDocument:query")
    @GetMapping("/{docId}")
    @Operation(summary = "获取知识库文档表详细信息",operationId = "KbDocument_getInfo")
    public R<KbDocumentVo> getInfo(@NotNull(message = "主键不能为空")
                                         @PathId @PathVariable Long docId) {
        return R.ok(kbDocumentService.queryById(docId));
    }

    /**
     * 新增知识库文档表
     */
    @SaCheckPermission("ai:kbDocument:add")
    @Log(title = "知识库文档表", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    @Operation(summary = "新增知识库文档表",operationId = "KbDocument_add")
    public R<Void> add(@Validated(AddGroup.class) @RequestBody KbDocumentBo bo) {
        return toAjax(kbDocumentService.insertByBo(bo));
    }

    @SaCheckPermission("ai:kbDocument:add")
    @Log(title = "Knowledge base document upload", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping("/upload")
    @Operation(summary = "Upload and index knowledge base document", operationId = "KbDocument_upload")
    public R<KbDocumentVo> upload(@RequestParam("kbId") Long kbId,
                                  @RequestPart("file") MultipartFile file) {
        return R.ok(kbPipelineService.uploadAndIndex(kbId, file));
    }

    /**
     * 修改知识库文档表
     */
    @SaCheckPermission("ai:kbDocument:edit")
    @Log(title = "知识库文档表", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    @Operation(summary = "修改知识库文档表",operationId = "KbDocument_edit")
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody KbDocumentBo bo) {
        return toAjax(kbDocumentService.updateByBo(bo));
    }

    /**
     * 删除知识库文档表
     *
     * @param docIds 主键串
     */
    @SaCheckPermission("ai:kbDocument:remove")
    @Log(title = "知识库文档表", businessType = BusinessType.DELETE)
    @DeleteMapping("/{docIds}")
    @Operation(summary = "删除知识库文档表",operationId = "KbDocument_remove")
    public R<Void> remove(@NotEmpty(message = "主键不能为空")
                          @PathVariable @PathIds Long[] docIds) {
        return toAjax(kbDocumentService.deleteWithValidByIds(List.of(docIds), true));
    }
}
