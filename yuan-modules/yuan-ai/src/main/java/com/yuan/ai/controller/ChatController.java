package com.yuan.ai.controller;


import com.yuan.ai.domain.dto.ChatRequest;
import com.yuan.ai.service.ChatService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@RestController
@RequestMapping("/ai/chat")
@RequiredArgsConstructor
@Tag(name = "ChatController",description = "ChatController")

public class ChatController {

    private final ChatService chatService;

    @PostMapping("/stream")
    @Operation(summary = "对话",operationId = "chat")
    public SseEmitter stream(@Valid @RequestBody ChatRequest req) {
        return chatService.chat(req);
    }
}