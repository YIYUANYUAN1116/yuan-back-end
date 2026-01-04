package com.yuan.system.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.hutool.core.util.ObjectUtil;
import com.yuan.common.core.domain.R;
import com.yuan.common.doc.annotation.PathId;
import com.yuan.common.doc.annotation.PathIds;
import com.yuan.common.excel.utils.ExcelUtil;
import com.yuan.common.log.annotation.Log;
import com.yuan.common.log.enums.BusinessType;
import com.yuan.common.web.core.BaseController;
import com.yuan.core.page.PageQuery;
import com.yuan.core.page.TableDataInfo;
import com.yuan.system.domain.bo.SysDictDataBo;
import com.yuan.system.domain.vo.SysDictDataVo;
import com.yuan.system.service.ISysDictDataService;
import com.yuan.system.service.ISysDictTypeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * 数据字典信息
 *
 * 
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/system/dict/data")
@Tag(name = "SysDictDataController",description = "数据字典信息")
public class SysDictDataController extends BaseController {

    private final ISysDictDataService dictDataService;
    private final ISysDictTypeService dictTypeService;

    /**
     * 查询字典数据列表
     */
    @SaCheckPermission("system:dict:list")
    @GetMapping("/list")
    @Operation(summary = "查询字典数据列表",operationId = "dict_list")
    public TableDataInfo<SysDictDataVo> list(SysDictDataBo bo, PageQuery pageQuery) {
        return dictDataService.selectPageDictDataList(bo, pageQuery);
    }

    /**
     * 导出字典数据列表
     */
    @Log(title = "字典数据", businessType = BusinessType.EXPORT)
    @SaCheckPermission("system:dict:export")
    @PostMapping("/export")
    @Operation(summary = "查询字典数据列表",operationId = "dict_export")
    public void export(SysDictDataBo bo, HttpServletResponse response) {
        List<SysDictDataVo> list = dictDataService.selectDictDataList(bo);
        ExcelUtil.exportExcel(list, "字典数据", SysDictDataVo.class, response);
    }

    /**
     * 查询字典数据详细
     *
     * @param dictCode 字典code
     */
    @SaCheckPermission("system:dict:query")
    @GetMapping(value = "/{dictCode}")
    @Operation(summary = "查询字典数据列表",operationId = "dict_getInfo")
    public R<SysDictDataVo> getInfo(@PathVariable @PathId Long dictCode) {
        return R.ok(dictDataService.selectDictDataById(dictCode));
    }

    /**
     * 根据字典类型查询字典数据信息
     *
     * @param dictType 字典类型
     */
    @GetMapping(value = "/type/{dictType}")
    @Operation(summary = "根据字典类型查询字典数据信息",operationId = "dict_dictType")
    public R<List<SysDictDataVo>> dictType(@PathVariable String dictType) {
        List<SysDictDataVo> data = dictTypeService.selectDictDataByType(dictType);
        if (ObjectUtil.isNull(data)) {
            data = new ArrayList<>();
        }
        return R.ok(data);
    }

    /**
     * 新增字典类型
     */
    @SaCheckPermission("system:dict:add")
    @Log(title = "字典数据", businessType = BusinessType.INSERT)
    @PostMapping
    @Operation(summary = "新增字典",operationId = "dict_add")
    public R<Void> add(@Validated @RequestBody SysDictDataBo bo) {
        if (!dictDataService.checkDictLabelUnique(bo)) {
            return R.fail("新增字典'" + bo.getDictLabel() + "'失败，字典名称已存在");
        } else if (!dictDataService.checkDictValueUnique(bo)) {
            return R.fail("新增字典'" + bo.getDictValue() + "'失败，字典值已存在");
        }
        dictDataService.insertDictData(bo);
        return R.ok();
    }

    /**
     * 修改保存字典类型
     */
    @SaCheckPermission("system:dict:edit")
    @Log(title = "字典数据", businessType = BusinessType.UPDATE)
    @PutMapping
    @Operation(summary = "修改保存字典",operationId = "dict_edit")
    public R<Void> edit(@Validated @RequestBody SysDictDataBo bo) {
        if (!dictDataService.checkDictLabelUnique(bo)) {
            return R.fail("修改字典'" + bo.getDictLabel() + "'失败，字典名称已存在");
        } else if (!dictDataService.checkDictValueUnique(bo)) {
            return R.fail("修改字典'" + bo.getDictValue() + "'失败，字典值已存在");
        }
        dictDataService.updateDictData(bo);
        return R.ok();
    }

    /**
     * 删除字典类型
     *
     * @param dictCodes 字典code串
     */
    @SaCheckPermission("system:dict:remove")
    @Log(title = "字典类型", businessType = BusinessType.DELETE)
    @Operation(summary = "删除字典类型",operationId = "dict_remove")
    @DeleteMapping("/{dictCodes}")
    public R<Void> remove(@PathVariable @PathIds Long[] dictCodes) {
        dictDataService.deleteDictDataByIds(dictCodes);
        return R.ok();
    }
}

