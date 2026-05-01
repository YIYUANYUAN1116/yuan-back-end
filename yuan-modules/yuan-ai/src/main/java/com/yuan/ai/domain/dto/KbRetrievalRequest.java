package com.yuan.ai.domain.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class KbRetrievalRequest {
    private String tenantId;

    private Long kbId;
    private List<Long> kbIds;

    @NotBlank
    private String query;

    private Long embeddingModelId;
    private Integer topK;
    private BigDecimal minScore;

    private Long sessionId;
    private Long conversationId;
    private Long messageId;
    private Long invocationId;
}
