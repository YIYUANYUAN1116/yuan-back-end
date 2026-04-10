package com.yuan.ai.domain.bo;

import com.yuan.ai.domain.ChatConversation;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * chat_conversation业务对象 chat_conversation
 *
 * @author yuan
 * @date Thu Feb 26 21:44:35 CST 2026
 */
@Data

@AutoMapper(target = ChatConversation.class, reverseConvertGenerate = false)
public class ChatConversationBo implements Serializable {

    private Long id;

    /**
     * tenantId
     */
    private String tenantId;
    /**
     * userId
     */
    private Long userId;
    /**
     * appId
     */
    private Long appId;
    /**
     * title
     */
    private String title;
    /**
     * defaultEndpointKey
     */
    private Long modelId;
    /**
     * metaJson
     */
    private String metaJson;
    /**
     * lastMessageAt
     */
    private LocalDateTime lastMessageAt;
    /**
     * createTime
     */
    private LocalDateTime createTime;
    /**
     * updateTime
     */
    private LocalDateTime updateTime;

}
