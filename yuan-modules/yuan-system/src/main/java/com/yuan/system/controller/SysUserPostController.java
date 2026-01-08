package com.yuan.system.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.yuan.common.core.domain.R;
import com.yuan.common.core.validate.AddGroup;
import com.yuan.common.core.validate.EditGroup;
import com.yuan.common.doc.annotation.PathId;
import com.yuan.common.excel.utils.ExcelUtil;
import com.yuan.common.idempotent.annotation.RepeatSubmit;
import com.yuan.common.log.annotation.Log;
import com.yuan.common.log.enums.BusinessType;
import com.yuan.common.web.core.BaseController;
import com.yuan.core.page.PageQuery;
import com.yuan.core.page.TableDataInfo;
import com.yuan.system.domain.bo.SysUserPostBo;
import com.yuan.system.domain.vo.SysUserPostVo;
import com.yuan.system.service.SysUserPostService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * post-user
 *
 
 * @date Mon Dec 22 15:05:48 CST 2025
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/system/sysUserPost")
@Tag(name = "SysUserPostController",description = "post-user")
public class SysUserPostController extends BaseController {

    private final SysUserPostService sysUserPostService;

/**
 * 查询post-user列表
 */
@SaCheckPermission("system:userpost:list")
@GetMapping("/list")
@Operation(summary = "查询post-user列表",operationId = "SysUserPost_list")
    public TableDataInfo<SysUserPostVo> list(SysUserPostBo bo, PageQuery pageQuery) {
        return sysUserPostService.queryPageList(bo, pageQuery);
    }

    /**
     * 导出post-user列表
     */
    @SaCheckPermission("system:userpost:export")
    @Log(title = "post-user", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @Operation(summary = "导出post-user列表",operationId = "SysUserPost_export")
    public void export(SysUserPostBo bo, HttpServletResponse response) {
        List<SysUserPostVo> list = sysUserPostService.queryList(bo);
        ExcelUtil.exportExcel(list, "post-user", SysUserPostVo.class, response);
    }

    /**
     * 获取post-user详细信息
     *
     * @param postId 主键
     */
    @SaCheckPermission("system:userpost:query")
    @GetMapping("/{postId}")
    @Operation(summary = "获取post-user详细信息",operationId = "SysUserPost_getInfo")
    public R<SysUserPostVo> getInfo(@NotNull(message = "主键不能为空")
                                     @PathVariable @PathId Long postId) {
        return R.ok(sysUserPostService.queryById(postId));
    }

    /**
     * 新增post-user
     */
    @SaCheckPermission("system:userpost:add")
    @Log(title = "post-user", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    @Operation(summary = "新增post-user",operationId = "SysUserPost_add")
    public R<Void> add(@Validated(AddGroup.class) @RequestBody SysUserPostBo bo) {
        return toAjax(sysUserPostService.insertByBo(bo));
    }

    /**
     * 修改post-user
     */
    @SaCheckPermission("system:userpost:edit")
    @Log(title = "post-user", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    @Operation(summary = "修改post-user",operationId = "SysUserPost_edit")
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody SysUserPostBo bo) {
        return toAjax(sysUserPostService.updateByBo(bo));
    }

    /**
     * 删除post-user
     *
     * @param postIds 主键串
     */
    @SaCheckPermission("system:userpost:remove")
    @Log(title = "post-user", businessType = BusinessType.DELETE)
    @DeleteMapping("/{postIds}")
    @Operation(summary = "删除post-user",operationId = "SysUserPost_remove")
    public R<Void> remove(@NotEmpty(message = "主键不能为空")
                          @PathVariable @PathId Long[] postIds) {
        return toAjax(sysUserPostService.deleteWithValidByIds(List.of(postIds), true));
    }
}
