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
import com.yuan.ai.domain.vo.KbEmbeddingVo;
import com.yuan.ai.domain.bo.KbEmbeddingBo;
import com.yuan.ai.service.KbEmbeddingService;
import com.yuan.core.page.TableDataInfo;

/**
 * 知识库向量元数据表
 *
 * @author yuan
 * @date Fri May 01 16:08:40 CST 2026
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/ai/kbEmbedding")
@Tag(name = "KbEmbeddingController",description = "知识库向量元数据表")
public class KbEmbeddingController extends BaseController {

    private final KbEmbeddingService kbEmbeddingService;

/**
 * 查询知识库向量元数据表列表
 */
@SaCheckPermission("ai:kbEmbedding:list")
@GetMapping("/list")
@Operation(summary = "查询知识库向量元数据表列表",operationId = "KbEmbedding_list")
    public TableDataInfo<KbEmbeddingVo> list(KbEmbeddingBo bo, PageQuery pageQuery) {
        return kbEmbeddingService.queryPageList(bo, pageQuery);
    }

    /**
     * 导出知识库向量元数据表列表
     */
    @SaCheckPermission("ai:kbEmbedding:export")
    @Log(title = "知识库向量元数据表", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @Operation(summary = "导出知识库向量元数据表列表",operationId = "KbEmbedding_export")
    public void export(KbEmbeddingBo bo, HttpServletResponse response) {
        List<KbEmbeddingVo> list = kbEmbeddingService.queryList(bo);
        ExcelUtil.exportExcel(list, "知识库向量元数据表", KbEmbeddingVo.class, response);
    }

    /**
     * 获取知识库向量元数据表详细信息
     *
     * @param embeddingId 主键
     */
    @SaCheckPermission("ai:kbEmbedding:query")
    @GetMapping("/{embeddingId}")
    @Operation(summary = "获取知识库向量元数据表详细信息",operationId = "KbEmbedding_getInfo")
    public R<KbEmbeddingVo> getInfo(@NotNull(message = "主键不能为空")
                                         @PathId @PathVariable Long embeddingId) {
        return R.ok(kbEmbeddingService.queryById(embeddingId));
    }

    /**
     * 新增知识库向量元数据表
     */
    @SaCheckPermission("ai:kbEmbedding:add")
    @Log(title = "知识库向量元数据表", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    @Operation(summary = "新增知识库向量元数据表",operationId = "KbEmbedding_add")
    public R<Void> add(@Validated(AddGroup.class) @RequestBody KbEmbeddingBo bo) {
        return toAjax(kbEmbeddingService.insertByBo(bo));
    }

    /**
     * 修改知识库向量元数据表
     */
    @SaCheckPermission("ai:kbEmbedding:edit")
    @Log(title = "知识库向量元数据表", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    @Operation(summary = "修改知识库向量元数据表",operationId = "KbEmbedding_edit")
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody KbEmbeddingBo bo) {
        return toAjax(kbEmbeddingService.updateByBo(bo));
    }

    /**
     * 删除知识库向量元数据表
     *
     * @param embeddingIds 主键串
     */
    @SaCheckPermission("ai:kbEmbedding:remove")
    @Log(title = "知识库向量元数据表", businessType = BusinessType.DELETE)
    @DeleteMapping("/{embeddingIds}")
    @Operation(summary = "删除知识库向量元数据表",operationId = "KbEmbedding_remove")
    public R<Void> remove(@NotEmpty(message = "主键不能为空")
                          @PathVariable @PathIds Long[] embeddingIds) {
        return toAjax(kbEmbeddingService.deleteWithValidByIds(List.of(embeddingIds), true));
    }
}
