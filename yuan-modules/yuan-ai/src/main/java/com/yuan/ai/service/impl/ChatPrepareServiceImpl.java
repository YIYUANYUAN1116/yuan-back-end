package com.yuan.ai.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import com.yuan.ai.domain.ChatConversation;
import com.yuan.ai.domain.LlmEndpoint;
import com.yuan.ai.domain.LlmModel;
import com.yuan.ai.domain.dto.ChatPrepareContext;
import com.yuan.ai.domain.dto.ChatRequest;
import com.yuan.ai.service.ChatConversationService;
import com.yuan.ai.service.ChatMessageService;
import com.yuan.ai.service.ChatPrepareService;
import com.yuan.ai.service.LlmEndpointService;
import com.yuan.ai.service.LlmModelService;
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

    @Override
    public ChatPrepareContext prepare(ChatRequest req) {
        String tenantId = LoginHelper.getTenantId();
        req.setTenantId(tenantId);
        req.setToken(StpUtil.getTokenValue());

        if (StringUtils.isBlank(req.getTraceId())){
            req.setTraceId(TraceIdUtil.newTraceId());
        }

        LlmEndpoint ep;
        if (req.getEndpointKey() != null && !req.getEndpointKey().isBlank()) {
            ep = endpointService.getEnabledByKey(tenantId, req.getEndpointKey());
        } else if (Boolean.TRUE.equals(req.getAutoSelectModel())) {
            ep = endpointService.pickHighestPriority(tenantId);
        } else {
            ep = null;
        }
        if (ep == null) {
            throw new IllegalArgumentException("No enabled endpoint for tenant=" + tenantId);
        }

        LlmModel model = modelService.getById(ep.getDefaultModelId());
        if (model == null || model.getStatus() == null || !model.getStatus().equals(SystemConstants.NORMAL)) {
            throw new IllegalArgumentException("Model disabled or not found: " + ep.getDefaultModelId());
        }

        boolean persistChatMessage = req.getPersistChatMessage() == null || req.getPersistChatMessage();

        Long conversationId = null;
        Long assistantMsgId = null;

        if (persistChatMessage) {
            ChatConversation conv = conversationService.getOrCreate(
                    tenantId,
                    req.getConversationId(),
                    req.getUserId(),
                    req.getAppId(),
                    ep.getEndpointKey()
            );
            conversationService.touch(conv.getId());

            String userContent = req.getMessages().isEmpty()
                    ? ""
                    : req.getMessages().get(req.getMessages().size() - 1).getContent();

            messageService.insertUserMsg(tenantId, conv.getId(), req.getUserId(), ep.getEndpointKey(), userContent);
            assistantMsgId = messageService.insertAssistantPlaceholder(tenantId, conv.getId(), req.getUserId(), ep.getEndpointKey());
            conversationId = conv.getId();
        }

        return ChatPrepareContext.builder()
                .tenantId(tenantId)
                .endpoint(ep)
                .model(model)
                .conversationId(conversationId)
                .assistantMsgId(assistantMsgId)
                .build();
    }
}
