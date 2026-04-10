package com.yuan.ai.support;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@Component
public class SseHelper {
    public void init(SseEmitter emitter) {
        emitter.onTimeout(emitter::complete);
        emitter.onError(emitter::completeWithError);
    }

    public void send(SseEmitter emitter,String event, Object data) {
        try {
            emitter.send(SseEmitter.event().name(event)
                    .data(data));
        } catch (Exception e) {
            emitter.completeWithError(e);
        }
    }

    public void done(SseEmitter emitter) {
        try {
            emitter.send(SseEmitter
                    .event().name("done").data("[DONE]"));
        } catch (Exception ignored) {
        }
        emitter.complete();
    }

    public void error(SseEmitter emitter, Throwable e) {
        send(emitter, "error", buildErrorMessage(e));
        emitter.completeWithError(e);
    }

    private String buildErrorMessage(Throwable e) {
        if (e == null) {
            return "Unknown error (Throwable is null)";
        }

        String msg = e.getMessage();

        if (msg == null || msg.isBlank()) {
            msg = e.toString();  // 至少带类名
        }

        // 可选：限制长度，避免数据库被撑爆
        if (msg.length() > 2000) {
            msg = msg.substring(0, 2000) + "...(truncated)";
        }

        return msg;
    }
}
