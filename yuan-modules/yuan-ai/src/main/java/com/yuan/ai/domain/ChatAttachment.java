package com.yuan.ai.domain;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import java.time.LocalDateTime;
import java.io.Serializable;

/**
 * chat_attachment对象 chat_attachment
 *
 * @author yuan
 * @date Thu Feb 26 21:44:43 CST 2026
 */
@Data
@TableName("chat_attachment")
public class ChatAttachment implements Serializable {


    /**
     * id
     */
        @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * tenantId
     */
    private String tenantId;

    /**
     * messageId
     */
    private Long messageId;

    /**
     * fileName
     */
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
    private String storage;

    /**
     * objectKey
     */
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
    private LocalDateTime createTime;


}
