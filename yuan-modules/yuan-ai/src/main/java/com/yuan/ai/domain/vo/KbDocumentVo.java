package com.yuan.ai.domain.vo;

    import java.time.LocalDateTime;
    import java.io.Serializable;
import com.yuan.ai.domain.KbDocument;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;

import java.util.Date;


/**
 * 知识库文档表视图对象 kb_document
 *
 * @author yuan
 * @date Fri May 01 16:08:29 CST 2026
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = KbDocument.class)
public class KbDocumentVo implements Serializable {

    private Long docId;
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
     * 统一文件表ID，如有
     */
    @ExcelProperty(value = "统一文件表ID，如有")
    private Long fileId;
    /**
     * 文件名
     */
    @ExcelProperty(value = "文件名")
    private String fileName;
    /**
     * 文件类型，如 pdf/docx/txt/md
     */
    @ExcelProperty(value = "文件类型，如 pdf/docx/txt/md")
    private String fileType;
    /**
     * 文件大小，单位字节
     */
    @ExcelProperty(value = "文件大小，单位字节")
    private Long fileSize;
    /**
     * 文件访问地址或对象存储地址
     */
    @ExcelProperty(value = "文件访问地址或对象存储地址")
    private String fileUrl;
    /**
     * 对象存储Key
     */
    @ExcelProperty(value = "对象存储Key")
    private String objectKey;
    /**
     * 文档标题
     */
    @ExcelProperty(value = "文档标题")
    private String title;
    /**
     * 来源类型：UPLOAD-上传，URL-网页，MANUAL-手工录入，API-接口导入
     */
    @ExcelProperty(value = "来源类型：UPLOAD-上传，URL-网页，MANUAL-手工录入，API-接口导入")
    private String sourceType;
    /**
     * 来源URL
     */
    @ExcelProperty(value = "来源URL")
    private String sourceUrl;
    /**
     * 解析状态：PENDING-待解析，PARSING-解析中，SUCCESS-成功，FAILED-失败
     */
    @ExcelProperty(value = "解析状态：PENDING-待解析，PARSING-解析中，SUCCESS-成功，FAILED-失败")
    private String parseStatus;
    /**
     * 向量化状态：PENDING-待处理，EMBEDDING-处理中，SUCCESS-成功，FAILED-失败
     */
    @ExcelProperty(value = "向量化状态：PENDING-待处理，EMBEDDING-处理中，SUCCESS-成功，FAILED-失败")
    private String embedStatus;
    /**
     * 切片数量
     */
    @ExcelProperty(value = "切片数量")
    private Integer chunkCount;
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
     * 文档内容Hash，用于判断是否重复或变更
     */
    @ExcelProperty(value = "文档内容Hash，用于判断是否重复或变更")
    private String contentHash;
    /**
     * 错误信息
     */
    @ExcelProperty(value = "错误信息")
    private String errorMessage;
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
