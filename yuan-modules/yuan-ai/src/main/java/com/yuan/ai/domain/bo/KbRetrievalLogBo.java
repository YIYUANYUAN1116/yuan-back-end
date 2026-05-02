package com.yuan.ai.domain.bo;

import com.yuan.ai.domain.KbRetrievalLog;
import com.yuan.common.core.validate.AddGroup;
import com.yuan.common.core.validate.EditGroup;
import io.github.linpeilie.annotations.AutoMapper;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 知识库检索日志表业务对象 kb_retrieval_log
 *
 * @author yuan
 * @date Fri May 01 16:08:45 CST 2026
 */
@Data

@AutoMapper(target = KbRetrievalLog.class, reverseConvertGenerate = false)
public class KbRetrievalLogBo implements Serializable {

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
    @NotBlank(message = "原始问题不能为空", groups = { AddGroup.class, EditGroup.class })
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
    @NotNull(message = "召回数量不能为空", groups = { AddGroup.class, EditGroup.class })
    private Integer topK;
    /**
     * 最低相似度分数
     */
    private BigDecimal minScore;
    /**
     * 命中数量
     */
    @NotNull(message = "命中数量不能为空", groups = { AddGroup.class, EditGroup.class })
    private Integer hitCount;
    /**
     * 最终注入上下文的数量
     */
    @NotNull(message = "最终注入上下文的数量不能为空", groups = { AddGroup.class, EditGroup.class })
    private Integer usedCount;
    /**
     * 检索耗时，毫秒
     */
    private Integer latencyMs;
    /**
     * 状态：SUCCESS-成功，FAILED-失败
     */
    @NotBlank(message = "状态：SUCCESS-成功，FAILED-失败不能为空", groups = { AddGroup.class, EditGroup.class })
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
