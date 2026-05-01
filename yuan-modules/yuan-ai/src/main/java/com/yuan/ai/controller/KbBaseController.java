package com.yuan.ai.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.yuan.ai.domain.bo.KbBaseBo;
import com.yuan.ai.domain.vo.KbBaseVo;
import com.yuan.ai.service.KbBaseService;
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
 * 知识库主表
 *
 * @author yuan
 * @date Fri May 01 16:08:21 CST 2026
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/ai/kbBase")
@Tag(name = "KbBaseController",description = "知识库主表")
public class KbBaseController extends BaseController {

    private final KbBaseService kbBaseService;

/**
 * 查询知识库主表列表
 */
@SaCheckPermission("ai:kbBase:list")
@GetMapping("/list")
@Operation(summary = "查询知识库主表列表",operationId = "KbBase_list")
    public TableDataInfo<KbBaseVo> list(KbBaseBo bo, PageQuery pageQuery) {
        return kbBaseService.queryPageList(bo, pageQuery);
    }

    /**
     * 导出知识库主表列表
     */
    @SaCheckPermission("ai:kbBase:export")
    @Log(title = "知识库主表", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @Operation(summary = "导出知识库主表列表",operationId = "KbBase_export")
    public void export(KbBaseBo bo, HttpServletResponse response) {
        List<KbBaseVo> list = kbBaseService.queryList(bo);
        ExcelUtil.exportExcel(list, "知识库主表", KbBaseVo.class, response);
    }

    /**
     * 获取知识库主表详细信息
     *
     * @param kbId 主键
     */
    @SaCheckPermission("ai:kbBase:query")
    @GetMapping("/{kbId}")
    @Operation(summary = "获取知识库主表详细信息",operationId = "KbBase_getInfo")
    public R<KbBaseVo> getInfo(@NotNull(message = "主键不能为空")
                                         @PathId @PathVariable Long kbId) {
        return R.ok(kbBaseService.queryById(kbId));
    }

    /**
     * 新增知识库主表
     */
    @SaCheckPermission("ai:kbBase:add")
    @Log(title = "知识库主表", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    @Operation(summary = "新增知识库主表",operationId = "KbBase_add")
    public R<Void> add(@Validated(AddGroup.class) @RequestBody KbBaseBo bo) {
        return toAjax(kbBaseService.insertByBo(bo));
    }

    /**
     * 修改知识库主表
     */
    @SaCheckPermission("ai:kbBase:edit")
    @Log(title = "知识库主表", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    @Operation(summary = "修改知识库主表",operationId = "KbBase_edit")
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody KbBaseBo bo) {
        return toAjax(kbBaseService.updateByBo(bo));
    }

    /**
     * 删除知识库主表
     *
     * @param kbIds 主键串
     */
    @SaCheckPermission("ai:kbBase:remove")
    @Log(title = "知识库主表", businessType = BusinessType.DELETE)
    @DeleteMapping("/{kbIds}")
    @Operation(summary = "删除知识库主表",operationId = "KbBase_remove")
    public R<Void> remove(@NotEmpty(message = "主键不能为空")
                          @PathVariable @PathIds Long[] kbIds) {
        return toAjax(kbBaseService.deleteWithValidByIds(List.of(kbIds), true));
    }
}
