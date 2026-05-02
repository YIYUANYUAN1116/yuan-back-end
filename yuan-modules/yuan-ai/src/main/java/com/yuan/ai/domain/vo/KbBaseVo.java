package com.yuan.ai.domain.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.yuan.ai.domain.KbBase;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;


/**
 * 知识库主表视图对象 kb_base
 *
 * @author yuan
 * @date Fri May 01 16:08:21 CST 2026
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = KbBase.class)
public class KbBaseVo implements Serializable {

    private Long kbId;
    /**
     * 租户ID
     */
    @ExcelProperty(value = "租户ID")
    private String tenantId;
    /**
     * 知识库编码
     */
    @ExcelProperty(value = "知识库编码")
    private String kbCode;
    /**
     * 知识库名称
     */
    @ExcelProperty(value = "知识库名称")
    private String kbName;
    /**
     * 知识库描述
     */
    @ExcelProperty(value = "知识库描述")
    private String description;
    /**
     * 可见范围：PRIVATE-私有，TEAM-团队，TENANT-租户，PUBLIC-公开
     */
    @ExcelProperty(value = "可见范围：PRIVATE-私有，TEAM-团队，TENANT-租户，PUBLIC-公开")
    private String visibility;
    /**
     * 知识库负责人/拥有者ID
     */
    @ExcelProperty(value = "知识库负责人/拥有者ID")
    private String ownerId;
    /**
     * 默认向量模型ID，对应 llm_model.model_id
     */
    @ExcelProperty(value = "默认向量模型ID，对应 llm_model.model_id")
    private Long embeddingModelId;

    private String embeddingModel;
    /**
     * 默认切分策略
     */
    @ExcelProperty(value = "默认切分策略")
    private String chunkStrategy;
    /**
     * 默认切片大小
     */
    @ExcelProperty(value = "默认切片大小")
    private Integer chunkSize;
    /**
     * 默认切片重叠大小
     */
    @ExcelProperty(value = "默认切片重叠大小")
    private Integer chunkOverlap;
    /**
     * 状态：ENABLED-启用，DISABLED-禁用
     */
    @ExcelProperty(value = "状态：ENABLED-启用，DISABLED-禁用")
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

}
