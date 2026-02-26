package com.yuan.ai.domain;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import java.io.Serializable;

/**
 * llm_route_rule对象 llm_route_rule
 *
 * @author yuan
 * @date Thu Feb 26 21:44:32 CST 2026
 */
@Data
@TableName("llm_route_rule")
public class LlmRouteRule implements Serializable {


    /**
     * id
     */
        @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * tenantId
     */
    private String tenantId;

    /**
     * ruleName
     */
    private String ruleName;

    /**
     * {"enableThinking":true,"stream":true}
     */
    private String matchJson;

    /**
     * ["tenantA_openai_4o","tenantA_deepseek_r1"]
     */
    private String candidateEndpoints;

    /**
     * PRIORITY/COST_MIN/LATENCY_MIN
     */
    private String strategy;

    /**
     * enabled
     */
    private Integer enabled;

    /**
     * priority
     */
    private Integer priority;


}
