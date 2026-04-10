package com.yuan.ai.provider.factory;

import com.yuan.ai.domain.LlmEndpoint;
import com.yuan.ai.domain.LlmModel;
import org.springframework.ai.chat.client.ChatClient;

public interface SpringAiChatClientFactory {
    boolean supports(String providerCode);
    ChatClient create(LlmEndpoint ep, LlmModel model);
}