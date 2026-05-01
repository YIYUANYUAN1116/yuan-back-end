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
import com.yuan.ai.domain.vo.KbRetrievalHitVo;
import com.yuan.ai.domain.bo.KbRetrievalHitBo;
import com.yuan.ai.service.KbRetrievalHitService;
import com.yuan.core.page.TableDataInfo;

/**
 * 知识库检索命中明细表
 *
 * @author yuan
 * @date Fri May 01 16:08:50 CST 2026
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/ai/kbRetrievalHit")
@Tag(name = "KbRetrievalHitController",description = "知识库检索命中明细表")
public class KbRetrievalHitController extends BaseController {

    private final KbRetrievalHitService kbRetrievalHitService;

/**
 * 查询知识库检索命中明细表列表
 */
@SaCheckPermission("ai:kbRetrievalHit:list")
@GetMapping("/list")
@Operation(summary = "查询知识库检索命中明细表列表",operationId = "KbRetrievalHit_list")
    public TableDataInfo<KbRetrievalHitVo> list(KbRetrievalHitBo bo, PageQuery pageQuery) {
        return kbRetrievalHitService.queryPageList(bo, pageQuery);
    }

    /**
     * 导出知识库检索命中明细表列表
     */
    @SaCheckPermission("ai:kbRetrievalHit:export")
    @Log(title = "知识库检索命中明细表", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @Operation(summary = "导出知识库检索命中明细表列表",operationId = "KbRetrievalHit_export")
    public void export(KbRetrievalHitBo bo, HttpServletResponse response) {
        List<KbRetrievalHitVo> list = kbRetrievalHitService.queryList(bo);
        ExcelUtil.exportExcel(list, "知识库检索命中明细表", KbRetrievalHitVo.class, response);
    }

    /**
     * 获取知识库检索命中明细表详细信息
     *
     * @param hitId 主键
     */
    @SaCheckPermission("ai:kbRetrievalHit:query")
    @GetMapping("/{hitId}")
    @Operation(summary = "获取知识库检索命中明细表详细信息",operationId = "KbRetrievalHit_getInfo")
    public R<KbRetrievalHitVo> getInfo(@NotNull(message = "主键不能为空")
                                         @PathId @PathVariable Long hitId) {
        return R.ok(kbRetrievalHitService.queryById(hitId));
    }

    /**
     * 新增知识库检索命中明细表
     */
    @SaCheckPermission("ai:kbRetrievalHit:add")
    @Log(title = "知识库检索命中明细表", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    @Operation(summary = "新增知识库检索命中明细表",operationId = "KbRetrievalHit_add")
    public R<Void> add(@Validated(AddGroup.class) @RequestBody KbRetrievalHitBo bo) {
        return toAjax(kbRetrievalHitService.insertByBo(bo));
    }

    /**
     * 修改知识库检索命中明细表
     */
    @SaCheckPermission("ai:kbRetrievalHit:edit")
    @Log(title = "知识库检索命中明细表", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    @Operation(summary = "修改知识库检索命中明细表",operationId = "KbRetrievalHit_edit")
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody KbRetrievalHitBo bo) {
        return toAjax(kbRetrievalHitService.updateByBo(bo));
    }

    /**
     * 删除知识库检索命中明细表
     *
     * @param hitIds 主键串
     */
    @SaCheckPermission("ai:kbRetrievalHit:remove")
    @Log(title = "知识库检索命中明细表", businessType = BusinessType.DELETE)
    @DeleteMapping("/{hitIds}")
    @Operation(summary = "删除知识库检索命中明细表",operationId = "KbRetrievalHit_remove")
    public R<Void> remove(@NotEmpty(message = "主键不能为空")
                          @PathVariable @PathIds Long[] hitIds) {
        return toAjax(kbRetrievalHitService.deleteWithValidByIds(List.of(hitIds), true));
    }
}
