package com.yuan.ai.support;

import com.yuan.ai.domain.model.ChatRequest;
import org.springframework.stereotype.Component;

@Component
public class TenantResolver {

    public String resolveTenantId(ChatRequest req) {
        // TODO: 从 req.token 解析租户；或从 session_id 反查租户
        return "000000";
    }
}