package com.yuan.workflow.core.engine;

import com.yuan.common.json.utils.JsonUtils;
import com.yuan.common.satoken.utils.LoginHelper;
import com.yuan.workflow.model.FlowNode;
import com.yuan.workflow.core.parser.FlowParser;
import com.yuan.workflow.core.resolver.AssigneeResolver;
import com.yuan.workflow.domain.*;
import com.yuan.workflow.enums.*;
import com.yuan.workflow.mapper.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.time.LocalDateTime;
import java.util.List;
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

    /**
     * 发起流程
     *
     * @param cmd
     * @return
     */
    @Override
    public Long startProcess(StartProcessCmd cmd) {
        Long userId = LoginHelper.getUserId();
        String tenantId = LoginHelper.getTenantId();

        //1.查询最新已发布流程定义
        WfDefinition def = definitionMapper.selectLatestPublished(
                tenantId, cmd.getProcessKey());
        Assert.notNull(def, "流程未发布");

        // 2. 创建流程实例
        WfInstance instance = new WfInstance();
        instance.setTenantId(tenantId);
        instance.setDefinitionId(def.getId());
        instance.setProcessKey(def.getProcessKey());
        instance.setVersion(def.getVersion());
        instance.setBusinessKey(cmd.getBusinessKey());
        instance.setStatus(InstanceStatus.RUNNING.getCode());
        instance.setStartUserId(userId);
        instance.setVariables(JsonUtils.toJsonString(cmd.getVariables()));
        instanceMapper.insert(instance);

        // 3. start 节点（自动完成）
        FlowNode startNode = flowParser.getStartNode(def);
        createNodeInstance(instance.getId(), startNode, NodeStatus.DONE, 1);

        //4.找到下一个节点
        FlowNode firstApproveNode = flowParser.getNextNode(def, startNode, cmd.getVariables());

        // 5. 创建审批节点 + 任务
        WfNodeInstance nodeInstance =
                createNodeInstance(instance.getId(), firstApproveNode,
                        NodeStatus.WAIT, 2);

        createTasks(instance, nodeInstance, firstApproveNode);

        return instance.getId();
    }


    /**
     * 审批通过
     *
     * @param taskId
     * @param comment
     */
    @Override
    public void approveTask(Long taskId, String comment) {
        Long userId = LoginHelper.getUserId();
        WfTask task = taskMapper.selectById(taskId);

        assertApproveTask(task,userId);

        // 2. 完成任务
        finishTask(task, TaskAction.APPROVE, comment);

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
     * @param taskId
     * @param comment
     */
    @Override
    public void rejectTask(Long taskId, String comment) {
        Long userId = LoginHelper.getUserId();
        WfTask task = taskMapper.selectById(taskId);

        assertRejectTask(task,userId);

        finishTask(task, TaskAction.REJECT, comment);

        WfInstance instance =
                instanceMapper.selectById(task.getInstanceId());

        instance.setStatus(InstanceStatus.REJECTED.getCode());
        instance.setEndTime(LocalDateTime.now());
        instanceMapper.updateById(instance);

        // 清理未完成节点 / 任务（软完成）
        nodeInstanceMapper.finishAll(instance.getId(),NodeStatus.CANCELED.getCode());
        taskMapper.finishAll(instance.getId(),TaskStatus.CANCELED.getCode());
    }

    @Override
    public void rollbackToPrev(Long taskId, String comment) {

    }

    @Override
    public void rollbackTo(Long taskId, String targetNodeId, String comment) {

    }

    @Override
    public void withdraw(Long instanceId, String comment) {

    }

    @Override
    public void transfer(Long taskId, Long toUserId, String reason) {

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
        if (next == null || NodeType.END.getCode().equals(next.getType())) {
            instance.setStatus(InstanceStatus.APPROVED.getCode());
            instance.setEndTime(LocalDateTime.now());
            instanceMapper.updateById(instance);
            return;
        }

        // 6. 创建下一个节点 + 任务
        WfNodeInstance nextNodeIns = createNodeInstance(instance.getId(), next, NodeStatus.WAIT, currentNode.getOrderNo() + 1);
        createTasks(instance, nextNodeIns, next);
    }
}
