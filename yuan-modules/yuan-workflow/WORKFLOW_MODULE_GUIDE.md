# Yuan-Workflow 模块新手入门文档

## 一、模块整体架构说明

### 1.1 架构分层

采用经典的四层架构，职责清晰：

```
Controller 层 (接口层)
    ↓
Service 层 (业务逻辑层)
    ↓
Core/Engine 层 (流程引擎核心)
    ↓
Mapper 层 (数据访问层)
```

### 1.2 各层职责说明

**Controller 层**
- 接收HTTP请求，参数校验
- 调用WorkflowEngine进行流程操作
- 返回统一格式的响应结果

**Service 层**
- 实现具体的业务查询逻辑
- 数据组装与转换
- 调用引擎层的核心方法

**Core/Engine 层**
这是模块的核心，包含：
- 流程引擎入口
- 各种操作处理器（启动、审批、拒绝、退回、撤回、转交）
- 流程定义解析器
- 审批人解析器
- 条件表达式求值器
- 生命周期管理

**Mapper 层**
- 使用MyBatis-Plus进行数据库操作
- 主要实体对应的Mapper

### 1.3 包结构说明

```
com.yuan.workflow
├── annotation/          # 注解定义（如 @FillWfCmd，用于自动填充命令上下文）
├── component/           # 组件（AOP、命令填充器等）
│   └── aop/            # AOP切面（WfCmdFillAspect 用于自动填充当前用户/租户信息）
├── controller/          # 控制器
├── core/               # 核心引擎
│   ├── engine/         # 引擎主入口与命令处理器
│   │   ├── handler/    # 各操作实现
│   │   └── support/    # 支撑服务（生命周期管理、上下文加载、推进服务等）
│   ├── evaluator/      # 条件求值
│   ├── event/          # 事件机制
│   ├── exception/      # 引擎异常定义
│   ├── parser/         # 流程定义解析
│   ├── resolver/       # 审批人解析
│   └── validator/      # 定义校验
├── domain/             # 领域模型
│   ├── bo/             # 业务对象
│   ├── vo/             # 视图对象
│   ├── dto/            # 数据传输对象
│   ├── enums/          # 枚举定义
│   ├── exception/      # 业务异常
│   ├── guard/          # 操作守卫（权限校验）
│   └── model/          # 模型与数据结构（流程节点、连线、网关条件等）
├── mapper/             # 数据访问层
└── service/            # 服务层
    ├── api/            # API服务（对外接口）
    └── impl/           # 服务实现
```

## 二、数据库表结构说明

### 2.1 核心表概览

共7张核心表：

| 表名 | 用途 | 说明 |
|-----|------|------|
| `wf_definition` | 流程定义表 | 存储流程定义的元数据和流程图JSON |
| `wf_instance` | 流程实例表 | 记录每个流程的运行实例 |
| `wf_node_instance` | 节点实例表 | 记录流程中每个节点的执行状态 |
| `wf_task` | 任务表 | 记录每个待办任务 |
| `wf_task_log` | 任务日志表 | 记录任务操作日志，用于审批历史 |
| `wf_biz_ref` | 业务关联表 | 将流程实例与业务数据解耦关联 |
| `wf_cc` | 抄送表 | 记录流程抄送人 |

### 2.2 表关系图

```
wf_definition (1)
    └─> wf_instance (N)
         ├─> wf_node_instance (N)
         │    └─> wf_task (N)
         │         └─> wf_task_log (N)
         ├─> wf_biz_ref (N)
         └─> wf_cc (N)
```

### 2.3 各表详细说明

#### 2.3.1 wf_definition (流程定义表)

**用途**：存储流程定义的元数据和流程图JSON

**关键字段**：
```java
Long id;                           // 流程定义ID（主键，自增）
String definitionKey;              // 流程业务标识(leave, expense)
String definitionName;             // 流程名称
Integer version;                   // 版本号(递增)
DefinitionStatus status;           // 状态(DRAFT/PUBLISHED/DISABLED)
String formSchema;                 // 表单定义(JSON Schema)
String flowJson;                   // 流程定义JSON
String remark;                     // 备注
String tenantId;                   // 租户ID
```

**与其他表关系**：
- 一对多：wf_definition → wf_instance（一个定义对应多个实例）

**状态枚举**：
- `DRAFT` - 草稿
- `PUBLISHED` - 已发布（可发起实例）
- `DISABLED` - 已停用

#### 2.3.2 wf_instance (流程实例表)

**用途**：记录每个流程的运行实例

**关键字段**：
```java
Long id;                           // 流程实例ID
Long definitionId;                 // 流程定义ID
String definitionKey;              // 流程业务标识
String definitionName;             // 流程名称
Integer definitionVersion;         // 流程版本
InstanceStatus status;             // 状态(RUNNING/APPROVED/REJECTED/CANCELED)
Long starterId;                    // 发起人ID
String starterName;                // 发起人姓名
Long starterDeptId;                // 发起部门ID
String starterDeptName;            // 发起部门名称
Long lastOperatorId;               // 最后操作人ID
String lastOperatorName;           // 最后操作人姓名
LocalDateTime startTime;           // 开始时间
LocalDateTime endTime;             // 结束时间
String variables;                  // 流程变量(JSON)
WfEndReason endReason;             // 结束原因
String endComment;                 // 结束备注
Long endBy;                        // 结束操作人
String tenantId;                   // 租户ID
```

**与其他表关系**：
- 多对一：wf_instance → wf_definition
- 一对多：wf_instance → wf_node_instance
- 一对多：wf_instance → wf_task
- 一对多：wf_instance → wf_biz_ref

**状态枚举**：
- `RUNNING` - 进行中
- `APPROVED` - 已通过
- `REJECTED` - 已驳回
- `CANCELED` - 已撤销

#### 2.3.3 wf_node_instance (节点实例表)

**用途**：记录流程中每个节点的执行状态

**关键字段**：
```java
Long id;                           // 节点实例ID
Long instanceId;                   // 流程实例ID
String nodeKey;                    // 节点唯一标识
String nodeName;                   // 节点名称
NodeType nodeType;                 // 节点类型(START/APPROVAL/GATEWAY/END)
AssigneeType assigneeType;         // 审批人类型(USER/ROLE/DEPT)
String assigneeValue;              // 审批人值
Long operatorId;                   // 触发该节点进入最终状态的操作人
NodeStatus status;                 // 状态(WAIT/DONE/CANCELED)
Integer orderNo;                   // 执行顺序
LocalDateTime finishedTime;        // 完成时间
String selectNodeKey;              // 选择的节点(网关场景)
```

**与其他表关系**：
- 多对一：wf_node_instance → wf_instance
- 一对多：wf_node_instance → wf_task

**状态枚举**：
- `WAIT` - 待执行
- `DONE` - 已完成
- `CANCELED` - 已取消
- `NOT_REACHED` - 未到达（定义层状态）

**orderNo 说明**：
- START节点：orderNo=1
- 后续节点：orderNo递增
- 用于退回上一节点时查找

#### 2.3.4 wf_task (任务表)

**用途**：记录每个待办任务

**关键字段**：
```java
Long id;                           // 任务ID
Long instanceId;                   // 流程实例ID
Long nodeInstanceId;               // 节点实例ID
Long assigneeId;                   // 审批人ID
Long operatorId;                   // 操作人ID
Long transferFrom;                 // 转交来源
LocalDateTime transferTime;        // 转交时间
TaskStatus status;                 // 状态(TODO/DONE/CANCELED)
TaskAction action;                 // 操作(APPROVE/REJECT/TRANSFER等)
String comment;                    // 审批意见
LocalDateTime finishTime;          // 完成时间
```

**与其他表关系**：
- 多对一：wf_task → wf_instance
- 多对一：wf_task → wf_node_instance
- 一对多：wf_task → wf_task_log

**状态枚举**：
- `TODO` - 待处理
- `DONE` - 已完成
- `CANCELED` - 已取消

**Task-Node 状态关系**：
```
场景              | Task 状态  | Node 状态
-----------------|-----------|----------
节点创建          | TODO       | WAIT
单人审批完成      | DONE       | DONE
会签完成一部分     | DONE/TODO  | WAIT
会签完成全部       | DONE       | DONE
rollback          | CANCELED   | CANCELED
withdraw          | CANCELED   | CANCELED
跳转              | CANCELED   | CANCELED
```

#### 2.3.5 wf_task_log (任务日志表)

**用途**：记录任务操作日志，用于审批历史

**关键字段**：
```java
Long id;                           // 日志ID
Long taskId;                       // 任务ID
Long instanceId;                   // 流程实例ID
TaskAction action;                 // 操作类型
Long operatorId;                   // 操作人ID
String comment;                    // 操作意见
LocalDateTime operateTime;          // 操作时间
```

#### 2.3.6 wf_biz_ref (业务关联表)

**用途**：将流程实例与业务数据解耦关联

**关键字段**：
```java
Long id;                           // 关联ID
String tenantId;                   // 租户ID
String bizType;                    // 业务类型(LEAVE/REIMBURSE)
Long bizId;                        // 业务主键
String bizNo;                      // 业务编号
Long instanceId;                   // 流程实例ID
InstanceStatus status;             // 状态(RUNNING/APPROVED/REJECTED/CANCELED)
String ref_type;                   // 关联类型(PRIMARY/RELATED)
```

**设计意图**：
- 为了让 workflow 支持一对多/多对多业务绑定
- 把 workflow 和业务模块解耦

#### 2.3.7 wf_cc (抄送表)

**用途**：记录流程抄送人

**关键字段**：
```java
Long id;                           // 抄送ID
Long instanceId;                   // 流程实例ID
Long userId;                       // 被抄送人
String readFlag;                   // 是否已读(0未读 1已读)
```

## 三、核心类说明

### 3.1 实体类 (Entity)

位于 `com.yuan.workflow.domain` 包下：

- `WfDefinition` - 流程定义实体
- `WfInstance` - 流程实例实体
- `WfNodeInstance` - 节点实例实体
- `WfTask` - 任务实体
- `WfTaskLog` - 任务日志实体
- `WfBizRef` - 业务关联实体
- `WfCc` - 抄送实体

### 3.2 命令对象 (Cmd)

位于 `com.yuan.workflow.cmd` 包下（在 yuan-workflow-api 模块）：

**基类**：
```java
public abstract class WorkflowCmd {
    private Long operatorId;        // 操作人（当前用户）
    private String operatorName;
    private String tenantId;
    private String comment;         // 备注/审批意见
    Map<String, Object> variables; // 流程变量
}
```

**具体命令**：

**StartProcessCmd** - 发起流程命令
```java
private String definitionKey;   // 流程定义key
private String bizType;          // 业务类型
private Long bizId;              // 业务ID
private String bizNo;            // 业务编号
private Long starterId;          // 发起人ID（可选，代发场景用）
private String title;            // 标题
```

**ApproveTaskCmd** - 审批通过命令
```java
private Long taskId;             // 任务ID
```

**RejectTaskCmd** - 审批拒绝命令
```java
private Long taskId;             // 任务ID
```

**WithdrawCmd** - 撤回命令
```java
private Long instanceId;         // 实例ID
```

**TransferTaskCmd** - 转交命令
```java
private Long taskId;             // 任务ID
private Long toUserId;           // 转办目标人ID
private String reason;          // 转办原因
```

**RollbackToPrevCmd** - 退回上一节点命令
```java
private Long taskId;             // 任务ID
```

**RollbackToActivityCmd** - 退回指定节点命令
```java
private Long taskId;             // 任务ID
private String targetActivityId; // 目标节点
```

### 3.3 控制器类 (Controller)

位于 `com.yuan.workflow.controller` 包下：

**WfDefinitionController** - 流程定义管理
- `list()` - 查询流程定义列表
- `getInfo()` - 获取流程定义详情
- `add()` - 新增流程定义
- `edit()` - 修改流程定义
- `remove()` - 删除流程定义
- `changeStatus()` - 修改流程定义状态（PUBLISH/DISABLE）

**WfInstanceController** - 流程实例管理
- `list()` - 查询实例列表
- `getInfo()` - 获取实例详情
- `start()` - 发起流程
- `getInstanceDetail()` - 获取审批详情（含时间线）

**WfTaskController** - 任务管理
- `approve()` - 审批通过
- `reject()` - 审批拒绝
- `withdraw()` - 撤回申请
- `transfer()` - 转交
- `rollbackTo()` - 退回指定节点
- `rollbackToPrev()` - 退回上一节点

### 3.4 服务类 (Service)

位于 `com.yuan.workflow.service` 包下：

**服务接口**：
- `WorkflowService` - 工作流统一接口
- `WfDefinitionService` - 流程定义服务
- `WfInstanceService` - 流程实例服务
- `WfTaskService` - 任务服务
- `WfNodeInstanceService` - 节点实例服务
- `WfBizRefService` - 业务关联服务
- `WfCcService` - 抄送服务

**WorkflowServiceImpl**（API层统一入口）：
```java
@Service
@FillWfCmd  // 自动填充当前用户/租户信息（AOP）
public class WorkflowServiceImpl implements WorkflowService {
    private final WorkflowEngine workflowEngine;

    public Long startProcess(StartProcessCmd cmd);
    public void approveTask(ApproveTaskCmd cmd);
    public void rejectTask(RejectTaskCmd cmd);
    public void rollbackToPrev(RollbackToPrevCmd cmd);
    public void rollbackTo(RollbackToActivityCmd cmd);
    public void withdraw(WithdrawCmd cmd);
    public void transfer(TransferTaskCmd cmd);
}
```

### 3.5 引擎核心类 (Engine)

**WorkflowEngine** - 引擎接口
```java
public interface WorkflowEngine {
    Long startProcess(StartProcessCmd cmd);
    void approveTask(ApproveTaskCmd cmd);
    void rejectTask(RejectTaskCmd cmd);
    void rollbackToPrev(RollbackToPrevCmd cmd);
    void rollbackToActivity(RollbackToActivityCmd cmd);
    void withdraw(WithdrawCmd cmd);
    void transfer(TransferTaskCmd cmd);
}
```

**SimpleWorkflowEngine** - 引擎实现
```java
@Service
@Transactional
public class SimpleWorkflowEngine implements WorkflowEngine {
    private final StartProcessHandler startProcessHandler;
    private final ApproveTaskHandler approveTaskHandler;
    private final RejectTaskHandler rejectTaskHandler;
    private final RollbackToActivityHandler rollbackToActivityHandler;
    private final RollbackToPrevHandler rollbackToPrevHandler;
    private final WithdrawHandler withdrawHandler;
    private final TransferHandler transferHandler;

    // 委托给对应的Handler处理
}
```

### 3.6 命令处理器 (Handler)

位于 `com.yuan.workflow.core.engine.handler` 包下：

**StartProcessHandler** - 发起流程处理器
```java
@Component
public class StartProcessHandler implements CommandHandler<StartProcessCmd, Long> {
    public Long handle(StartProcessCmd cmd) {
        // 1. 查最新已发布定义
        // 2. 创建流程实例
        // 3. 绑定业务（WfBizRef）
        // 4. 创建START节点（自动完成）
        // 5. 找到下一个节点
        // 6. 创建审批节点+任务
        return instanceId;
    }
}
```

**ApproveTaskHandler** - 审批通过处理器
```java
@Component
public class ApproveTaskHandler implements CommandHandler<ApproveTaskCmd, Void> {
    public Void handle(ApproveTaskCmd cmd) {
        // 1. 加载上下文（task/node/instance/def/bizRef）
        // 2. 参数+权限校验
        // 3. 合并变量
        // 4. 完成任务
        // 5. 完成节点
        // 6. 推进到下一节点
        return null;
    }
}
```

**RejectTaskHandler** - 审批拒绝处理器
```java
@Component
public class RejectTaskHandler implements CommandHandler<RejectTaskCmd, Void> {
    public Void handle(RejectTaskCmd cmd) {
        // 1. 加载上下文
        // 2. 校验
        // 3. 合并变量
        // 4. 完成任务（REJECT）
        // 5. 完成节点（CANCELED）
        // 6. 结束实例（REJECTED）
        // 7. 发布"任务驳回"事件（afterCommit）
        return null;
    }
}
```

**WithdrawHandler** - 撤回处理器
```java
@Component
public class WithdrawHandler implements CommandHandler<WithdrawCmd, Void> {
    public Void handle(WithdrawCmd cmd) {
        // 1. 校验（必须是发起人）
        // 2. 取消所有待办任务（实例级）
        // 3. 取消所有WAIT节点
        // 4. 更新实例状态为CANCELED
        // 5. 发布撤回事件（afterCommit）
        return null;
    }
}
```

**RollbackToPrevHandler** - 退回上一节点处理器（刚实现）
```java
@Component
public class RollbackToPrevHandler implements CommandHandler<RollbackToPrevCmd, Void> {
    public Void handle(RollbackToPrevCmd cmd) {
        // 1. 加载上下文
        // 2. 找到上一节点（按orderNo）
        // 3. 校验操作权限
        // 4. 合并变量
        // 5. 完成当前任务（ROLLBACK）
        // 6. 修改当前节点状态为CANCELED
        // 7. 推进到上一节点
        return null;
    }
}
```

**TransferHandler** - 转交处理器
```java
@Component
public class TransferHandler implements CommandHandler<TransferTaskCmd, Void> {
    public Void handle(TransferTaskCmd cmd) {
        // 1. 加载上下文
        // 2. 校验操作权限
        // 3. 更新任务的assigneeId、transferFrom、transferTime
        // 4. 记录日志
        return null;
    }
}
```

**RollbackToActivityHandler** - 退回指定节点处理器

### 3.7 支撑服务 (Support Services)

位于 `com.yuan.workflow.core.engine.support` 包下：

**WfContextLoader** - 上下文加载器
```java
public record TaskCtx(WfTask task, WfNodeInstance node, WfInstance instance,
                     WfDefinition def, WfBizRef bizRef) {}

public TaskCtx loadTaskCtx(Long taskId) {
    // 通过taskId一次性加载task、node、instance、definition、bizRef
    // 用于避免N+1查询问题
}
```

**FlowAdvanceService** - 流程推进服务
```java
public void advance(WfNodeInstance currentNode, ApproveTaskCmd cmd) {
    // 1. 获取实例和定义
    // 2. 获取当前节点
    // 3. 用最新变量找下一节点
    // 4. 推进到目标节点
}

public void advanceToTarget(WfInstance instance, LfNode lfNode, WorkflowCmd cmd) {
    // 1. 如果是END节点，结束实例
    // 2. 否则创建节点实例和任务
}
```

**TaskLifecycle** - 任务生命周期管理
```java
public void finish(WfTask task, TaskAction action, String comment, Long operatorId) {
    // 1. 更新任务状态为DONE
    // 2. 或签场景：取消同节点其他人的TODO任务
    // 3. 插入任务日志
}

public void transfer(WfTask task, TransferTaskCmd cmd) {
    // 更新任务assignee和转交信息
}

public boolean allDone(Long nodeInstanceId) {
    // 会签场景：判断该节点所有任务是否都DONE
}
```

**NodeInstanceLifeCycle** - 节点实例生命周期管理
```java
public void finishDone(Long nodeInstanceId, Long operatorId) {
    // 完成节点，状态改为DONE
}

public void finishCancel(Long nodeInstanceId, Long operatorId) {
    // 取消节点，状态改为CANCELED
}

public void cancelAllWaitByInstance(Long instanceId, Long operatorId) {
    // 取消实例所有WAIT节点（用于撤回）
}
```

**InstanceLifecycle** - 流程实例生命周期管理
```java
public void finishApproved(WfInstance instance, WorkflowCmd cmd) {
    // 完成实例（通过），发布实例结束事件
}

public void finishRejected(WfInstance instance, RejectTaskCmd cmd) {
    // 完成实例（拒绝），发布实例结束事件
}

public void finishWithDraw(WfInstance instance, WithdrawCmd cmd) {
    // 完成实例（撤回），取消所有待办和WAIT节点，发布事件
}
```

**VariableService** - 变量服务
```java
public void mergeAndSave(WfInstance instance, Map<String, Object> newVars) {
    // 合并新变量并保存到wf_instance.variables字段
}

public Map<String, Object> getVars(WfInstance instance) {
    // 从JSON字段解析变量
}
```

### 3.8 流程解析器 (Parser)

**FlowParser** - 流程定义解析器
```java
public LfNode getStartNode(WfDefinition def);
public LfNode getNode(WfDefinition def, String nodeKey);
public LfNode getNextNode(WfDefinition def, LfNode currentNode,
                         Map<String, Object> variables);
```

解析逻辑：
1. 非网关：只有一条出边，直接返回目标节点；若目标节点是网关则继续查找
2. 网关：按条件判断遍历出边，找到匹配条件的目标节点；若目标节点是网关则继续查找；若无匹配且无默认，抛出异常

### 3.9 审批人解析器 (Resolver)

**AssigneeResolver** - 审批人解析器
```java
public Set<Long> resolve(LfNode node) {
    AssigneeKind kind = node.getProperties().getAssignee().getKind();
    AssigneeType type = node.getProperties().getAssignee().getType();
    if (FIXED) {
        if (USER) return assignee.getUserIds();
        if (ROLE) return userQueryApi.findUserIdsByRoleIds(...);
        if (DEPT) return userQueryApi.findUserIdsByDeptIds(...);
        if (POST) return userQueryApi.findUserIdsByPostIds(...);
    }
}
```

### 3.10 条件求值器 (Evaluator)

**ConditionEvaluator** - 条件表达式求值器
- 支持AND/OR嵌套
- 支持操作符：EQ/NE/GT/GE/LT/LE/IN/NOT_IN
- 支持类型：number/date/datetime/boolean/string

**SimpleConditionEvaluator** - 简单条件求值器实现

### 3.11 操作守卫 (Guard)

**WfOperationGuard** - 操作守卫（权限校验）
```java
public void assertCanOperate(WfTask task, Long operatorId) {
    assertTodoTask(task, operatorId);
    assertAssignee(task, operatorId);
}

public void assertStartInstance(WfDefinition definition, StartProcessCmd cmd) {
    // 校验定义是否存在
    // 校验bizNo是否已存在
}

public void assertWithDraw(WfInstance instance, Long operatorId) {
    // 校验必须是发起人
}
```

### 3.12 事件机制 (Event)

**WfEvent** - 工作流事件（领域事件）
```java
public class WfEvent implements Serializable {
    private String eventId;
    private WfEventType eventType;      // TASK_DECIDED/INSTANCE_ENDED等
    private Long instanceId;
    private Long taskId;
    private String definitionKey;
    private Long operatorId;
    private WfDecision decision;         // APPROVE/REJECT/ROLLBACK...
    private WfEndReason endReason;       // APPROVED/REJECTED/WITHDRAWN...
    private String bizType;
    private Long bizId;
    // ...更多字段见源码
}
```

**WfEventFactory** - 事件工厂
- `buildTaskRejected()` - 构建任务驳回事件
- `buildInstanceEnded()` - 构建实例结束事件

**SpringWfEventPublisher** - Spring事件发布器（事务提交后发布）

### 3.13 组件 (Component)

**WfCmdFiller** - 命令填充器
```java
public void fill(WorkflowCmd cmd) {
    LoginUser u = LoginHelper.getLoginUser();
    cmd.setTenantId(u.getTenantId());
    cmd.setOperatorId(u.getUserId());
    cmd.setOperatorName(u.getNickName());
    if (cmd instanceof StartProcessCmd) {
        // 填充发起人信息
    }
}
```

**WfCmdFillAspect** - AOP切面，自动调用填充器

## 四、业务流程说明

### 4.1 流程启动

**调用链**：
```
用户发起
  ↓
WfInstanceController.start()
  ↓
WorkflowServiceImpl.startProcess()  [AOP自动填充用户信息]
  ↓
SimpleWorkflowEngine.startProcess()
  ↓
StartProcessHandler.handle()
  ├─ 查最新已发布定义
  ├─ 创建流程实例 (RUNNING)
  ├─ 创建业务关联
  ├─ 创建START节点 (DONE)
  ├─ 解析找到下一节点
  └─ 创建审批节点 (WAIT) + 任务 (TODO)
```

**关键代码**：
```java
// 1) 查最新已发布定义
WfDefinition def = definitionMapper.selectLatestPublished(tenantId, cmd.getDefinitionKey());

// 2) 创建实例
WfInstance instance = wfInstanceService.createInstance(cmd, def);

// 3) 绑定业务
wfBizRefService.bindWfBizRef(cmd, instance.getId());

// 4) start节点（自动完成）
LfNode startNode = flowParser.getStartNode(def);
wfNodeInstanceService.createNodeInstance(instance.getId(), startNode, NodeStatus.DONE, 1);

// 5) 找到下一个节点
LfNode firstApproveNode = flowParser.getNextNode(def, startNode, cmd.getVariables());

// 6) 创建审批节点 + 任务
if (firstApproveNode != null && !NodeType.END.equals(...)) {
    WfNodeInstance nodeInstance = wfNodeInstanceService.createNodeInstance(..., NodeStatus.WAIT, 2);
    Set<Long> userIds = assigneeResolver.resolve(firstApproveNode);
    wfTaskService.createTasks(instance, nodeInstance, userIds);
} else {
    // 直接结束
    instanceLifecycle.finishApproved(instance, cmd);
}
```

### 4.2 审批通过

**调用链**：
```
用户点击同意
  ↓
WorkflowServiceImpl.approveTask()  [AOP自动填充用户信息]
  ↓
SimpleWorkflowEngine.approveTask()
  ↓
ApproveTaskHandler.handle()
  ├─ WfContextLoader.loadTaskCtx()  加载上下文
  ├─ WfOperationGuard.assertCanOperate()  权限校验
  ├─ VariableService.mergeAndSave()  合并变量
  ├─ TaskLifecycle.finish()  完成任务
  ├─ NodeInstanceLifeCycle.finishDone()  完成节点
  └─ FlowAdvanceService.advance()  推进到下一节点
      ├─ FlowParser.getNextNode()  找下一节点
      └─ 若是END → InstanceLifecycle.finishApproved()
      └─ 若是USER_TASK → 创建节点实例+任务
```

### 4.3 审批拒绝

**调用链**：
```
用户点击拒绝
  ↓
WorkflowServiceImpl.rejectTask()  [AOP自动填充用户信息]
  ↓
SimpleWorkflowEngine.rejectTask()
  ↓
RejectTaskHandler.handle()
  ├─ WfContextLoader.loadTaskCtx()
  ├─ WfOperationGuard.assertCanOperate()
  ├─ VariableService.mergeAndSave()
  ├─ TaskLifecycle.finish(action=REJECT)
  ├─ NodeInstanceLifeCycle.finishCancel()
  ├─ SpringWfEventPublisher.publishAfterCommit()  发布事件
  └─ InstanceLifecycle.finishRejected(status=REJECTED)
```

### 4.4 退回上一节点（刚实现）

**调用链**：
```
用户点击退回上一节点
  ↓
WorkflowServiceImpl.rollbackToPrev()  [AOP自动填充用户信息]
  ↓
SimpleWorkflowEngine.rollbackToPrev()
  ↓
RollbackToPrevHandler.handle()
  ├─ WfContextLoader.loadTaskCtx()
  ├─ findPreviousNode(instanceId, orderNo)  按orderNo查找
  │   └─ 查询：status=DONE, orderNo<currentOrderNo, ORDER BY orderNo DESC LIMIT 1
  ├─ FlowParser.getNode(def, nodeKey)  从定义中获取节点信息
  ├─ WfOperationGuard.assertCanOperate()
  ├─ VariableService.mergeAndSave()
  ├─ TaskLifecycle.finish(action=ROLLBACK)
  ├─ NodeInstanceLifeCycle.finishCancel()
  └─ FlowAdvanceService.advanceToTarget()  推进到上一节点
```

**关键代码**（RollbackToPrevHandler）：
```java
// 找到上一节点（按orderNo）
LfNode prevNode = findPreviousNode(def, instance.getId(), currentNode.getOrderNo());
if (prevNode == null) {
    throw new RollbackTargetInvalidException();
}

// 判断当前操作人是否可操作该任务
wfOperationGuard.assertCanOperate(task, cmd.getOperatorId());

variableService.mergeAndSave(taskCtx.instance(), cmd.getVariables());

// 完成当前任务（ROLLBACK）
taskLifecycle.finish(task, TaskAction.ROLLBACK, cmd.getComment(), cmd.getOperatorId());

// 修改节点状态（CANCELED）
nodeInstanceLifeCycle.finishCancel(currentNode.getId(), cmd.getOperatorId());

// 推进到上一节点
flowAdvanceService.advanceToTarget(instance, prevNode, cmd);
```

### 4.5 撤回

**调用链**：
```
发起人点击撤回
  ↓
WorkflowServiceImpl.withdraw()  [AOP自动填充用户信息]
  ↓
SimpleWorkflowEngine.withdraw()
  ↓
WithdrawHandler.handle()
  ├─ WfInstanceMapper.selectById(instanceId)
  ├─ WfOperationGuard.assertWithDraw(instance, operatorId)  校验是否是发起人
  ├─ InstanceLifecycle.finishWithDraw(instance, cmd)
  │   ├─ TaskLifecycle.cancelAllTodoTasks(instanceId)  取消所有TODO任务
  │   ├─ NodeInstanceLifeCycle.cancelAllWaitByInstance(instanceId)  取消所有WAIT节点
  │   ├─ 更新wf_instance: status=CANCELED, endTime, endComment, endBy
  │   ├─ afterFinishProcess()  更新wf_biz_ref.status=CANCELED
  │   └─ SpringWfEventPublisher.publishAfterCommit()  发布事件
  └─ return
```

### 4.6 转交

**调用链**：
```
用户点击转交
  ↓
WorkflowServiceImpl.transfer()  [AOP自动填充用户信息]
  ↓
SimpleWorkflowEngine.transfer()
  ↓
TransferHandler.handle()
  ├─ WfContextLoader.loadTaskCtx()
  ├─ WfOperationGuard.assertCanOperate()
  └─ TaskLifecycle.transfer(task, cmd)
      ├─ 更新wf_task: assigneeId=toUserId, transferFrom=operatorId, transferTime=NOW
      └─ WfTaskLogMapper.insert(action=TRANSFER)
```

## 五、常用枚举说明

### 5.1 InstanceStatus - 流程实例状态

```java
public enum InstanceStatus {
    RUNNING("RUNNING", "进行中"),
    APPROVED("APPROVED", "已通过"),
    REJECTED("REJECTED", "已驳回"),
    CANCELED("CANCELED", "已撤销");
}
```

### 5.2 TaskStatus - 任务状态

```java
public enum TaskStatus {
    TODO("TODO", "待处理"),
    DONE("DONE", "已完成"),
    CANCELED("CANCELED", "已取消");
}
```

### 5.3 TaskAction - 任务操作类型

```java
public enum TaskAction {
    ANY_APPROVE("ANY_APPROVE", "或签同意"),
    REJECT("REJECT", "驳回"),
    ROLLBACK("ROLLBACK", "退回"),
    TRANSFER("TRANSFER", "转签"),
    ADD_SIGN("ADD_SIGN", "加签"),
    ALL_APPROVE("ALL_APPROVE","会签同意"),
    WITHDRAW("WITHDRAW", "撤回");
}
```

### 5.4 NodeStatus - 节点状态

```java
public enum NodeStatus {
    // 运行层状态
    WAIT("WAIT", "待执行"),
    DONE("DONE", "已完成"),
    CANCELED("CANCELED", "已取消"),
    // 定义层状态
    NOT_REACHED("NOT_REACHED","未到达");
}
```

### 5.5 NodeType - 节点类型

```java
public enum NodeType {
    START("START", "开始节点"),
    USER_TASK("USER_TASK", "审批节点"),
    SYSTEM_TASK("SYSTEM_TASK", "系统审批"),
    GATEWAY("GATEWAY", "条件网关"),
    END("END", "结束节点");
}
```

### 5.6 DefinitionStatus - 流程定义状态

```java
public enum DefinitionStatus {
    DRAFT("DRAFT", "草稿"),
    PUBLISHED("PUBLISHED", "已发布"),
    DISABLED("DISABLED", "已停用");
}
```

### 5.7 AssigneeType - 审批人类型

```java
public enum AssigneeType {
    USER("USER", "指定用户"),
    ROLE("ROLE", "角色"),
    DEPT("DEPT", "部门"),
    STARTER("STARTER", "流程发起人"),
    STARTER_MANAGER("STARTER_MANAGER", "发起人上级");
}
```

### 5.8 WfEndReason - 流程结束原因

```java
public enum WfEndReason {
    APPROVED("APPROVED","正常流转通过导致结束"),
    REJECTED("REJECTED","驳回导致结束"),
    WITHDRAWN("WITHDRAWN","撤回导致结束"),
    TERMINATED("TERMINATED","管理员强制终止");
}
```

### 5.9 ConditionOperator - 条件操作符

```java
public enum ConditionOperator {
    EQ("EQ", "="),
    NE("NE", "!="),
    GT("GT", ">"),
    GE("GE", ">="),
    LT("LT", "<"),
    LE("LE", "<="),
    IN("IN", "包含"),
    NOT_IN("NOT_IN", "不包含");
}
```

## 六、如何快速上手

### 6.1 发起一个流程

```java
// 1. 构建发起命令
StartProcessCmd cmd = new StartProcessCmd();
cmd.setDefinitionKey("leave");  // 流程定义key
cmd.setBizType("LEAVE");        // 业务类型
cmd.setBizId(123L);             // 业务ID
cmd.setBizNo("LEAVE20260126001"); // 业务编号
cmd.setTitle("请假申请");       // 标题

// 2. 设置流程变量（可选，用于网关判断）
Map<String, Object> variables = new HashMap<>();
variables.put("days", 3);
variables.put("amount", 1000);
cmd.setVariables(variables);

// 3. 调用服务发起
Long instanceId = workflowService.startProcess(cmd);
System.out.println("流程实例ID: " + instanceId);
```

### 6.2 审批一个任务

```java
// 1. 构建审批命令
ApproveTaskCmd cmd = new ApproveTaskCmd();
cmd.setTaskId(456L);           // 任务ID
cmd.setComment("同意");        // 审批意见

// 2. 可选：设置流程变量
Map<String, Object> variables = new HashMap<>();
variables.put("next", true);
cmd.setVariables(variables);

// 3. 调用服务审批
workflowService.approveTask(cmd);
```

### 6.3 拒绝一个任务

```java
RejectTaskCmd cmd = new RejectTaskCmd();
cmd.setTaskId(456L);
cmd.setComment("驳回原因：资料不完整");
workflowService.rejectTask(cmd);
```

### 6.4 转交任务

```java
TransferTaskCmd cmd = new TransferTaskCmd();
cmd.setTaskId(456L);
cmd.setToUserId(789L);         // 转交给谁
cmd.setReason("转交原因");
workflowService.transfer(cmd);
```

### 6.5 退回上一节点

```java
RollbackToPrevCmd cmd = new RollbackToPrevCmd();
cmd.setTaskId(456L);
cmd.setComment("退回修改");
workflowService.rollbackToPrev(cmd);
```

### 6.6 退回指定节点

```java
RollbackToActivityCmd cmd = new RollbackToActivityCmd();
cmd.setTaskId(456L);
cmd.setTargetActivityId("node_123"); // 目标节点ID
cmd.setComment("退回重新审批");
workflowService.rollbackTo(cmd);
```

### 6.7 撤回流程

```java
WithdrawCmd cmd = new WithdrawCmd();
cmd.setInstanceId(123L);
cmd.setComment("撤回原因");
workflowService.withdraw(cmd);
```

### 6.8 查询我的待办

```java
// 1. 构建查询条件
WfTaskBo bo = new WfTaskBo();
bo.setTenantId("your-tenant-id");

// 2. 分页查询
PageQuery pageQuery = new PageQuery();
pageQuery.setPageNum(1);
pageQuery.setPageSize(10);

// 3. 调用服务
TableDataInfo<WfTaskVo> result = wfTaskService.queryPageList(bo, pageQuery);
```

### 6.9 创建流程定义

```java
// 1. 构建定义对象
WfDefinitionBo bo = new WfDefinitionBo();
bo.setDefinitionKey("expense");
bo.setDefinitionName("报销流程");
bo.setStatus(DefinitionStatus.DRAFT);
bo.setFormSchema("{...}");  // JSON Schema
bo.setFlowJson("{...}");   // 流程图JSON

// 2. 保存草稿
wfDefinitionService.insertByBo(bo);

// 3. 发布流程
wfDefinitionService.changeStatus(bo.getId(), DefinitionAction.PUBLISH);
```

### 6.10 调试技巧

**查看数据库表状态**：
```sql
-- 查看实例状态
SELECT * FROM wf_instance WHERE id = 123;

-- 查看当前待办任务
SELECT * FROM wf_task WHERE instance_id = 123 AND status = 'TODO';

-- 查看节点执行顺序
SELECT * FROM wf_node_instance WHERE instance_id = 123 ORDER BY order_no;

-- 查看任务日志
SELECT * FROM wf_task_log WHERE task_id = 456 ORDER BY operate_time;
```

**启用SQL日志**：
```yaml
mybatis-plus:
  configuration:
    log-impl: org.apache.ibatis.logging.stdout.StdOutImpl
```

**断点调试关键位置**：
- `SimpleWorkflowEngine` - 入口
- `StartProcessHandler` - 发起流程
- `ApproveTaskHandler` - 审批通过
- `RejectTaskHandler` - 审批拒绝
- `RollbackToPrevHandler` - 退回上一节点
- `FlowAdvanceService.advance()` - 流程推进

**事件监听（用于业务回调）**：
```java
@Component
public class WorkflowEventListener {

    @EventListener
    public void handleInstanceEnded(WfEvent event) {
        if (event.getEventType() == WfEventType.INSTANCE_ENDED) {
            // 流程结束，更新业务状态
            Long bizId = event.getBizId();
            String bizType = event.getBizType();
            WfEndReason reason = event.getEndReason();

            // 根据reason更新业务表
            if (reason == WfEndReason.APPROVED) {
                // 通过
            } else if (reason == WfEndReason.REJECTED) {
                // 驳回
            }
        }
    }
}
```

## 七、注意事项

### 7.1 常见问题

**Q1: 流程启动失败，提示"流程定义不存在"**
- 检查definitionKey是否正确
- 检查流程定义状态是否为PUBLISHED
- 检查租户ID是否匹配

**Q2: 审批时提示"任务不存在"或"无权限操作"**
- 检查taskId是否正确
- 检查任务状态是否为TODO
- 检查assigneeId是否为当前登录用户

**Q3: 退回上一节点失败**
- 确认当前节点不是第一个节点
- 确认orderNo正确（start节点orderNo=1）

**Q4: 撤回失败，提示"无权限"**
- 只有流程发起人可以撤回
- 检查instance.starterId是否等于当前用户ID

**Q5: 流程没有按照预期流转**
- 检查网关条件表达式是否正确
- 检查流程变量是否正确传递
- 查看wf_instance.variables字段

### 7.2 开发规范

1. **使用统一的异常处理**
```java
// 抛出业务异常
throw new WorkflowException(WorkflowErrorCode.WF_DEFINITION_NOT_FOUND);
```

2. **操作命令必须经过AOP自动填充**
```java
@Service
@FillWfCmd  // 自动填充tenantId/operatorId等
public class WorkflowServiceImpl implements WorkflowService {
    // ...
}
```

3. **所有关键操作使用事务**
```java
@Transactional
public Void handle(ApproveTaskCmd cmd) {
    // ...
}
```

4. **事件在事务提交后发布**
```java
eventPublisher.publishAfterCommit(event);  // 确保事务成功后才发布
```

5. **使用上下文加载器避免N+1查询**
```java
WfContextLoader.TaskCtx ctx = contextLoader.loadTaskCtx(taskId);
// 一次性加载task/node/instance/definition/bizRef
```

6. **或签场景：审批一个人后，取消其他人的任务**
```java
// 在TaskLifecycle.finish()中调用
cancelOtherTodoTasks(nodeInstanceId, keepTaskId, action);
```

7. **会签场景：所有任务完成后才推进**
```java
if (taskLifecycle.allDone(nodeInstanceId)) {
    // 推进到下一节点
}
```

8. **状态更新必须带乐观锁**
```java
// MyBatis-Plus使用@Version注解
@Version
private Integer version;
```

9. **使用枚举而非字符串常量**
```java
instance.setStatus(InstanceStatus.RUNNING);
// 而不是 instance.setStatus("RUNNING");
```

10. **所有数据库操作使用MyBatis-Plus**
```java
// 使用lambdaQuery避免硬编码字段名
nodeInstanceMapper.update(Wrappers.<WfNodeInstance>lambdaUpdate()
    .set(WfNodeInstance::getStatus, NodeStatus.DONE)
    .eq(WfNodeInstance::getId, nodeInstanceId));
```

### 7.3 性能优化建议

1. **流程定义解析结果缓存**
```java
private final Map<String, LfGraph> cache = new ConcurrentHashMap<>();
```

2. **批量查询避免N+1**
```java
// 使用WfContextLoader一次性加载所有相关数据
Set<Long> instanceIds = tasks.stream().map(WfTask::getInstanceId).collect(Collectors.toSet());
List<WfInstance> instances = instanceMapper.selectByIds(instanceIds);
```

3. **分页查询大数据量**
```java
Page<WfTask> page = taskMapper.selectPage(pageQuery.build(), lqw);
```

4. **合理使用索引**
- wf_instance(definition_key, definition_version, status)
- wf_task(instance_id, assignee_id, status)
- wf_task_log(task_id)

### 7.4 扩展建议

1. **自定义审批人解析器**
```java
@Component
public class CustomAssigneeResolver {
    public Set<Long> resolve(LfNode node) {
        // 自定义逻辑
    }
}
```

2. **自定义条件求值器**
```java
public class CustomConditionEvaluator extends ConditionEvaluator {
    @Override
    protected boolean eq(Object actual, String expected, String valueType) {
        // 自定义比较逻辑
    }
}
```

3. **监听流程事件做业务回调**
```java
@EventListener
public void handle(WfEvent event) {
    // 监听各种事件：TASK_DECIDED、INSTANCE_ENDED等
}
```

4. **扩展任务操作类型**
```java
// 在TaskAction枚举中添加
ADD_SIGN("ADD_SIGN", "加签"),
DELEGATE("DELEGATE", "委派"),
```

## 八、总结

yuan-workflow模块是一个轻量级的工作流引擎，具有以下特点：

### 8.1 优势

1. ✅ 架构清晰，分层合理（Controller/Service/Engine/Mapper）
2. ✅ 使用命令模式+策略模式，扩展性强
3. ✅ 内置权限校验、变量管理、条件判断
4. ✅ 支持或签、会签、退回、转交、撤回等常见功能
5. ✅ 通过事件机制与业务解耦
6. ✅ 支持多租户隔离

### 8.2 待完善功能

1. ⚠️ 数据校验逻辑不完善（多处TODO）
2. ⚠️ 动态审批人解析未实现
3. ⚠️ 部分状态流转校验被注释
4. ⚠️ 缺少单元测试

### 8.3 快速参考

| 功能 | Controller方法 | Service方法 | Handler |
|-----|--------------|------------|---------|
| 发起流程 | `WfInstanceController.start()` | `workflowService.startProcess()` | `StartProcessHandler` |
| 审批通过 | `WfTaskController.approve()` | `workflowService.approveTask()` | `ApproveTaskHandler` |
| 审批拒绝 | `WfTaskController.reject()` | `workflowService.rejectTask()` | `RejectTaskHandler` |
| 退回上一节点 | `WfTaskController.rollbackToPrev()` | `workflowService.rollbackToPrev()` | `RollbackToPrevHandler` |
| 退回指定节点 | `WfTaskController.rollbackTo()` | `workflowService.rollbackTo()` | `RollbackToActivityHandler` |
| 撤回 | `WfTaskController.withdraw()` | `workflowService.withdraw()` | `WithdrawHandler` |
| 转交 | `WfTaskController.transfer()` | `workflowService.transfer()` | `TransferHandler` |

---

**文档版本**: v1.0
**更新日期**: 2026-01-26
**适用模块**: yuan-workflow
**作者**: Auto AI Assistant

通过本文档，新开发者可以：
- 理解整体架构和各层职责
- 掌握数据库表结构和关系
- 熟悉核心类的作用和使用方法
- 了解业务流程的完整链路
- 快速上手开发工作流相关功能

如有疑问，建议：
1. 查看源码注释（代码中有详细注释）
2. 启动SQL日志观察数据库操作
3. 断点调试关键流程
4. 查看单元测试用例
