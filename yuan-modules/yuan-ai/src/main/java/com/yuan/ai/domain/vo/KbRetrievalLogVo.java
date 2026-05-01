package com.yuan.ai.domain.vo;

    import java.math.BigDecimal;
    import java.time.LocalDateTime;
    import java.io.Serializable;
import com.yuan.ai.domain.KbRetrievalLog;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;

import java.util.Date;


/**
 * 知识库检索日志表视图对象 kb_retrieval_log
 *
 * @author yuan
 * @date Fri May 01 16:08:45 CST 2026
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = KbRetrievalLog.class)
public class KbRetrievalLogVo implements Serializable {

    private Long logId;
    /**
     * 租户ID
     */
    @ExcelProperty(value = "租户ID")
    private String tenantId;
    /**
     * 知识库ID，单知识库检索时使用
     */
    @ExcelProperty(value = "知识库ID，单知识库检索时使用")
    private Long kbId;
    /**
     * 知识库ID列表，多知识库检索时使用，逗号分隔
     */
    @ExcelProperty(value = "知识库ID列表，多知识库检索时使用，逗号分隔")
    private String kbIds;
    /**
     * 聊天会话ID
     */
    @ExcelProperty(value = "聊天会话ID")
    private Long sessionId;
    /**
     * 对话ID
     */
    @ExcelProperty(value = "对话ID")
    private Long conversationId;
    /**
     * 用户消息ID
     */
    @ExcelProperty(value = "用户消息ID")
    private Long messageId;
    /**
     * 模型调用记录ID
     */
    @ExcelProperty(value = "模型调用记录ID")
    private Long invocationId;
    /**
     * 原始问题
     */
    @ExcelProperty(value = "原始问题")
    private String queryText;
    /**
     * 改写后的检索问题
     */
    @ExcelProperty(value = "改写后的检索问题")
    private String rewriteQuery;
    /**
     * 问题向量化模型ID
     */
    @ExcelProperty(value = "问题向量化模型ID")
    private Long embeddingModelId;
    /**
     * 召回数量
     */
    @ExcelProperty(value = "召回数量")
    private Integer topK;
    /**
     * 最低相似度分数
     */
    @ExcelProperty(value = "最低相似度分数")
    private BigDecimal minScore;
    /**
     * 命中数量
     */
    @ExcelProperty(value = "命中数量")
    private Integer hitCount;
    /**
     * 最终注入上下文的数量
     */
    @ExcelProperty(value = "最终注入上下文的数量")
    private Integer usedCount;
    /**
     * 检索耗时，毫秒
     */
    @ExcelProperty(value = "检索耗时，毫秒")
    private Integer latencyMs;
    /**
     * 状态：SUCCESS-成功，FAILED-失败
     */
    @ExcelProperty(value = "状态：SUCCESS-成功，FAILED-失败")
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
