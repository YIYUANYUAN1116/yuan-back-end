package com.yuan.ai.domain.vo;

    import java.time.LocalDateTime;
    import java.io.Serializable;
import com.yuan.ai.domain.KbDocumentText;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;

import java.util.Date;


/**
 * 知识库文档解析文本表视图对象 kb_document_text
 *
 * @author yuan
 * @date Fri May 01 16:08:34 CST 2026
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = KbDocumentText.class)
public class KbDocumentTextVo implements Serializable {

    private Long textId;
    /**
     * 租户ID
     */
    @ExcelProperty(value = "租户ID")
    private String tenantId;
    /**
     * 知识库ID
     */
    @ExcelProperty(value = "知识库ID")
    private Long kbId;
    /**
     * 文档ID
     */
    @ExcelProperty(value = "文档ID")
    private Long docId;
    /**
     * 解析原始文本
     */
    @ExcelProperty(value = "解析原始文本")
    private String rawText;
    /**
     * 清洗后文本
     */
    @ExcelProperty(value = "清洗后文本")
    private String cleanText;
    /**
     * 解析器类型：PDF/DOCX/TXT/MARKDOWN
     */
    @ExcelProperty(value = "解析器类型：PDF/DOCX/TXT/MARKDOWN")
    private String parserType;
    /**
     * 解析器版本
     */
    @ExcelProperty(value = "解析器版本")
    private String parserVersion;
    /**
     * 字符数量
     */
    @ExcelProperty(value = "字符数量")
    private Integer charCount;
    /**
     * Token数量
     */
    @ExcelProperty(value = "Token数量")
    private Integer tokenCount;
    /**
     * 状态：ENABLED-启用，DISABLED-禁用
     */
    @ExcelProperty(value = "状态：ENABLED-启用，DISABLED-禁用")
    private String status;
    /**
     * 创建人
     */
    @ExcelProperty(value = "创建人")
    private String createBy;
    /**
     * 创建时间
     */
    @ExcelProperty(value = "创建时间")
    private LocalDateTime createTime;
    /**
     * 更新人
     */
    @ExcelProperty(value = "更新人")
    private String updateBy;
    /**
     * 更新时间
     */
    @ExcelProperty(value = "更新时间")
    private LocalDateTime updateTime;
    /**
     * 逻辑删除：0-未删除，2-已删除
     */
    @ExcelProperty(value = "逻辑删除：0-未删除，2-已删除")
    private String delFlag;

}
