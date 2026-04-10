package com.yuan.ai.service.api;


import com.yuan.ai.api.AiTemplateExecuteApi;
import com.yuan.ai.api.dto.AiTemplateExecuteRequest;
import com.yuan.ai.api.dto.AiTemplateExecuteResult;
import com.yuan.ai.api.dto.ChatExecuteResult;
import com.yuan.ai.domain.dto.ChatMsg;
import com.yuan.ai.domain.dto.ChatPrepareContext;
import com.yuan.ai.domain.dto.ChatRequest;
import com.yuan.ai.provider.ChatProvider;
import com.yuan.ai.service.AiTemplateService;
import com.yuan.ai.service.ChatPrepareService;
import com.yuan.ai.service.ChatService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class DefaultAiTemplateExecuteApi implements AiTemplateExecuteApi {

    private final AiTemplateService aiTemplateService;
    private final ChatService chatService;
    private final ChatPrepareService chatPrepareService;
    private final ChatProvider chatProvider;

    @Override
    public AiTemplateExecuteResult execute(AiTemplateExecuteRequest request) {
        long start = System.currentTimeMillis();

        validateRequest(request);

        String templateContent = null;
        String finalPrompt = null;

        try {
            // 1. 获取模板内容
            templateContent = resolveTemplateContent(request);

            // 2. 渲染模板
            finalPrompt = aiTemplateService.render(templateContent, request.getVariables());

            // 3. 构造 ChatRequest
            ChatRequest chatRequest = buildChatRequest(request, finalPrompt);

            // 4. 统一准备上下文（选 endpoint/model，可选 conversation/message）
            ChatPrepareContext ctx = chatPrepareService.prepare(chatRequest);

            // 5. 执行同步 AI 调用
            ChatExecuteResult executeResult = chatProvider.call(
                    chatRequest,
                    ctx
            );

            return AiTemplateExecuteResult.builder()
                    .success(Boolean.TRUE.equals(executeResult.getSuccess()))
                    .prompt(finalPrompt)
                    .content(executeResult.getContent())
                    .invocationId(executeResult.getInvocationId())
                    .endpointCode(executeResult.getEndpointCode())
                    .providerCode(executeResult.getProviderCode())
                    .modelName(executeResult.getModelName())
                    .latencyMs(executeResult.getLatencyMs())
                    .errorMessage(executeResult.getErrorMessage())
                    .build();

        } catch (Exception e) {
            String err = buildErrorMessage(e);
            log.error("DefaultAiTemplateExecuteApi.execute error, templateCode={}, sceneCode={}, bizType={}, bizId={}",
                    request.getTemplateCode(), request.getSceneCode(), request.getBizType(), request.getBizId(), e);

            return AiTemplateExecuteResult.builder()
                    .success(Boolean.FALSE)
                    .prompt(finalPrompt)
                    .content(null)
                    .invocationId(null)
                    .endpointCode(null)
                    .providerCode(null)
                    .modelName(null)
                    .latencyMs((int) (System.currentTimeMillis() - start))
                    .errorMessage(err)
                    .build();
        }
    }

    private void validateRequest(AiTemplateExecuteRequest request) {
        if (request == null) {
            throw new IllegalArgumentException("AiTemplateExecuteRequest cannot be null");
        }

        boolean noTemplateCode = !StringUtils.hasText(request.getTemplateCode());
        boolean noTemplateContent = !StringUtils.hasText(request.getTemplateContent());

        if (noTemplateCode && noTemplateContent) {
            throw new IllegalArgumentException("templateCode and templateContent cannot both be blank");
        }
    }

    private String resolveTemplateContent(AiTemplateExecuteRequest request) {
        if (StringUtils.hasText(request.getTemplateContent())) {
            return request.getTemplateContent();
        }
        return aiTemplateService.getTemplateContent(request.getTemplateCode());
    }

    private ChatRequest buildChatRequest(AiTemplateExecuteRequest request, String finalPrompt) {
        ChatRequest chatRequest = new ChatRequest();

        chatRequest.setEndpointCode(request.getEndpointCode());
        chatRequest.setAutoSelectModel(
                request.getAutoSelectModel() == null ? Boolean.TRUE : request.getAutoSelectModel()
        );

        chatRequest.setStream(Boolean.FALSE);
        chatRequest.setPersistChatMessage(
                request.getPersistChatMessage() == null ? Boolean.FALSE : request.getPersistChatMessage()
        );

        chatRequest.setSceneCode(
                StringUtils.hasText(request.getSceneCode()) ? request.getSceneCode() : "workflow_ai"
        );
        chatRequest.setBizType(request.getBizType());
        chatRequest.setBizId(request.getBizId());

        chatRequest.setUserId(request.getUserId());

        chatRequest.setTraceId(request.getTraceId());

        chatRequest.setEnableThinking(request.getEnableThinking());
        chatRequest.setSystemPrompt(request.getSystemPrompt());
        chatRequest.setPrompt(finalPrompt);

        List<ChatMsg> messages = new ArrayList<>();

        ChatMsg user = new ChatMsg("user", finalPrompt);
        ChatMsg system = new ChatMsg("system", "你是一个严格的 JSON 输出助手。\n" +
                "无论用户输入什么，都只输出合法 JSON，不要输出 markdown，不要输出解释性文字。");
        messages.add(user);
        messages.add(system);
        chatRequest.setMessages(messages);


        return chatRequest;
    }

    private String buildErrorMessage(Throwable e) {
        if (e == null) {
            return "Unknown error";
        }
        if (StringUtils.hasText(e.getMessage())) {
            return e.getMessage();
        }
        return e.getClass().getSimpleName();
    }
}