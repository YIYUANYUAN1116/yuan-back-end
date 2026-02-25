package com.yuan.ai.service.impl;

import com.yuan.ai.domain.ChatModel;
import com.yuan.ai.domain.model.ChatRequest;
import com.yuan.ai.service.ChatModelService;
import com.yuan.ai.service.IChatService;
import com.yuan.ai.service.provider.ChatProvider;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.List;

@Service
public class ChatServiceImpl implements IChatService {

    private final ChatModelService chatModelService;
    private final List<ChatProvider> providers;

    public ChatServiceImpl(ChatModelService chatModelService, List<ChatProvider> providers) {
        this.chatModelService = chatModelService;
        this.providers = providers;
    }

    @Override
    public SseEmitter chat(ChatRequest req, SseEmitter emitter) {
        // autoSelectModel：如果你允许前端不传 model，可在这里兜底
        ChatModel model;
        if ((req.getModelKey() == null || req.getModelKey().isBlank()) && Boolean.TRUE.equals(req.getAutoSelectModel())) {
            model = chatModelService.pickAutoModel(req);
        } else {
            model = chatModelService.getByModelKeyOrName(req, req.getModelKey());
        }

        if (model == null) {
            throw new IllegalArgumentException("未找到可用模型: " + req.getModelKey());
        }

        String providerType = pickProviderType(model);
        ChatProvider provider = providers.stream()
                .filter(p -> p.supports(providerType))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("没有可用的Provider实现: " + providerType));

        return provider.chat(req, model, emitter);
    }

    private String pickProviderType(ChatModel model) {
        if (model.getProviderType() != null && !model.getProviderType().isBlank()) return model.getProviderType();
        if (model.getProviderName() != null && !model.getProviderName().isBlank()) return model.getProviderName();
        return "OPENAI_COMPAT";
    }
}