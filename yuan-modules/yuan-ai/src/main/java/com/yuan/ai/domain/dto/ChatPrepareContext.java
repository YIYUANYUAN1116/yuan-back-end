package com.yuan.ai.domain.dto;

import com.yuan.ai.domain.LlmEndpoint;
import com.yuan.ai.domain.LlmModel;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ChatPrepareContext {
    private String tenantId;
    private String traceId;
    private LlmEndpoint endpoint;
    private LlmModel model;
    private Long conversationId;
    private Long assistantMsgId;
}