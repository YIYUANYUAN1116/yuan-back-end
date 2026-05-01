package com.yuan.ai.kb.vector;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class KbVectorItem {
    private String vectorId;
    private String tenantId;
    private Long kbId;
    private Long docId;
    private Long chunkId;
    private String content;
    private float[] vector;
}
