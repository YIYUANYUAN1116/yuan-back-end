package com.yuan.ai.domain.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.yuan.ai.domain.KbRetrievalHit;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;


/**
 * 知识库检索命中明细表视图对象 kb_retrieval_hit
 *
 * @author yuan
 * @date Fri May 01 16:08:50 CST 2026
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = KbRetrievalHit.class)
public class KbRetrievalHitVo implements Serializable {

    private Long hitId;
    /**
     * 租户ID
     */
    @ExcelProperty(value = "租户ID")
    private String tenantId;
    /**
     * 检索日志ID
     */
    @ExcelProperty(value = "检索日志ID")
    private Long logId;
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
     * 召回排序
     */
    @ExcelProperty(value = "召回排序")
    private Integer rankNo;
    /**
     * 向量相似度分数
     */
    @ExcelProperty(value = "向量相似度分数")
    private BigDecimal score;
    /**
     * 重排序分数
     */
    @ExcelProperty(value = "重排序分数")
    private BigDecimal rerankScore;
    /**
     * 是否最终注入Prompt：0-否，1-是
     */
    @ExcelProperty(value = "是否最终注入Prompt：0-否，1-是")
    private Integer usedInPrompt;
    /**
     * 命中内容预览
     */
    @ExcelProperty(value = "命中内容预览")
    private String contentPreview;
    /**
     * 状态：SUCCESS-成功，DISCARDED-丢弃
     */
    @ExcelProperty(value = "状态：SUCCESS-成功，DISCARDED-丢弃")
    private String status;
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


    /*********************************helper************************************/
    private String completeContent;
}
