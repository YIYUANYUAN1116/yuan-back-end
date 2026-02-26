package com.yuan.ai.domain;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import java.time.LocalDateTime;
import java.io.Serializable;

/**
 * chat_message_chunk对象 chat_message_chunk
 *
 * @author yuan
 * @date Thu Feb 26 21:44:46 CST 2026
 */
@Data
@TableName("chat_message_chunk")
public class ChatMessageChunk implements Serializable {


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
     * messageId
     */
    private Long messageId;

    /**
     * seq
     */
    private Integer seq;

    /**
     * deltaText
     */
    private String deltaText;

    /**
     * createTime
     */
    private LocalDateTime createTime;


}
