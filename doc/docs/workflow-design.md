# Workflow Engine Design

## 1. Overview

`yuan-workflow` 是 Yuan Project
的流程引擎模块，提供通用的流程管理能力，用于支持企业级审批流程、业务流程编排以及未来的
AI + Workflow 场景。

核心目标：

-   提供可复用的流程引擎
-   支持审批流、任务流转
-   支持回退、转办、会签等扩展能力
-   支持未来 AI 节点能力
-   与业务模块解耦

------------------------------------------------------------------------

# 2. Core Concepts

流程引擎主要包含以下核心概念：

    Workflow Definition
    Workflow Instance
    Workflow Task
    Node Instance
    Transition

## Workflow Definition

流程定义描述整个流程结构，包括：

-   节点
-   边
-   条件
-   节点类型

流程定义通常由前端流程设计器生成 JSON 存储。

------------------------------------------------------------------------

## Workflow Instance

流程实例代表一次实际运行的流程。

例如：

-   请假流程实例
-   报销流程实例

主要字段：

    instance_id
    definition_id
    biz_id
    status
    start_time
    end_time

------------------------------------------------------------------------

## Workflow Task

任务代表当前需要处理的审批或操作。

典型字段：

    task_id
    instance_id
    node_id
    assignee
    status
    create_time
    complete_time

------------------------------------------------------------------------

## Node Instance

节点实例表示流程运行过程中每个节点的执行记录。

主要字段：

    node_instance_id
    instance_id
    node_id
    node_type
    status
    order_no

------------------------------------------------------------------------

## Transition

Transition 表示节点之间的连接关系。

例如：

    start -> manager approval
    manager approval -> hr approval
    hr approval -> end

------------------------------------------------------------------------

# 3. Architecture

流程引擎采用 **Command + Handler 架构**。

主要组件：

    Command
    CommandHandler
    NodeHandler
    ProcessAdvancer
    FlowParser
    OperationGuard
    EventPublisher

------------------------------------------------------------------------

## Command

Command 表示流程操作。

例如：

    StartCmd
    ApproveTaskCmd
    RejectTaskCmd
    RollbackCmd
    TransferCmd

------------------------------------------------------------------------

## CommandHandler

CommandHandler 负责处理具体命令。

例如：

    StartHandler
    ApproveTaskHandler
    RejectTaskHandler

职责：

-   校验操作合法性
-   更新任务状态
-   推进流程

------------------------------------------------------------------------

## NodeHandler

NodeHandler 负责处理节点到达逻辑。

例如：

    UserTaskArrivalHandler
    GatewayArrivalHandler
    EndNodeArrivalHandler
    AiTaskArrivalHandler

------------------------------------------------------------------------

## ProcessAdvancer

ProcessAdvancer 负责流程推进。

主要职责：

-   计算下一节点
-   创建节点实例
-   创建任务

------------------------------------------------------------------------

## FlowParser

FlowParser 负责解析流程定义 JSON。

提供能力：

-   获取节点信息
-   获取节点连接关系
-   获取下一节点

------------------------------------------------------------------------

## OperationGuard

OperationGuard 负责校验流程操作是否合法。

例如：

-   任务是否属于当前用户
-   流程是否已结束
-   状态是否允许操作

------------------------------------------------------------------------

# 4. Execution Flow

典型流程执行步骤：

    User Start Process
          │
    StartHandler
          │
    Create Instance
          │
    Create First NodeInstance
          │
    ProcessAdvancer
          │
    Create Task

审批流程：

    User Approve Task
           │
    ApproveTaskHandler
           │
    Complete Task
           │
    ProcessAdvancer
           │
    Create Next Node

------------------------------------------------------------------------

# 5. AI Node Design

未来流程引擎支持 **AI节点**。

AI节点主要用于：

-   审核建议
-   自动分类
-   自动补充信息

AI节点原则：

AI **不直接做最终审批决策**，只提供建议。

流程仍由人类用户完成审批。

典型流程：

    User Submit
       │
    AI Analysis
       │
    Manager Approval

------------------------------------------------------------------------

# 6. Concurrency Strategy

流程引擎需要处理并发问题。

关键策略：

-   数据库事务控制
-   乐观锁
-   顺序号控制
- 
应避免并发问题，可以通过：

-   乐观锁
-   sequence
-   事务锁

进行控制。

------------------------------------------------------------------------

# 7. Future Enhancements

未来流程引擎可能扩展：

-   会签节点
-   条件网关
-   定时节点
-   AI审批建议
-   事件驱动流程
