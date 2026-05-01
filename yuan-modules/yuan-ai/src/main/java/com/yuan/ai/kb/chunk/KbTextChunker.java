package com.yuan.ai.kb.chunk;

import com.yuan.common.core.utils.StringUtils;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class KbTextChunker {

    private static final int DEFAULT_CHUNK_SIZE = 1000;
    private static final int DEFAULT_OVERLAP = 100;

    public List<KbTextChunk> split(String text, String title, Integer chunkSize, Integer chunkOverlap) {
        String normalized = StringUtils.defaultString(text).replace("\r\n", "\n").replace('\r', '\n').trim();
        if (StringUtils.isBlank(normalized)) {
            return List.of();
        }

        int size = chunkSize == null || chunkSize <= 0 ? DEFAULT_CHUNK_SIZE : chunkSize;
        int overlap = chunkOverlap == null || chunkOverlap < 0 ? DEFAULT_OVERLAP : chunkOverlap;
        if (overlap >= size) {
            overlap = Math.max(0, size / 5);
        }

        List<KbTextChunk> chunks = new ArrayList<>();
        int start = 0;
        int no = 1;
        while (start < normalized.length()) {
            int hardEnd = Math.min(normalized.length(), start + size);
            int end = findBoundary(normalized, start, hardEnd);
            if (end <= start) {
                end = hardEnd;
            }
            String content = normalized.substring(start, end).trim();
            if (StringUtils.isNotBlank(content)) {
                chunks.add(KbTextChunk.builder()
                        .chunkNo(no++)
                        .title(buildChunkTitle(title, no - 1))
                        .sectionTitle(findSectionTitle(normalized, start))
                        .content(content)
                        .startOffset(start)
                        .endOffset(end)
                        .build());
            }
            if (end >= normalized.length()) {
                break;
            }
            start = Math.max(0, end - overlap);
        }
        return chunks;
    }

    private int findBoundary(String text, int start, int hardEnd) {
        if (hardEnd >= text.length()) {
            return text.length();
        }
        int paragraph = text.lastIndexOf("\n\n", hardEnd);
        if (paragraph > start + 100) {
            return paragraph;
        }
        int line = text.lastIndexOf('\n', hardEnd);
        if (line > start + 100) {
            return line;
        }
        int sentence = Math.max(text.lastIndexOf('。', hardEnd), text.lastIndexOf('.', hardEnd));
        if (sentence > start + 100) {
            return sentence + 1;
        }
        return hardEnd;
    }

    private String buildChunkTitle(String title, int chunkNo) {
        String base = StringUtils.defaultIfBlank(title, "Untitled");
        return base + " #" + chunkNo;
    }

    private String findSectionTitle(String text, int offset) {
        int cursor = Math.min(offset, text.length());
        while (cursor >= 0) {
            int lineStart = text.lastIndexOf('\n', Math.max(0, cursor - 1));
            int from = lineStart < 0 ? 0 : lineStart + 1;
            int lineEnd = text.indexOf('\n', from);
            if (lineEnd < 0) {
                lineEnd = text.length();
            }
            String line = text.substring(from, lineEnd).trim();
            if (line.startsWith("#")) {
                return line.replaceFirst("^#+", "").trim();
            }
            if (lineStart < 0) {
                break;
            }
            cursor = lineStart;
        }
        return null;
    }
}
