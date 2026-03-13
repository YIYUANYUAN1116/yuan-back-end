# AI Runtime Architecture

## 1. Document Purpose

本文档用于说明 `yuan-ai` 模块在**运行时**的核心结构、职责边界、调用链路与扩展方式。

与 `ai-system-design.md` 偏“领域模型 / 表结构设计”不同，本文档更关注：

- Chat Runtime 的核心组件
- Provider / Builder / Factory / Invoker 的职责划分
- UnifiedChatProvider 的定位
- Runtime Context 的组织方式
- 模型路由、消息构建、调用执行、审计记录的协作关系
- Workflow 接入 AI Runtime 的推荐方式

本文档参考当前项目中的 runtime 结构设计：

```text
provider
 ├─ builder
 │  ├─ ChatMessageBuilder
 │  └─ DefaultChatMessageBuilder
 ├─ factory
 │  ├─ OpenAiCompatChatClientFactory
 │  └─ SpringAiChatClientFactory
 ├─ invoker
 │  ├─ DifyInvoker
 │  ├─ InvokerRegistry
 │  ├─ ProviderInvoker
 │  └─ SpringAiChatClientInvoker
 ├─ ChatProvider
 └─ UnifiedChatProvider
```

---

## 2. Runtime Design Goal

AI Runtime 层的目标不是简单“调一次模型接口”，而是构建一个统一、可扩展、可审计的 **Chat 执行运行时**。

它需要解决的问题包括：

1. 如何统一不同 Provider 的调用方式
2. 如何统一消息构建
3. 如何统一 Client 创建与缓存
4. 如何统一模型路由
5. 如何统一流式 / 非流式调用
6. 如何统一 Invocation 审计
7. 如何让 Workflow / Chat / 未来其他业务复用同一套 Runtime

---

## 3. Runtime Layering

建议将当前 Runtime 理解为以下分层：

```text
Business Layer
   ↓
ChatProvider Layer
   ↓
Routing Layer
   ↓
Message Builder Layer
   ↓
Invoker Layer
   ↓
Factory / Client Layer
   ↓
LLM Provider
```

更具体一点：

```text
Controller / Service
   ↓
UnifiedChatProvider
   ↓
ModelRoutingService
   ↓
ChatMessageBuilder
   ↓
InvokerRegistry -> ProviderInvoker
   ↓
*ChatClientFactory
   ↓
Remote LLM Service
```

---

## 4. Core Components

## 4.1 ChatProvider

`ChatProvider` 是对外统一暴露的聊天能力接口。

建议职责：

- 向上层提供统一 chat / stream 能力
- 屏蔽底层 Provider 差异
- 不暴露底层 client、HTTP、Spring AI 等技术细节

建议接口风格：

```text
chat(request)
stream(request)
```

建议原则：

- 对上层只暴露业务语义
- 不让 controller / workflow 直接接触 invoker / factory
- 统一作为 Runtime 的对外入口抽象

---

## 4.2 UnifiedChatProvider

`UnifiedChatProvider` 是 `ChatProvider` 的统一实现，也是整个 Chat Runtime 的**编排入口**。

定位：

> 负责组织一次完整的 Chat 调用流程，但不承担底层调用细节。

建议职责：

- 接收统一 `ChatRequest`
- 调用路由服务选择模型
- 构建 `ChatRuntimeContext`
- 调用 `ChatMessageBuilder` 生成消息载荷
- 通过 `InvokerRegistry` 选择合适的 `ProviderInvoker`
- 调用 `LlmInvocationService` 记录调用
- 返回最终结果或流式输出

适合放在 UnifiedChatProvider 的逻辑：

- runtime 主链路 orchestration
- route -> build -> invoke -> record 主流程
- stream / non-stream 分流
- 统一异常包装

不适合放在 UnifiedChatProvider 的逻辑：

- 具体 HTTP 调用
- Spring AI 具体 API 细节
- Dify 协议细节
- client 创建细节
- token 解析细节
- 复杂 message 拼接细节

结论：

`UnifiedChatProvider` 应该是**门面 + 编排器**，而不是上帝类。

---

## 4.3 ChatMessageBuilder

`ChatMessageBuilder` 负责构建底层调用所需的消息内容。

定位：

> 把业务请求、历史消息、系统提示词、附件等信息，转换为底层 LLM 调用可消费的消息载荷。

建议接口：

```text
build(runtimeContext)
```

输入：

- 会话信息
- 当前用户输入
- 历史消息
- system prompt
- attachments
- variables

输出：

- 标准化消息列表
- 或 provider 可消费的 request payload

---

### DefaultChatMessageBuilder

默认实现建议负责：

- system message 注入
- user message 组装
- assistant history 拼接
- 历史上下文裁剪
- 附件转 message part
- 最终 message list 构建

不建议负责：

- 选模型
- 调接口
- 记录数据库
- 选 provider
- 处理 client cache

核心原则：

> Builder 只做“构消息”，不做“调模型”。

---

## 4.4 Factory Layer

Factory 层负责创建或获取底层可复用客户端实例。

定位：

> 统一封装不同调用客户端的构建细节与缓存策略。

---

### OpenAiCompatChatClientFactory

建议职责：

- 根据 `baseUrl + apiKey + modelName` 创建或获取 client
- 对 OpenAI Compatible 服务进行 client cache
- 屏蔽具体 SDK 初始化细节

适合处理：

- client key 构造
- cache 复用
- timeout / baseUrl 初始化
- API Key 注入

---

### SpringAiChatClientFactory

建议职责：

- 创建或复用 Spring AI 的 `ChatClient` / `ChatModel` wrapper
- 屏蔽 Spring AI 的底层初始化逻辑
- 向 invoker 提供统一 client 获取能力

---

### Factory 层设计原则

Factory 只负责：

- 创建 client
- 缓存 client
- 统一 client 初始化

Factory 不负责：

- 选模型
- 业务路由
- 记录调用日志
- 拼装消息内容

---

## 4.5 Invoker Layer

Invoker 层是不同底层调用方式的**适配执行层**。

定位：

> 负责把统一 runtime 请求，转换为具体 Provider/协议可执行的调用。

建议抽象接口：

```text
ProviderInvoker {
    supports(...)
    invoke(...)
    stream(...)
}
```

Invoker 的本质是：

- 统一消费 runtime context / request payload
- 调用不同底层客户端
- 把响应结果转换成统一结构

---

### ProviderInvoker

建议作为统一调用接口。

建议职责：

- 声明自己支持的 providerType / runtimeType
- 提供同步调用能力
- 提供流式调用能力

建议 supports 维度可以是：

- provider type
- invoke type
- engine type

例如：

```text
spring-ai
openai-compat
dify
```

---

### SpringAiChatClientInvoker

建议职责：

- 调用 Spring AI 的 ChatClient / ChatModel
- 消费 builder 输出的消息内容
- 处理 stream 响应
- 输出统一 `ChatResult` / `Flux<String>`

它不应该负责：

- 选 provider
- 查数据库策略
- 记录 LlmInvocation
- 自己重复拼 message

---

### DifyInvoker

建议职责：

- 适配 Dify 的 chat / workflow 接口
- 处理 Dify 特有请求结构
- 输出统一结果对象
- 保证上层感知不到 Dify 协议细节

---

### OpenAiCompatInvoker

虽然你的截图里还没看到这个类，但长期建议补上。

建议职责：

- 适配 OpenAI compatible 调用
- 使用 `OpenAiCompatChatClientFactory`
- 处理标准聊天与流式输出

---

### InvokerRegistry

`InvokerRegistry` 负责管理全部 `ProviderInvoker`。

定位：

> Runtime 中的 invoker 分发中心。

建议职责：

- 启动时收集所有 invoker
- 根据 providerType / engineType 查找适合的 invoker
- 对外提供统一获取能力

建议接口：

```text
getInvoker(providerType)
```

好处：

- 避免在 `UnifiedChatProvider` 中写大量 if/else
- 新增 invoker 时无需改主流程
- 运行时扩展点更清晰

---

## 4.6 Runtime Context

当前结构已经很适合引入统一上下文对象：

### ChatRuntimeContext

定位：

> 一次 AI Runtime 调用全过程共享的上下文载体。

建议包含：

```text
provider
endpoint
model
chatModel
policy
routeRule

session
conversation
historyMessages
attachments

userInput
systemPrompt
variables
stream
```

可扩展字段：

```text
tenantId
sceneCode
bizType
bizId
operator
requestId
traceId
```

价值：

- 避免方法参数爆炸
- 方便 builder / invoker / invocation service 共享上下文
- 为 Workflow AI Node 提供统一复用对象
- 便于日志、链路追踪、审计扩展

---

## 4.7 Routing Layer

虽然截图中没有单独展示 routing 包，但运行时强烈建议单独保留路由服务。

### ModelRoutingService

定位：

> 负责把逻辑模型 / 场景配置解析为最终可调用的 provider + endpoint + model。

建议职责：

- 根据 scene / chatModel / tenant / policy 选策略
- 根据 routeRule 选最终模型
- 处理 fallback / priority / default
- 返回路由结果供 runtime 使用

建议原则：

- 路由逻辑集中管理
- 不要分散到 provider / invoker / factory
- `UnifiedChatProvider` 调 routing service，拿结果后再进入执行流程

---

## 4.8 Invocation Service

建议统一有一层：

### LlmInvocationService

定位：

> 统一记录 AI 调用过程与结果的审计服务。

建议职责：

- 创建 invocation 记录
- 记录 provider / endpoint / model / policy / rule
- 记录耗时、状态、错误信息、token
- 在流式调用结束时补全最终结果

建议原则：

- 所有 AI 调用统一进入 `LlmInvocation`
- 不要让每个 invoker 各自乱记日志
- invocation 记录应是 runtime 主链路的一部分

---

## 5. Recommended Runtime Flow

## 5.1 Standard Chat Flow

推荐主链路如下：

```text
ChatController
   ↓
ChatService
   ↓
UnifiedChatProvider
   ↓
1. ModelRoutingService 选择 provider / endpoint / model
   ↓
2. 构建 ChatRuntimeContext
   ↓
3. ChatMessageBuilder 构建消息
   ↓
4. LlmInvocationService.createStart(...)
   ↓
5. InvokerRegistry 选择 ProviderInvoker
   ↓
6. invoker.invoke / stream
   ↓
7. LlmInvocationService.finish / fail
   ↓
8. 返回 ChatResult / Flux<String>
```

总结成一句话：

```text
route -> context -> build -> invoke -> record
```

这是最推荐的 runtime 主链路。

---

## 5.2 Stream Flow

流式调用建议如下：

```text
User Request
   ↓
UnifiedChatProvider.stream()
   ↓
创建 invocation
   ↓
构建消息
   ↓
选择 invoker
   ↓
stream delta
   ↓
doOnNext: 记录 chunk / 推送 SSE
   ↓
doOnError: invocation fail
   ↓
doOnComplete: finalize message + invocation success
```

建议：

- chunk 落库与 SSE 推送分离
- error / complete 统一收口
- 防止 `e.getMessage()` 为空，错误记录要兜底

---

## 5.3 Workflow AI Node Flow

未来 Workflow 调 AI 时，建议不要直接依赖 invoker / factory，而是走统一能力层。

推荐链路：

```text
AiTaskArrivalHandler
   ↓
WfAiNodeService
   ↓
UnifiedChatProvider / AiFacade
   ↓
ModelRoutingService
   ↓
ProviderInvoker
   ↓
LlmInvocation
   ↓
返回 AI 结果
```

原则：

- workflow 模块只关心“我要一份 AI 分析结果”
- 不关心底层是 Spring AI、Dify 还是 OpenAI Compatible
- runtime 层负责路由、构消息、调用、审计

---

## 6. Recommended Package Structure

结合你当前截图，建议逐步收敛为：

```text
yuan-ai
 └─ provider
    ├─ ChatProvider
    ├─ UnifiedChatProvider
    ├─ context
    │  └─ ChatRuntimeContext
    ├─ builder
    │  ├─ ChatMessageBuilder
    │  └─ DefaultChatMessageBuilder
    ├─ factory
    │  ├─ OpenAiCompatChatClientFactory
    │  └─ SpringAiChatClientFactory
    ├─ invoker
    │  ├─ ProviderInvoker
    │  ├─ InvokerRegistry
    │  ├─ SpringAiChatClientInvoker
    │  ├─ DifyInvoker
    │  └─ OpenAiCompatInvoker
    └─ routing
       └─ ModelRoutingService
```

此外建议在 service 层配套：

```text
service
 ├─ ChatService
 ├─ LlmInvocationService
 ├─ ChatSessionService
 ├─ ChatMessageService
 └─ WfAiNodeService
```

---

## 7. Responsibility Boundary Rules

### 规则 1：Provider 负责编排，不负责底层实现

`UnifiedChatProvider` 做 orchestration，不直接处理底层 SDK / HTTP。

---

### 规则 2：Builder 只构消息

`ChatMessageBuilder` 只负责 message payload，不负责选模型、调接口、记日志。

---

### 规则 3：Factory 只创建 client

Factory 只负责 client 构建与缓存，不负责业务路由和调用审计。

---

### 规则 4：Invoker 只做适配执行

Invoker 负责把统一请求转换为特定协议调用，不负责全局路由和数据库策略判断。

---

### 规则 5：Routing 独立集中

逻辑模型选择、默认模型、fallback、租户策略，统一交给 `ModelRoutingService`。

---

### 规则 6：Invocation 统一记录

所有 AI 请求统一通过 `LlmInvocationService` 进入 `LlmInvocation` 审计。

---

### 规则 7：Workflow 不直接碰 Runtime 内部细节

Workflow 只依赖 `WfAiNodeService` 或更高层 AI facade，不直接依赖 invoker/factory。

---

## 8. Key Risks to Avoid

### 风险 1：Builder 和 Invoker 同时拼消息

后果：

- 重复逻辑
- provider 差异处理分裂
- 调试困难

解决：

- Builder 统一产出 request payload
- Invoker 只消费 payload

---

### 风险 2：Invoker 自己 new client

后果：

- cache 失效
- client 初始化逻辑分散
- timeout/baseUrl 配置不统一

解决：

- Invoker 统一依赖 Factory 获取 client

---

### 风险 3：UnifiedChatProvider 变成上帝类

后果：

- 类过大
- 维护困难
- 扩展 Dify / Workflow / Prompt 时会很乱

解决：

- 路由、构消息、调用、审计分层清楚
- UnifiedChatProvider 只做主链路编排

---

### 风险 4：路由逻辑散落

后果：

- 默认模型选择混乱
- fallback 难统一
- 多租户模型策略难扩展

解决：

- Routing 层集中

---

## 9. Recommended Next Step

结合你现在项目进度，最建议优先补齐这些类：

```text
ChatRuntimeContext
ModelRoutingService
OpenAiCompatInvoker
DefaultLlmInvocationService
DefaultWfAiNodeService
```

同时建议明确以下几个接口边界：

```text
ChatProvider
ChatMessageBuilder
ProviderInvoker
LlmInvocationService
WfAiNodeService
```

这些边界一旦清楚，后面再扩：

- Chat
- Workflow AI Node
- AI 翻译
- AI 校对
- AI 提取
- RAG / Agent

都会顺很多。

---

## 10. Final Conclusion

当前这套 runtime 设计可以概括为：

- `UnifiedChatProvider` = 统一运行时编排入口
- `ChatMessageBuilder` = 消息构建层
- `ProviderInvoker` = 不同调用协议适配层
- `*ChatClientFactory` = 客户端创建与缓存层
- `InvokerRegistry` = invoker 分发中心
- `ModelRoutingService` = 统一选模与路由层
- `LlmInvocationService` = 调用审计层

只要持续守住这个边界，你这套 `yuan-ai` runtime 后面支撑：

- Spring AI
- OpenAI Compatible
- Dify
- Workflow AI Node

都是没问题的。
