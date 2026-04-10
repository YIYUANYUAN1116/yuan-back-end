package com.yuan.ai.domain;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import java.io.Serializable;

/**
 * llm_policy对象 llm_policy
 *
 * @author yuan
 * @date Thu Feb 26 21:44:28 CST 2026
 */
@Data
@TableName("llm_policy")
public class LlmPolicy implements Serializable {


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
     * TENANT/USER/APP
     */
    private String scopeType;

    /**
     * scopeId
     */
    private String scopeId;

    /**
     * dailyCalls
     */
    private Integer dailyCalls;

    /**
     * dailyTokens
     */
    private Integer dailyTokens;

    /**
     * concurrency
     */
    private Integer concurrency;

    /**
     * allowEndpoints
     */
    private String allowEndpoints;

    /**
     * enabled
     */
    private Integer enabled;


}
