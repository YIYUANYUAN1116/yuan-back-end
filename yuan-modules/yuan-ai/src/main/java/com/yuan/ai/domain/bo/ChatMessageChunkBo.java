package com.yuan.ai.domain.bo;

import com.yuan.ai.domain.ChatMessageChunk;
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
 * chat_message_chunk业务对象 chat_message_chunk
 *
 * @author yuan
 * @date Thu Feb 26 21:44:46 CST 2026
 */
@Data

@AutoMapper(target = ChatMessageChunk.class, reverseConvertGenerate = false)
public class ChatMessageChunkBo implements Serializable {

    private Long id;

    /**
     * tenantId
     */
    @NotBlank(message = "tenantId不能为空", groups = { AddGroup.class, EditGroup.class })
    private String tenantId;
    /**
     * messageId
     */
    @NotNull(message = "messageId不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long messageId;
    /**
     * seq
     */
    @NotNull(message = "seq不能为空", groups = { AddGroup.class, EditGroup.class })
    private Integer seq;
    /**
     * deltaText
     */
    @NotBlank(message = "deltaText不能为空", groups = { AddGroup.class, EditGroup.class })
    private String deltaText;
    /**
     * createTime
     */
    @NotNull(message = "createTime不能为空", groups = { AddGroup.class, EditGroup.class })
    private LocalDateTime createTime;

}
