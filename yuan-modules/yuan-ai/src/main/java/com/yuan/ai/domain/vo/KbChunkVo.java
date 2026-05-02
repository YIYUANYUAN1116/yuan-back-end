package com.yuan.ai.domain.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.yuan.ai.domain.KbChunk;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;


/**
 * 知识库文档切片表视图对象 kb_chunk
 *
 * @author yuan
 * @date Fri May 01 16:08:37 CST 2026
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = KbChunk.class)
public class KbChunkVo implements Serializable {

    private Long chunkId;
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
    private String kbName;
    /**
     * 文档ID
     */
    @ExcelProperty(value = "文档ID")
    private Long docId;
    private String docName;
    /**
     * 文档内切片序号
     */
    @ExcelProperty(value = "文档内切片序号")
    private Integer chunkNo;
    /**
     * 切片标题
     */
    @ExcelProperty(value = "切片标题")
    private String chunkTitle;
    /**
     * 切片内容
     */
    @ExcelProperty(value = "切片内容")
    private String content;
    /**
     * 切片内容Hash
     */
    @ExcelProperty(value = "切片内容Hash")
    private String contentHash;
    /**
     * Token数量
     */
    @ExcelProperty(value = "Token数量")
    private Integer tokenCount;
    /**
     * 字符数量
     */
    @ExcelProperty(value = "字符数量")
    private Integer charCount;
    /**
     * 页码，PDF/Word 可用
     */
    @ExcelProperty(value = "页码，PDF/Word 可用")
    private Integer pageNo;
    /**
     * 章节标题
     */
    @ExcelProperty(value = "章节标题")
    private String sectionTitle;
    /**
     * 在文档文本中的起始位置
     */
    @ExcelProperty(value = "在文档文本中的起始位置")
    private Integer startOffset;
    /**
     * 在文档文本中的结束位置
     */
    @ExcelProperty(value = "在文档文本中的结束位置")
    private Integer endOffset;
    /**
     * 向量化状态：PENDING-待处理，EMBEDDING-处理中，SUCCESS-成功，FAILED-失败
     */
    @ExcelProperty(value = "向量化状态：PENDING-待处理，EMBEDDING-处理中，SUCCESS-成功，FAILED-失败")
    private String embeddingStatus;
    /**
     * 默认向量记录ID
     */
    @ExcelProperty(value = "默认向量记录ID")
    private Long embeddingId;
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
