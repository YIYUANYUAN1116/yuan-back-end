package com.yuan.ai.domain.bo;

import com.yuan.ai.domain.LlmInvocation;
import com.yuan.core.domain.BaseEntity;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import lombok.EqualsAndHashCode;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.io.Serializable;
import com.yuan.common.core.validate.AddGroup;
import com.yuan.common.core.validate.EditGroup;
import java.time.LocalDateTime;
import java.io.Serializable;
import com.yuan.common.core.validate.AddGroup;
import com.yuan.common.core.validate.EditGroup;
import java.io.Serializable;
import java.io.Serializable;
import com.yuan.common.core.validate.AddGroup;
import com.yuan.common.core.validate.EditGroup;

/**
 * llm_invocation业务对象 llm_invocation
 *
 * @author yuan
 * @date Thu Feb 26 21:44:25 CST 2026
 */
@Data

@AutoMapper(target = LlmInvocation.class, reverseConvertGenerate = false)
public class LlmInvocationBo implements Serializable {

    private Long id;

    /**
     * tenantId
     */
    @NotBlank(message = "tenantId不能为空", groups = { AddGroup.class, EditGroup.class })
    private String tenantId;
    /**
     * traceId
     */
    @NotBlank(message = "traceId不能为空", groups = { AddGroup.class, EditGroup.class })
    private String traceId;
    /**
     * endpointKey
     */
    @NotBlank(message = "endpointKey不能为空", groups = { AddGroup.class, EditGroup.class })
    private String endpointKey;
    /**
     * providerCode
     */
    @NotBlank(message = "providerCode不能为空", groups = { AddGroup.class, EditGroup.class })
    private String providerCode;
    /**
     * modelName
     */
    @NotBlank(message = "modelName不能为空", groups = { AddGroup.class, EditGroup.class })
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
    @NotBlank(message = "requestJson不能为空", groups = { AddGroup.class, EditGroup.class })
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
    @NotBlank(message = "SUCCESS/FAILED不能为空", groups = { AddGroup.class, EditGroup.class })
    private String status;
    /**
     * errorMsg
     */
    private String errorMsg;
    /**
     * createTime
     */
    @NotNull(message = "createTime不能为空", groups = { AddGroup.class, EditGroup.class })
    private LocalDateTime createTime;

}
