package com.yuan.ai.domain.bo;

import com.yuan.ai.domain.KbEmbedding;
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
 * 知识库向量元数据表业务对象 kb_embedding
 *
 * @author yuan
 * @date Fri May 01 16:08:40 CST 2026
 */
@Data

@AutoMapper(target = KbEmbedding.class, reverseConvertGenerate = false)
public class KbEmbeddingBo implements Serializable {

    private Long embeddingId;

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
     * 切片ID
     */
    @NotNull(message = "切片ID不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long chunkId;
    /**
     * 向量模型ID，对应 llm_model.model_id
     */
    @NotNull(message = "向量模型ID，对应 llm_model.model_id不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long modelId;
    /**
     * 向量模型编码
     */
    @NotBlank(message = "向量模型编码不能为空", groups = { AddGroup.class, EditGroup.class })
    private String modelCode;
    /**
     * 向量库类型：QDRANT/MILVUS/ELASTICSEARCH/PGVECTOR
     */
    @NotBlank(message = "向量库类型：QDRANT/MILVUS/ELASTICSEARCH/PGVECTOR不能为空", groups = { AddGroup.class, EditGroup.class })
    private String vectorStore;
    /**
     * 向量集合名称
     */
    private String collectionName;
    /**
     * 向量库中的向量ID
     */
    @NotBlank(message = "向量库中的向量ID不能为空", groups = { AddGroup.class, EditGroup.class })
    private String vectorId;
    /**
     * 向量维度
     */
    @NotNull(message = "向量维度不能为空", groups = { AddGroup.class, EditGroup.class })
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
     * 状态：SUCCESS-成功，FAILED-失败，DISABLED-禁用
     */
    @NotBlank(message = "状态：SUCCESS-成功，FAILED-失败，DISABLED-禁用不能为空", groups = { AddGroup.class, EditGroup.class })
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
