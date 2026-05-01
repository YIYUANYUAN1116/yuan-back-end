package com.yuan.ai.kb.parser;

public interface KbDocumentParser {
    boolean supports(String fileName, String contentType);

    KbParsedDocument parse(String fileName, byte[] bytes);
}
