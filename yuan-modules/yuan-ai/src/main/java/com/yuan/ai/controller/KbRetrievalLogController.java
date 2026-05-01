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
import com.yuan.ai.domain.vo.KbRetrievalLogVo;
import com.yuan.ai.domain.bo.KbRetrievalLogBo;
import com.yuan.ai.service.KbRetrievalLogService;
import com.yuan.core.page.TableDataInfo;

/**
 * 知识库检索日志表
 *
 * @author yuan
 * @date Fri May 01 16:08:45 CST 2026
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/ai/kbRetrievalLog")
@Tag(name = "KbRetrievalLogController",description = "知识库检索日志表")
public class KbRetrievalLogController extends BaseController {

    private final KbRetrievalLogService kbRetrievalLogService;

/**
 * 查询知识库检索日志表列表
 */
@SaCheckPermission("ai:kbRetrievalLog:list")
@GetMapping("/list")
@Operation(summary = "查询知识库检索日志表列表",operationId = "KbRetrievalLog_list")
    public TableDataInfo<KbRetrievalLogVo> list(KbRetrievalLogBo bo, PageQuery pageQuery) {
        return kbRetrievalLogService.queryPageList(bo, pageQuery);
    }

    /**
     * 导出知识库检索日志表列表
     */
    @SaCheckPermission("ai:kbRetrievalLog:export")
    @Log(title = "知识库检索日志表", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @Operation(summary = "导出知识库检索日志表列表",operationId = "KbRetrievalLog_export")
    public void export(KbRetrievalLogBo bo, HttpServletResponse response) {
        List<KbRetrievalLogVo> list = kbRetrievalLogService.queryList(bo);
        ExcelUtil.exportExcel(list, "知识库检索日志表", KbRetrievalLogVo.class, response);
    }

    /**
     * 获取知识库检索日志表详细信息
     *
     * @param logId 主键
     */
    @SaCheckPermission("ai:kbRetrievalLog:query")
    @GetMapping("/{logId}")
    @Operation(summary = "获取知识库检索日志表详细信息",operationId = "KbRetrievalLog_getInfo")
    public R<KbRetrievalLogVo> getInfo(@NotNull(message = "主键不能为空")
                                         @PathId @PathVariable Long logId) {
        return R.ok(kbRetrievalLogService.queryById(logId));
    }

    /**
     * 新增知识库检索日志表
     */
    @SaCheckPermission("ai:kbRetrievalLog:add")
    @Log(title = "知识库检索日志表", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    @Operation(summary = "新增知识库检索日志表",operationId = "KbRetrievalLog_add")
    public R<Void> add(@Validated(AddGroup.class) @RequestBody KbRetrievalLogBo bo) {
        return toAjax(kbRetrievalLogService.insertByBo(bo));
    }

    /**
     * 修改知识库检索日志表
     */
    @SaCheckPermission("ai:kbRetrievalLog:edit")
    @Log(title = "知识库检索日志表", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    @Operation(summary = "修改知识库检索日志表",operationId = "KbRetrievalLog_edit")
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody KbRetrievalLogBo bo) {
        return toAjax(kbRetrievalLogService.updateByBo(bo));
    }

    /**
     * 删除知识库检索日志表
     *
     * @param logIds 主键串
     */
    @SaCheckPermission("ai:kbRetrievalLog:remove")
    @Log(title = "知识库检索日志表", businessType = BusinessType.DELETE)
    @DeleteMapping("/{logIds}")
    @Operation(summary = "删除知识库检索日志表",operationId = "KbRetrievalLog_remove")
    public R<Void> remove(@NotEmpty(message = "主键不能为空")
                          @PathVariable @PathIds Long[] logIds) {
        return toAjax(kbRetrievalLogService.deleteWithValidByIds(List.of(logIds), true));
    }
}
