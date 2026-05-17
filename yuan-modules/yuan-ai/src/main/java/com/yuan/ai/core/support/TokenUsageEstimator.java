package com.yuan.ai.core.support;

import com.yuan.common.core.utils.StringUtils;
import org.springframework.ai.chat.messages.Message;

import java.util.List;

public final class TokenUsageEstimator {

    private TokenUsageEstimator() {
    }

    public static int estimateMessages(List<Message> messages) {
        if (messages == null || messages.isEmpty()) {
            return 0;
        }
        return messages.stream()
            .map(Message::getText)
            .mapToInt(TokenUsageEstimator::estimateText)
            .sum();
    }

    public static int estimateText(String text) {
        String value = StringUtils.defaultString(text).trim();
        if (value.isEmpty()) {
            return 0;
        }
        return Math.max(1, value.length() / 4);
    }
}
