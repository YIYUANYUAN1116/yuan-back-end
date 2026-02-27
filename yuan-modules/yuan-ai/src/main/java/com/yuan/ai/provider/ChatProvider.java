package com.yuan.ai.provider;


import com.yuan.ai.domain.LlmEndpoint;
import com.yuan.ai.domain.LlmModel;
import com.yuan.ai.domain.dto.ChatRequest;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

public interface ChatProvider {

    SseEmitter chat(ChatRequest req, LlmEndpoint ep, LlmModel model, long conversationId, long assistantMsgId, SseEmitter emitter);
}