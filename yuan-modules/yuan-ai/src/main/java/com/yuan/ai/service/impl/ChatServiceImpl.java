package com.yuan.ai.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import com.yuan.ai.domain.ChatConversation;
import com.yuan.ai.domain.LlmEndpoint;
import com.yuan.ai.domain.LlmModel;
import com.yuan.ai.domain.dto.ChatRequest;
import com.yuan.ai.provider.UnifiedChatProvider;
import com.yuan.ai.service.*;
import com.yuan.ai.support.SseHelper;
import com.yuan.common.core.constant.SystemConstants;
import com.yuan.common.satoken.utils.LoginHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@Service
@RequiredArgsConstructor
public class ChatServiceImpl implements ChatService {
    private final LlmEndpointService endpointService;
    private final LlmModelService modelService;
    private final ChatConversationService conversationService;
    private final ChatMessageService messageService;
    private final UnifiedChatProvider chatProvider;
    private final SseHelper sse;

    @Override
    public SseEmitter chat(ChatRequest req) {
        SseEmitter emitter = new SseEmitter(5 * 60 * 1000L);
        sse.init(emitter);
        String tenantId = LoginHelper.getTenantId();
        req.setTenantId(tenantId);
        req.setToken(StpUtil.getTokenValue());

        // 1) choose endpoint
        LlmEndpoint ep;
        if (req.getEndpointKey() != null && !req.getEndpointKey().isBlank()) {
            ep = endpointService.getEnabledByKey(tenantId, req.getEndpointKey());
        } else if (Boolean.TRUE.equals(req.getAutoSelectModel())) {
            ep = endpointService.pickHighestPriority(tenantId);
        } else {
            ep = null;
        }
        if (ep == null) {
            sse.error(emitter, new IllegalArgumentException("No enabled endpoint for tenant=" + tenantId));
            return emitter;
        }
        // 2) load model
        LlmModel model = modelService.getById(ep.getDefaultModelId());
        if (model == null || model.getStatus() == null || !model.getStatus().equals(SystemConstants.NORMAL)) {
            sse.error(emitter, new IllegalArgumentException("Model disabled or not found: " + ep.getDefaultModelId()));
            return emitter;
        }

        // 3) conversation
        ChatConversation conv = conversationService.getOrCreate(
                tenantId,
                req.getConversationId(),
                req.getUserId(),
                req.getAppId(),
                ep.getEndpointKey()
        );
        conversationService.touch(conv.getId());

        // 4) insert user message (last user content)
        String userContent = req.getMessages().isEmpty() ? "" : req.getMessages().get(req.getMessages().size() - 1).getContent();
        messageService.insertUserMsg(tenantId, conv.getId(), req.getUserId(), ep.getEndpointKey(), userContent);

        // 5) assistant placeholder
        long assistantMsgId = messageService.insertAssistantPlaceholder(tenantId, conv.getId(), req.getUserId(), ep.getEndpointKey());

        // 7) run
        return chatProvider.chat(req, ep, model, conv.getId(), assistantMsgId, emitter);

    }
}
