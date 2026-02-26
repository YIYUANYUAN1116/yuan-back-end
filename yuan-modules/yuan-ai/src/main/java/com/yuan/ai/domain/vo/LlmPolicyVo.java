package com.yuan.ai.domain.vo;

    import java.io.Serializable;
import com.yuan.ai.domain.LlmPolicy;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;

import java.util.Date;


/**
 * llm_policy视图对象 llm_policy
 *
 * @author yuan
 * @date Thu Feb 26 21:44:28 CST 2026
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = LlmPolicy.class)
public class LlmPolicyVo implements Serializable {

    private Long id;
    /**
     * tenantId
     */
    @ExcelProperty(value = "tenantId")
    private String tenantId;
    /**
     * TENANT/USER/APP
     */
    @ExcelProperty(value = "TENANT/USER/APP")
    private String scopeType;
    /**
     * scopeId
     */
    @ExcelProperty(value = "scopeId")
    private String scopeId;
    /**
     * dailyCalls
     */
    @ExcelProperty(value = "dailyCalls")
    private Integer dailyCalls;
    /**
     * dailyTokens
     */
    @ExcelProperty(value = "dailyTokens")
    private Integer dailyTokens;
    /**
     * concurrency
     */
    @ExcelProperty(value = "concurrency")
    private Integer concurrency;
    /**
     * allowEndpoints
     */
    @ExcelProperty(value = "allowEndpoints")
    private String allowEndpoints;
    /**
     * enabled
     */
    @ExcelProperty(value = "enabled")
    private Integer enabled;

}
