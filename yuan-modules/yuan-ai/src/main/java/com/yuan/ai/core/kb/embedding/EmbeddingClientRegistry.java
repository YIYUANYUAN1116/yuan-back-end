package com.yuan.ai.core.kb.embedding;

import com.yuan.common.core.utils.StringUtils;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class EmbeddingClientRegistry {

    private final List<KbEmbeddingClient> clients;

    public EmbeddingClientRegistry(List<KbEmbeddingClient> clients) {
        this.clients = clients;
    }

    public KbEmbeddingClient resolve(String providerCode) {
        String pc = StringUtils.trimToEmpty(providerCode).toUpperCase();
        return clients.stream()
                .filter(i -> i.supports(pc))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("No KbEmbeddingClient for providerCode=" + pc));
    }
}
