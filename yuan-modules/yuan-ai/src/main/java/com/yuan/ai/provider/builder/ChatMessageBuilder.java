package com.yuan.ai.provider.builder;

import com.yuan.ai.domain.dto.ChatRequest;
import org.springframework.ai.chat.messages.Message;

import java.util.List;

public interface ChatMessageBuilder {
    List<Message> build(ChatRequest req);
}