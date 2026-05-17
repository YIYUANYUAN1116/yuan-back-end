package com.yuan.ai.core.chat.invoker;

import com.yuan.ai.core.common.AiModelRuntime;
import com.yuan.ai.domain.dto.ChatRequest;
import org.springframework.ai.chat.messages.Message;
import reactor.core.publisher.Flux;

import java.util.List;

public interface ChatInvoker {

    boolean supports(String providerCode);

    Flux<String> stream(ChatRequest req, AiModelRuntime runtime, List<Message> messages);

    String call(ChatRequest req, AiModelRuntime runtime, List<Message> messages);
}
