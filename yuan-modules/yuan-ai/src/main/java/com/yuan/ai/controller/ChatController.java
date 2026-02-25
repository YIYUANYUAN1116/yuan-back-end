package com.yuan.ai.controller;


import com.yuan.ai.domain.model.ChatRequest;
import com.yuan.ai.service.IChatService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@RestController
@RequestMapping("/api/chat")
public class ChatController {

    private final IChatService chatService;

    public ChatController(IChatService chatService) {
        this.chatService = chatService;
    }

    @PostMapping("/stream")
    public SseEmitter stream(@Valid @RequestBody ChatRequest req) {
        // 你也可以：new SseEmitter(0L) 不超时；这里给 5 分钟
        SseEmitter emitter = new SseEmitter(5 * 60 * 1000L);
        return chatService.chat(req, emitter);
    }
}