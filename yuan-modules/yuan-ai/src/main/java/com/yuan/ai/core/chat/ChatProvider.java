package com.yuan.ai.core.chat;

import com.yuan.ai.domain.dto.ChatPrepareContext;
import com.yuan.ai.domain.dto.ChatRequest;
import reactor.core.publisher.Flux;

public interface ChatProvider {

    Flux<String> stream(ChatRequest req, ChatPrepareContext ctx);

    String call(ChatRequest req, ChatPrepareContext ctx);
}
