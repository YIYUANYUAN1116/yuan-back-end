package com.yuan.ai.domain;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.io.Serializable;

/**
 * chat_message对象 chat_message
 *
 * @author yuan
 * @date Thu Feb 26 21:44:39 CST 2026
 */
@Data
@TableName("chat_message")
public class ChatMessage implements Serializable {


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
     * conversationId
     */
    private Long conversationId;

    /**
     * userId
     */
    private Long userId;

    /**
     * system/user/assistant/tool
     */
    private String role;

    /**
     * content
     */
    private String content;

    /**
     * contentFormat
     */
    private String contentFormat;

    /**
     * parentId
     */
    private Long parentId;

    /**
     * PENDING/STREAMING/DONE/FAILED
     */
    private String status;

    /**
     * endpointKey
     */
    private String endpointKey;

    /**
     * invocationId
     */
    private Long invocationId;

    /**
     * tokenIn
     */
    private Integer tokenIn;

    /**
     * tokenOut
     */
    private Integer tokenOut;

    /**
     * costAmount
     */
    private BigDecimal costAmount;

    /**
     * finishReason
     */
    private String finishReason;

    /**
     * errorMsg
     */
    private String errorMsg;

    /**
     * createTime
     */
    private LocalDateTime createTime;

    /**
     * updateTime
     */
    private LocalDateTime updateTime;


}
