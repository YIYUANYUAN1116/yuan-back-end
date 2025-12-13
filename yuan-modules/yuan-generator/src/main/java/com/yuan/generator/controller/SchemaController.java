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
import com.yuan.generator.domain.bo.SchemaBo;
import com.yuan.generator.domain.vo.SchemaVo;
import com.yuan.generator.service.SchemaService;
import com.yuan.helper.DataBaseHelper;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 数据模型
 *
 * @author ruoyi
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/dev/schema")
@Tag(name = "SchemaController",description = "数据模型")
public class SchemaController extends BaseController {

    private final SchemaService schemaService;

    /**
     * 查询数据模型列表
     */
    @SaCheckPermission("dev:schema:list")
    @GetMapping("/list")
    @Operation(summary = "查询数据模型列表",operationId = "schema_list")
    public TableDataInfo<SchemaVo> list(SchemaBo bo, PageQuery pageQuery) {
        return schemaService.queryPageList(bo, pageQuery);
    }

    /**
     * 获取数据模型详细信息
     *
     * @param id 主键
     */
    @SaCheckPermission("dev:schema:query")
    @GetMapping("/{id}")
    @Operation(summary = "获取数据模型详细信息",operationId = "schema_getInfo")

    public R<SchemaVo> getInfo(@NotNull(message = "主键不能为空") @PathVariable Long id) {
        return R.ok(schemaService.queryById(id));
    }

    /**
     * 新增数据模型
     */
    @SaCheckPermission("dev:schema:add")
    @Log(title = "数据模型", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    @Operation(summary = "新增数据模型",operationId = "schema_add")

    public R<Void> add(@Validated(AddGroup.class) @RequestBody SchemaBo bo) {
        return toAjax(schemaService.insertByBo(bo));
    }

    /**
     * 修改数据模型
     */
    @SaCheckPermission("dev:schema:edit")
    @Log(title = "数据模型", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    @Operation(summary = "修改数据模型",operationId = "schema_edit")
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody SchemaBo bo) {
        return toAjax(schemaService.updateByBo(bo));
    }

    /**
     * 删除数据模型
     *
     * @param ids 主键串
     */
    @SaCheckPermission("dev:schema:remove")
    @Log(title = "数据模型", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    @Operation(summary = "删除数据模型",operationId = "schema_remove")
    public R<Void> remove(@NotEmpty(message = "主键不能为空") @PathVariable Long[] ids) {
        return toAjax(schemaService.deleteWithValidByIds(List.of(ids), true));
    }

    /**
     * 查询数据源表名
     */
    @SaCheckPermission("dev:schema:getTableNameList")
    @GetMapping(value = "/getDataNames")
    @Operation(summary = "查询数据源表名",operationId = "schema_getCurrentDataSourceTableNameList")
    public R<Object> getCurrentDataSourceTableNameList() {
        return R.ok(DataBaseHelper.getCurrentDataSourceTableNameList());
    }
}