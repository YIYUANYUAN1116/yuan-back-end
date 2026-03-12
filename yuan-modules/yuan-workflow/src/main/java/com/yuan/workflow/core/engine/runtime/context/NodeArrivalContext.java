package com.yuan.workflow.core.engine.runtime.context;


import com.yuan.workflow.cmd.WorkflowCmd;
import com.yuan.workflow.domain.WfDefinition;
import com.yuan.workflow.domain.WfInstance;
import com.yuan.workflow.domain.WfNodeInstance;
import com.yuan.workflow.model.logicflow.LfNode;
import lombok.Builder;
import lombok.Data;

import java.util.Map;

@Data
@Builder
public class NodeArrivalContext {

    /**
     * 流程实例
     */
    private WfInstance instance;

    /**
     * 流程定义
     */
    private WfDefinition definition;

    /**
     * 到达的目标节点定义（流程图节点）
     */
    private LfNode targetNode;

    /**
     * 到达后创建的节点实例
     */
    private WfNodeInstance targetNodeInstance;

    /**
     * 触发本次推进的命令
     */
    private WorkflowCmd triggerCmd;

    /**
     * 当前流程变量快照
     */
    private Map<String, Object> variables;
}