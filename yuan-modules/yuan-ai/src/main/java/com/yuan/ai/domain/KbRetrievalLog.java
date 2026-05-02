package com.yuan.ai.domain;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.io.Serializable;

/**
 * 知识库检索日志表对象 kb_retrieval_log
 *
 * @author yuan
 * @date Fri May 01 16:08:45 CST 2026
 */
@Data
@TableName("kb_retrieval_log")
public class KbRetrievalLog implements Serializable {


    /**
     * 检索日志ID
     */
        @TableId(value = "log_id", type = IdType.AUTO)
    private Long logId;

    /**
     * 租户ID
     */
    private String tenantId;

    /**
     * 知识库ID，单知识库检索时使用
     */
    private Long kbId;

    /**
     * 知识库ID列表，多知识库检索时使用，逗号分隔
     */
    private String kbIds;


    /**
     * 对话ID
     */
    private Long conversationId;

    /**
     * 用户消息ID
     */
    private Long messageId;

    /**
     * 模型调用记录ID
     */
    private Long invocationId;

    /**
     * 原始问题
     */
    private String queryText;

    /**
     * 改写后的检索问题
     */
    private String rewriteQuery;

    /**
     * 问题向量化模型ID
     */
    private Long embeddingModelId;

    /**
     * 召回数量
     */
    private Integer topK;

    /**
     * 最低相似度分数
     */
    private BigDecimal minScore;

    /**
     * 命中数量
     */
    private Integer hitCount;

    /**
     * 最终注入上下文的数量
     */
    private Integer usedCount;

    /**
     * 检索耗时，毫秒
     */
    private Integer latencyMs;

    /**
     * 状态：SUCCESS-成功，FAILED-失败
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
        @TableLogic
    private String delFlag;


}
