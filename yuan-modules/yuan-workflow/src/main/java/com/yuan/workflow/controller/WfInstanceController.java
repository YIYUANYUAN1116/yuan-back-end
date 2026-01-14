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
import com.yuan.workflow.api.cmd.StartProcessCmd;
import com.yuan.workflow.domain.bo.WfInstanceBo;
import com.yuan.workflow.domain.vo.WfInstanceVo;
import com.yuan.workflow.domain.vo.WorkItemRowVO;
import com.yuan.workflow.service.WfInstanceService;
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
 * wfi
 *
 * @author ageerle
 * @date Sun Dec 28 11:26:34 CST 2025
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/workflow/wfInstance")
@Tag(name = "WfInstanceController",description = "wfi")
public class WfInstanceController extends BaseController {

    private final WfInstanceService wfInstanceService;
    private final WorkflowService workflowService;


/**
 * 查询wfi列表
 */
@SaCheckPermission("workflow:wfInstance:list")
@GetMapping("/list")
@Operation(summary = "查询wfi列表",operationId = "WfInstance_list")
    public TableDataInfo<WfInstanceVo> list(WfInstanceBo bo, PageQuery pageQuery) {
        return wfInstanceService.queryPageList(bo, pageQuery);
    }

    /**
     * 导出wfi列表
     */
    @SaCheckPermission("workflow:wfInstance:export")
    @Log(title = "流程实例", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @Operation(summary = "导出wfi列表",operationId = "WfInstance_export")
    public void export(WfInstanceBo bo, HttpServletResponse response) {
        List<WfInstanceVo> list = wfInstanceService.queryList(bo);
        ExcelUtil.exportExcel(list, "wfi", WfInstanceVo.class, response);
    }

    /**
     * 获取wfi详细信息
     *
     * @param id 主键
     */
    @SaCheckPermission("workflow:wfInstance:query")
    @GetMapping("/{id}")
    @Operation(summary = "获取wfi详细信息",operationId = "WfInstance_getInfo")
    public R<WfInstanceVo> getInfo(@NotNull(message = "主键不能为空")
                                         @PathId @PathVariable Long id) {
        return R.ok(wfInstanceService.queryById(id));
    }

    /**
     * 新增wfi
     */
    @SaCheckPermission("workflow:wfInstance:add")
    @Log(title = "流程实例", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    @Operation(summary = "新增wfi",operationId = "WfInstance_add")
    public R<Void> add(@Validated(AddGroup.class) @RequestBody WfInstanceBo bo) {
        return toAjax(wfInstanceService.insertByBo(bo));
    }

    /**
     * 修改wfi
     */
    @SaCheckPermission("workflow:wfInstance:edit")
    @Log(title = "流程实例", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    @Operation(summary = "修改wfi",operationId = "WfInstance_edit")
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody WfInstanceBo bo) {
        return toAjax(wfInstanceService.updateByBo(bo));
    }

    /**
     * 删除wfi
     *
     * @param ids 主键串
     */
    @SaCheckPermission("workflow:wfInstance:remove")
    @Log(title = "流程实例", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    @Operation(summary = "删除wfi",operationId = "WfInstance_remove")
    public R<Void> remove(@NotEmpty(message = "主键不能为空")
                          @PathVariable @PathIds Long[] ids) {
        return toAjax(wfInstanceService.deleteWithValidByIds(List.of(ids), true));
    }

    @PostMapping("/start")
    @Log(title = "流程实例", businessType = BusinessType.INSERT)
    @Operation(summary = "流程发起",operationId = "WfInstance_start")
    public R<Long> start(@RequestBody StartProcessCmd cmd) {
        Long instanceId = workflowService.startProcess(cmd);
        return R.ok(instanceId);
    }

    @SaCheckPermission("workflow:wfInstance:list")
    @GetMapping("/my-apply")
    @Operation(summary = "我的申请",operationId = "WfInstance_MyApply")
    public TableDataInfo<WorkItemRowVO> apply(WfInstanceBo bo, PageQuery pageQuery) {
        return wfInstanceService.myApply(bo, pageQuery);
    }

}
