package com.yuan.ai.domain.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.yuan.ai.domain.ChatMessageChunk;
import io.github.linpeilie.annotations.AutoMapper;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;


/**
 * chat_message_chunk视图对象 chat_message_chunk
 *
 * @author yuan
 * @date Thu Feb 26 21:44:46 CST 2026
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = ChatMessageChunk.class)
public class ChatMessageChunkVo implements Serializable {
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
    private Long id;
    /**
     * tenantId
     */
    @ExcelProperty(value = "tenantId")
    private String tenantId;
    /**
     * messageId
     */
    @ExcelProperty(value = "messageId")
    private Long messageId;
    /**
     * seq
     */
    @ExcelProperty(value = "seq")
    private Integer seq;
    /**
     * deltaText
     */
    @ExcelProperty(value = "deltaText")
    private String deltaText;
    /**
     * createTime
     */
    @ExcelProperty(value = "createTime")
    private LocalDateTime createTime;

}
