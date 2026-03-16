package com.yuan.ai.provider;


import com.yuan.ai.api.dto.ChatExecuteResult;
import com.yuan.ai.domain.dto.ChatPrepareContext;
import com.yuan.ai.domain.dto.ChatRequest;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

public interface ChatProvider {

    /**
     * 流式输出
     * @param req
     * @param ctx
     * @param emitter
     * @return
     */
    SseEmitter stream(ChatRequest req, ChatPrepareContext ctx, SseEmitter emitter);

    /**
     * 非流式输出
     * @param req
     * @param ctx
     * @return
     */
    ChatExecuteResult call(ChatRequest req, ChatPrepareContext ctx);
}