package com.yuan.ai.core.kb.parser;

import com.yuan.common.core.utils.StringUtils;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;

@Component
public class TextKbDocumentParser implements KbDocumentParser {

    @Override
    public boolean supports(String fileName, String contentType) {
        String lower = StringUtils.defaultString(fileName).toLowerCase();
        return lower.endsWith(".txt") || StringUtils.defaultString(contentType).startsWith("text/plain");
    }

    @Override
    public KbParsedDocument parse(String fileName, byte[] bytes) {
        String text = new String(bytes, StandardCharsets.UTF_8);
        return KbParsedDocument.builder()
                .rawText(text)
                .cleanText(cleanText(text))
                .parserType("TXT")
                .parserVersion("1.0")
                .title(stripExtension(fileName))
                .build();
    }

    private String cleanText(String text) {
        return StringUtils.defaultString(text)
                .replace("\r\n", "\n")
                .replace('\r', '\n')
                .replaceAll("[ \\t]+", " ")
                .trim();
    }

    private String stripExtension(String fileName) {
        String name = StringUtils.defaultIfBlank(fileName, "Untitled");
        int idx = name.lastIndexOf('.');
        return idx > 0 ? name.substring(0, idx) : name;
    }
}
