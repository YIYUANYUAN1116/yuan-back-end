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
import com.yuan.ai.domain.vo.KbChunkVo;
import com.yuan.ai.domain.bo.KbChunkBo;
import com.yuan.ai.service.KbChunkService;
import com.yuan.core.page.TableDataInfo;

/**
 * 知识库文档切片表
 *
 * @author yuan
 * @date Fri May 01 16:08:37 CST 2026
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/ai/kbChunk")
@Tag(name = "KbChunkController",description = "知识库文档切片表")
public class KbChunkController extends BaseController {

    private final KbChunkService kbChunkService;

/**
 * 查询知识库文档切片表列表
 */
@SaCheckPermission("ai:kbChunk:list")
@GetMapping("/list")
@Operation(summary = "查询知识库文档切片表列表",operationId = "KbChunk_list")
    public TableDataInfo<KbChunkVo> list(KbChunkBo bo, PageQuery pageQuery) {
        return kbChunkService.queryPageList(bo, pageQuery);
    }

    /**
     * 导出知识库文档切片表列表
     */
    @SaCheckPermission("ai:kbChunk:export")
    @Log(title = "知识库文档切片表", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @Operation(summary = "导出知识库文档切片表列表",operationId = "KbChunk_export")
    public void export(KbChunkBo bo, HttpServletResponse response) {
        List<KbChunkVo> list = kbChunkService.queryList(bo);
        ExcelUtil.exportExcel(list, "知识库文档切片表", KbChunkVo.class, response);
    }

    /**
     * 获取知识库文档切片表详细信息
     *
     * @param chunkId 主键
     */
    @SaCheckPermission("ai:kbChunk:query")
    @GetMapping("/{chunkId}")
    @Operation(summary = "获取知识库文档切片表详细信息",operationId = "KbChunk_getInfo")
    public R<KbChunkVo> getInfo(@NotNull(message = "主键不能为空")
                                         @PathId @PathVariable Long chunkId) {
        return R.ok(kbChunkService.queryById(chunkId));
    }

    /**
     * 新增知识库文档切片表
     */
    @SaCheckPermission("ai:kbChunk:add")
    @Log(title = "知识库文档切片表", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    @Operation(summary = "新增知识库文档切片表",operationId = "KbChunk_add")
    public R<Void> add(@Validated(AddGroup.class) @RequestBody KbChunkBo bo) {
        return toAjax(kbChunkService.insertByBo(bo));
    }

    /**
     * 修改知识库文档切片表
     */
    @SaCheckPermission("ai:kbChunk:edit")
    @Log(title = "知识库文档切片表", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    @Operation(summary = "修改知识库文档切片表",operationId = "KbChunk_edit")
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody KbChunkBo bo) {
        return toAjax(kbChunkService.updateByBo(bo));
    }

    /**
     * 删除知识库文档切片表
     *
     * @param chunkIds 主键串
     */
    @SaCheckPermission("ai:kbChunk:remove")
    @Log(title = "知识库文档切片表", businessType = BusinessType.DELETE)
    @DeleteMapping("/{chunkIds}")
    @Operation(summary = "删除知识库文档切片表",operationId = "KbChunk_remove")
    public R<Void> remove(@NotEmpty(message = "主键不能为空")
                          @PathVariable @PathIds Long[] chunkIds) {
        return toAjax(kbChunkService.deleteWithValidByIds(List.of(chunkIds), true));
    }
}
