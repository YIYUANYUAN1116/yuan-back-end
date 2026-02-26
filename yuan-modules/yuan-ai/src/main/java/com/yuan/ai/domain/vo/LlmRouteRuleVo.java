package com.yuan.ai.domain.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.yuan.ai.domain.LlmRouteRule;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;

import java.io.Serializable;


/**
 * llm_route_rule视图对象 llm_route_rule
 *
 * @author yuan
 * @date Thu Feb 26 21:44:32 CST 2026
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = LlmRouteRule.class)
public class LlmRouteRuleVo implements Serializable {

    private Long id;
    /**
     * tenantId
     */
    @ExcelProperty(value = "tenantId")
    private String tenantId;
    /**
     * ruleName
     */
    @ExcelProperty(value = "ruleName")
    private String ruleName;
    /**
     * {"enableThinking":true,"stream":true}
     */
    @ExcelProperty(value = "matchJson")
    private String matchJson;
    /**
     * ["tenantA_openai_4o","tenantA_deepseek_r1"]
     */
    @ExcelProperty(value = "candidateEndpoints")
    private String candidateEndpoints;
    /**
     * PRIORITY/COST_MIN/LATENCY_MIN
     */
    @ExcelProperty(value = "PRIORITY/COST_MIN/LATENCY_MIN")
    private String strategy;
    /**
     * enabled
     */
    @ExcelProperty(value = "enabled")
    private Integer enabled;
    /**
     * priority
     */
    @ExcelProperty(value = "priority")
    private Integer priority;

}
