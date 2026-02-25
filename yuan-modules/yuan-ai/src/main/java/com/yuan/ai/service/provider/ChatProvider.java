package com.yuan.ai.service.provider;

import com.yuan.ai.domain.ChatModel;
import com.yuan.ai.domain.model.ChatRequest;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

public interface ChatProvider {

    boolean supports(String providerType);

    SseEmitter chat(ChatRequest req, ChatModel modelConfig, SseEmitter emitter);
}