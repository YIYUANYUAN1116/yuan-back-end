package com.yuan.ai.domain;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import java.time.LocalDateTime;
import java.io.Serializable;

/**
 * 知识库文档切片表对象 kb_chunk
 *
 * @author yuan
 * @date Fri May 01 16:08:37 CST 2026
 */
@Data
@TableName("kb_chunk")
public class KbChunk implements Serializable {


    /**
     * 切片ID
     */
        @TableId(value = "chunkId", type = IdType.AUTO)
    private Long chunkId;

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
     * 文档内切片序号
     */
    private Integer chunkNo;

    /**
     * 切片标题
     */
    private String chunkTitle;

    /**
     * 切片内容
     */
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
    private String embeddingStatus;

    /**
     * 默认向量记录ID
     */
    private Long embeddingId;

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
