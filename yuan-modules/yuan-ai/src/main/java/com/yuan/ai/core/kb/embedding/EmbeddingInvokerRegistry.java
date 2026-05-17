package com.yuan.ai.core.kb.embedding;

import com.yuan.common.core.utils.StringUtils;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class EmbeddingInvokerRegistry {

    private final List<KbEmbeddingClient> invokers;

    public EmbeddingInvokerRegistry(List<KbEmbeddingClient> invokers) {
        this.invokers = invokers;
    }

    public KbEmbeddingClient resolve(String providerCode) {
        String pc = StringUtils.trimToEmpty(providerCode).toUpperCase();
        return invokers.stream()
                .filter(i -> i.supports(pc))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("No ProviderInvoker for providerCode=" + pc));
    }
}