package com.yuan.ai.service;


import com.yuan.ai.domain.dto.ChatRequest;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

public interface ChatService {
    SseEmitter chat(ChatRequest req);
}