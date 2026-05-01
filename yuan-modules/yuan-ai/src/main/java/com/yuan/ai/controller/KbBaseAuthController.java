package com.yuan.ai.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.yuan.ai.domain.bo.KbBaseAuthBo;
import com.yuan.ai.domain.vo.KbBaseAuthVo;
import com.yuan.ai.service.KbBaseAuthService;
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
 * 知识库授权表
 *
 * @author yuan
 * @date Fri May 01 16:08:26 CST 2026
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/ai/kbBaseAuth")
@Tag(name = "KbBaseAuthController",description = "知识库授权表")
public class KbBaseAuthController extends BaseController {

    private final KbBaseAuthService kbBaseAuthService;

/**
 * 查询知识库授权表列表
 */
@SaCheckPermission("ai:kbBaseAuth:list")
@GetMapping("/list")
@Operation(summary = "查询知识库授权表列表",operationId = "KbBaseAuth_list")
    public TableDataInfo<KbBaseAuthVo> list(KbBaseAuthBo bo, PageQuery pageQuery) {
        return kbBaseAuthService.queryPageList(bo, pageQuery);
    }

    /**
     * 导出知识库授权表列表
     */
    @SaCheckPermission("ai:kbBaseAuth:export")
    @Log(title = "知识库授权表", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @Operation(summary = "导出知识库授权表列表",operationId = "KbBaseAuth_export")
    public void export(KbBaseAuthBo bo, HttpServletResponse response) {
        List<KbBaseAuthVo> list = kbBaseAuthService.queryList(bo);
        ExcelUtil.exportExcel(list, "知识库授权表", KbBaseAuthVo.class, response);
    }

    /**
     * 获取知识库授权表详细信息
     *
     * @param authId 主键
     */
    @SaCheckPermission("ai:kbBaseAuth:query")
    @GetMapping("/{authId}")
    @Operation(summary = "获取知识库授权表详细信息",operationId = "KbBaseAuth_getInfo")
    public R<KbBaseAuthVo> getInfo(@NotNull(message = "主键不能为空")
                                         @PathId @PathVariable Long authId) {
        return R.ok(kbBaseAuthService.queryById(authId));
    }

    /**
     * 新增知识库授权表
     */
    @SaCheckPermission("ai:kbBaseAuth:add")
    @Log(title = "知识库授权表", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    @Operation(summary = "新增知识库授权表",operationId = "KbBaseAuth_add")
    public R<Void> add(@Validated(AddGroup.class) @RequestBody KbBaseAuthBo bo) {
        return toAjax(kbBaseAuthService.insertByBo(bo));
    }

    /**
     * 修改知识库授权表
     */
    @SaCheckPermission("ai:kbBaseAuth:edit")
    @Log(title = "知识库授权表", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    @Operation(summary = "修改知识库授权表",operationId = "KbBaseAuth_edit")
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody KbBaseAuthBo bo) {
        return toAjax(kbBaseAuthService.updateByBo(bo));
    }

    /**
     * 删除知识库授权表
     *
     * @param authIds 主键串
     */
    @SaCheckPermission("ai:kbBaseAuth:remove")
    @Log(title = "知识库授权表", businessType = BusinessType.DELETE)
    @DeleteMapping("/{authIds}")
    @Operation(summary = "删除知识库授权表",operationId = "KbBaseAuth_remove")
    public R<Void> remove(@NotEmpty(message = "主键不能为空")
                          @PathVariable @PathIds Long[] authIds) {
        return toAjax(kbBaseAuthService.deleteWithValidByIds(List.of(authIds), true));
    }
}
