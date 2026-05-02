package com.yuan.ai.service;

import com.yuan.ai.domain.dto.KbRetrievalRequest;
import com.yuan.ai.domain.dto.KbRetrievalResponse;

public interface KbRetrievalService {
    KbRetrievalResponse retrieve(KbRetrievalRequest request);

    void bindInvocation(Long logId, Long invocationId);

    String buildPromptContext(KbRetrievalResponse response);
}
