package com.yuan.ai.domain.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.yuan.ai.domain.LlmModel;
import io.github.linpeilie.annotations.AutoMapper;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;


/**
 * llm_model视图对象 llm_model
 *
 * @author yuan
 * @date Thu Feb 26 21:44:17 CST 2026
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = LlmModel.class)
public class LlmModelVo implements Serializable {
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
     private Long id;

    private String endpointKey;

    /**
     * providerCode
     */
    @ExcelProperty(value = "providerCode")
    private String providerCode;
    /**
     * remote model name, e.g. gpt-4o-mini
     */
    @ExcelProperty(value = "remote model name, e.g. gpt-4o-mini")
    private String modelName;
    /**
     * displayName
     */
    @ExcelProperty(value = "displayName")
    private String displayName;
    /**
     * {"stream":true,"json":true,"tools":true,"vision":false,"thinking":false}
     */
    @ExcelProperty(value = "capabilityJson")
    private String capabilityJson;
    /**
     * contextWindow
     */
    @ExcelProperty(value = "contextWindow")
    private Integer contextWindow;
    /**
     * enabled
     */
    @ExcelProperty(value = "status")
    private String status;

    @ExcelProperty(value = "createTime")
    private LocalDateTime createTime;
    /**
     * updateTime
     */
    @ExcelProperty(value = "updateTime")
    private LocalDateTime updateTime;

}
