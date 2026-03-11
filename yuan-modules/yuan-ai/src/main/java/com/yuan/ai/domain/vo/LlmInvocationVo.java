package com.yuan.ai.domain.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.yuan.ai.domain.LlmInvocation;
import io.github.linpeilie.annotations.AutoMapper;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;


/**
 * llm_invocation视图对象 llm_invocation
 *
 * @author yuan
 * @date Thu Feb 26 21:44:25 CST 2026
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = LlmInvocation.class)
public class LlmInvocationVo implements Serializable {
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
     private Long id;
    /**
     * tenantId
     */
    @ExcelProperty(value = "tenantId")
    private String tenantId;
    /**
     * traceId
     */
    @ExcelProperty(value = "traceId")
    private String traceId;
    /**
     * endpointKey
     */
    @ExcelProperty(value = "endpointKey")
    private String endpointKey;
    /**
     * providerCode
     */
    @ExcelProperty(value = "providerCode")
    private String providerCode;
    /**
     * modelName
     */
    @ExcelProperty(value = "modelName")
    private String modelName;
    /**
     * conversationId
     */
    @ExcelProperty(value = "conversationId")
    private Long conversationId;
    /**
     * messageId
     */
    @ExcelProperty(value = "messageId")
    private Long messageId;
    /**
     * requestJson
     */
    @ExcelProperty(value = "requestJson")
    private String requestJson;
    /**
     * responseText
     */
    @ExcelProperty(value = "responseText")
    private String responseText;
    /**
     * responseJson
     */
    @ExcelProperty(value = "responseJson")
    private String responseJson;
    /**
     * tokenIn
     */
    @ExcelProperty(value = "tokenIn")
    private Integer tokenIn;
    /**
     * tokenOut
     */
    @ExcelProperty(value = "tokenOut")
    private Integer tokenOut;
    /**
     * costAmount
     */
    @ExcelProperty(value = "costAmount")
    private BigDecimal costAmount;
    /**
     * latencyMs
     */
    @ExcelProperty(value = "latencyMs")
    private Integer latencyMs;
    /**
     * SUCCESS/FAILED
     */
    @ExcelProperty(value = "SUCCESS/FAILED")
    private String status;
    /**
     * errorMsg
     */
    @ExcelProperty(value = "errorMsg")
    private String errorMsg;
    /**
     * createTime
     */
    @ExcelProperty(value = "createTime")
    private LocalDateTime createTime;

}
