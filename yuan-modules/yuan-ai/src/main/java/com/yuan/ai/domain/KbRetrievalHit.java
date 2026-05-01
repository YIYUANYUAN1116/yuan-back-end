package com.yuan.ai.domain;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.io.Serializable;

/**
 * 知识库检索命中明细表对象 kb_retrieval_hit
 *
 * @author yuan
 * @date Fri May 01 16:08:50 CST 2026
 */
@Data
@TableName("kb_retrieval_hit")
public class KbRetrievalHit implements Serializable {


    /**
     * 命中记录ID
     */
        @TableId(value = "hitId", type = IdType.AUTO)
    private Long hitId;

    /**
     * 租户ID
     */
    private String tenantId;

    /**
     * 检索日志ID
     */
    private Long logId;

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
     * 召回排序
     */
    private Integer rankNo;

    /**
     * 向量相似度分数
     */
    private BigDecimal score;

    /**
     * 重排序分数
     */
    private BigDecimal rerankScore;

    /**
     * 是否最终注入Prompt：0-否，1-是
     */
    private Integer usedInPrompt;

    /**
     * 命中内容预览
     */
    private String contentPreview;

    /**
     * 状态：SUCCESS-成功，DISCARDED-丢弃
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
