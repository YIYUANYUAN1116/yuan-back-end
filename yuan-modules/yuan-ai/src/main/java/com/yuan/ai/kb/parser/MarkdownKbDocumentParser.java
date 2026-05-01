package com.yuan.ai.kb.parser;

import com.yuan.common.core.utils.StringUtils;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;

@Component
public class MarkdownKbDocumentParser implements KbDocumentParser {

    @Override
    public boolean supports(String fileName, String contentType) {
        String lower = StringUtils.defaultString(fileName).toLowerCase();
        String ct = StringUtils.defaultString(contentType).toLowerCase();
        return lower.endsWith(".md") || lower.endsWith(".markdown") || ct.contains("markdown");
    }

    @Override
    public KbParsedDocument parse(String fileName, byte[] bytes) {
        String raw = new String(bytes, StandardCharsets.UTF_8);
        String clean = cleanMarkdown(raw);
        return KbParsedDocument.builder()
                .rawText(raw)
                .cleanText(clean)
                .parserType("MARKDOWN")
                .parserVersion("1.0")
                .title(extractTitle(raw, fileName))
                .build();
    }

    private String cleanMarkdown(String raw) {
        return StringUtils.defaultString(raw)
                .replace("\r\n", "\n")
                .replace('\r', '\n')
                .replaceAll("(?s)```.*?```", " ")
                .replaceAll("!\\[([^]]*)]\\([^)]+\\)", "$1")
                .replaceAll("\\[([^]]+)]\\([^)]+\\)", "$1")
                .replaceAll("(?m)^\\s{0,3}#{1,6}\\s*", "")
                .replaceAll("[*_`>]", "")
                .replaceAll("[ \\t]+", " ")
                .trim();
    }

    private String extractTitle(String raw, String fileName) {
        for (String line : StringUtils.defaultString(raw).split("\\R")) {
            String trimmed = line.trim();
            if (trimmed.startsWith("#")) {
                return trimmed.replaceFirst("^#+", "").trim();
            }
        }
        String name = StringUtils.defaultIfBlank(fileName, "Untitled");
        int idx = name.lastIndexOf('.');
        return idx > 0 ? name.substring(0, idx) : name;
    }
}
