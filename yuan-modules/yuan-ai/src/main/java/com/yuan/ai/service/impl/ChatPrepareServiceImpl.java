package com.yuan.ai.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import com.yuan.ai.domain.ChatConversation;
import com.yuan.ai.domain.LlmEndpoint;
import com.yuan.ai.domain.LlmModel;
import com.yuan.ai.domain.dto.ChatPrepareContext;
import com.yuan.ai.domain.dto.ChatRequest;
import com.yuan.ai.mapper.LlmEndpointMapper;
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
        LlmEndpoint ep = endpointMapper.selectById(model.getEndpointId());
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
                    modelId,
                    req.getPrompt().substring(0, Math.min(req.getPrompt().length(), 10))
            );
            conversationService.touch(conv.getId());

//            String userContent = req.getMessages().isEmpty()
//                    ? ""
//                    : req.getMessages().get(req.getMessages().size() - 1).getContent();

            messageService.insertUserMsg(tenantId, conv.getId(), req.getUserId(), model.getId(), req.getPrompt());
            assistantMsgId = messageService.insertAssistantPlaceholder(tenantId, conv.getId(), req.getUserId(), model.getId());
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
