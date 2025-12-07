package com.yuan.system.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.yuan.common.core.domain.R;
import com.yuan.common.core.validate.AddGroup;
import com.yuan.common.core.validate.EditGroup;
import com.yuan.common.excel.utils.ExcelUtil;
import com.yuan.common.idempotent.annotation.RepeatSubmit;
import com.yuan.common.log.annotation.Log;
import com.yuan.common.log.enums.BusinessType;
import com.yuan.common.web.core.BaseController;
import com.yuan.core.page.PageQuery;
import com.yuan.core.page.TableDataInfo;
import com.yuan.system.domain.bo.SysUserBo;
import com.yuan.system.domain.vo.SysUserVo;
import com.yuan.system.service.SysUserService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 用户
 *
 * @author ageerle
 * @date Sun Dec 07 17:25:38 CST 2025
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/system/sysUser")
public class SysUserController extends BaseController {

    private final SysUserService sysUserService;

/**
 * 查询用户列表
 */
@SaCheckPermission("system:sysUser:list")
@GetMapping("/list")
    public TableDataInfo<SysUserVo> list(SysUserBo bo, PageQuery pageQuery) {
        return sysUserService.queryPageList(bo, pageQuery);
    }

    /**
     * 导出用户列表
     */
    @SaCheckPermission("system:sysUser:export")
    @Log(title = "用户", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(SysUserBo bo, HttpServletResponse response) {
        List<SysUserVo> list = sysUserService.queryList(bo);
        ExcelUtil.exportExcel(list, "用户", SysUserVo.class, response);
    }

    /**
     * 获取用户详细信息
     *
     * @param userId 主键
     */
    @SaCheckPermission("system:sysUser:query")
    @GetMapping("/{userId}")
    public R<SysUserVo> getInfo(@NotNull(message = "主键不能为空")
                                     @PathVariable Long userId) {
        return R.ok(sysUserService.queryById(userId));
    }

    /**
     * 新增用户
     */
    @SaCheckPermission("system:sysUser:add")
    @Log(title = "用户", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public R<Void> add(@Validated(AddGroup.class) @RequestBody SysUserBo bo) {
        return toAjax(sysUserService.insertByBo(bo));
    }

    /**
     * 修改用户
     */
    @SaCheckPermission("system:sysUser:edit")
    @Log(title = "用户", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody SysUserBo bo) {
        return toAjax(sysUserService.updateByBo(bo));
    }

    /**
     * 删除用户
     *
     * @param userIds 主键串
     */
    @SaCheckPermission("system:sysUser:remove")
    @Log(title = "用户", businessType = BusinessType.DELETE)
    @DeleteMapping("/{userIds}")
    public R<Void> remove(@NotEmpty(message = "主键不能为空")
                          @PathVariable Long[] userIds) {
        return toAjax(sysUserService.deleteWithValidByIds(List.of(userIds), true));
    }
}
