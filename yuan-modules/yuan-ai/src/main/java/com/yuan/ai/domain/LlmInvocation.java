package com.yuan.ai.domain;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.io.Serializable;

/**
 * llm_invocation对象 llm_invocation
 *
 * @author yuan
 * @date Thu Feb 26 21:44:25 CST 2026
 */
@Data
@TableName("llm_invocation")
public class LlmInvocation implements Serializable {


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
     * traceId
     */
    private String traceId;

    private Long endpointId;

    private Long providerId;

    /**
     * modelName
     */
    private String modelName;

    /**
     * conversationId
     */
    private Long conversationId;

    /**
     * messageId
     */
    private Long messageId;

    /**
     * requestJson
     */
    private String requestJson;

    /**
     * responseText
     */
    private String responseText;

    /**
     * responseJson
     */
    private String responseJson;

    /**
     * tokenIn
     */
    private Integer tokenIn;

    /**
     * tokenOut
     */
    private Integer tokenOut;

    /**
     * costAmount
     */
    private BigDecimal costAmount;

    /**
     * latencyMs
     */
    private Integer latencyMs;

    /**
     * SUCCESS/FAILED
     */
    private String status;

    /**
     * errorMsg
     */
    private String errorMsg;

    /**
     * createTime
     */
    private LocalDateTime createTime;


}
