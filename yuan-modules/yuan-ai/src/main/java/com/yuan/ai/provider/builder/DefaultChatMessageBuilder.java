package com.yuan.ai.provider.builder;

import com.yuan.ai.domain.dto.ChatMsg;
import com.yuan.ai.domain.dto.ChatRequest;
import com.yuan.common.core.utils.StringUtils;
import org.springframework.ai.chat.messages.AssistantMessage;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.SystemMessage;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Component
public class DefaultChatMessageBuilder implements ChatMessageBuilder {

    @Override
    public List<Message> build(ChatRequest req) {
        List<Message> out = new ArrayList<>();

        // 1) systemPrompt（合并/优先）
        if (StringUtils.isNotBlank(req.getSystemPrompt())) {
            out.add(new SystemMessage(req.getSystemPrompt()));
        }

        // 2) history
        if (req.getMessages() != null) {
            for (ChatMsg m : req.getMessages()) {
                if (m == null) continue;
                String role = StringUtils.trimToEmpty(m.getRole());
                String content = m.getContent();
                if (StringUtils.isBlank(content)) continue;

                if ("system".equalsIgnoreCase(role)) out.add(new SystemMessage(content));
                else if ("assistant".equalsIgnoreCase(role)) out.add(new AssistantMessage(content));
                else out.add(new UserMessage(content));
            }
        }

        // 3) prompt 去重追加
        if (StringUtils.isNotBlank(req.getPrompt())) {
            String p = req.getPrompt().trim();
            Message last = out.isEmpty() ? null : out.get(out.size() - 1);
            boolean dup = last instanceof UserMessage && Objects.equals(((UserMessage) last).getText(), p);
            if (!dup) out.add(new UserMessage(p));
        }

        // 4) 可选：system 合并（如果不希望多个 system）
        // out = mergeSystem(out);

        return out;
    }
}