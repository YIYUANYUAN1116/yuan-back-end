package com.yuan.ai.service;

import com.yuan.ai.domain.dto.ChatPrepareContext;
import com.yuan.ai.domain.dto.ChatRequest;

public interface ChatPrepareService {
    ChatPrepareContext prepare(ChatRequest req);
}
