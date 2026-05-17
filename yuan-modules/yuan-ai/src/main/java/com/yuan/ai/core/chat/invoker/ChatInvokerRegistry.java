package com.yuan.ai.core.chat.invoker;

import com.yuan.common.core.utils.StringUtils;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ChatInvokerRegistry {

    private final List<ChatInvoker> invokers;

    public ChatInvokerRegistry(List<ChatInvoker> invokers) {
        this.invokers = invokers;
    }

    public ChatInvoker resolve(String providerCode) {
        String pc = StringUtils.trimToEmpty(providerCode).toUpperCase();
        return invokers.stream()
                .filter(i -> i.supports(pc))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("No ChatInvoker for providerCode=" + pc));
    }
}
