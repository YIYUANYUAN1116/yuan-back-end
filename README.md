# Yuan Backend

`Yuan Backend` 是一个基于 `Java 17 + Spring Boot 3 + Maven` 的多模块后端项目，当前以系统基础能力、工作流引擎和 AI Runtime 为核心，适合用来搭建可扩展的企业级后台系统。

项目目前包含：

- 系统基础能力：用户、角色、权限、菜单、租户
- 自研工作流能力：流程定义、实例、任务、节点流转
- AI Runtime：统一模型接入、对话能力、流式响应
- 示例业务模块：OA 请假流程
- 代码生成相关模块：数据模型与生成能力

## 项目特点

- 多模块 Maven 架构，模块边界清晰
- 基于 `Spring Boot 3.4.8`、`MyBatis-Plus`、`Sa-Token`
- 默认支持 `MySQL 8`、`Redis`、`MinIO / S3 Compatible OSS`
- 内置 OpenAPI / Knife4j 接口文档
- 支持本地运行、Jar 部署、Docker 部署
- 已提供生产环境 `docker-compose` 与部署环境变量模板

## 技术栈

- Java 17
- Spring Boot 3
- Maven
- MyBatis / MyBatis-Plus
- Sa-Token
- Redis
- MySQL 8
- MinIO / S3 Compatible OSS
- Spring AI / OpenAI Compatible API
- Knife4j / SpringDoc

## 目录结构

```text
yuan-back-end
├── yuan-apps
│   └── yuan-admin                 # 启动入口模块
├── yuan-common                    # 通用基础模块
│   ├── yuan-common-core
│   ├── yuan-common-web
│   ├── yuan-common-mybatis
│   ├── yuan-common-redis
│   ├── yuan-common-satoken
│   ├── yuan-common-tenant
│   ├── yuan-common-oss
│   └── ...
├── yuan-modules                   # 业务与领域模块
│   ├── yuan-system                # 系统模块
│   ├── yuan-workflow              # 工作流模块
│   ├── yuan-ai                    # AI 模块
│   ├── yuan-oa                    # OA 示例模块
│   └── yuan-generator             # 代码生成模块
├── yuan-modules-api               # 对外 API 契约模块
│   ├── yuan-system-api
│   ├── yuan-workflow-api
│   ├── yuan-oa-api
│   └── yuan-ai-api
├── doc
│   ├── docs                       # 架构说明文档
│   └── script/sql                 # 初始化 SQL
├── deploy                         # 生产部署编排与环境变量模板
├── Dockerfile
└── docker-compose.yml
```

## 核心模块说明

### `yuan-admin`

系统启动入口，主类为 [YuanBackEndApplication.java](/Users/yuan/IdeaProjects/yuan-back-end/yuan-apps/yuan-admin/src/main/java/com/yuan/admin/YuanBackEndApplication.java)。

职责：

- 聚合所有业务模块
- 提供统一的 Web API 出口
- 加载运行配置

### `yuan-system`

系统基础能力模块，负责：

- 用户、角色、菜单、部门、租户
- 登录鉴权
- 权限控制
- 操作日志等基础后台能力

### `yuan-workflow`

工作流核心模块，负责：

- 流程定义
- 流程发布与状态管理
- 流程实例与任务流转
- 审批流扩展基础

### `yuan-ai`

AI 能力模块，负责：

- LLM 供应商、模型、策略管理
- 对话接口
- 流式输出
- AI 调用记录与管理能力

### `yuan-oa`

业务示例模块，当前主要用于演示请假申请与工作流结合。

### `yuan-generator`

代码生成相关模块，提供数据模型、字段配置与代码模板生成能力。

## 运行环境

建议本地准备以下环境：

- JDK 17
- Maven 3.9+
- MySQL 8.x
- Redis 7.x
- MinIO

## 配置说明

应用默认端口：

- `6011`

主要配置文件：

- [application.yml](/Users/yuan/IdeaProjects/yuan-back-end/yuan-apps/yuan-admin/src/main/resources/application.yml)
- [application-dev.yml](/Users/yuan/IdeaProjects/yuan-back-end/yuan-apps/yuan-admin/src/main/resources/application-dev.yml)
- [application-prod.yml](/Users/yuan/IdeaProjects/yuan-back-end/yuan-apps/yuan-admin/src/main/resources/application-prod.yml)

默认启用环境：

- `spring.profiles.active=dev`

`dev` 环境默认依赖：

- MySQL：`localhost:3306/yuan`
- Redis：`localhost:6379`
- OSS：`http://minio:9000`

如果你的本地服务地址、账号或密码不同，请先修改 `application-dev.yml`。

## 本地启动

### 1. 初始化数据库

创建数据库：

```sql
CREATE DATABASE yuan DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
```

导入初始化脚本：

- [doc/script/sql/yuan.sql](/Users/yuan/IdeaProjects/yuan-back-end/doc/script/sql/yuan.sql)

项目里也保留了若干数据库备份文件，可按需要参考：

- [doc/script/sql](/Users/yuan/IdeaProjects/yuan-back-end/doc/script/sql)

### 2. 启动基础依赖

至少需要启动：

- MySQL
- Redis
- MinIO

说明：

- 根目录的 [docker-compose.yml](/Users/yuan/IdeaProjects/yuan-back-end/docker-compose.yml) 更偏向服务器部署，里面使用了 `/data/...` 的挂载路径和固定构建上下文
- 本地开发如果直接使用该文件，通常需要先按你的机器目录结构调整路径
- 如果只是本地联调，直接手动启动 MySQL / Redis / MinIO 会更直接

### 3. 调整开发配置

根据本地环境修改：

- 数据库连接
- Redis 连接
- OSS/MinIO 配置
- AI 接口配置

重点文件：

- [application-dev.yml](/Users/yuan/IdeaProjects/yuan-back-end/yuan-apps/yuan-admin/src/main/resources/application-dev.yml)
- [application.yml](/Users/yuan/IdeaProjects/yuan-back-end/yuan-apps/yuan-admin/src/main/resources/application.yml)

### 4. 启动项目

在项目根目录执行：

```bash
mvn -pl yuan-apps/yuan-admin -am spring-boot:run
```

或先打包再运行：

```bash
mvn -pl yuan-apps/yuan-admin -am -DskipTests clean package
java -jar yuan-apps/yuan-admin/target/*.jar
```

## 访问地址

项目启动后可访问：

- 服务地址：[http://localhost:6011](http://localhost:6011)
- Knife4j 文档：[http://localhost:6011/doc.html](http://localhost:6011/doc.html)
- Swagger UI：[http://localhost:6011/swagger-ui/index.html](http://localhost:6011/swagger-ui/index.html)

常见接口示例：

- 登录：`POST /auth/login`
- 退出登录：`POST /auth/logout`
- 临时文件上传：`POST /oss/temp/upload`
- AI 对话流式接口：`POST /ai/chat/stream`

## 登录说明

项目初始化 SQL 中包含管理员相关数据，通常可从 `sys_user`、`sys_role`、`sys_tenant` 等表开始联调。

注意：

- 项目开启了多租户能力
- 登录请求体包含 `tenantId`、`username`、`password`、`code`、`uuid`
- 当前登录实现中用户名密码登录可正常工作，验证码字段在现有代码中未强制校验

登录请求体示例：

```json
{
  "tenantId": "000000",
  "username": "admin",
  "password": "your-password",
  "code": "",
  "uuid": ""
}
```

如果导入的不是初始化 SQL，而是其他备份数据，请以实际数据库中的用户数据为准。

## Docker 打包与部署

项目提供了 Docker 构建文件：

- [Dockerfile](/Users/yuan/IdeaProjects/yuan-back-end/Dockerfile)

构建逻辑：

- 通过 Maven 镜像构建 `yuan-admin`
- 产出可运行 Jar
- 使用 `eclipse-temurin:17-jre` 作为运行时镜像

### 本地构建镜像

```bash
docker build -t yuan-backend-admin:latest .
```

### 生产部署文件

生产部署相关文件位于：

- [deploy/docker-compose.prod.yml](/Users/yuan/IdeaProjects/yuan-back-end/deploy/docker-compose.prod.yml)
- [deploy/.env.example](/Users/yuan/IdeaProjects/yuan-back-end/deploy/.env.example)

这套编排默认包含：

- 应用服务
- MySQL
- Redis
- MinIO

使用前建议：

1. 复制 `deploy/.env.example` 为 `.env`
2. 按实际环境修改数据库、Redis、MinIO 和 AI 相关参数
3. 再执行 `docker compose -f deploy/docker-compose.prod.yml up -d`

## 开发文档

仓库内已经提供了一些架构设计文档，适合进一步了解系统设计：

- [doc/docs/yuan-project-overview.md](/Users/yuan/IdeaProjects/yuan-back-end/doc/docs/yuan-project-overview.md)
- [doc/docs/workflow-design.md](/Users/yuan/IdeaProjects/yuan-back-end/doc/docs/workflow-design.md)
- [doc/docs/workflow-runtime-architecture.md](/Users/yuan/IdeaProjects/yuan-back-end/doc/docs/workflow-runtime-architecture.md)
- [doc/docs/ai-system-design.md](/Users/yuan/IdeaProjects/yuan-back-end/doc/docs/ai-system-design.md)
- [doc/docs/ai-runtime-architecture.md](/Users/yuan/IdeaProjects/yuan-back-end/doc/docs/ai-runtime-architecture.md)

## 常用命令

```bash
# 整体编译
mvn clean package -DskipTests

# 仅启动后台入口模块，并自动构建依赖模块
mvn -pl yuan-apps/yuan-admin -am spring-boot:run

# 仅打包后台入口模块
mvn -pl yuan-apps/yuan-admin -am -DskipTests clean package
```

## 补充说明

- 项目当前更偏向平台骨架 + 业务验证工程
- 工作流、AI、系统能力都已经具备独立模块化结构，适合继续扩展
- 如果你还会配套开发前端项目，推荐把接口联调入口统一指向 `yuan-admin`

## License

MIT
