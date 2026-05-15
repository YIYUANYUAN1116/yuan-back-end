package com.yuan.ai.domain;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import java.time.LocalDateTime;
import java.io.Serializable;

/**
 * 知识库向量元数据表对象 kb_embedding
 *
 * @author yuan
 * @date Fri May 01 16:08:40 CST 2026
 */
@Data
@TableName("kb_embedding")
public class KbEmbedding implements Serializable {


    /**
     * 向量记录ID
     */
        @TableId(value = "embedding_id", type = IdType.AUTO)
    private Long embeddingId;

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
     * 切片ID
     */
    private Long chunkId;

    /**
     * 向量模型ID，对应 llm_model.model_id
     */
    private Long modelId;

    /**
     * 向量模型编码
     */
    private String modelCode;

    /**
     * 向量库类型：QDRANT/MILVUS/ELASTICSEARCH/PGVECTOR
     */
    private String vectorStore;

    /**
     * 向量集合名称
     */
    private String collectionName;

    /**
     * 向量库中的向量ID
     */
    private String vectorId;

    /**
     * 向量维度
     */
    private Integer vectorDim;

    /**
     * 对应切片内容Hash
     */
    private String contentHash;

    /**
     * 向量化版本，用于重新向量化区分
     */
    private String embedVersion;

    /**
     * 状态：0-成功，1-失败，2-禁用
     */
    private String status;

    /**
     * 错误信息
     */
    private String errorMessage;

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
//        @TableLogic
//    private String delFlag;


}
