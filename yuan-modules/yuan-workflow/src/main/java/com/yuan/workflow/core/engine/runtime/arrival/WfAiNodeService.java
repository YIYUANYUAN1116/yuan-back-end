package com.yuan.workflow.core.engine.runtime.arrival;


import com.yuan.workflow.domain.dto.WfAiNodeRequest;
import com.yuan.workflow.domain.dto.WfAiNodeResult;

public interface WfAiNodeService {

    /**
     * 执行 AI 节点，返回需要写回流程的变量
     */
    WfAiNodeResult analyze(WfAiNodeRequest wfAiNodeRequest);
}