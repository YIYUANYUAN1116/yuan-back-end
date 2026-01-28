package com.yuan.workflow.core.engine.handler;

import com.yuan.workflow.cmd.StartCmd;
import com.yuan.workflow.core.engine.support.FlowAdvanceService;
import com.yuan.workflow.core.engine.support.InstanceStateManager;
import com.yuan.workflow.core.parser.FlowParser;
import com.yuan.workflow.core.resolver.AssigneeResolver;
import com.yuan.workflow.domain.WfDefinition;
import com.yuan.workflow.domain.WfInstance;
import com.yuan.workflow.domain.enums.NodeStatus;
import com.yuan.workflow.domain.guard.WfOperationGuard;
import com.yuan.workflow.mapper.WfDefinitionMapper;
import com.yuan.workflow.model.logicflow.LfNode;
import com.yuan.workflow.service.WfBizRefService;
import com.yuan.workflow.service.WfInstanceService;
import com.yuan.workflow.service.WfNodeInstanceService;
import com.yuan.workflow.service.WfTaskService;
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
    private final WfTaskService wfTaskService;
    private final AssigneeResolver assigneeResolver;
    private final WfOperationGuard wfOperationGuard;
    private final FlowAdvanceService flowAdvanceService;


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
        LfNode startNode = flowParser.getStartNode(def);
        wfNodeInstanceService.createNodeInstance(instance.getId(), startNode, NodeStatus.DONE, 1);

        // 5) 找到下一个节点
        List<LfNode> nodeList = flowParser.getNextNode(def, startNode, cmd.getVariables());

        if (nodeList.isEmpty()){
            log.warn("no first finish node. defId={},defVersion={}", def.getId(), def.getVersion());
            instanceStateManager.finishApproved(instance, cmd);
        }
        for (LfNode next : nodeList) {
            flowAdvanceService.advanceToTarget(instance,def,next,cmd,cmd.getVariables());
        }
        return instance.getId();
    }
}
