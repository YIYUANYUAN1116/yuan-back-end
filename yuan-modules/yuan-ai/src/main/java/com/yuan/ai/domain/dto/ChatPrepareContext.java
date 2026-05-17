package com.yuan.ai.domain.dto;

import com.yuan.ai.core.common.AiModelRuntime;
import com.yuan.ai.domain.LlmEndpoint;
import com.yuan.ai.domain.LlmModel;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ChatPrepareContext {
    private String tenantId;
    private String traceId;
    private AiModelRuntime runtime;
    private LlmEndpoint endpoint;
    private LlmModel model;
    private Long conversationId;
    private Long userMsgId;
    private Long assistantMsgId;
    private Long kbRetrievalLogId;
}
