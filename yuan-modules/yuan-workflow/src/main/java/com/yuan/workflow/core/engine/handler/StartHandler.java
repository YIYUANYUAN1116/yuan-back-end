package com.yuan.workflow.core.engine.handler;

import com.yuan.workflow.cmd.RecordTransitionCmd;
import com.yuan.workflow.cmd.StartCmd;
import com.yuan.workflow.core.engine.runtime.InstanceStateManager;
import com.yuan.workflow.core.engine.runtime.InstanceTransitionManager;
import com.yuan.workflow.core.engine.runtime.TransitionLogManager;
import com.yuan.workflow.core.parser.FlowParser;
import com.yuan.workflow.domain.WfDefinition;
import com.yuan.workflow.domain.WfInstance;
import com.yuan.workflow.domain.WfNodeInstance;
import com.yuan.workflow.domain.enums.NodeStatus;
import com.yuan.workflow.domain.guard.WfOperationGuard;
import com.yuan.workflow.enums.OperatorType;
import com.yuan.workflow.enums.TransitionAction;
import com.yuan.workflow.mapper.WfDefinitionMapper;
import com.yuan.workflow.model.logicflow.LfNode;
import com.yuan.workflow.service.WfBizRefService;
import com.yuan.workflow.service.WfInstanceService;
import com.yuan.workflow.service.WfNodeInstanceService;
import com.yuan.workflow.service.WfTransitionLogService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class StartHandler implements  CommandHandler<StartCmd,Long>{
    private final WfDefinitionMapper definitionMapper;
    private final WfInstanceService wfInstanceService;
    private final WfBizRefService wfBizRefService;
    private final FlowParser flowParser;
    private final WfNodeInstanceService wfNodeInstanceService;
    private final InstanceStateManager instanceStateManager;
    private final WfOperationGuard wfOperationGuard;
    private final InstanceTransitionManager instanceTransitionManager;
    private final WfTransitionLogService transitionLogService;
    private final TransitionLogManager transitionLogManager;

    @Override
    @Transactional
    public Long handle(StartCmd cmd) {
        String tenantId = cmd.getTenantId();

        // 1) 查最新已发布定义
        WfDefinition def = definitionMapper.selectLatestPublished(tenantId, cmd.getDefinitionKey());
        wfOperationGuard.assertStartInstance(def,cmd);

        // 2) 创建实例
        WfInstance instance =  wfInstanceService.createInstance(cmd, def);

        // 3) 绑定业务
        wfBizRefService.bindWfBizRef(cmd,instance.getId());

        // 4) start 节点（自动完成）
        LfNode startLfNode = flowParser.getStartNode(def);
        WfNodeInstance startNode = wfNodeInstanceService.createNodeInstance(instance.getId(), startLfNode, NodeStatus.DONE, 1);

        transitionLog(instance,startNode,cmd);


        // 5) 找到下一个节点
        List<LfNode> nodeList = flowParser.getNextNode(def, startLfNode, cmd.getVariables());

        if (nodeList.isEmpty()){
            log.warn("no first finish node. defId={},defVersion={}", def.getId(), def.getVersion());
            instanceStateManager.finishApproved(instance, cmd);
        }
        for (LfNode next : nodeList) {
            instanceTransitionManager.advanceToTarget(instance,def,next,cmd,cmd.getVariables());
        }
        return instance.getId();
    }

    private void transitionLog(WfInstance instance, WfNodeInstance startNode, StartCmd cmd) {
        transitionLogService.recordSuccess(RecordTransitionCmd.builder()
                .tenantId(instance.getTenantId())
                .defId(instance.getDefinitionId())
                .defVersion(instance.getDefinitionVersion())
                .instanceId(instance.getId())
                .nodeInstanceId(startNode.getId())
                .fromNodeKey(null)                 // START 可空
                .toNodeKey(startNode.getNodeKey())
                .action(TransitionAction.START)
                .operatorType(OperatorType.USER)
                .operatorId(cmd.getStarterId())
                .comment(cmd.getComment())
                .build());
    }
}
