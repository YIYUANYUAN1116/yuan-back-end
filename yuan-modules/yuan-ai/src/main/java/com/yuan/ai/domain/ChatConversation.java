package com.yuan.ai.domain;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import java.time.LocalDateTime;
import java.io.Serializable;

/**
 * chat_conversation对象 chat_conversation
 *
 * @author yuan
 * @date Thu Feb 26 21:44:35 CST 2026
 */
@Data
@TableName("chat_conversation")
public class ChatConversation implements Serializable {


    /**
     * id
     */
        @TableId(value = "id", type = IdType.AUTO)
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
    private String defaultEndpointKey;

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
