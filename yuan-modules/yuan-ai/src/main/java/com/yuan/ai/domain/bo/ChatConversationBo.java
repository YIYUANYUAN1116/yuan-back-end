package com.yuan.ai.domain.bo;

import com.yuan.ai.domain.ChatConversation;
import com.yuan.core.domain.BaseEntity;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import lombok.EqualsAndHashCode;
import jakarta.validation.constraints.*;
import java.time.LocalDateTime;
import java.io.Serializable;
import com.yuan.common.core.validate.AddGroup;
import com.yuan.common.core.validate.EditGroup;
import java.io.Serializable;
import java.io.Serializable;
import com.yuan.common.core.validate.AddGroup;
import com.yuan.common.core.validate.EditGroup;

/**
 * chat_conversation业务对象 chat_conversation
 *
 * @author yuan
 * @date Thu Feb 26 21:44:35 CST 2026
 */
@Data

@AutoMapper(target = ChatConversation.class, reverseConvertGenerate = false)
public class ChatConversationBo implements Serializable {

    private Long id;

    /**
     * tenantId
     */
    @NotBlank(message = "tenantId不能为空", groups = { AddGroup.class, EditGroup.class })
    private String tenantId;
    /**
     * userId
     */
    @NotNull(message = "userId不能为空", groups = { AddGroup.class, EditGroup.class })
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
    private Long modelId;
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
    @NotNull(message = "createTime不能为空", groups = { AddGroup.class, EditGroup.class })
    private LocalDateTime createTime;
    /**
     * updateTime
     */
    @NotNull(message = "updateTime不能为空", groups = { AddGroup.class, EditGroup.class })
    private LocalDateTime updateTime;

}
