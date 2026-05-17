package com.yuan.ai.core.kb.vector;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class KbVectorSearchHit {
    private String vectorId;
    private Long kbId;
    private Long docId;
    private Long chunkId;
    private String content;
    private double score;
}
