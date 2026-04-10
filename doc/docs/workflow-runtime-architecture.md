# Workflow Runtime Architecture

## 1. Document Purpose

本文档用于说明 `yuan-workflow` 在**运行时**的核心组件、职责边界、调用链路与设计原则。

与 `workflow-design.md` 偏“概念设计”不同，本文档更关注：

- Runtime 执行链路
- Command 与 Handler 的职责划分
- Node 到达后的处理机制
- ProcessAdvancer 的定位
- Guard / Event / Parser 的协作方式
- AI Node 在运行时中的接入方式

---

## 2. Runtime Core Components

运行时建议围绕以下组件组织：

```text
Command
CommandHandler
NodeArrivalHandler
ProcessAdvancer
FlowParser
WfOperationGuard
WfEventPublisher
WfRuntimeContext
```

---

## 3. Component Responsibilities

### 3.1 Command

`Command` 表示一次明确的流程操作请求。

典型命令：

```text
StartCmd
ApproveTaskCmd
RejectTaskCmd
RollbackToPrevCmd
TransferTaskCmd
TerminateProcessCmd
```

特点：

- 只承载参数
- 不包含业务逻辑
- 面向“动作”建模，而不是面向“表”建模

建议：

- 每个命令只表达一个动作
- 命名统一使用动词 + 业务对象
- 命令中保留必要的 operator / comment / variables / bizInfo 等字段

---

### 3.2 CommandHandler

`CommandHandler<CMD, R>` 负责执行具体命令。

典型实现：

```text
StartHandler
ApproveTaskHandler
RejectTaskHandler
RollbackToPrevHandler
TransferTaskHandler
TerminateProcessHandler
```

职责：

- 参数合法性校验
- 加载流程上下文
- 调用 Guard 做操作校验
- 更新实例 / 任务 / 节点状态
- 调用 `ProcessAdvancer` 推进流程
- 发布流程事件

不负责：

- 解析完整流程图细节
- 决定节点到达后的全部逻辑
- 承担所有节点类型分支判断

建议原则：

- 一个 Handler 只处理一种 Command
- Handler 自身保持“薄”
- 复杂推进逻辑下沉给 `ProcessAdvancer`
- 节点差异逻辑下沉给 `NodeArrivalHandler`

---

### 3.3 NodeArrivalHandler

`NodeArrivalHandler` 负责处理“流程推进到某个节点后应该怎么做”。

建议接口：

```text
NodeArrivalHandler {
    boolean supports(nodeType)
    void onArrive(WfRuntimeContext context, FlowNode node)
}
```

典型实现：

```text
StartNodeArrivalHandler
UserTaskArrivalHandler
GatewayArrivalHandler
EndNodeArrivalHandler
AiTaskArrivalHandler
```

职责：

- 创建节点实例
- 判断该节点是否需要创建任务
- 判断是否立即继续推进
- 处理节点类型差异

各类节点处理建议：

#### StartNodeArrivalHandler
- 通常仅记录开始节点到达
- 可立即推进到下一节点

#### UserTaskArrivalHandler
- 创建 `wf_node_instance`
- 创建待办任务 `wf_task`
- 挂起流程，等待人工操作

#### GatewayArrivalHandler
- 根据条件计算出边
- 决定一个或多个后继节点
- 通常不创建人工任务
- 可直接继续推进

#### EndNodeArrivalHandler
- 完成结束节点实例
- 关闭流程实例
- 记录结束原因
- 发布结束事件

#### AiTaskArrivalHandler
- 调用 AI 服务进行分析
- 输出建议 / 标签 / 结果摘要
- 将 AI 结果写入变量或意见表
- 默认不直接决定审批通过或拒绝
- 通常仍推进到人工审批节点

---

### 3.4 ProcessAdvancer

`ProcessAdvancer` 是运行时的**流程推进器**，是最核心的 runtime 组件之一。

定位：

> 负责“从当前状态出发，把流程推进到下一个或下一批节点”。

建议职责：

- 读取当前流程图
- 解析当前节点与后继节点
- 逐个触发对应 `NodeArrivalHandler`
- 在网关、并行、结束等场景下协调推进
- 统一封装“继续流转”的公共逻辑

适合放在 `ProcessAdvancer` 的逻辑：

- `next nodes` 计算
- 节点遍历推进
- 到达节点的分发
- 流程递归 / 迭代推进
- 统一变量上下文传递

不适合放在 `ProcessAdvancer` 的逻辑：

- 命令级权限校验
- controller 参数转换
- AI 模型配置查询细节
- 具体某种任务审批业务规则

建议：

- `ProcessAdvancer` 做成纯 runtime 服务
- 避免与具体业务模块耦合
- 优先依赖抽象接口而非 controller/service 细节

---

### 3.5 FlowParser

`FlowParser` 负责解析流程定义 JSON / DSL。

职责：

- 解析节点列表
- 解析边列表
- 构建 nodeId -> node 映射
- 获取后继节点
- 计算网关条件
- 提供拓扑与图遍历基础能力

建议输出能力：

```text
getNode(nodeId)
getOutgoing(nodeId)
getIncoming(nodeId)
findNextNodes(nodeId, variables)
isEndNode(nodeId)
```

建议：

- `FlowParser` 保持纯粹
- 不直接操作数据库运行时状态
- 只对“定义”负责，不对“实例”负责

---

### 3.6 WfOperationGuard

`WfOperationGuard` 负责运行时操作合法性校验。

典型校验：

- 流程实例是否存在
- 流程是否已结束
- 任务是否存在
- 任务是否已完成
- 当前用户是否有权操作
- 是否允许回退 / 转办 / 撤回
- 节点状态是否允许当前动作

建议：

- 所有“操作前校验”统一收口到 Guard
- Handler 只负责调用 Guard
- 错误信息保持统一可读

这样可以避免：

- 校验逻辑散落在各个 Handler 中
- 同类规则重复实现
- 审批 / 驳回 / 回退逻辑不一致

---

### 3.7 WfEventPublisher

`WfEventPublisher` 负责流程事件发布。

建议事件：

```text
PROCESS_STARTED
TASK_CREATED
TASK_COMPLETED
TASK_REJECTED
PROCESS_ENDED
NODE_ARRIVED
NODE_COMPLETED
AI_NODE_ANALYZED
```

用途：

- 审计日志
- 消息通知
- 业务回调
- AI记录
- 后续扩展监听器机制

建议：

- 事件在事务提交后触发更稳妥
- 可通过 after-commit 机制发布
- 事件对象统一封装上下文数据

---

### 3.8 WfRuntimeContext

建议引入 `WfRuntimeContext` 作为运行时上下文对象。

建议包含：

```text
definition
instance
currentNode
operator
variables
bizRef
comment
routeDecision
```

价值：

- 避免方法参数过长
- 方便在 Handler / Advancer / ArrivalHandler 之间传递上下文
- 统一 AI / Gateway / Task 节点共享数据

---

## 4. Runtime Main Flows

### 4.1 Start Process Flow

```text
Client Request
   ↓
StartCmd
   ↓
StartHandler
   ↓
Guard 校验
   ↓
创建 wf_instance
   ↓
创建开始节点 node_instance
   ↓
ProcessAdvancer.advanceFrom(startNode)
   ↓
NodeArrivalHandler 分发
   ↓
创建首个 UserTask / Gateway / End
```

建议：

- `StartHandler` 只做“启动实例”和“触发首轮推进”
- 真正的后继节点流转交给 `ProcessAdvancer`

---

### 4.2 Approve Task Flow

```text
Client Request
   ↓
ApproveTaskCmd
   ↓
ApproveTaskHandler
   ↓
Guard 校验任务可审批
   ↓
完成当前任务
   ↓
完成当前节点实例（如适用）
   ↓
ProcessAdvancer.advanceFrom(currentNode)
   ↓
到达下一个节点
```

审批通过后的关键点：

- 当前任务状态改为已完成
- 记录处理意见
- 写入处理人 / 时间
- 更新实例变量
- 推进后继节点

---

### 4.3 Reject Task Flow

```text
Client Request
   ↓
RejectTaskCmd
   ↓
RejectTaskHandler
   ↓
Guard 校验
   ↓
关闭当前任务
   ↓
更新实例状态 / 结束原因 / 回退标记
   ↓
按设计规则处理：
   - 结束流程
   - 回退到指定节点
   - 退回发起人
```

建议：

- “驳回”不是单一行为，底层策略需要清晰定义
- 先区分：
  - Reject To End
  - Reject To Previous
  - Reject To Starter

---

### 4.4 Gateway Flow

```text
到达 Gateway 节点
   ↓
GatewayArrivalHandler
   ↓
读取 variables
   ↓
计算满足条件的出边
   ↓
ProcessAdvancer 继续推进
```

建议：

- 条件表达式求值与出边筛选逻辑独立封装
- Gateway 不直接处理任务，只负责路由决策

---

### 4.5 AI Node Flow

```text
到达 AI Node
   ↓
AiTaskArrivalHandler
   ↓
加载 workflow 默认模型 / 租户模型策略
   ↓
调用 AI 服务
   ↓
写入 AI 分析结果
   ↓
继续推进到人工节点
```

建议原则：

- AI 主要做建议，不直接替代审批人
- AI 输出可存入 variables / comment / ai_result 表
- 后续可支持“AI 自动通过”作为受控策略，但不要默认开启

---

## 5. Suggested Package Structure

可参考如下包结构：

```text
yuan-workflow
 └─ runtime
    ├─ command
    │  ├─ StartCmd
    │  ├─ ApproveTaskCmd
    │  ├─ RejectTaskCmd
    │  └─ ...
    ├─ handler
    │  ├─ command
    │  │  ├─ StartHandler
    │  │  ├─ ApproveTaskHandler
    │  │  └─ ...
    │  └─ arrival
    │     ├─ UserTaskArrivalHandler
    │     ├─ GatewayArrivalHandler
    │     ├─ EndNodeArrivalHandler
    │     └─ AiTaskArrivalHandler
    ├─ parser
    │  └─ FlowParser
    ├─ advance
    │  └─ ProcessAdvancer
    ├─ guard
    │  └─ WfOperationGuard
    ├─ event
    │  ├─ WfEvent
    │  ├─ WfEventPublisher
    │  └─ WfEventType
    └─ context
       └─ WfRuntimeContext
```

---

## 6. Transaction and Concurrency Strategy

### 6.1 Transaction Boundary

建议：

- 一个 CommandHandler 对应一个主事务边界
- 事务内完成：
  - 任务更新
  - 节点实例更新
  - 流程实例更新
  - 必要的推进创建动作

事务外或 after-commit：

- 发通知
- 发事件到外部系统
- 非关键异步处理

---

### 6.2 Order Number Strategy

你之前提到的：

```sql
SELECT COALESCE(MAX(order_no), 0) + 1
FROM wf_node_instance
WHERE instance_id = #{instanceId}
```

该方式在并发下容易重复。

更稳妥的做法：

- 使用实例表中的版本号 / next_order_no 字段统一分配
- 或使用数据库 sequence / 雪花 / 独立流水表
- 或对实例行加锁后分配

建议优先：

```text
wf_instance.next_order_no
```

处理方式：

- 读取实例并加锁
- 取当前 next_order_no
- 分配后回写 +1

这样比扫 `MAX(order_no)` 更稳、更高效。

---

### 6.3 Optimistic Lock

建议关键表支持 version：

- `wf_instance`
- `wf_task`
- 必要时 `wf_node_instance`

用途：

- 防止重复审批
- 防止并发状态覆盖
- 提高流程幂等控制能力

---

## 7. Design Rules

建议长期坚持以下规则：

### 规则 1：CommandHandler 不直接写满所有逻辑
复杂推进逻辑统一下沉到 `ProcessAdvancer`

### 规则 2：节点差异由 ArrivalHandler 处理
不要在一个巨大的 if/else 中处理所有 nodeType

### 规则 3：Parser 只解析定义，不处理实例
定义层与运行时状态层分离

### 规则 4：Guard 统一前置校验
避免审批、驳回、回退各写一套校验

### 规则 5：AI 只做建议，默认不做最终审批
先保证系统可控，再逐步放权

### 规则 6：业务模块只通过接口接入 workflow
不要让业务模块侵入 runtime 内核

---

## 8. Recommended Next Step

在当前阶段，建议先把以下几类能力补齐：

1. `WfRuntimeContext`
2. `NodeArrivalHandler` 抽象与分发器
3. `ProcessAdvancer` 统一推进入口
4. `WfOperationGuard` 统一校验
5. `WfEventPublisher` after-commit 事件机制
6. `wf_instance.next_order_no` 并发安全顺序号分配

完成这些后，再继续扩展：

- 回退
- 转办
- 会签
- AI 节点
- 条件网关增强
