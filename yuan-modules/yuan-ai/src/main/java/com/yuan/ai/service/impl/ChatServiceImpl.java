package com.yuan.ai.service.impl;

import com.yuan.ai.domain.dto.ChatPrepareContext;
import com.yuan.ai.domain.dto.ChatRequest;
import com.yuan.ai.provider.UnifiedChatProvider;
import com.yuan.ai.service.ChatPrepareService;
import com.yuan.ai.service.ChatService;
import com.yuan.ai.support.SseHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@Service
@RequiredArgsConstructor
public class ChatServiceImpl implements ChatService {
    private final UnifiedChatProvider chatProvider;
    private final SseHelper sse;
    private final ChatPrepareService chatPrepareService;

    @Override
    public SseEmitter chat(ChatRequest req) {
        SseEmitter emitter = new SseEmitter(5 * 60 * 1000L);
        sse.init(emitter);
        try {
            ChatPrepareContext ctx = chatPrepareService.prepare(req);
            return chatProvider.stream(req, ctx, emitter);
        } catch (Exception e) {
            sse.error(emitter, e);
            return emitter;
        }
    }
}
