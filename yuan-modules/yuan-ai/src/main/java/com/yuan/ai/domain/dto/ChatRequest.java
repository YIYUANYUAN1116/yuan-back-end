package com.yuan.ai.domain.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.List;

@Data
public class ChatRequest {

    @NotBlank
    private String tenantId;

    @NotBlank
    private String traceId;

    @NotEmpty
    private List<ChatMsg> messages;

    /** 前端首选：直接传 endpointKey */
    private String endpointKey;

    /** 自动选模型：endpointKey 为空时用路由规则 */
    private Boolean autoSelectModel = Boolean.TRUE;

    /** 业务侧字段（可选） */
    private Long userId;
    private Long conversationId;
    private Long appId;

    /** 可选提示词 */
    private String systemPrompt;
    private String prompt;

    /** 是否流式 */
    private Boolean stream = Boolean.TRUE;

    /** thinking 标记（用于路由策略） */
    private Boolean enableThinking = Boolean.FALSE;

    private String token;
}