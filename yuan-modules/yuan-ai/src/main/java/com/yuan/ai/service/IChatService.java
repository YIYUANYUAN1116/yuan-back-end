package com.yuan.ai.service;

import com.yuan.ai.domain.model.ChatRequest;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

public interface IChatService {

    SseEmitter chat(ChatRequest req, SseEmitter emitter);
}
