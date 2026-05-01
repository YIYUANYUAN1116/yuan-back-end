package com.yuan.ai.domain.bo;

import com.yuan.ai.domain.KbBase;
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
 * 知识库主表业务对象 kb_base
 *
 * @author yuan
 * @date Fri May 01 16:08:21 CST 2026
 */
@Data

@AutoMapper(target = KbBase.class, reverseConvertGenerate = false)
public class KbBaseBo implements Serializable {

    private Long kbId;

    /**
     * 租户ID
     */
    private String tenantId;
    /**
     * 知识库编码
     */
    @NotBlank(message = "知识库编码不能为空", groups = { AddGroup.class, EditGroup.class })
    private String kbCode;
    /**
     * 知识库名称
     */
    @NotBlank(message = "知识库名称不能为空", groups = { AddGroup.class, EditGroup.class })
    private String kbName;
    /**
     * 知识库描述
     */
    private String description;
    /**
     * 可见范围：PRIVATE-私有，TEAM-团队，TENANT-租户，PUBLIC-公开
     */
    @NotBlank(message = "可见范围：PRIVATE-私有，TEAM-团队，TENANT-租户，PUBLIC-公开不能为空", groups = { AddGroup.class, EditGroup.class })
    private String visibility;
    /**
     * 知识库负责人/拥有者ID
     */
    private String ownerId;
    /**
     * 默认向量模型ID，对应 llm_model.model_id
     */
    private Long embeddingModelId;
    /**
     * 默认切分策略
     */
    private String chunkStrategy;
    /**
     * 默认切片大小
     */
    private Integer chunkSize;
    /**
     * 默认切片重叠大小
     */
    private Integer chunkOverlap;
    /**
     * 状态：ENABLED-启用，DISABLED-禁用
     */
    @NotBlank(message = "状态：ENABLED-启用，DISABLED-禁用不能为空", groups = { AddGroup.class, EditGroup.class })
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
