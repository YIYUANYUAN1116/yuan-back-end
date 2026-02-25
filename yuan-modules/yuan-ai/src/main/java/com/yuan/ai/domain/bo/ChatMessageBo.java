package com.yuan.ai.domain.bo;

import com.yuan.ai.domain.ChatMessage;
import com.yuan.common.core.validate.AddGroup;
import com.yuan.common.core.validate.EditGroup;
import io.github.linpeilie.annotations.AutoMapper;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * chat-message业务对象 chat_message
 *
 * @author ageerle
 * @date Mon Feb 16 14:59:03 CST 2026
 */
@Data

@AutoMapper(target = ChatMessage.class, reverseConvertGenerate = false)
public class ChatMessageBo implements Serializable {

    private Long id;

    /**
     * 会话id
     */
    private Long sessionId;
    /**
     * 用户id
     */
    @NotNull(message = "用户id不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long userId;
    /**
     * 消息内容
     */
    private String content;
    /**
     * 对话角色
     */
    private String role;
    /**
     * 扣除金额
     */
    private BigDecimal deductCost;
    /**
     * 累计 Tokens
     */
    private Integer totalTokens;
    /**
     * 模型名称
     */
    private String modelName;
    /**
     * 创建部门
     */
    private Long createDept;
    /**
     * 创建者
     */
    private Long createBy;
    /**
     * 创建时间
     */
    private LocalDateTime createTime;
    /**
     * 更新者
     */
    private Long updateBy;
    /**
     * 更新时间
     */
    private LocalDateTime updateTime;
    /**
     * 备注
     */
    private String remark;
    /**
     * 计费类型（1-token计费，2-次数计费，null-普通消息）
     */
    private String billingType;

    private String tracId;
    private String promptTokens;
    private String completionTokens;
    private String finishReason;
    private String errorMsg;
}
