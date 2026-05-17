package com.yuan.ai.core.chat;

import com.yuan.ai.core.chat.builder.ChatMessageBuilder;
import com.yuan.ai.core.chat.invoker.ChatInvoker;
import com.yuan.ai.core.chat.invoker.ChatInvokerRegistry;
import com.yuan.ai.core.common.AiModelRuntime;
import com.yuan.ai.domain.dto.ChatPrepareContext;
import com.yuan.ai.domain.dto.ChatRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.messages.Message;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

import java.util.List;
import java.util.Objects;

@Component
@RequiredArgsConstructor
public class UnifiedChatProvider implements ChatProvider {

    private final ChatMessageBuilder messageBuilder;
    private final ChatInvokerRegistry invokerRegistry;

    @Override
    public Flux<String> stream(ChatRequest req, ChatPrepareContext ctx) {
        AiModelRuntime runtime = Objects.requireNonNull(ctx.getRuntime(), "AiModelRuntime is required");
        ChatInvoker invoker = invokerRegistry.resolve(runtime.getProviderCode());
        List<Message> messages = messageBuilder.build(req);
        return invoker.stream(req, runtime, messages);
    }

    @Override
    public String call(ChatRequest req, ChatPrepareContext ctx) {
        AiModelRuntime runtime = Objects.requireNonNull(ctx.getRuntime(), "AiModelRuntime is required");
        ChatInvoker invoker = invokerRegistry.resolve(runtime.getProviderCode());
        List<Message> messages = messageBuilder.build(req);
        return invoker.call(req, runtime, messages);
    }
}
