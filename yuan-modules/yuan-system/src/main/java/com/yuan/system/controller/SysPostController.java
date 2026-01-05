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
import com.yuan.system.domain.bo.SysPostBo;
import com.yuan.system.domain.bo.SysUserBo;
import com.yuan.system.domain.vo.SysPostVo;
import com.yuan.system.domain.vo.SysUserVo;
import com.yuan.system.service.SysPostService;
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
 * post
 *

 * @date Mon Dec 22 15:05:40 CST 2025
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/system/sysPost")
@Tag(name = "SysPostController",description = "post")
public class SysPostController extends BaseController {

    private final SysPostService sysPostService;

/**
 * 查询岗位列表
 */
@SaCheckPermission("system:sysPost:list")
@GetMapping("/list")
@Operation(summary = "查询岗位列表",operationId = "SysPost_list")
    public TableDataInfo<SysPostVo> list(SysPostBo bo, PageQuery pageQuery) {
        return sysPostService.queryPageList(bo, pageQuery);
    }

    /**
     * 导出岗位列表
     */
    @SaCheckPermission("system:sysPost:export")
    @Log(title = "post", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @Operation(summary = "导出岗位列表",operationId = "SysPost_export")
    public void export(SysPostBo bo, HttpServletResponse response) {
        List<SysPostVo> list = sysPostService.queryList(bo);
        ExcelUtil.exportExcel(list, "post", SysPostVo.class, response);
    }

    /**
     * 获取post详细信息
     *
     * @param postId 主键
     */
    @SaCheckPermission("system:sysPost:query")
    @GetMapping("/{postId}")
    @Operation(summary = "获取岗位详细信息",operationId = "SysPost_getInfo")
    public R<SysPostVo> getInfo(@NotNull(message = "主键不能为空")
                                     @PathVariable @PathId Long postId) {
        return R.ok(sysPostService.queryById(postId));
    }

    /**
     * 新增post
     */
    @SaCheckPermission("system:sysPost:add")
    @Log(title = "post", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    @Operation(summary = "新增岗位",operationId = "SysPost_add")
    public R<Void> add(@Validated(AddGroup.class) @RequestBody SysPostBo bo) {
        if (!sysPostService.checkPostNameUnique(bo)) {
            return R.fail("新增岗位'" + bo.getPostName() + "'失败，岗位名称已存在");
        } else if (!sysPostService.checkPostKeyUnique(bo)) {
            return R.fail("新增岗位'" + bo.getPostCode() + "'失败，岗位编码已存在");
        }
        return toAjax(sysPostService.insertByBo(bo));
    }

    /**
     * 修改post
     */
    @SaCheckPermission("system:sysPost:edit")
    @Log(title = "post", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    @Operation(summary = "修改岗位",operationId = "SysPost_edit")
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody SysPostBo bo) {
        if (!sysPostService.checkPostNameUnique(bo)) {
            return R.fail("修改岗位'" + bo.getPostName() + "'失败，岗位名称已存在");
        } else if (!sysPostService.checkPostKeyUnique(bo)) {
            return R.fail("修改岗位'" + bo.getPostCode() + "'失败，岗位编码已存在");
        }
        return toAjax(sysPostService.updateByBo(bo));
    }

    /**
     * 删除post
     *
     * @param postIds 主键串
     */
    @SaCheckPermission("system:sysPost:remove")
    @Log(title = "post", businessType = BusinessType.DELETE)
    @DeleteMapping("/{postIds}")
    @Operation(summary = "删除岗位",operationId = "SysPost_remove")
    public R<Void> remove(@NotEmpty(message = "主键不能为空")
                          @PathVariable @PathIds Long[] postIds) {
        return toAjax(sysPostService.deleteWithValidByIds(List.of(postIds), true));
    }

    /**
     * 查询已分配用户岗位列表
     */
    @SaCheckPermission("system:sysPost:list")
    @GetMapping("/allocatedList")
    @Operation(summary = "获取岗位已分配用户列表",operationId = "postAllocatedUserList")
    public TableDataInfo<SysUserVo> allocatedUserList(SysUserBo bo, PageQuery pageQuery) {
        return sysPostService.selectAllocatedUserList(bo, pageQuery);
    }

    @SaCheckPermission("system:sysPost:query")
    @GetMapping("/byUser/{userId}")
    @Operation(summary = "获取用户岗位详细信息",operationId = "SysPost_getByUserId")
    public R<List<SysPostVo>> getByUserId(@NotNull(message = "主键不能为空")
                                @PathVariable @PathId Long userId) {
        return R.ok(sysPostService.queryByUserId(userId));
    }

    @SaCheckPermission("system:sysPost:query")
    @GetMapping("/dept/{deptId}")
    @Operation(summary = "根据部门获取岗位",operationId = "SysPost_getByDeptId")
    public R<List<SysPostVo>> getByDeptId(@NotNull(message = "主键不能为空")
                                          @PathVariable @PathId Long deptId) {
        return R.ok(sysPostService.getByDeptId(deptId));
    }

    /**
     * 岗位授权角色
     *
     * @param postId  用户Id
     * @param roleIds 角色ID串
     */
    @SaCheckPermission("system:sysPost:edit")
    @Log(title = "岗位管理", businessType = BusinessType.GRANT)
    @PutMapping("/insertPostRole")
    @Operation(summary = "岗位授权角色",operationId = "sysPost_insertPostRole")
    public R<Void> insertPostRole(@PathId Long postId,@PathIds Long[] roleIds) {
        sysPostService.insertPostRole(postId, roleIds);
        return R.ok();
    }
}
