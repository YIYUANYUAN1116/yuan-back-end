package com.yuan.workflow.core.engine;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.yuan.common.json.utils.JsonUtils;
import com.yuan.common.satoken.utils.LoginHelper;
import com.yuan.workflow.api.cmd.*;
import com.yuan.workflow.api.enums.*;
import com.yuan.workflow.api.event.WfEvent;
import com.yuan.workflow.api.event.WfEventContext;
import com.yuan.workflow.api.event.WfEventFactory;
import com.yuan.workflow.core.event.WfEventPublisher;
import com.yuan.workflow.core.parser.FlowParser;
import com.yuan.workflow.core.resolver.AssigneeResolver;
import com.yuan.workflow.domain.*;
import com.yuan.workflow.mapper.*;
import com.yuan.workflow.model.FlowNode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;
import org.springframework.util.Assert;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Transactional
public class SimpleWorkflowEngine implements WorkflowEngine {

    private final WfDefinitionMapper definitionMapper;
    private final WfInstanceMapper instanceMapper;
    private final WfNodeInstanceMapper nodeInstanceMapper;
    private final WfTaskMapper taskMapper;
    private final WfTaskLogMapper taskLogMapper;
    private final FlowParser flowParser;
    private final AssigneeResolver assigneeResolver;
    private final WfBizRefMapper bizRefMapper;
    private final WfEventPublisher wfEventPublisher;


    /**
     * 发起流程
     *
     * @param cmd
     * @return
     */
    @Override
    public Long startProcess(StartProcessCmd cmd) {
        String tenantId = cmd.getTenantId();
        //1.查询最新已发布流程定义
        WfDefinition def = definitionMapper.selectLatestPublished(
                tenantId, cmd.getDefinitionKey());
        Assert.notNull(def, "流程未发布");

        // 2. 创建流程实例
        WfInstance instance = createInstance(cmd, def);

        // 3. 绑定业务
        bindWfBizRef(cmd,instance.getId());

        // 4. start 节点（自动完成）
        FlowNode startNode = flowParser.getStartNode(def);
        createNodeInstance(instance.getId(), startNode, NodeStatus.DONE, 1);

        //5.找到下一个节点
        FlowNode firstApproveNode = flowParser.getNextNode(def, startNode, cmd.getVariables());
        //6. 创建审批节点 + 任务
        WfNodeInstance nodeInstance =
                createNodeInstance(instance.getId(), firstApproveNode,
                        NodeStatus.WAIT, 2);
        createTasks(instance, nodeInstance, firstApproveNode);


        return instance.getId();
    }




    /**
     * 审批通过
     *
     */
    @Override
    public void approveTask(ApproveTaskCmd cmd) {
        Long userId = cmd.getOperatorUserId();
        WfTask task = taskMapper.selectById(cmd.getTaskId());

        assertApproveTask(task,userId);

        // 2. 完成任务
        finishTask(task, TaskAction.APPROVE, cmd.getComment());

        // 3. 完成节点
        WfNodeInstance node =
                nodeInstanceMapper.selectById(task.getNodeInstanceId());
        node.setStatus(NodeStatus.DONE.getCode());
        nodeInstanceMapper.updateById(node);

        // 4. 推进流程
        advance(node);
    }

    private void assertApproveTask(WfTask task, Long userId) {
        Assert.notNull(task, "任务不存在");
        Assert.isTrue(task.getAssigneeId().equals(userId), "无权审批该任务");
        Assert.isTrue(!Objects.equals(task.getStatus(), TaskStatus.TODO.getCode()), "任务已处理");
    }


    /**
     * 审批回驳(终止型)
     *
     */
    @Override
    public void rejectTask(RejectTaskCmd cmd) {
        Long userId = cmd.getOperatorUserId();
        WfTask task = taskMapper.selectById(cmd.getTaskId());
        //参数校验
        assertRejectTask(task,userId);
        //更新任务
        finishTask(task, TaskAction.REJECT, cmd.getComment());

        // 2. 业务规则：拒绝是否直接结束？
        boolean instanceEnded = isRejectEnd(task);


        WfEventContext wfEventContext = buildEventContextByTask(task);
        WfEvent rejectEvent = WfEventFactory.buildTaskRejected(wfEventContext,cmd.getComment(),Map.of());
        publishAfterCommit(rejectEvent);


        // 4. 如果拒绝即结束
        if (instanceEnded) {
            WfInstance instance =
                    instanceMapper.selectById(task.getInstanceId());
            WfBizRef bizRef = bizRefMapper.selectByInstanceId(instance.getId());

            WfEvent endEvent = WfEventFactory.buildInstanceEnded(wfEventContext,WfEndReason.REJECTED,Map.of());
            instance.setStatus(InstanceStatus.REJECTED.getCode());
            instance.setEndTime(LocalDateTime.now());
            instanceMapper.updateById(instance);

            bizRef.setStatus(InstanceStatus.REJECTED.getCode());
            bizRefMapper.updateById(bizRef);
            // 清理未完成节点 / 任务（软完成）
            nodeInstanceMapper.finishAll(instance.getId(),NodeStatus.CANCELED.getCode());
            taskMapper.finishAll(instance.getId(),TaskStatus.CANCELED.getCode());

            publishAfterCommit(endEvent);

        }

    }

    private WfEventContext buildEventContextByTask(WfTask task) {
        WfInstance instance =
                instanceMapper.selectById(task.getInstanceId());
        WfBizRef bizRef = bizRefMapper.selectByInstanceId(instance.getId());

        return WfEventContext.builder()
                .tenantId(instance.getTenantId())
                .bizId(bizRef.getBizId())
                .bizType(bizRef.getBizType())
                .definitionId(instance.getDefinitionId())
                .instanceId(instance.getId())
                .taskId(task.getId())
                .nodeId(task.getNodeInstanceId())
                .starterUserId(instance.getStartUserId())
                .operatorUserId(task.getAssigneeId())
                .build();
    }

    private boolean isRejectEnd(WfTask task) {
        return true;
    }

    @Override
    public void rollbackToPrev(RollbackToPrevCmd rollbackToPrevCmd) {

    }

    @Override
    public void rollbackTo(RollbackToCmd rollbackToCmd) {

    }

    @Override
    public void withdraw(WithdrawCmd withdrawCmd) {

    }

    @Override
    public void transfer(TransferTaskCmd transferTaskCmd) {

    }


    private void assertRejectTask(WfTask task, Long userId) {
        Assert.notNull(task, "任务不存在");
        Assert.isTrue(task.getAssigneeId().equals(userId), "无权操作");
    }


    private WfNodeInstance createNodeInstance(Long instanceId, FlowNode node, NodeStatus status, int orderNo) {
        WfNodeInstance ni = new WfNodeInstance();
        ni.setInstanceId(instanceId);
        ni.setNodeKey(node.getId());
        ni.setNodeType(node.getType().getCode());
        ni.setStatus(status.getCode());
        ni.setOrderNo(orderNo);
        nodeInstanceMapper.insert(ni);
        return ni;
    }


    private void createTasks(WfInstance instance, WfNodeInstance nodeInstance, FlowNode node) {
        List<Long> userIds =
                assigneeResolver.resolve(node, instance);
        for (Long uid : userIds) {
            WfTask task = new WfTask();
            task.setTenantId(instance.getTenantId());
            task.setInstanceId(instance.getId());
            task.setNodeInstanceId(nodeInstance.getId());
            task.setAssigneeId(uid);
            task.setStatus(TaskStatus.TODO.getCode());
            taskMapper.insert(task);
        }
    }

    /**
     * 完成任务 + 日志
     * @param task
     * @param action
     * @param comment
     */
    private void finishTask(WfTask task, TaskAction action, String comment) {
        task.setStatus(TaskStatus.DONE.getCode());
        task.setAction(action.getCode());
        task.setComment(comment);
        task.setFinishTime(LocalDateTime.now());
        taskMapper.updateById(task);

        WfTaskLog log = new WfTaskLog();
        log.setTaskId(task.getId());
        log.setInstanceId(task.getInstanceId());
        log.setAction(action.getCode());
        log.setOperatorId(LoginHelper.getUserId());
        log.setComment(comment);
        taskLogMapper.insert(log);
    }

    /**
     * 推进流程
     * @param currentNode
     */
    private void advance(WfNodeInstance currentNode) {
        WfInstance instance = instanceMapper.selectById(currentNode.getInstanceId());

        WfDefinition def  = definitionMapper.selectById(instance.getDefinitionId());

        FlowNode flowNode = flowParser.getNode(def ,currentNode.getNodeKey());

        FlowNode next = flowParser.getNextNode(def, flowNode, JsonUtils.parseMap(instance.getVariables()));

        // 5. 没有下一个节点 → 结束
        if (next == null || NodeType.END.equals(next.getType())) {
            instance.setStatus(InstanceStatus.APPROVED.getCode());
            instance.setEndTime(LocalDateTime.now());
            instanceMapper.updateById(instance);
            afterFinishProcess(instance.getId(), InstanceStatus.APPROVED.getCode());
            return;
        }

        // 6. 创建下一个节点 + 任务
        WfNodeInstance nextNodeIns = createNodeInstance(instance.getId(), next, NodeStatus.WAIT, currentNode.getOrderNo() + 1);
        createTasks(instance, nextNodeIns, next);
    }

    private void afterFinishProcess(Long instanceId, String status) {
        // 1. 更新业务绑定表
        WfBizRef ref = bizRefMapper.selectOne(
                new LambdaQueryWrapper<WfBizRef>()
                        .eq(WfBizRef::getInstanceId, instanceId)
        );
        ref.setStatus(status);
        ref.setUpdatedTime(LocalDateTime.now());
        bizRefMapper.updateById(ref);


    }

    private void bindWfBizRef(StartProcessCmd cmd,Long instanceId) {
        WfBizRef ref = new WfBizRef();
        ref.setBizType(cmd.getBizType());
        ref.setBizId(cmd.getBizId());
        ref.setInstanceId(instanceId);
        ref.setStatus(InstanceStatus.RUNNING.getCode());
        ref.setCreatedBy(cmd.getStarterUserId());
        ref.setCreatedTime(LocalDateTime.now());
        ref.setUpdatedTime(LocalDateTime.now());
        bizRefMapper.insert(ref);
    }

    private WfInstance createInstance(StartProcessCmd cmd, WfDefinition def) {
        WfInstance instance = new WfInstance();
        instance.setTenantId(cmd.getTenantId());
        instance.setDefinitionId(def.getId());
        instance.setDefinitionKey(def.getDefinitionKey());
        instance.setVersion(def.getVersion());
        instance.setBusinessKey(cmd.getBizType());
        instance.setStatus(InstanceStatus.RUNNING.getCode());
        instance.setStartUserId(cmd.getStarterUserId());
        instance.setVariables(JsonUtils.toJsonString(cmd.getVariables()));
        instanceMapper.insert(instance);
        return instance;
    }

    private void publishAfterCommit(WfEvent event) {
        TransactionSynchronizationManager.registerSynchronization(
                new TransactionSynchronization() {
                    @Override
                    public void afterCommit() {
                        wfEventPublisher.publish(event);
                    }
                }
        );
    }

}
