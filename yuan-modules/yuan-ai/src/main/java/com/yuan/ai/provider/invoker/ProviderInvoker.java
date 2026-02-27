package com.yuan.ai.provider.invoker;

import com.yuan.ai.domain.LlmEndpoint;
import com.yuan.ai.domain.LlmModel;
import com.yuan.ai.domain.dto.ChatRequest;
import org.springframework.ai.chat.messages.Message;
import reactor.core.publisher.Flux;

import java.util.List;

public interface ProviderInvoker {

    /**
     * 是否支持该 providerCode（建议 invoker 自己做）
     */
    boolean supports(String providerCode);

    /**
     * 流式输出：返回“增量 delta”流（每个元素就是一段文本增量）
     */
    Flux<String> stream(ChatRequest req, LlmEndpoint ep, LlmModel model, List<Message> messages);

    /**
     * 非流式：返回完整内容
     */
    String call(ChatRequest req, LlmEndpoint ep, LlmModel model, List<Message> messages);
}