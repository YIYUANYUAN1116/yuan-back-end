package com.yuan.ai.domain.bo;

import com.yuan.ai.domain.KbChunk;
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
 * 知识库文档切片表业务对象 kb_chunk
 *
 * @author yuan
 * @date Fri May 01 16:08:37 CST 2026
 */
@Data

@AutoMapper(target = KbChunk.class, reverseConvertGenerate = false)
public class KbChunkBo implements Serializable {

    private Long chunkId;

    /**
     * 租户ID
     */
    private String tenantId;
    /**
     * 知识库ID
     */
    @NotNull(message = "知识库ID不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long kbId;
    private String kbName;
    /**
     * 文档ID
     */
    @NotNull(message = "文档ID不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long docId;
    private String docName;
    /**
     * 文档内切片序号
     */
    @NotNull(message = "文档内切片序号不能为空", groups = { AddGroup.class, EditGroup.class })
    private Integer chunkNo;
    /**
     * 切片标题
     */
    private String chunkTitle;
    /**
     * 切片内容
     */
    @NotBlank(message = "切片内容不能为空", groups = { AddGroup.class, EditGroup.class })
    private String content;
    /**
     * 切片内容Hash
     */
    private String contentHash;
    /**
     * Token数量
     */
    private Integer tokenCount;
    /**
     * 字符数量
     */
    private Integer charCount;
    /**
     * 页码，PDF/Word 可用
     */
    private Integer pageNo;
    /**
     * 章节标题
     */
    private String sectionTitle;
    /**
     * 在文档文本中的起始位置
     */
    private Integer startOffset;
    /**
     * 在文档文本中的结束位置
     */
    private Integer endOffset;
    /**
     * 向量化状态：PENDING-待处理，EMBEDDING-处理中，SUCCESS-成功，FAILED-失败
     */
    @NotBlank(message = "向量化状态：PENDING-待处理，EMBEDDING-处理中，SUCCESS-成功，FAILED-失败不能为空", groups = { AddGroup.class, EditGroup.class })
    private String embeddingStatus;
    /**
     * 默认向量记录ID
     */
    private Long embeddingId;
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
