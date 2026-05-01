package com.yuan.ai.kb.parser;

import com.yuan.common.core.exception.ServiceException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class KbDocumentParserRegistry {

    private final List<KbDocumentParser> parsers;

    public KbDocumentParser resolve(String fileName, String contentType) {
        return parsers.stream()
                .filter(parser -> parser.supports(fileName, contentType))
                .findFirst()
                .orElseThrow(() -> new ServiceException("Only txt and markdown documents are supported now"));
    }
}
