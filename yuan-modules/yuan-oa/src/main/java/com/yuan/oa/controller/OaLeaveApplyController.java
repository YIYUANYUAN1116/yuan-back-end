package com.yuan.oa.controller;

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
import com.yuan.oa.domain.bo.OaLeaveApplyBo;
import com.yuan.oa.domain.vo.OaLeaveApplyVo;
import com.yuan.oa.service.OaLeaveApplyService;
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
 * 请假申请
 *
 * @author yuan
 * @date Fri Jan 16 13:46:51 CST 2026
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/oa/leave")
@Tag(name = "OaLeaveApplyController",description = "请假申请")
public class OaLeaveApplyController extends BaseController {

    private final OaLeaveApplyService oaLeaveApplyService;

/**
 * 查询请假申请列表
 */
@SaCheckPermission("oa:leave:list")
@GetMapping("/list")
@Operation(summary = "查询请假申请列表",operationId = "OaLeaveApply_list")
    public TableDataInfo<OaLeaveApplyVo> list(OaLeaveApplyBo bo, PageQuery pageQuery) {
        return oaLeaveApplyService.queryPageList(bo, pageQuery);
    }

    /**
     * 导出请假申请列表
     */
    @SaCheckPermission("oa:leave:export")
    @Log(title = "请假申请", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @Operation(summary = "导出请假申请列表",operationId = "OaLeaveApply_export")
    public void export(OaLeaveApplyBo bo, HttpServletResponse response) {
        List<OaLeaveApplyVo> list = oaLeaveApplyService.queryList(bo);
        ExcelUtil.exportExcel(list, "请假申请", OaLeaveApplyVo.class, response);
    }

    /**
     * 获取请假申请详细信息
     *
     * @param id 主键
     */
    @SaCheckPermission("oa:leave:query")
    @GetMapping("/{id}")
    @Operation(summary = "获取请假申请详细信息",operationId = "OaLeaveApply_getInfo")
    public R<OaLeaveApplyVo> getInfo(@NotNull(message = "主键不能为空")
                                         @PathId @PathVariable Long id) {
        return R.ok(oaLeaveApplyService.queryById(id));
    }

    /**
     * 获取请假申请详细信息
     *
     */
    @SaCheckPermission("oa:leave:query")
    @GetMapping("/bizNo/{bizNo}")
    @Operation(summary = "获取请假申请详细信息",operationId = "OaLeaveApply_getInfoByBizNo")
    public R<OaLeaveApplyVo> getInfoByBizNo(@NotNull(message = "主键不能为空")
                                     @PathVariable String bizNo) {
        return R.ok(oaLeaveApplyService.queryByBizNo(bizNo));
    }

    /**
     * 新增请假申请
     */
    @SaCheckPermission("oa:leave:add")
    @Log(title = "请假申请", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    @Operation(summary = "新增请假申请",operationId = "OaLeaveApply_add")
    public R<Void> add(@Validated(AddGroup.class) @RequestBody OaLeaveApplyBo bo) {
        return toAjax(oaLeaveApplyService.insertByBo(bo));
    }

    /**
     * 修改请假申请
     */
    @SaCheckPermission("oa:leave:edit")
    @Log(title = "请假申请", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    @Operation(summary = "修改请假申请",operationId = "OaLeaveApply_edit")
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody OaLeaveApplyBo bo) {
        return toAjax(oaLeaveApplyService.updateByBo(bo));
    }

    /**
     * 删除请假申请
     *
     * @param ids 主键串
     */
    @SaCheckPermission("oa:leave:remove")
    @Log(title = "请假申请", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    @Operation(summary = "删除请假申请",operationId = "OaLeaveApply_remove")
    public R<Void> remove(@NotEmpty(message = "主键不能为空")
                          @PathVariable @PathIds Long[] ids) {
        return toAjax(oaLeaveApplyService.deleteWithValidByIds(List.of(ids), true));
    }

    @SaCheckPermission("oa:leave:submit")
    @Log(title = "发起请假申请", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @GetMapping("/submit")
    @Operation(summary = "发起请假申请",operationId = "OaLeaveApply_submit")
    public R<Void> submit(@RequestParam String bizNo) {
        return toAjax(oaLeaveApplyService.submit(bizNo));
    }
}
