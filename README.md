# Yuan Project

## 🚀 项目简介

Yuan Project 是一个基于 Java 构建的企业级平台，核心包含：

- 自研 Workflow 工作流引擎
- 统一 AI Runtime（大模型接入层）
- 多模块业务架构

项目以 **流程引擎为核心能力，AI 作为扩展能力**，实现业务流程的可编排与智能化增强。

---

## 🧱 项目结构
yuan-admin # 入口模块（Controller / 配置）
yuan-common # 公共模块（工具类 / 数据库 / Redis / 配置）
yuan-system # 系统模块（用户 / 角色 / 权限）
yuan-workflow # 工作流引擎（核心）
yuan-ai # AI Runtime（模型接入层）
yuan-oa # 业务模块

---

## ⚙️ 技术栈

- Java 17
- Spring Boot 3
- MyBatis Plus
- Redis
- MySQL 
- React + Ant Design
- Spring AI
- OpenAI 兼容接口

---

## 🔄 工作流引擎（核心模块）

自研轻量级工作流引擎，支持：

- 流程定义 / 发布 / 实例化
- 任务流转与审批处理
- 条件网关（Gateway）分支控制
- 多类型节点扩展（用户节点 / AI节点等）

### 核心设计

- Command + Handler 模式（流程操作解耦）
- NodeArrivalHandler（节点到达处理机制）
- ProcessAdvancer（流程推进器）
- FlowParser（流程路径解析）
- WfOperationGuard（流程操作校验）

> 支持通过扩展 Handler 快速新增节点类型，具备良好的扩展性。

---

## 🤖 AI Runtime（扩展能力）

统一的大模型调用层，用于支撑：

- 智能对话（支持 SSE 流式输出）
- 工作流 AI 节点能力（自动分析 / 建议）
- 文本处理（预留扩展能力）

### 核心组件

- ChatProvider（AI能力抽象）
- UnifiedChatProvider（统一调用入口）
- ChatMessageBuilder（消息构建）
- ProviderInvoker（模型执行层）
- InvokerRegistry（多模型路由）

---

## 🔌 核心特性

- 模块化架构，支持快速扩展
- 自研 Workflow 引擎（非 BPMN 重型框架）
- AI 与业务流程融合（AI 节点）
- SSE 流式响应（适用于长耗时 AI 调用）
- 多模型接入与路由能力
- AI 调用审计（日志 / 耗时 / 结果记录）

---

## 🧩 设计原则

- 职责分离（Workflow / AI / 业务模块解耦）
- 可插拔设计（Provider / Handler）
- AI 作为增强，而非替代业务逻辑
- 高内聚，低耦合

---

## 📌 后续规划
- 模型路由策略优化
- AI 辅助决策能力（增强审计能力）

---

## 📖 License
MIT License
## 👤 作者
Yuan Aaron
