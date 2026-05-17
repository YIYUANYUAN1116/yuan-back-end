package com.yuan.ai.core.chat.factory;

import com.yuan.ai.core.common.AiModelRuntime;
import org.springframework.ai.chat.client.ChatClient;

public interface SpringAiChatClientFactory {
    boolean supports(String providerCode);
    ChatClient create(AiModelRuntime runtime);
}
