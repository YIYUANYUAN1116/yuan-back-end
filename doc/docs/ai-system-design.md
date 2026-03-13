# AI System Design

## 1. Document Purpose

本文档用于说明 `yuan-ai` 模块的定位、核心能力、表结构职责划分、运行链路以及与其他模块的集成方式。

本文档重点面向当前 Yuan Project 的 AI 能力建设，目标不是做一个“单一聊天页面”，而是构建一个可扩展的 **AI Provider / Model / Policy / Chat** 平台能力层。

当前已存在的核心领域对象包括：

```text
ChatAttachment
ChatConversation
ChatMessage
ChatMessageChunk
ChatModel
ChatSession
LlmEndpoint
LlmInvocation
LlmModel
LlmPolicy
LlmProvider
LlmRouteRule
```

---

## 2. Module Positioning

`yuan-ai` 是 Yuan Project 的 AI 能力模块，定位为：

- 统一封装多模型调用能力
- 统一管理 Provider / Endpoint / Model / Policy
- 提供 Chat 对话能力
- 提供调用审计与统计能力
- 为 Workflow / OA / 未来业务模块提供 AI 服务能力

它不是单纯的页面模块，而是一个 **AI 基础设施层**。

---

## 3. Design Goals

当前阶段的设计目标：

1. 支持多 Provider
2. 支持多 Endpoint
3. 支持多 Model
4. 支持按策略路由模型
5. 支持 Chat 会话与消息存储
6. 支持流式输出与 chunk 存储
7. 支持调用审计、耗时、错误记录
8. 为 Workflow AI Node 提供统一接入能力

未来目标：

- Prompt 管理
- RAG
- Agent
- 多租户模型隔离
- 调用限额 / 配额管理
- AI 能力开放给多个业务模块

---

## 4. Core Domain Structure

当前 AI 模块建议分成 4 个子域：

```text
1. Provider 基础配置域
2. Model 路由策略域
3. Chat 对话域
4. Invocation 调用审计域
```

---

## 5. Core Tables and Responsibilities

## 5.1 Provider 基础配置域

### LlmProvider

表示模型提供方。

例如：

- OpenAI Compatible
- DashScope
- Ollama
- DeepSeek
- 其他兼容 OpenAI 协议的服务

建议职责：

- 记录 provider 名称、类型、状态
- 定义该 provider 的基础能力
- 作为 endpoint / model 的归属上层

建议字段方向：

```text
provider_id
provider_code
provider_name
provider_type
status
remark
```

说明：

- `provider_code` 建议全局唯一
- provider 主要描述“供应商”层，不直接承担密钥

---

### LlmEndpoint

表示一个可访问的模型服务端点。

例如：

- 某个 OpenAI 兼容 baseUrl
- 某个租户专用 endpoint
- 某个本地 Ollama 地址

建议职责：

- 维护实际调用地址
- 挂载认证信息或认证引用
- 控制 endpoint 状态
- 与 provider 建立多对一关系

建议字段方向：

```text
endpoint_id
provider_id
endpoint_name
base_url
api_key / credential_ref
status
priority
timeout_ms
```

说明：

- 一个 Provider 可对应多个 Endpoint
- Endpoint 是“真正可调用的入口”

---

### LlmModel

表示底层可调用模型。

例如：

- gpt-4o
- gpt-4.1
- qwen-max
- deepseek-chat
- llama3

建议职责：

- 记录模型标识
- 关联 provider / endpoint
- 维护模型上下文长度、是否支持流式输出、是否支持视觉等能力
- 作为策略路由的候选模型

建议字段方向：

```text
model_id
provider_id
endpoint_id
model_code
model_name
model_family
status
supports_stream
supports_vision
context_window
max_output_tokens
```

说明：

- `model_code` 建议存真实调用名
- `model_name` 可存展示名
- 一个 endpoint 下可挂多个模型

---

## 5.2 Model 路由策略域

### ChatModel

表示面向聊天场景的“业务模型配置”。

它不是底层物理模型，而是一个**对业务暴露的逻辑模型入口**。

例如：

- default-chat
- workflow-ai-review
- proofread-model
- translation-model

建议职责：

- 面向业务定义逻辑模型
- 屏蔽底层 provider / model 变化
- 让前端或业务模块只感知逻辑模型，而不是底层物理模型

建议字段方向：

```text
chat_model_id
model_code
model_name
scene_code
status
default_flag
remark
```

说明：

- `ChatModel` 更偏“业务入口层”
- 可通过 policy / routeRule 决定最终落到哪个 `LlmModel`

---

### LlmPolicy

表示模型调用策略。

例如：

- 默认策略
- 某租户优先某模型
- 某场景使用低成本模型
- 超时时自动降级

建议职责：

- 定义某类请求如何选模型
- 控制默认模型、优先级、失败重试策略
- 支持按场景/租户/模块做差异配置

建议字段方向：

```text
policy_id
policy_code
policy_name
scene_code
status
default_flag
remark
```

---

### LlmRouteRule

表示具体路由规则。

例如：

- scene = chat -> gpt-4o
- scene = workflow_ai -> qwen-max
- tenant = A -> endpoint_1
- provider unavailable -> fallback model

建议职责：

- 把 Policy 细化成可执行规则
- 支持条件匹配、优先级、回退逻辑
- 将逻辑模型映射到物理模型

建议字段方向：

```text
route_rule_id
policy_id
match_type
match_value
target_model_id
target_endpoint_id
priority
enabled
fallback_flag
```

说明：

- `LlmPolicy` 是策略头
- `LlmRouteRule` 是策略明细
- 二者关系类似“头 + 行”

---

## 5.3 Chat 对话域

### ChatSession

表示一次聊天会话。

建议职责：

- 记录会话维度信息
- 关联用户、逻辑模型、场景
- 保存会话标题、状态、最后活跃时间

建议字段方向：

```text
session_id
user_id
chat_model_id
scene_code
title
status
last_message_time
```

说明：

- 一个 Session 下有多个 Message
- Session 是对话容器

---

### ChatConversation

如果当前设计中 `ChatConversation` 与 `ChatSession` 同时存在，建议明确边界。

建议两种选法二选一：

#### 方案 A：保留二者
- `ChatSession`：更偏技术会话容器
- `ChatConversation`：更偏业务对话对象，可承载主题、知识库、业务绑定等信息

#### 方案 B：中短期简化
如果当前差异不明显，可以先把 `ChatConversation` 作为 Session 的上层包装对象，避免语义重复。

建议字段方向：

```text
conversation_id
session_id
biz_type
biz_id
subject
status
```

建议：

- 如果没有明确业务差异，不要让 Session 和 Conversation 完全重复
- 需要尽早统一定义，不然后期代码会混乱

---

### ChatMessage

表示一条消息。

建议职责：

- 保存 user / assistant / system 消息
- 保存消息内容、状态、耗时、token 等
- 关联调用记录

建议字段方向：

```text
message_id
session_id
conversation_id
role
content
status
seq_no
input_tokens
output_tokens
latency_ms
invocation_id
```

说明：

- 建议有 `seq_no`
- 支持消息状态：生成中 / 成功 / 失败 / 中断

---

### ChatMessageChunk

表示流式输出过程中的分片。

建议职责：

- 支持 SSE / stream 输出落盘
- 记录 assistant 增量内容
- 支持异常恢复和调试

建议字段方向：

```text
chunk_id
message_id
chunk_no
delta_content
create_time
```

建议：

- 如果 chunk 只是调试用途，可设置保留周期
- 如果存储压力大，可只保留完整消息，不长期保留所有 chunk

---

### ChatAttachment

表示聊天附件。

建议职责：

- 记录用户上传到聊天中的文件
- 支持 PDF / DOCX / 图片 / 其他附件
- 供后续 AI 解析、翻译、校对等能力使用

建议字段方向：

```text
attachment_id
session_id
message_id
file_id
file_name
file_type
file_size
storage_url / object_key
parse_status
```

说明：

- 附件应尽量复用系统统一文件表能力
- `ChatAttachment` 只做聊天场景关联，不重复存完整文件元数据

---

## 5.4 Invocation 调用审计域

### LlmInvocation

表示一次实际的大模型调用记录。

这是非常关键的一张表。

建议职责：

- 记录本次调用落到了哪个 provider / endpoint / model
- 记录请求耗时、状态、错误信息
- 记录 token 消耗
- 支持问题排查与成本统计

建议字段方向：

```text
invocation_id
provider_id
endpoint_id
model_id
chat_model_id
policy_id
route_rule_id
scene_code
status
error_message
latency_ms
input_tokens
output_tokens
total_tokens
request_time
response_time
```

说明：

- `ChatMessage` 可关联 `invocation_id`
- Workflow AI 节点等非聊天调用也可直接写 `LlmInvocation`
- 这是统一审计入口，不应只服务聊天

---

## 6. Suggested Relationship Model

推荐按以下逻辑理解表关系：

```text
LlmProvider
   └── LlmEndpoint
          └── LlmModel

ChatModel
   └── LlmPolicy
          └── LlmRouteRule
                 └── LlmModel / LlmEndpoint

ChatSession
   └── ChatConversation
          └── ChatMessage
                 ├── ChatMessageChunk
                 ├── ChatAttachment
                 └── LlmInvocation
```

补充说明：

- `ChatModel` 是逻辑模型入口
- `LlmModel` 是物理模型
- `LlmPolicy + LlmRouteRule` 决定逻辑模型最终落到哪个物理模型
- `LlmInvocation` 是统一调用审计表
- `Chat*` 系列表负责聊天产品形态
- Workflow 未来可直接复用 `LlmInvocation + LlmPolicy + LlmRouteRule`

---

## 7. Recommended Runtime Flow

## 7.1 Chat Request Flow

```text
User Request
   ↓
Chat Controller
   ↓
Chat Service
   ↓
Load ChatSession / ChatModel
   ↓
Resolve Policy
   ↓
Match RouteRule
   ↓
Select LlmEndpoint + LlmModel
   ↓
Create LlmInvocation
   ↓
Call Provider Client
   ↓
Stream Delta / Save Chunk
   ↓
Finalize ChatMessage
   ↓
Finalize Invocation
```

---

## 7.2 Policy Routing Flow

```text
scene_code / tenant / biz_type / user_choice
   ↓
find matched policy
   ↓
find route rules ordered by priority
   ↓
select target endpoint + model
   ↓
fallback if needed
```

建议：

- 路由逻辑统一收口到 `ModelRoutingService`
- 不要让 controller / chat service 到处写路由判断

---

## 7.3 Workflow AI Node Flow

未来 `yuan-workflow` 接入 AI 时，建议走统一 AI 服务而不是直接在 workflow 模块硬编码调用模型。

流程建议：

```text
Workflow AiTaskArrivalHandler
   ↓
WfAiNodeService
   ↓
ModelRoutingService
   ↓
LlmInvocationService
   ↓
Provider Client
   ↓
AI result
   ↓
write back to workflow variables / opinion
```

原则：

- Workflow 只关心“要一个 AI 分析结果”
- 不直接关心 provider / endpoint / apiKey 细节
- AI 模块作为统一能力层输出服务

---

## 8. Package Structure Suggestion

建议 `yuan-ai` 采用如下包结构：

```text
yuan-ai
 ├─ controller
 ├─ domain
 │  ├─ bo
 │  ├─ dto
 │  ├─ vo
 │  ├─ ChatAttachment
 │  ├─ ChatConversation
 │  ├─ ChatMessage
 │  ├─ ChatMessageChunk
 │  ├─ ChatModel
 │  ├─ ChatSession
 │  ├─ LlmEndpoint
 │  ├─ LlmInvocation
 │  ├─ LlmModel
 │  ├─ LlmPolicy
 │  ├─ LlmProvider
 │  └─ LlmRouteRule
 ├─ mapper
 ├─ service
 │  ├─ chat
 │  ├─ provider
 │  ├─ routing
 │  ├─ invocation
 │  └─ model
 ├─ client
 │  ├─ spi
 │  ├─ impl
 │  └─ factory
 ├─ support
 │  ├─ sse
 │  ├─ token
 │  ├─ prompt
 │  └─ parser
 └─ api
```

推荐再抽几个关键服务接口：

```text
LlmProviderClient
LlmProviderClientFactory
ModelRoutingService
LlmInvocationService
ChatService
WfAiNodeService
```

---

## 9. Design Rules

### 规则 1：逻辑模型与物理模型分离

- `ChatModel` 面向业务
- `LlmModel` 面向底层调用

不要把前端直接绑死到物理模型名。

---

### 规则 2：Provider / Endpoint / Model 三层分离

- Provider 描述供应商
- Endpoint 描述调用入口
- Model 描述具体模型

这样扩展多个地址、多环境、多租户会更自然。

---

### 规则 3：调用审计统一进入 LlmInvocation

无论是：

- Chat
- Workflow AI
- 翻译
- 校对
- 摘要

只要调用模型，都应统一记录到 `LlmInvocation`。

---

### 规则 4：路由能力集中，不要散落

模型选择、降级、fallback、默认模型，都应集中在 Routing 层处理。

---

### 规则 5：Chat 是产品形态，不等于 AI 全部能力

`Chat*` 系列表是聊天场景的数据结构，但 AI 模块还要服务 workflow、翻译、校对、抽取等能力。

---

### 规则 6：Session 与 Conversation 尽快统一语义

如果二者边界不清晰，后面消息、附件、上下文挂载会越来越乱。

---

## 10. Current Recommended Focus

结合你现在的项目阶段，建议优先把以下能力做扎实：

1. `LlmProvider / LlmEndpoint / LlmModel` 基础配置能力
2. `ChatModel / LlmPolicy / LlmRouteRule` 路由能力
3. `LlmInvocation` 审计能力
4. `ChatSession / ChatMessage / ChatMessageChunk` 对话能力
5. `ModelRoutingService` 统一选模
6. `LlmProviderClient` 抽象与缓存
7. `WfAiNodeService` 对 workflow 输出统一能力

---

## 11. Recommended Next Step

下一阶段建议落地以下组件：

```text
LlmProviderClient SPI
LlmProviderClientFactory
ModelRoutingService
DefaultChatService
DefaultLlmInvocationService
DefaultWfAiNodeService
```

如果这些基础设施打稳，后续再扩：

- AI 翻译
- AI 校对
- AI 提取
- Workflow AI Node
- RAG
- Agent

都会顺很多。
