package com.yuan.workflow.core.engine.runtime.arrival;


import com.yuan.common.core.constant.AiBizTypeConstants;
import com.yuan.common.core.exception.workflow.WorkflowErrorCode;
import com.yuan.common.core.exception.workflow.WorkflowException;
import com.yuan.common.core.utils.TraceIdUtil;
import com.yuan.workflow.cmd.WorkflowCmd;
import com.yuan.workflow.core.engine.runtime.NodeInstanceStateManager;
import com.yuan.workflow.core.engine.runtime.TransitionLogManager;
import com.yuan.workflow.core.engine.runtime.VariableManager;
import com.yuan.workflow.core.engine.runtime.WfEventManager;
import com.yuan.workflow.core.engine.runtime.context.NodeArrivalContext;
import com.yuan.workflow.core.parser.FlowParser;
import com.yuan.workflow.domain.WfInstance;
import com.yuan.workflow.domain.WfNodeInstance;
import com.yuan.workflow.domain.dto.WfAiNodeRequest;
import com.yuan.workflow.domain.dto.WfAiNodeResult;
import com.yuan.workflow.domain.enums.NodeType;
import com.yuan.workflow.enums.OperatorType;
import com.yuan.workflow.enums.TransitionAction;
import com.yuan.workflow.model.logicflow.LfNode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
@Slf4j
public class AiTaskArrivalHandler implements NodeArrivalHandler {

    private final NodeInstanceStateManager nodeInstanceStateManager;
    private final VariableManager variableManager;
    private final WfAiNodeService wfAiNodeService;
    private final WfEventManager wfEventManager;
    private final FlowParser flowParser;
    private final TransitionLogManager transitionLogManager;

    @Override
    public boolean supports(String nodeType) {
        return NodeType.AI_TASK.getCode().equals(nodeType);
    }

    @Override
    public List<LfNode> handle(NodeArrivalContext context) {
        WfInstance instance = context.getInstance();
        WfNodeInstance nodeInstance = context.getTargetNodeInstance();
        LfNode targetNode = context.getTargetNode();
        WorkflowCmd cmd = context.getTriggerCmd();

        try {
            WfAiNodeRequest request = new WfAiNodeRequest();
            request.setInstanceId(instance.getId());
            request.setNodeInstanceId(nodeInstance.getId());
            request.setOperatorId(cmd.getOperatorId());
            request.setTraceId(TraceIdUtil.newTraceIdPrefix(AiBizTypeConstants.WORKFLOW));

            request.setTemplateCode("");
            request.setTemplateContent("");
            request.setSystemPrompt("");
            request.setEndpointCode("");
            request.setAutoSelectModel(Boolean.TRUE);
            request.setEnableThinking(true);

            request.setVariables(context.getVariables());

            WfAiNodeResult result = wfAiNodeService.analyze(request);

            if (!Boolean.TRUE.equals(result.getSuccess())) {
                throw new IllegalStateException("AI节点执行失败: " + result.getErrorMessage());
            }

            if (result.getOutputVariables() != null && !result.getOutputVariables().isEmpty()) {
                variableManager.mergeAndSave(instance, result.getOutputVariables());
            }


            // 3. 完成节点
            nodeInstanceStateManager.aiCompleteAuto(nodeInstance,cmd);
            Map<String, Object> vars = variableManager.getVars(instance);
            List<LfNode> nextNode = flowParser.getNextNode(context.getDefinition(), targetNode, vars);
            for (LfNode next : nextNode) {
                transitionLogManager.transitionLog(instance, nodeInstance, next, TransitionAction.AI_SUGGEST, OperatorType.SYSTEM, cmd, null, vars);

            }

            return nextNode;

        } catch (Exception e) {
            nodeInstanceStateManager.aiFailAuto(nodeInstance, cmd);
            log.error("[AiTaskArrivalHandler] aiFailAuto fail",e);
            throw new WorkflowException(WorkflowErrorCode.WF_AI_EXECUTE_FAIL);
        }
    }
}