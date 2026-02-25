package com.yuan.ai.support;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@Component
public class SseHelper {

    public void init(SseEmitter emitter) {
        emitter.onTimeout(emitter::complete);
        emitter.onError(emitter::completeWithError);
    }

    public void send(SseEmitter emitter, String event, Object data) {
        try {
            emitter.send(SseEmitter.event().name(event).data(data));
        } catch (Exception e) {
            emitter.completeWithError(e);
        }
    }

    public void done(SseEmitter emitter) {
        try {
            emitter.send(SseEmitter.event().name("done").data("[DONE]"));
        } catch (Exception ignored) {}
        emitter.complete();
    }

    public void error(SseEmitter emitter, Throwable e) {
        send(emitter, "error", e.getMessage());
        emitter.completeWithError(e);
    }
}