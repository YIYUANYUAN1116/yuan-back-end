package com.yuan.ai.domain.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.yuan.ai.domain.ChatAttachment;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;


/**
 * chat_attachment视图对象 chat_attachment
 *
 * @author yuan
 * @date Thu Feb 26 21:44:43 CST 2026
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = ChatAttachment.class)
public class ChatAttachmentVo implements Serializable {

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
     * fileName
     */
    @ExcelProperty(value = "fileName")
    private String fileName;
    /**
     * fileType
     */
    @ExcelProperty(value = "fileType")
    private String fileType;
    /**
     * fileSize
     */
    @ExcelProperty(value = "fileSize")
    private Long fileSize;
    /**
     * storage
     */
    @ExcelProperty(value = "storage")
    private String storage;
    /**
     * objectKey
     */
    @ExcelProperty(value = "objectKey")
    private String objectKey;
    /**
     * url
     */
    @ExcelProperty(value = "url")
    private String url;
    /**
     * metaJson
     */
    @ExcelProperty(value = "metaJson")
    private String metaJson;
    /**
     * createTime
     */
    @ExcelProperty(value = "createTime")
    private LocalDateTime createTime;

}
