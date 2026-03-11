package com.yuan.ai.domain.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.yuan.ai.domain.ChatSession;
import io.github.linpeilie.annotations.AutoMapper;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;


/**
 * chat-session视图对象 chat_session
 *
 * @author ageerle
 * @date Mon Feb 16 14:59:22 CST 2026
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = ChatSession.class)
public class ChatSessionVo implements Serializable {
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
     private Long id;
    /**
     * 用户id
     */
    @ExcelProperty(value = "用户id")
    private Long userId;
    /**
     * 会话标题
     */
    @ExcelProperty(value = "会话标题")
    private String sessionTitle;
    /**
     * 会话内容
     */
    @ExcelProperty(value = "会话内容")
    private String sessionContent;
    /**
     * 部门
     */
    @ExcelProperty(value = "部门")
    private String createDept;
    /**
     * 创建者
     */
    @ExcelProperty(value = "创建者")
    private Long createBy;
    /**
     * 创建时间
     */
    @ExcelProperty(value = "创建时间")
    private LocalDateTime createTime;
    /**
     * 更新者
     */
    @ExcelProperty(value = "更新者")
    private Long updateBy;
    /**
     * 更新时间
     */
    @ExcelProperty(value = "更新时间")
    private LocalDateTime updateTime;
    /**
     * 备注
     */
    @ExcelProperty(value = "备注")
    private String remark;
    /**
     * 会话ID
     */
    @ExcelProperty(value = "会话ID")
    private String conversationId;

}
