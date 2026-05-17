package com.yuan.ai.domain.bo;

import com.yuan.ai.domain.KbDocumentText;
import com.yuan.common.core.validate.AddGroup;
import com.yuan.common.core.validate.EditGroup;
import io.github.linpeilie.annotations.AutoMapper;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 知识库文档解析文本表业务对象 kb_document_text
 *
 * @author yuan
 * @date Fri May 01 16:08:34 CST 2026
 */
@Data

@AutoMapper(target = KbDocumentText.class, reverseConvertGenerate = false)
public class KbDocumentTextBo implements Serializable {

    private Long textId;

    /**
     * 租户ID
     */
    private String tenantId;
    /**
     * 知识库ID
     */
    @NotNull(message = "知识库ID不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long kbId;
    /**
     * 文档ID
     */
    @NotNull(message = "文档ID不能为空", groups = { AddGroup.class, EditGroup.class })
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
    @NotNull(message = "字符数量不能为空", groups = { AddGroup.class, EditGroup.class })
    private Integer charCount;
    /**
     * Token数量
     */
    @NotNull(message = "Token数量不能为空", groups = { AddGroup.class, EditGroup.class })
    private Integer tokenCount;
    /**
     * 状态：ENABLED-启用，DISABLED-禁用
     */
    @NotBlank(message = "状态：ENABLED-启用，DISABLED-禁用不能为空", groups = { AddGroup.class, EditGroup.class })
    private String status;
    /**
     * 创建人
     */
    private String createBy;
    /**
     * 创建时间
     */
    @NotNull(message = "创建时间不能为空", groups = { AddGroup.class, EditGroup.class })
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
//    @NotBlank(message = "逻辑删除：0-未删除，2-已删除不能为空", groups = { AddGroup.class, EditGroup.class })
//    private String delFlag;

}
