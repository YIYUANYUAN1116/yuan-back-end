package com.yuan.ai.domain.bo;

import com.yuan.ai.domain.ChatMessage;
import com.yuan.core.domain.BaseEntity;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import lombok.EqualsAndHashCode;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.io.Serializable;
import com.yuan.common.core.validate.AddGroup;
import com.yuan.common.core.validate.EditGroup;
import java.time.LocalDateTime;
import java.io.Serializable;
import com.yuan.common.core.validate.AddGroup;
import com.yuan.common.core.validate.EditGroup;
import java.io.Serializable;
import java.io.Serializable;
import com.yuan.common.core.validate.AddGroup;
import com.yuan.common.core.validate.EditGroup;

/**
 * chat_message业务对象 chat_message
 *
 * @author yuan
 * @date Thu Feb 26 21:44:39 CST 2026
 */
@Data

@AutoMapper(target = ChatMessage.class, reverseConvertGenerate = false)
public class ChatMessageBo implements Serializable {

    private Long id;

    /**
     * tenantId
     */
    @NotBlank(message = "tenantId不能为空", groups = { AddGroup.class, EditGroup.class })
    private String tenantId;
    /**
     * conversationId
     */
    @NotNull(message = "conversationId不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long conversationId;
    /**
     * userId
     */
    @NotNull(message = "userId不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long userId;
    /**
     * system/user/assistant/tool
     */
    @NotBlank(message = "system/user/assistant/tool不能为空", groups = { AddGroup.class, EditGroup.class })
    private String role;
    /**
     * content
     */
    private String content;
    /**
     * contentFormat
     */
    @NotBlank(message = "contentFormat不能为空", groups = { AddGroup.class, EditGroup.class })
    private String contentFormat;
    /**
     * parentId
     */
    private Long parentId;
    /**
     * PENDING/STREAMING/DONE/FAILED
     */
    @NotBlank(message = "PENDING/STREAMING/DONE/FAILED不能为空", groups = { AddGroup.class, EditGroup.class })
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
    @NotNull(message = "createTime不能为空", groups = { AddGroup.class, EditGroup.class })
    private LocalDateTime createTime;
    /**
     * updateTime
     */
    @NotNull(message = "updateTime不能为空", groups = { AddGroup.class, EditGroup.class })
    private LocalDateTime updateTime;

}
