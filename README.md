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

## 🚀 自动化部署

仓库已经补好了基于 GitHub Actions + GHCR + Docker Compose 的自动部署链路：

- 触发条件：`master` 分支有新的 push
- 镜像仓库：`ghcr.io/<你的 GitHub 用户名>/yuan-backend-admin`
- 部署方式：CI 构建镜像并推送到 GHCR，然后通过 SSH 登录服务器执行 `docker compose pull && up -d`

### 仓库内新增文件

- `.github/workflows/deploy-master.yml`：自动构建并部署
- `deploy/docker-compose.prod.yml`：服务器生产编排文件
- `deploy/deploy.sh`：服务器部署脚本
- `deploy/.env.example`：生产环境变量模板

### 你需要提前准备

1. 服务器已安装 `docker` 和 `docker compose`
2. 在服务器创建部署目录，例如 `/data/app/yuan/backend/admin`
3. 把服务器目录里的 `.env.example` 复制为 `/data/app/yuan/backend/admin/.env`，并填好生产配置
4. 如果你有额外 Spring 配置，可放到 `/data/app/yuan/backend/admin/config/`

### GitHub Secrets

在仓库 `Settings -> Secrets and variables -> Actions` 中添加：

- `SERVER_HOST`：服务器 IP 或域名
- `SERVER_PORT`：SSH 端口，例如 `22`
- `SERVER_USER`：SSH 登录用户
- `SERVER_SSH_KEY`：私钥内容
- `DEPLOY_PATH`：服务器部署目录，例如 `/data/app/yuan/backend/admin`
- `GHCR_USERNAME`：用于服务器拉取 GHCR 镜像的 GitHub 用户名
- `GHCR_TOKEN`：用于服务器拉取 GHCR 镜像的 Token，至少需要 `read:packages`

### 首次上线

首次需要手动在服务器执行一次：

```bash
cd /data/app/yuan/backend/admin
cp .env.example .env
# 修改 .env 后
chmod +x deploy.sh
APP_IMAGE=ghcr.io/<你的 GitHub 用户名>/yuan-backend-admin:latest \
GHCR_USERNAME=<你的 GitHub 用户名> \
GHCR_TOKEN=<你的 GHCR Token> \
DEPLOY_DIR=/data/app/yuan/backend/admin \
./deploy.sh
```

之后只要 `master` 分支有新提交，GitHub Actions 就会自动构建并部署。
