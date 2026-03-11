package com.yuan.ai.domain.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.yuan.ai.domain.LlmProvider;
import io.github.linpeilie.annotations.AutoMapper;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;


/**
 * llm_provider视图对象 llm_provider
 *
 * @author yuan
 * @date Thu Feb 26 21:44:12 CST 2026
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = LlmProvider.class)
public class LlmProviderVo implements Serializable {
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
    private Long id;
    /**
     * OPENAI_COMPAT/AZURE/OLLAMA/SELF_HOST
     */
    @ExcelProperty(value = "OPENAI_COMPAT/AZURE/OLLAMA/SELF_HOST")
    private String code;
    /**
     * name
     */
    @ExcelProperty(value = "name")
    private String name;
    /**
     * openai_compat/azure/ollama/...
     */
    @ExcelProperty(value = "openai_compat/azure/ollama/...")
    private String protocol;
    /**
     * createTime
     */
    @ExcelProperty(value = "createTime")
    private LocalDateTime createTime;
    private String remark;
    private Long createBy;
    private Long updateBy;
    private Date updateTime;
}
