package com.yuan.ai.domain.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.util.List;

@Data
public class ChatRequest {

    @NotEmpty(message = "对话消息不能为空")
    private List<ChatMsg> messages;

    /** 前端选择的模型Key（对应 chat_model.model_key） */
    @NotBlank(message = "传入的模型不能为空")
    private String modelKey;

    private String prompt;
    private String sysPrompt;

    private Long messageId;
    private Boolean stream = Boolean.TRUE;

    private String kid;
    private Long userId;
    private Long sessionId;
    private String appId;
    private String role;
    private Long uuid;
    private Boolean hasAttachment;
    private Boolean enableThinking;
    private Boolean autoSelectModel;
    private String token;

    /** 强烈建议加：链路追踪 */
    private String traceId;
    private String tenantId;
}

