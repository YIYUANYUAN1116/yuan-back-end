package com.yuan.ai.domain.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.yuan.common.excel.annotation.ExcelDictFormat;
import com.yuan.common.excel.convert.ExcelDictConvert;
import com.yuan.ai.domain.ChatMessage;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;


/**
 * chat-message视图对象 chat_message
 *
 * @author ageerle
 * @date Mon Feb 16 14:59:03 CST 2026
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = ChatMessage.class)
public class ChatMessageVo implements Serializable {

    private Long id;
    /**
     * 会话id
     */
    @ExcelProperty(value = "会话id")
    private Long sessionId;
    /**
     * 用户id
     */
    @ExcelProperty(value = "用户id")
    private Long userId;
    /**
     * 消息内容
     */
    @ExcelProperty(value = "消息内容")
    private String content;
    /**
     * 对话角色
     */
    @ExcelProperty(value = "对话角色")
    private String role;
    /**
     * 扣除金额
     */
    @ExcelProperty(value = "扣除金额")
    private BigDecimal deductCost;
    /**
     * 累计 Tokens
     */
    @ExcelProperty(value = "累计 Tokens")
    private Integer totalTokens;
    /**
     * 模型名称
     */
    @ExcelProperty(value = "模型名称")
    private String modelName;
    /**
     * 创建部门
     */
    @ExcelProperty(value = "创建部门")
    private Long createDept;
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
     * 计费类型（1-token计费，2-次数计费，null-普通消息）
     */
    @ExcelProperty(value = "计费类型", converter = ExcelDictConvert.class)
    @ExcelDictFormat(readConverterExp = "$column.readConverterExp()")
    private String billingType;

    private String tracId;
    private String promptTokens;
    private String completionTokens;
    private String finishReason;
    private String errorMsg;

}
