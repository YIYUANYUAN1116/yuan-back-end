package com.yuan.workflow.controller;

import com.yuan.workflow.service.WorkflowAppService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/workflow/wfCommand")
@Tag(name = "WorkflowCommandController",description = "wfCommand")
public class WorkflowCommandController {
    private final WorkflowAppService workflowAppService;
}
