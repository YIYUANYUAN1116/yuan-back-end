package com.yuan.ai.domain.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class KbRetrievalHitDto {
    private Long kbId;
    private Long docId;
    private Long chunkId;
    private Integer rankNo;
    private BigDecimal score;
    private String content;
    private String contentPreview;
}
