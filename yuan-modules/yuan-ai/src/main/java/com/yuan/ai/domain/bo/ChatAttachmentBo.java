package com.yuan.ai.domain.bo;

import com.yuan.ai.domain.ChatAttachment;
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
 * chat_attachment业务对象 chat_attachment
 *
 * @author yuan
 * @date Thu Feb 26 21:44:43 CST 2026
 */
@Data

@AutoMapper(target = ChatAttachment.class, reverseConvertGenerate = false)
public class ChatAttachmentBo implements Serializable {

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
     * fileName
     */
    @NotBlank(message = "fileName不能为空", groups = { AddGroup.class, EditGroup.class })
    private String fileName;
    /**
     * fileType
     */
    private String fileType;
    /**
     * fileSize
     */
    private Long fileSize;
    /**
     * storage
     */
    @NotBlank(message = "storage不能为空", groups = { AddGroup.class, EditGroup.class })
    private String storage;
    /**
     * objectKey
     */
    @NotBlank(message = "objectKey不能为空", groups = { AddGroup.class, EditGroup.class })
    private String objectKey;
    /**
     * url
     */
    private String url;
    /**
     * metaJson
     */
    private String metaJson;
    /**
     * createTime
     */
    @NotNull(message = "createTime不能为空", groups = { AddGroup.class, EditGroup.class })
    private LocalDateTime createTime;

}
