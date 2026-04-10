package com.yuan.ai.domain.dto;

import lombok.Data;

@Data
public class AiModelResolved {

    /**
     * 提供商编码
     */
    private String providerCode;

    /**
     * 模型编码
     */
    private String modelCode;

    /**
     * 凭证编码 / 凭证ID
     */
    private String credentialCode;

    /**
     * 命中来源：
     * REQUEST / TENANT_SCENE / PLATFORM_SCENE / TEMPLATE_DEFAULT / GLOBAL_DEFAULT
     */
    private String source;
}