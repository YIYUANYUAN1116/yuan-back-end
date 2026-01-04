package com.yuan.workflow.controller;

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
import com.yuan.workflow.api.cmd.ApproveTaskCmd;
import com.yuan.workflow.domain.bo.WfTaskBo;
import com.yuan.workflow.domain.vo.WfTaskVo;
import com.yuan.workflow.service.WfTaskService;
import com.yuan.workflow.service.WorkflowService;
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
 * wft
 *
 * @author ageerle
 * @date Sun Dec 28 11:26:41 CST 2025
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/workflow/wfTask")
@Tag(name = "WfTaskController",description = "wft")
public class WfTaskController extends BaseController {

    private final WfTaskService wfTaskService;
    private final WorkflowService workflowService;

/**
 * 查询wft列表
 */
@SaCheckPermission("system:wfTask:list")
@GetMapping("/list")
@Operation(summary = "查询wft列表",operationId = "WfTask_list")
    public TableDataInfo<WfTaskVo> list(WfTaskBo bo, PageQuery pageQuery) {
        return wfTaskService.queryPageList(bo, pageQuery);
    }

    /**
     * 导出wft列表
     */
    @SaCheckPermission("system:wfTask:export")
    @Log(title = "流程任务", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @Operation(summary = "导出wft列表",operationId = "WfTask_export")
    public void export(WfTaskBo bo, HttpServletResponse response) {
        List<WfTaskVo> list = wfTaskService.queryList(bo);
        ExcelUtil.exportExcel(list, "wft", WfTaskVo.class, response);
    }

    /**
     * 获取wft详细信息
     *
     * @param id 主键
     */
    @SaCheckPermission("system:wfTask:query")
    @GetMapping("/{id}")
    @Operation(summary = "获取wft详细信息",operationId = "WfTask_getInfo")
    public R<WfTaskVo> getInfo(@NotNull(message = "主键不能为空")
                                         @PathId @PathVariable Long id) {
        return R.ok(wfTaskService.queryById(id));
    }

    /**
     * 新增wft
     */
    @SaCheckPermission("system:wfTask:add")
    @Log(title = "流程任务", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    @Operation(summary = "新增wft",operationId = "WfTask_add")
    public R<Void> add(@Validated(AddGroup.class) @RequestBody WfTaskBo bo) {
        return toAjax(wfTaskService.insertByBo(bo));
    }

    /**
     * 修改wft
     */
    @SaCheckPermission("system:wfTask:edit")
    @Log(title = "流程任务", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    @Operation(summary = "修改wft",operationId = "WfTask_edit")
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody WfTaskBo bo) {
        return toAjax(wfTaskService.updateByBo(bo));
    }

    /**
     * 删除wft
     *
     * @param ids 主键串
     */
    @SaCheckPermission("system:wfTask:remove")
    @Log(title = "流程任务", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    @Operation(summary = "删除wft",operationId = "WfTask_remove")
    public R<Void> remove(@NotEmpty(message = "主键不能为空")
                          @PathVariable @PathIds Long[] ids) {
        return toAjax(wfTaskService.deleteWithValidByIds(List.of(ids), true));
    }


    @PostMapping("/approve")
    @Log(title = "wfi", businessType = BusinessType.INSERT)
    @Operation(summary = "流程发起",operationId = "WfInstance_start")
    public R<Long> start(@RequestBody ApproveTaskCmd cmd) {
        workflowService.approveTask(cmd);
        return R.ok();
    }
}
