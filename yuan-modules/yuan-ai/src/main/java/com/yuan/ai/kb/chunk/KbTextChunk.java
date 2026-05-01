package com.yuan.ai.kb.chunk;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class KbTextChunk {
    private Integer chunkNo;
    private String title;
    private String sectionTitle;
    private String content;
    private Integer startOffset;
    private Integer endOffset;
}
