package com.yuan.ai.domain;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import java.time.LocalDateTime;
import java.io.Serializable;

/**
 * 知识库文档解析文本表对象 kb_document_text
 *
 * @author yuan
 * @date Fri May 01 16:08:34 CST 2026
 */
@Data
@TableName("kb_document_text")
public class KbDocumentText implements Serializable {


    /**
     * 文本ID
     */
        @TableId(value = "text_id", type = IdType.AUTO)
    private Long textId;

    /**
     * 租户ID
     */
    private String tenantId;

    /**
     * 知识库ID
     */
    private Long kbId;

    /**
     * 文档ID
     */
    private Long docId;

    /**
     * 解析原始文本
     */
    private String rawText;

    /**
     * 清洗后文本
     */
    private String cleanText;

    /**
     * 解析器类型：PDF/DOCX/TXT/MARKDOWN
     */
    private String parserType;

    /**
     * 解析器版本
     */
    private String parserVersion;

    /**
     * 字符数量
     */
    private Integer charCount;

    /**
     * Token数量
     */
    private Integer tokenCount;

    /**
     * 状态：ENABLED-启用，DISABLED-禁用
     */
    private String status;

    /**
     * 创建人
     */
    private String createBy;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新人
     */
    private String updateBy;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;

    /**
     * 逻辑删除：0-未删除，2-已删除
     */
        @TableLogic
    private String delFlag;


}
