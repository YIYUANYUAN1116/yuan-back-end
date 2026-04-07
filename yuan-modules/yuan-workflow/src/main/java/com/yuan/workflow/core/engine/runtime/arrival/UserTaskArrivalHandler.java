package com.yuan.workflow.core.engine.runtime.arrival;


import com.yuan.workflow.core.engine.runtime.TaskStateManager;
import com.yuan.workflow.core.engine.runtime.context.NodeArrivalContext;
import com.yuan.workflow.core.resolver.AssigneeResolver;
import com.yuan.workflow.domain.WfInstance;
import com.yuan.workflow.domain.WfNodeInstance;
import com.yuan.workflow.domain.enums.NodeType;
import com.yuan.workflow.model.logicflow.LfNode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class UserTaskArrivalHandler implements NodeArrivalHandler {
    private final TaskStateManager taskStateManager;
    private final AssigneeResolver assigneeResolver;


    @Override
    public boolean supports(String nodeType) {
        return NodeType.USER_TASK.getCode().equals(nodeType);
    }

    @Override
    public List<LfNode> handle(NodeArrivalContext context) {
        WfInstance instance = context.getInstance();
        WfNodeInstance nodeInstance = context.getTargetNodeInstance();
        LfNode targetNode = context.getTargetNode();
        Map<String, Object> vars = context.getVariables();
//
//        // 1. 激活节点
//        nodeInstanceStateManager.activate(nodeInstance);
//
//        // 2. 解析审批人
//        List<Long> assigneeIds = assigneeResolver.resolve(targetNode, instance, vars);
//
//        // 3. 创建待办任务
//        for (Long assigneeId : assigneeIds) {
//            WfTask task = taskStateManager.createTodoTask(instance, nodeInstance, targetNode, assigneeId);
//            wfEventManager.onTaskCreated(instance, nodeInstance, task);
//        }
//
//        // 4. 节点进入等待状态（如果你现在 activate 就表示等待，可不单独调）
//        nodeInstanceStateManager.waitUserAction(nodeInstance);

        // 5. 到这里停住，等待人工处理

        Set<Long> userIds = assigneeResolver.resolve(targetNode);
        taskStateManager.createTodoTasks(instance, nodeInstance, userIds);
        return null;
    }
}