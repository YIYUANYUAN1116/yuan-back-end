package com.yuan.ai.domain.vo;

    import java.time.LocalDateTime;
    import java.io.Serializable;
import com.yuan.ai.domain.KbEmbedding;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;

import java.util.Date;


/**
 * 知识库向量元数据表视图对象 kb_embedding
 *
 * @author yuan
 * @date Fri May 01 16:08:40 CST 2026
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = KbEmbedding.class)
public class KbEmbeddingVo implements Serializable {

    private Long embeddingId;
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
     * 切片ID
     */
    @ExcelProperty(value = "切片ID")
    private Long chunkId;
    /**
     * 向量模型ID，对应 llm_model.model_id
     */
    @ExcelProperty(value = "向量模型ID，对应 llm_model.model_id")
    private Long modelId;
    /**
     * 向量模型编码
     */
    @ExcelProperty(value = "向量模型编码")
    private String modelCode;
    /**
     * 向量库类型：QDRANT/MILVUS/ELASTICSEARCH/PGVECTOR
     */
    @ExcelProperty(value = "向量库类型：QDRANT/MILVUS/ELASTICSEARCH/PGVECTOR")
    private String vectorStore;
    /**
     * 向量集合名称
     */
    @ExcelProperty(value = "向量集合名称")
    private String collectionName;
    /**
     * 向量库中的向量ID
     */
    @ExcelProperty(value = "向量库中的向量ID")
    private String vectorId;
    /**
     * 向量维度
     */
    @ExcelProperty(value = "向量维度")
    private Integer vectorDim;
    /**
     * 对应切片内容Hash
     */
    @ExcelProperty(value = "对应切片内容Hash")
    private String contentHash;
    /**
     * 向量化版本，用于重新向量化区分
     */
    @ExcelProperty(value = "向量化版本，用于重新向量化区分")
    private String embedVersion;
    /**
     * 状态：SUCCESS-成功，FAILED-失败，DISABLED-禁用
     */
    @ExcelProperty(value = "状态：SUCCESS-成功，FAILED-失败，DISABLED-禁用")
    private String status;
    /**
     * 错误信息
     */
    @ExcelProperty(value = "错误信息")
    private String errorMessage;
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
