package com.yuan.system.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
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
import com.yuan.system.domain.bo.SysTenantBo;
import com.yuan.system.domain.vo.SysTenantVo;
import com.yuan.system.service.SysTenantService;
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
 * 多租户
 *
 * @author ageerle
 * @date Wed Dec 10 17:18:08 CST 2025
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/system/sysTenant")
public class SysTenantController extends BaseController {

    private final SysTenantService sysTenantService;

/**
 * 查询多租户列表
 */
@SaCheckPermission("system:sysTenant:list")
@GetMapping("/list")
    public TableDataInfo<SysTenantVo> list(SysTenantBo bo, PageQuery pageQuery) {
        return sysTenantService.queryPageList(bo, pageQuery);
    }

    /**
     * 导出多租户列表
     */
    @SaCheckPermission("system:sysTenant:export")
    @Log(title = "多租户", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(SysTenantBo bo, HttpServletResponse response) {
        List<SysTenantVo> list = sysTenantService.queryList(bo);
        ExcelUtil.exportExcel(list, "多租户", SysTenantVo.class, response);
    }

    /**
     * 获取多租户详细信息
     *
     * @param id 主键
     */
    @SaCheckPermission("system:sysTenant:query")
    @GetMapping("/{id}")
    public R<SysTenantVo> getInfo(@NotNull(message = "主键不能为空")
                                     @PathVariable Long id) {
        return R.ok(sysTenantService.queryById(id));
    }

    /**
     * 新增多租户
     */
    @SaCheckPermission("system:sysTenant:add")
    @Log(title = "多租户", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public R<Void> add(@Validated(AddGroup.class) @RequestBody SysTenantBo bo) {
        return toAjax(sysTenantService.insertByBo(bo));
    }

    /**
     * 修改多租户
     */
    @SaCheckPermission("system:sysTenant:edit")
    @Log(title = "多租户", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody SysTenantBo bo) {
        return toAjax(sysTenantService.updateByBo(bo));
    }

    /**
     * 删除多租户
     *
     * @param ids 主键串
     */
    @SaCheckPermission("system:sysTenant:remove")
    @Log(title = "多租户", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public R<Void> remove(@NotEmpty(message = "主键不能为空")
                          @PathVariable Long[] ids) {
        return toAjax(sysTenantService.deleteWithValidByIds(List.of(ids), true));
    }
}
