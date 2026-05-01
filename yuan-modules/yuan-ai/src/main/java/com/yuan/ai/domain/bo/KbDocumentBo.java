package com.yuan.ai.domain.bo;

import com.yuan.ai.domain.KbDocument;
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
 * 知识库文档表业务对象 kb_document
 *
 * @author yuan
 * @date Fri May 01 16:08:29 CST 2026
 */
@Data

@AutoMapper(target = KbDocument.class, reverseConvertGenerate = false)
public class KbDocumentBo implements Serializable {

    private Long docId;

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
     * 统一文件表ID，如有
     */
    private Long fileId;
    /**
     * 文件名
     */
    @NotBlank(message = "文件名不能为空", groups = { AddGroup.class, EditGroup.class })
    private String fileName;
    /**
     * 文件类型，如 pdf/docx/txt/md
     */
    private String fileType;
    /**
     * 文件大小，单位字节
     */
    private Long fileSize;
    /**
     * 文件访问地址或对象存储地址
     */
    private String fileUrl;
    /**
     * 对象存储Key
     */
    private String objectKey;
    /**
     * 文档标题
     */
    private String title;
    /**
     * 来源类型：UPLOAD-上传，URL-网页，MANUAL-手工录入，API-接口导入
     */
    @NotBlank(message = "来源类型：UPLOAD-上传，URL-网页，MANUAL-手工录入，API-接口导入不能为空", groups = { AddGroup.class, EditGroup.class })
    private String sourceType;
    /**
     * 来源URL
     */
    private String sourceUrl;
    /**
     * 解析状态：PENDING-待解析，PARSING-解析中，SUCCESS-成功，FAILED-失败
     */
    @NotBlank(message = "解析状态：PENDING-待解析，PARSING-解析中，SUCCESS-成功，FAILED-失败不能为空", groups = { AddGroup.class, EditGroup.class })
    private String parseStatus;
    /**
     * 向量化状态：PENDING-待处理，EMBEDDING-处理中，SUCCESS-成功，FAILED-失败
     */
    @NotBlank(message = "向量化状态：PENDING-待处理，EMBEDDING-处理中，SUCCESS-成功，FAILED-失败不能为空", groups = { AddGroup.class, EditGroup.class })
    private String embedStatus;
    /**
     * 切片数量
     */
    @NotNull(message = "切片数量不能为空", groups = { AddGroup.class, EditGroup.class })
    private Integer chunkCount;
    /**
     * Token数量
     */
    @NotNull(message = "Token数量不能为空", groups = { AddGroup.class, EditGroup.class })
    private Integer tokenCount;
    /**
     * 字符数量
     */
    @NotNull(message = "字符数量不能为空", groups = { AddGroup.class, EditGroup.class })
    private Integer charCount;
    /**
     * 文档内容Hash，用于判断是否重复或变更
     */
    private String contentHash;
    /**
     * 错误信息
     */
    private String errorMessage;
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
    @NotBlank(message = "逻辑删除：0-未删除，2-已删除不能为空", groups = { AddGroup.class, EditGroup.class })
    private String delFlag;

}
