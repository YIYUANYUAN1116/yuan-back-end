package com.yuan.ai.domain.bo;

import com.yuan.ai.domain.KbRetrievalHit;
import com.yuan.core.domain.BaseEntity;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import lombok.EqualsAndHashCode;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.io.Serializable;
import com.yuan.common.core.validate.AddGroup;
import com.yuan.common.core.validate.EditGroup;
import java.time.LocalDateTime;
import java.io.Serializable;
import com.yuan.common.core.validate.AddGroup;
import com.yuan.common.core.validate.EditGroup;
import java.io.Serializable;
import java.io.Serializable;
import com.yuan.common.core.validate.AddGroup;
import com.yuan.common.core.validate.EditGroup;

/**
 * 知识库检索命中明细表业务对象 kb_retrieval_hit
 *
 * @author yuan
 * @date Fri May 01 16:08:50 CST 2026
 */
@Data

@AutoMapper(target = KbRetrievalHit.class, reverseConvertGenerate = false)
public class KbRetrievalHitBo implements Serializable {

    private Long hitId;

    /**
     * 租户ID
     */
    private String tenantId;
    /**
     * 检索日志ID
     */
    @NotNull(message = "检索日志ID不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long logId;
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
     * 召回排序
     */
    @NotNull(message = "召回排序不能为空", groups = { AddGroup.class, EditGroup.class })
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
    @NotNull(message = "是否最终注入Prompt：0-否，1-是不能为空", groups = { AddGroup.class, EditGroup.class })
    private Integer usedInPrompt;
    /**
     * 命中内容预览
     */
    private String contentPreview;
    /**
     * 状态：SUCCESS-成功，DISCARDED-丢弃
     */
    @NotBlank(message = "状态：SUCCESS-成功，DISCARDED-丢弃不能为空", groups = { AddGroup.class, EditGroup.class })
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
