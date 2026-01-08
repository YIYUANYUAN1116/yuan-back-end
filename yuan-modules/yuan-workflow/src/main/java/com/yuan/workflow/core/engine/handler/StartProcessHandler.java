package com.yuan.workflow.core.engine.handler;

import com.yuan.workflow.api.cmd.StartProcessCmd;
import com.yuan.workflow.api.enums.NodeStatus;
import com.yuan.workflow.api.enums.NodeType;
import com.yuan.workflow.core.engine.support.InstanceLifecycle;
import com.yuan.workflow.core.parser.FlowParser;
import com.yuan.workflow.core.resolver.AssigneeResolver;
import com.yuan.workflow.domain.WfDefinition;
import com.yuan.workflow.domain.WfInstance;
import com.yuan.workflow.domain.WfNodeInstance;
import com.yuan.workflow.mapper.WfDefinitionMapper;
import com.yuan.workflow.model.logicflow.LfNode;
import com.yuan.workflow.service.WfBizRefService;
import com.yuan.workflow.service.WfInstanceService;
import com.yuan.workflow.service.WfNodeInstanceService;
import com.yuan.workflow.service.WfTaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.List;

@Component
@RequiredArgsConstructor
public class StartProcessHandler implements  CommandHandler<StartProcessCmd,Long>{
    private final WfDefinitionMapper definitionMapper;
    private final WfInstanceService wfInstanceService;
    private final WfBizRefService wfBizRefService;
    private final FlowParser flowParser;
    private final WfNodeInstanceService wfNodeInstanceService;
    private final InstanceLifecycle instanceLifecycle;
    private final WfTaskService wfTaskService;
    private final AssigneeResolver assigneeResolver;

    @Override
    @Transactional
    public Long handle(StartProcessCmd cmd) {
        String tenantId = cmd.getTenantId();

        // 1) 查最新已发布定义
        WfDefinition def = definitionMapper.selectLatestPublished(tenantId, cmd.getDefinitionKey());
        Assert.notNull(def, "流程未发布");

        // 2) 创建实例
        WfInstance instance =  wfInstanceService.createInstance(cmd, def);

        // 3) 绑定业务
        wfBizRefService.bindWfBizRef(cmd,instance.getId());

        // 4) start 节点（自动完成）
        LfNode startNode = flowParser.getStartNode(def);
        wfNodeInstanceService.createNodeInstance(instance.getId(), startNode, NodeStatus.DONE, 1);

        // 5) 找到下一个节点
        LfNode firstApproveNode = flowParser.getNextNode(def, startNode, cmd.getVariables());

        // 6) 创建审批节点 + 任务
        if (firstApproveNode != null && !NodeType.END.getCode().equals(firstApproveNode.getProperties().getWfType())) {
            WfNodeInstance nodeInstance = wfNodeInstanceService.createNodeInstance(instance.getId(), firstApproveNode, NodeStatus.WAIT, 2);
            List<Long> userIds = assigneeResolver.resolve(firstApproveNode, instance);
            wfTaskService.createTasks(instance, nodeInstance,userIds);
        } else {
            // 没有审批节点，直接结束
            instanceLifecycle.finishApproved(instance.getId(), cmd.getOperatorUserId());
        }

        return instance.getId();
    }
}
