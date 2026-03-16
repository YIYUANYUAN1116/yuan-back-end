package com.yuan.workflow.core.engine.runtime.arrival;


import com.yuan.common.core.constant.AiBizTypeConstants;
import com.yuan.common.core.exception.workflow.WorkflowErrorCode;
import com.yuan.common.core.exception.workflow.WorkflowException;
import com.yuan.common.core.utils.TraceIdUtil;
import com.yuan.workflow.cmd.WorkflowCmd;
import com.yuan.workflow.core.engine.runtime.NodeInstanceStateManager;
import com.yuan.workflow.core.engine.runtime.ProcessAdvancer;
import com.yuan.workflow.core.engine.runtime.VariableManager;
import com.yuan.workflow.core.engine.runtime.WfEventManager;
import com.yuan.workflow.core.engine.runtime.context.NodeArrivalContext;
import com.yuan.workflow.domain.WfInstance;
import com.yuan.workflow.domain.WfNodeInstance;
import com.yuan.workflow.domain.dto.WfAiNodeRequest;
import com.yuan.workflow.domain.dto.WfAiNodeResult;
import com.yuan.workflow.domain.enums.NodeType;
import com.yuan.workflow.model.logicflow.LfNode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class AiTaskArrivalHandler implements NodeArrivalHandler {

    private final NodeInstanceStateManager nodeInstanceStateManager;
    private final VariableManager variableManager;
    private final ProcessAdvancer processAdvancer;
    private final WfAiNodeService wfAiNodeService;
    private final WfEventManager wfEventManager;

    @Override
    public boolean supports(String nodeType) {
        return NodeType.AI_TASK.getCode().equals(nodeType);
    }

    @Override
    public void handle(NodeArrivalContext context) {
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
            request.setEndpointKey("");
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

            // 4. 继续推进
            processAdvancer.advance(nodeInstance, context.getTriggerCmd());

        } catch (Exception e) {
            nodeInstanceStateManager.aiFailAuto(nodeInstance, cmd);
            log.error("[AiTaskArrivalHandler] aiFailAuto fail",e);
            throw new WorkflowException(WorkflowErrorCode.WF_AI_EXECUTE_FAIL);
        }
    }
}