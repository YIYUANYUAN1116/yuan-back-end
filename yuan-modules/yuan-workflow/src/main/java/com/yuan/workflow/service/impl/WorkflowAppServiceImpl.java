package com.yuan.workflow.service.impl;

import com.yuan.workflow.core.engine.WorkflowEngine;
import com.yuan.workflow.service.WorkflowAppService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WorkflowAppServiceImpl implements WorkflowAppService {
    private final WorkflowEngine workflowEngine;
}
