package com.yuan.generator.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import com.yuan.common.core.domain.R;
import com.yuan.common.core.validate.AddGroup;
import com.yuan.common.core.validate.EditGroup;
import com.yuan.common.idempotent.annotation.RepeatSubmit;
import com.yuan.common.log.annotation.Log;
import com.yuan.common.log.enums.BusinessType;
import com.yuan.common.web.core.BaseController;
import com.yuan.core.page.PageQuery;
import com.yuan.core.page.TableDataInfo;
import com.yuan.generator.domain.bo.SchemaGroupBo;
import com.yuan.generator.domain.vo.SchemaGroupVo;
import com.yuan.generator.service.SchemaGroupService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 数据模型分组
 *
 * @author ruoyi
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/dev/schemaGroup")
@Tag(name = "SchemaFieldController",description = "数据模型分组")
public class SchemaGroupController extends BaseController {

    private final SchemaGroupService schemaGroupService;

    /**
     * 查询数据模型分组列表
     */
    @SaCheckPermission("dev:schemaGroup:list")
    @GetMapping("/list")
    @Operation(summary = "查询数据模型分组列表",operationId = "schemaGroup_list")
    public TableDataInfo<SchemaGroupVo> list(SchemaGroupBo bo, PageQuery pageQuery) {
        return schemaGroupService.queryPageList(bo, pageQuery);
    }

    /**
     * 获取数据模型分组选择列表
     */
    @SaCheckPermission("dev:schemaGroup:select")
    @GetMapping("/select")
    @Operation(summary = "获取数据模型分组选择列表",operationId = "schemaGroup_select")
    public R<List<SchemaGroupVo>> select() {
        SchemaGroupBo bo = new SchemaGroupBo();
        List<SchemaGroupVo> list = schemaGroupService.queryList(bo);
        return R.ok(list);
    }

    /**
     * 获取数据模型分组详细信息
     *
     * @param id 主键
     */
    @SaCheckPermission("dev:schemaGroup:query")
    @GetMapping("/{id}")
    @Operation(summary = "获取数据模型分组详细信息",operationId = "schemaGroup_getInfo")
    public R<SchemaGroupVo> getInfo(@NotNull(message = "主键不能为空") @PathVariable Long id) {
        return R.ok(schemaGroupService.queryById(id));
    }

    /**
     * 新增数据模型分组
     */
    @SaCheckPermission("dev:schemaGroup:add")
    @Log(title = "数据模型分组", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    @Operation(summary = "新增数据模型分组",operationId = "schemaGroup_add")
    public R<Void> add(@Validated(AddGroup.class) @RequestBody SchemaGroupBo bo) {
        return toAjax(schemaGroupService.insertByBo(bo));
    }

    /**
     * 修改数据模型分组
     */
    @SaCheckPermission("dev:schemaGroup:edit")
    @Log(title = "数据模型分组", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    @Operation(summary = "修改数据模型分组",operationId = "schemaGroup_edit")
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody SchemaGroupBo bo) {
        return toAjax(schemaGroupService.updateByBo(bo));
    }

    /**
     * 删除数据模型分组
     *
     * @param ids 主键串
     */
    @SaCheckPermission("dev:schemaGroup:remove")
    @Log(title = "数据模型分组", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    @Operation(summary = "删除数据模型分组",operationId = "schemaGroup_remove")
    public R<Void> remove(@NotEmpty(message = "主键不能为空") @PathVariable Long[] ids) {
        return toAjax(schemaGroupService.deleteWithValidByIds(List.of(ids), true));
    }

}