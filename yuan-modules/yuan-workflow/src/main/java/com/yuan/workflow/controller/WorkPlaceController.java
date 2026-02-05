package com.yuan.workflow.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.yuan.core.page.PageQuery;
import com.yuan.core.page.TableDataInfo;
import com.yuan.workflow.domain.bo.WfWorklistQueryBo;
import com.yuan.workflow.domain.vo.WorkItemRowVO;
import com.yuan.workflow.service.WfInstanceService;
import com.yuan.workflow.service.WfTaskService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/workplace")
@Tag(name = "WorkPlaceController",description = "工作台")
public class WorkPlaceController {
    private final WfTaskService wfTaskService;
    private final WfInstanceService wfInstanceService;


    @SaCheckPermission("workplace:task:list")
    @GetMapping("/my-task")
    @Operation(summary = "我的代办",operationId = "workPlaceMyTask")
    public TableDataInfo<WorkItemRowVO> myTask(WfWorklistQueryBo bo, PageQuery pageQuery) {
        return wfTaskService.myTask(bo, pageQuery);
    }

    @SaCheckPermission("workplace:approve:list")
    @GetMapping("/my-approval")
    @Operation(summary = "我的已办",operationId = "workPlaceApprovals")
    public TableDataInfo<WorkItemRowVO> myApproval(WfWorklistQueryBo bo, PageQuery pageQuery) {
        return wfTaskService.myApproval(bo, pageQuery);
    }

    @SaCheckPermission("workplace:apply:list")
    @GetMapping("/my-apply")
    @Operation(summary = "我的申请",operationId = "workPlaceMyApply")
    public TableDataInfo<WorkItemRowVO> apply(WfWorklistQueryBo bo, PageQuery pageQuery) {
        return wfInstanceService.myApply(bo, pageQuery);
    }
}
