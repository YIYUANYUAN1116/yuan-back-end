package com.yuan.ai.api.dto;

import lombok.Data;

import java.util.Map;

@Data
public class AiTemplateExecuteRequest {

    /**
     * 模板编码
     */
    private String templateCode;

    /**
     * 直接传模板内容，可选；有值时优先使用
     */
    private String templateContent;

    /**
     * 模板变量
     */
    private Map<String, Object> variables;

    /**
     * 系统提示词
     */
    private String systemPrompt;

    /**
     * 逻辑模型编码，可选
     */
    private String endpointKey;

    /**
     * 是否自动选模型
     */
    private Boolean autoSelectModel = Boolean.TRUE;

    /**
     * 场景编码
     */
    private String sceneCode;

    /**
     * 业务类型
     */
    private String bizType;

    /**
     * 业务ID
     */
    private Long bizId;

    /**
     * 操作人
     */
    private Long userId;

    /**
     * 应用ID
     */
    private String appId;

    /**
     * traceId
     */
    private String traceId;

    /**
     * 是否保留 chat message，workflow 通常 false
     */
    private Boolean persistChatMessage = Boolean.FALSE;

    /**
     * 是否开启思维链式能力之类的模型参数
     */
    private Boolean enableThinking;

    /**
     * 扩展参数
     */
    private Map<String, Object> ext;
}