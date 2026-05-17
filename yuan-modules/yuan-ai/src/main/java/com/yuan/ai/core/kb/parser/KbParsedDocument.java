package com.yuan.ai.core.kb.parser;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class KbParsedDocument {
    private String rawText;
    private String cleanText;
    private String parserType;
    private String parserVersion;
    private String title;
}
