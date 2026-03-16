package com.yuan.ai.service.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yuan.ai.api.AiStructuredResultParser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
@RequiredArgsConstructor
public class DefaultAiStructuredResultParser implements AiStructuredResultParser {

    private final ObjectMapper objectMapper;

    @Override
    public <T> T parse(String content, Class<T> clazz) {
        if (!StringUtils.hasText(content)) {
            throw new IllegalArgumentException("AI content is blank");
        }

        String text = content.trim();

        // 1. 先直接解析
        try {
            return objectMapper.readValue(text, clazz);
        } catch (Exception ignore) {
        }

        // 2. 尝试提取 ```json ... ```
        String jsonBlock = extractJsonBlock(text);
        if (StringUtils.hasText(jsonBlock)) {
            try {
                return objectMapper.readValue(jsonBlock, clazz);
            } catch (Exception ignore) {
            }
        }

        // 3. 尝试截取首尾大括号
        String jsonObject = extractJsonObject(text);
        if (StringUtils.hasText(jsonObject)) {
            try {
                return objectMapper.readValue(jsonObject, clazz);
            } catch (Exception ignore) {
            }
        }

        throw new IllegalArgumentException("Failed to parse AI structured result: " + abbreviate(text, 500));
    }

    private String extractJsonBlock(String text) {
        String lower = text.toLowerCase();
        int start = lower.indexOf("```json");
        if (start >= 0) {
            int contentStart = start + 7;
            int end = lower.indexOf("```", contentStart);
            if (end > contentStart) {
                return text.substring(contentStart, end).trim();
            }
        }

        start = lower.indexOf("```");
        if (start >= 0) {
            int contentStart = start + 3;
            int end = lower.indexOf("```", contentStart);
            if (end > contentStart) {
                return text.substring(contentStart, end).trim();
            }
        }
        return null;
    }

    private String extractJsonObject(String text) {
        int start = text.indexOf('{');
        int end = text.lastIndexOf('}');
        if (start >= 0 && end > start) {
            return text.substring(start, end + 1).trim();
        }
        return null;
    }

    private String abbreviate(String text, int maxLen) {
        if (text == null) {
            return null;
        }
        if (text.length() <= maxLen) {
            return text;
        }
        return text.substring(0, maxLen) + "...";
    }
}