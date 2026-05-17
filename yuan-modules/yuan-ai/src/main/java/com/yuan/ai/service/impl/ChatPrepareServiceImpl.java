package com.yuan.ai.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import com.yuan.ai.core.common.AiModelRuntime;
import com.yuan.ai.domain.ChatConversation;
import com.yuan.ai.domain.LlmEndpoint;
import com.yuan.ai.domain.LlmModel;
import com.yuan.ai.domain.LlmProvider;
import com.yuan.ai.domain.dto.ChatPrepareContext;
import com.yuan.ai.domain.dto.ChatRequest;
import com.yuan.ai.mapper.LlmEndpointMapper;
import com.yuan.ai.mapper.LlmProviderMapper;
import com.yuan.ai.service.*;
import com.yuan.common.core.constant.SystemConstants;
import com.yuan.common.core.utils.StringUtils;
import com.yuan.common.core.utils.TraceIdUtil;
import com.yuan.common.satoken.utils.LoginHelper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class ChatPrepareServiceImpl implements ChatPrepareService {

    private final LlmEndpointService endpointService;
    private final LlmModelService modelService;
    private final ChatConversationService conversationService;
    private final ChatMessageService messageService;
    private final LlmEndpointMapper endpointMapper;
    private final LlmProviderMapper providerMapper;

    @Override
    public ChatPrepareContext prepare(ChatRequest req) {
        String tenantId = LoginHelper.getTenantId();
        req.setTenantId(tenantId);
        req.setToken(StpUtil.getTokenValue());

        if (StringUtils.isBlank(req.getTraceId())){
            req.setTraceId(TraceIdUtil.newTraceId());
        }
        Long modelId = req.getModelId();
        LlmModel model = modelService.getById(modelId);
        if (model == null || model.getStatus() == null || !model.getStatus().equals(SystemConstants.NORMAL)) {
            throw new IllegalArgumentException("Model disabled or not found: " + modelId);
        }
        LlmEndpoint ep = endpointMapper.selectById(model.getEndpointId());
        if (ep == null) {
            throw new IllegalArgumentException("Endpoint not found: " + model.getEndpointId());
        }
        LlmProvider provider = providerMapper.selectById(model.getProviderId());
        if (provider == null) {
            throw new IllegalArgumentException("Provider not found: " + model.getProviderId());
        }
        AiModelRuntime runtime = AiModelRuntime.builder()
                .tenantId(tenantId)
                .providerCode(provider.getProviderCode())
                .endpoint(ep)
                .model(model)
                .build();

        boolean persistChatMessage = req.getPersistChatMessage() == null || req.getPersistChatMessage();

        Long conversationId = null;
        Long userMsgId = null;
        Long assistantMsgId = null;

        if (persistChatMessage) {
            ChatConversation conv = conversationService.getOrCreate(
                    tenantId,
                    req.getConversationId(),
                    req.getUserId(),
                    req.getAppId(),
                    modelId,
                    req.getPrompt().substring(0, Math.min(req.getPrompt().length(), 10))
            );
            conversationService.touch(conv.getId());

//            String userContent = req.getMessages().isEmpty()
//                    ? ""
//                    : req.getMessages().get(req.getMessages().size() - 1).getContent();

            userMsgId = messageService.insertUserMsg(tenantId, conv.getId(), req.getUserId(), model.getId(), req.getPrompt());
            assistantMsgId = messageService.insertAssistantPlaceholder(tenantId, conv.getId(), req.getUserId(), model.getId());
            conversationId = conv.getId();
            req.setConversationId(conversationId);
        }

        return ChatPrepareContext.builder()
                .tenantId(tenantId)
                .traceId(req.getTraceId())
                .runtime(runtime)
                .endpoint(ep)
                .model(model)
                .conversationId(conversationId)
                .userMsgId(userMsgId)
                .assistantMsgId(assistantMsgId)
                .build();
    }
}
