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
import com.yuan.generator.domain.bo.SchemaFieldBo;
import com.yuan.generator.domain.vo.SchemaFieldVo;
import com.yuan.generator.service.SchemaFieldService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 数据模型字段
 *
 * @author ruoyi
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/dev/schemaField")
@Tag(name = "SchemaFieldController",description = "数据模型字段")
public class SchemaFieldController extends BaseController {

    private final SchemaFieldService schemaFieldService;

    /**
     * 查询数据模型字段列表
     */
    @SaCheckPermission("dev:schemaField:list")
    @GetMapping("/list")
    @Operation(summary = "查询数据模型字段列表",operationId = "schemaField_list")
    public TableDataInfo<SchemaFieldVo> list(SchemaFieldBo bo, PageQuery pageQuery) {
        return schemaFieldService.queryPageList(bo, pageQuery);
    }

    /**
     * 获取数据模型字段详细信息
     *
     * @param id 主键
     */
    @SaCheckPermission("dev:schemaField:query")
    @GetMapping("/{id}")
    @Operation(summary = "获取数据模型字段详细信息",operationId = "schemaField_getInfo")
    public R<SchemaFieldVo> getInfo(@NotNull(message = "主键不能为空")
                                    @PathVariable Long id) {
        return R.ok(schemaFieldService.queryById(id));
    }

    /**
     * 新增数据模型字段
     */
    @SaCheckPermission("dev:schemaField:add")
    @Log(title = "数据模型字段", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    @Operation(summary = "新增数据模型字段",operationId = "schemaField_add")
    public R<Void> add(@Validated(AddGroup.class) @RequestBody SchemaFieldBo bo) {
        return toAjax(schemaFieldService.insertByBo(bo));
    }

    /**
     * 修改数据模型字段
     */
    @SaCheckPermission("dev:schemaField:edit")
    @Log(title = "数据模型字段", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    @Operation(summary = "修改数据模型字段",operationId = "schemaField_edit")
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody SchemaFieldBo bo) {
        return toAjax(schemaFieldService.updateByBo(bo));
    }

    /**
     * 删除数据模型字段
     *
     * @param ids 主键串
     */
    @SaCheckPermission("dev:schemaField:remove")
    @Log(title = "数据模型字段", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    @Operation(summary = "删除数据模型字段",operationId = "schemaField_remove")
    public R<Void> remove(@NotEmpty(message = "主键不能为空")
                          @PathVariable Long[] ids) {
        return toAjax(schemaFieldService.deleteWithValidByIds(List.of(ids), true));
    }

    /**
     * 批量更新字段配置
     *
     * @param fields 字段配置列表
     */
    @SaCheckPermission("dev:schemaField:edit")
    @Log(title = "批量更新字段配置", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping("/batchUpdate")
    @Operation(summary = "批量更新字段配置",operationId = "schemaField_batchUpdateFieldConfig")
    public R<Void> batchUpdateFieldConfig(@Validated(EditGroup.class) @RequestBody List<SchemaFieldBo> fields) {
        return toAjax(schemaFieldService.batchUpdateFieldConfig(fields));
    }

    /**
     * 根据模型ID查询字段列表
     *
     * @param schemaId 模型ID
     */
    @SaCheckPermission("dev:schemaField:list")
    @GetMapping("/listBySchemaId/{schemaId}")
    @Operation(summary = "根据模型ID查询字段列表",operationId = "schemaField_listBySchemaId")
    public R<List<SchemaFieldVo>> listBySchemaId(@NotNull(message = "模型ID不能为空") @PathVariable Long schemaId) {
        return R.ok(schemaFieldService.queryListBySchemaId(schemaId));
    }
}