package com.yuan.ai.domain.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.yuan.ai.domain.ChatConversation;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;


/**
 * chat_conversation视图对象 chat_conversation
 *
 * @author yuan
 * @date Thu Feb 26 21:44:35 CST 2026
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = ChatConversation.class)
public class ChatConversationVo implements Serializable {

    private Long id;
    /**
     * tenantId
     */
    @ExcelProperty(value = "tenantId")
    private String tenantId;
    /**
     * userId
     */
    @ExcelProperty(value = "userId")
    private Long userId;
    /**
     * appId
     */
    @ExcelProperty(value = "appId")
    private Long appId;
    /**
     * title
     */
    @ExcelProperty(value = "title")
    private String title;
    /**
     * defaultEndpointKey
     */
    @ExcelProperty(value = "defaultEndpointKey")
    private String defaultEndpointKey;
    /**
     * metaJson
     */
    @ExcelProperty(value = "metaJson")
    private String metaJson;
    /**
     * lastMessageAt
     */
    @ExcelProperty(value = "lastMessageAt")
    private LocalDateTime lastMessageAt;
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
