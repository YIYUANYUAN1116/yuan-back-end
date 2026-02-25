package com.yuan.ai.service.provider;


import com.yuan.ai.domain.ChatModel;
import com.yuan.ai.domain.model.ChatMsg;
import com.yuan.ai.domain.model.ChatRequest;
import com.yuan.ai.service.ChatMessageService;
import com.yuan.ai.support.SseHelper;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.concurrent.ConcurrentHashMap;

@Component
public class OpenAiCompatChatProvider implements ChatProvider {

    private final ConcurrentHashMap<String, ChatClient> cache = new ConcurrentHashMap<>();

    private final TaskExecutor chatTaskExecutor;
    private final SseHelper sseHelper;
    private final ChatMessageService chatMessageService;

    public OpenAiCompatChatProvider(TaskExecutor chatTaskExecutor,
                                   SseHelper sseHelper,
                                   ChatMessageService chatMessageService) {
        this.chatTaskExecutor = chatTaskExecutor;
        this.sseHelper = sseHelper;
        this.chatMessageService = chatMessageService;
    }

    @Override
    public boolean supports(String providerType) {
        if (providerType == null) return true; // 兼容旧数据：没有 providerType 默认按 openai兼容处理
        String t = providerType.trim().toUpperCase();
        return t.equals("OPENAI_COMPAT") || t.equals("OPENAI") || t.equals("DEEPSEEK") || t.equals("OLLAMA_COMPAT");
    }

    @Override
    public SseEmitter chat(ChatRequest req, ChatModel model, SseEmitter emitter) {
        sseHelper.init(emitter);

//        chatTaskExecutor.execute(() -> {
//            try {
//                String baseUrl = firstNotBlank(model.getBaseUrl(), joinBaseUrl(model.getApiHost(), model.getApiUrl()));
//                String apiKey = model.getApiKey();
//
//                String remoteModel = firstNotBlank(model.getRemoteModel(), model.getModelName());
//                if (remoteModel == null || remoteModel.isBlank()) {
//                    throw new IllegalArgumentException("remoteModel/modelName is empty for modelId=" + model.getId());
//                }
//                if (baseUrl == null || baseUrl.isBlank()) {
//                    throw new IllegalArgumentException("baseUrl is empty for modelId=" + model.getId());
//                }
//
//                // 1) 落库：用户消息（取 messages 最后一条 user 当做本次输入；你也可以拼合全部）
//                String userInput = pickLastUserContent(req);
//                chatMessageService.insertUserMessage(req, userInput);
//
//                // 2) assistant 占位
//                long assistantMsgId = chatMessageService.insertAssistantPlaceholder(req, remoteModel);
//
//                // 3) 调用 Spring AI
//                ChatClient client = getOrCreateClient(baseUrl, apiKey, remoteModel);
//
//
//                ChatClient.PromptBuilder pb = client.prompt();
//
//                // system prompt：模型自带 + req.sysPrompt
//                String sys = mergeSystemPrompt(model.getSystemPrompt(), req.getSysPrompt());
//                if (sys != null && !sys.isBlank()) {
//                    pb = pb.system(sys);
//                }
//
//                // 可选：req.prompt 作为额外 user 指令
//                if (req.getPrompt() != null && !req.getPrompt().isBlank()) {
//                    pb = pb.user(req.getPrompt());
//                }
//
//                // 历史消息
//                for (ChatMsg m : req.getMessages()) {
//                    String role = m.getRole();
//                    String content = m.getContent();
//                    if (content == null) continue;
//
//                    if ("system".equalsIgnoreCase(role)) pb = pb.system(content);
//                    else if ("assistant".equalsIgnoreCase(role)) pb = pb.assistant(content);
//                    else pb = pb.user(content);
//                }
//
//                boolean stream = req.getStream() == null || req.getStream();
//
//                // options（先最小化：只设置 model）
//                OpenAiChatOptions options = OpenAiChatOptions.builder()
//                        .model(remoteModel)
//                        .build();
//
//                if (stream) {
//                    Flux<String> flux = pb.options(options).stream().content();
//
//                    StringBuilder full = new StringBuilder();
//                    flux.doOnNext(delta -> {
//                                if (delta == null) return;
//                                full.append(delta);
//                                sseHelper.send(emitter, "delta", delta);
//                            })
//                            .doOnError(e -> {
//                                // 落库已生成内容（尽可能）
//                                chatMessageService.updateAssistantContent(assistantMsgId, full.toString());
//                                sseHelper.error(emitter, e);
//                            })
//                            .doOnComplete(() -> {
//                                chatMessageService.updateAssistantContent(assistantMsgId, full.toString());
//                                sseHelper.done(emitter);
//                            })
//                            .subscribe();
//
//                } else {
//                    String content = pb.options(options).call().content();
//                    content = Objects.toString(content, "");
//                    sseHelper.send(emitter, "message", content);
//                    chatMessageService.updateAssistantContent(assistantMsgId, content);
//                    sseHelper.done(emitter);
//                }
//
//            } catch (Exception e) {
//                sseHelper.error(emitter, e);
//            }
//        });

        return emitter;
    }

    private ChatClient getOrCreateClient(String baseUrl, String apiKey, String remoteModel) {
//        String key = baseUrl + "|" + (apiKey == null ? "" : apiKey) + "|" + remoteModel;
//        return cache.computeIfAbsent(key, k -> {
//            ApiKey apiKeyObj = new ApiKey(apiKey);
//            OpenAiApi api = new OpenAiApi(baseUrl, apiKey);
//            OpenAiChatModel chatModel = new OpenAiChatModel(api,
//                    OpenAiChatOptions.builder().model(remoteModel).build()
//            );
//            return ChatClient.builder(chatModel).build();
//        });
        return null;
    }

    private String mergeSystemPrompt(String modelSys, String reqSys) {
        if (isBlank(modelSys)) return reqSys;
        if (isBlank(reqSys)) return modelSys;
        return modelSys + "\n" + reqSys;
    }

    private String pickLastUserContent(ChatRequest req) {
        if (req.getMessages() == null || req.getMessages().isEmpty()) return "";
        for (int i = req.getMessages().size() - 1; i >= 0; i--) {
            ChatMsg m = req.getMessages().get(i);
            if (m != null && "user".equalsIgnoreCase(m.getRole())) {
                return m.getContent();
            }
        }
        // 没有 user 就取最后一条
        return req.getMessages().get(req.getMessages().size() - 1).getContent();
    }

    private String firstNotBlank(String a, String b) {
        return !isBlank(a) ? a : b;
    }

    private boolean isBlank(String s) {
        return s == null || s.trim().isEmpty();
    }

    private String joinBaseUrl(String host, String suffix) {
        if (isBlank(host)) return null;
        if (isBlank(suffix)) return host;
        String h = host.trim();
        String s = suffix.trim();
        if (h.endsWith("/") && s.startsWith("/")) return h + s.substring(1);
        if (!h.endsWith("/") && !s.startsWith("/")) return h + "/" + s;
        return h + s;
    }
}