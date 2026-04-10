package com.yuan.ai.provider.invoker;

import com.yuan.common.core.utils.StringUtils;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class InvokerRegistry {

    private final List<ProviderInvoker> invokers;

    public InvokerRegistry(List<ProviderInvoker> invokers) {
        this.invokers = invokers;
    }

    public ProviderInvoker resolve(String providerCode) {
        String pc = StringUtils.trimToEmpty(providerCode).toUpperCase();
        return invokers.stream()
                .filter(i -> i.supports(pc))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("No ProviderInvoker for providerCode=" + pc));
    }
}