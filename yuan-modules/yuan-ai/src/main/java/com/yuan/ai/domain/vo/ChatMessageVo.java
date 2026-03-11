package com.yuan.ai.domain.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.yuan.ai.domain.ChatMessage;
import io.github.linpeilie.annotations.AutoMapper;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;


/**
 * chat_message视图对象 chat_message
 *
 * @author yuan
 * @date Thu Feb 26 21:44:39 CST 2026
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = ChatMessage.class)
public class ChatMessageVo implements Serializable {
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
     private Long id;
    /**
     * tenantId
     */
    @ExcelProperty(value = "tenantId")
    private String tenantId;
    /**
     * conversationId
     */
    @ExcelProperty(value = "conversationId")
    private Long conversationId;
    /**
     * userId
     */
    @ExcelProperty(value = "userId")
    private Long userId;
    /**
     * system/user/assistant/tool
     */
    @ExcelProperty(value = "system/user/assistant/tool")
    private String role;
    /**
     * content
     */
    @ExcelProperty(value = "content")
    private String content;
    /**
     * contentFormat
     */
    @ExcelProperty(value = "contentFormat")
    private String contentFormat;
    /**
     * parentId
     */
    @ExcelProperty(value = "parentId")
    private Long parentId;
    /**
     * PENDING/STREAMING/DONE/FAILED
     */
    @ExcelProperty(value = "PENDING/STREAMING/DONE/FAILED")
    private String status;
    /**
     * endpointKey
     */
    @ExcelProperty(value = "endpointKey")
    private String endpointKey;
    /**
     * invocationId
     */
    @ExcelProperty(value = "invocationId")
    private Long invocationId;
    /**
     * tokenIn
     */
    @ExcelProperty(value = "tokenIn")
    private Integer tokenIn;
    /**
     * tokenOut
     */
    @ExcelProperty(value = "tokenOut")
    private Integer tokenOut;
    /**
     * costAmount
     */
    @ExcelProperty(value = "costAmount")
    private BigDecimal costAmount;
    /**
     * finishReason
     */
    @ExcelProperty(value = "finishReason")
    private String finishReason;
    /**
     * errorMsg
     */
    @ExcelProperty(value = "errorMsg")
    private String errorMsg;
    /**
     * createTime
     */
    @ExcelProperty(value = "createTime")
    private LocalDateTime createTime;
    /**
     * updateTime
     */
    @ExcelProperty(value = "updateTime")
    private LocalDateTime updateTime;

}
