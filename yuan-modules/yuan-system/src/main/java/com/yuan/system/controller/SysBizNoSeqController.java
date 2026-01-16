package com.yuan.system.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
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
import com.yuan.system.domain.bo.SysBizNoSeqBo;
import com.yuan.system.domain.vo.SysBizNoSeqVo;
import com.yuan.system.service.SysBizNoSeqService;
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
 * 业务序列表
 *
 * @author yuan
 * @date Fri Jan 16 16:53:43 CST 2026
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/system/sysBizNoSeq")
@Tag(name = "SysBizNoSeqController",description = "业务序列表")
public class SysBizNoSeqController extends BaseController {

    private final SysBizNoSeqService sysBizNoSeqService;

/**
 * 查询业务序列表列表
 */
@SaCheckPermission("system:sysBizNoSeq:list")
@GetMapping("/list")
@Operation(summary = "查询业务序列表列表",operationId = "SysBizNoSeq_list")
    public TableDataInfo<SysBizNoSeqVo> list(SysBizNoSeqBo bo, PageQuery pageQuery) {
        return sysBizNoSeqService.queryPageList(bo, pageQuery);
    }

    /**
     * 导出业务序列表列表
     */
    @SaCheckPermission("system:sysBizNoSeq:export")
    @Log(title = "业务序列表", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @Operation(summary = "导出业务序列表列表",operationId = "SysBizNoSeq_export")
    public void export(SysBizNoSeqBo bo, HttpServletResponse response) {
        List<SysBizNoSeqVo> list = sysBizNoSeqService.queryList(bo);
        ExcelUtil.exportExcel(list, "业务序列表", SysBizNoSeqVo.class, response);
    }

    /**
     * 获取业务序列表详细信息
     *
     * @param id 主键
     */
    @SaCheckPermission("system:sysBizNoSeq:query")
    @GetMapping("/{id}")
    @Operation(summary = "获取业务序列表详细信息",operationId = "SysBizNoSeq_getInfo")
    public R<SysBizNoSeqVo> getInfo(@NotNull(message = "主键不能为空")
                                         @PathId @PathVariable Long id) {
        return R.ok(sysBizNoSeqService.queryById(id));
    }

    /**
     * 新增业务序列表
     */
    @SaCheckPermission("system:sysBizNoSeq:add")
    @Log(title = "业务序列表", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    @Operation(summary = "新增业务序列表",operationId = "SysBizNoSeq_add")
    public R<Void> add(@Validated(AddGroup.class) @RequestBody SysBizNoSeqBo bo) {
        return toAjax(sysBizNoSeqService.insertByBo(bo));
    }

    /**
     * 修改业务序列表
     */
    @SaCheckPermission("system:sysBizNoSeq:edit")
    @Log(title = "业务序列表", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    @Operation(summary = "修改业务序列表",operationId = "SysBizNoSeq_edit")
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody SysBizNoSeqBo bo) {
        return toAjax(sysBizNoSeqService.updateByBo(bo));
    }

    /**
     * 删除业务序列表
     *
     * @param ids 主键串
     */
    @SaCheckPermission("system:sysBizNoSeq:remove")
    @Log(title = "业务序列表", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    @Operation(summary = "删除业务序列表",operationId = "SysBizNoSeq_remove")
    public R<Void> remove(@NotEmpty(message = "主键不能为空")
                          @PathVariable @PathIds Long[] ids) {
        return toAjax(sysBizNoSeqService.deleteWithValidByIds(List.of(ids), true));
    }
}
