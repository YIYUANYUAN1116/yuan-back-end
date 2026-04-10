# Yuan Project Overview

## 1. Project Introduction

**Yuan Project** 是一个以 **System + Workflow + AI**
为核心能力的平台骨架项目，用于构建可扩展的多租户企业级业务系统。

项目目标：

-   提供统一的 **系统基础能力（用户 / 权限 / 菜单）**
-   构建可复用的 **流程引擎（Workflow Engine）**
-   提供统一的 **AI调用能力（LLM Provider + Chat）**
-   通过示例业务模块验证平台能力
-   为未来 **AI + Workflow + SaaS** 能力扩展提供基础架构

当前项目主要用于：

-   技术验证
-   架构设计
-   AI与Workflow结合的探索

------------------------------------------------------------------------

# 2. Project Modules

当前项目采用 **多模块结构**：

    yuan-project
     ├─ yuan-admin
     ├─ yuan-common
     ├─ yuan-system
     ├─ yuan-ai
     ├─ yuan-workflow
     └─ yuan-oa

## yuan-admin

系统启动模块。

职责：

-   Spring Boot 启动入口
-   聚合各业务模块
-   提供后台管理接口

通常依赖：

    yuan-system
    yuan-ai
    yuan-workflow
    yuan-oa

------------------------------------------------------------------------

## yuan-common

公共基础模块。

提供：

-   通用返回结构
-   工具类
-   公共常量
-   通用枚举
-   基础配置
-   公共异常

所有模块均依赖该模块。

------------------------------------------------------------------------

## yuan-system

系统基础模块。

负责系统基础能力：

-   用户管理
-   角色管理
-   租户管理
-   菜单管理
-   权限控制

该模块为平台的 **基础能力层**。

Workflow / AI / 业务模块通常会依赖 system 模块中的：

-   用户
-   组织
-   权限

------------------------------------------------------------------------

## yuan-ai

AI能力模块。

用于统一封装大模型调用能力。

当前主要能力：

-   AI Provider 管理
-   AI Model 管理
-   API Credential 管理
-   调用策略（Policy）
-   Chat 对话能力

典型数据结构：

    ai_provider
    ai_model
    ai_credential
    ai_policy
    chat_session
    chat_message

未来扩展方向：

-   RAG
-   AI Agent
-   Prompt 管理
-   Token 统计
-   Workflow AI 节点

------------------------------------------------------------------------

## yuan-workflow

流程引擎模块。

提供通用的流程管理能力。

核心能力：

-   流程定义
-   流程实例
-   任务管理
-   节点实例
-   流程流转

核心概念：

    Workflow Definition
    Workflow Instance
    Workflow Task
    Node Instance
    Transition

未来支持：

-   审批流
-   回退
-   转办
-   会签
-   AI节点

------------------------------------------------------------------------

## yuan-oa

业务示例模块。

当前仅用于 **Workflow能力演示**。

示例业务：

-   请假申请

用途：

-   验证 workflow 引擎设计
-   展示 workflow 如何接入业务模块

不承担完整 OA 系统职责。

------------------------------------------------------------------------

# 3. Module Dependency

模块依赖关系大致如下：

                yuan-common
                    │
            ┌───────┼────────┐
            │       │        │
       yuan-system  │    yuan-ai
            │       │        │
            │       │        │
            └──── yuan-workflow
                    │
                    │
                yuan-oa

说明：

-   `yuan-common` 为所有模块提供基础能力
-   `yuan-system` 提供用户与权限体系
-   `yuan-ai` 提供 AI 调用能力
-   `yuan-workflow` 提供流程引擎
-   `yuan-oa` 为业务示例模块

------------------------------------------------------------------------

# 4. Design Principles

项目遵循以下设计原则：

## 模块职责清晰

每个模块只负责自身领域能力。

例如：

    system → 用户权限
    workflow → 流程引擎
    ai → AI能力

业务模块不应侵入基础模块。

------------------------------------------------------------------------

## 平台能力优先

基础能力模块：

    system
    workflow
    ai

应保持通用性。

业务模块只作为能力的使用方。

------------------------------------------------------------------------

## 松耦合设计

模块之间通过：

-   Service接口
-   事件
-   数据结构

进行交互，避免强依赖。

------------------------------------------------------------------------

## 可扩展性

项目设计目标：

支持未来扩展：

    AI + Workflow
    AI Agent
    SaaS 多租户
    企业级业务系统

------------------------------------------------------------------------

# 5. Current Development Focus

当前项目重点方向：

1.  完善 Workflow Engine
2.  构建统一 AI Provider 层
3.  探索 AI + Workflow 结合
4.  通过 OA Demo 验证流程能力

------------------------------------------------------------------------

# 6. Future Directions

未来可能扩展能力：

-   AI Workflow Node
-   AI Agent
-   RAG Knowledge Base
-   SaaS 多租户
-   Plugin Architecture
