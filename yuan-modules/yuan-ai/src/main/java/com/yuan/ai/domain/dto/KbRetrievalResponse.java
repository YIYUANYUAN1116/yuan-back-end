package com.yuan.ai.domain.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class KbRetrievalResponse {
    private Long logId;
    private Integer hitCount;
    private Integer usedCount;
    private List<KbRetrievalHitDto> hits;
}
