package com.yuan.ai.api.dto;

import lombok.Data;

import java.util.Map;

@Data
public class AiTemplateExecuteRequest {

    /**
     * 租户ID
     */
    private String tenantId;

    /**
     * 业务类型，例如 WORKFLOW / OA / SYSTEM
     */
    private String bizType;

    /**
     * 场景码，例如 WORKFLOW_NODE / CHAT_DEFAULT
     */
    private String sceneCode;

    /**
     * 模板编码
     */
    private String templateCode;

    /**
     * 模型编码，可为空；为空则由AI模块自动选择
     */
    private String modelCode;

    /**
     * 业务ID
     */
    private String bizId;

    /**
     * 操作人ID
     */
    private Long operatorId;

    /**
     * 模板参数
     */
    private Map<String, Object> params;

    /**
     * 是否期望 JSON 输出
     */
    private Boolean jsonOutput;

    /**
     * 超时
     */
    private Integer timeoutMs;
}